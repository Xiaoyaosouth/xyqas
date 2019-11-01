package controller;

import domain.Reply;
import domain.Tip;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.ReplyService;
import service.TipService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 回贴控制器
 */
@Controller
public class ReplyController {
    @Resource
    private ReplyService replyService;

    @Resource
    private TipService tipService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    /**
     * 回贴控制
     * @param reply 回复实体对象
     * @return
     */
    @RequestMapping("publishReply.do")
    public ModelAndView addReply(Reply reply){
        ModelAndView mv = new ModelAndView();
        String resultStr = null;
        // 处理参数
        User user = (User) session.getAttribute("USER");
        Integer tipId = Integer.valueOf(request.getParameter("tip_id"));
        try{
            if (tipId == null || user == null){
                throw new Exception("user或tip_id不存在");
            }else {
                // 用户登录、状态正常才可以发回复
                if (user.getUser_status() == 0){
                    // 将user对象包装到reply
                    reply.setUser(user);
                    Tip tmpTip = new Tip();
                    tmpTip.setTip_id(tipId);
                    // 将tip对象包装到reply
                    reply.setTip(tmpTip);
                    // 生成回复时间
                    Date date = new Date();
                    reply.setReply_publishTime(date);
                    reply.setReply_modifyTime(date);
                    // 调用业务层
                    resultStr = replyService.addReply(reply);
                }else {
                    resultStr = new String("回复失败：用户状态不正常！");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("myInfo", resultStr);
        mv.setViewName("redirect:showTip.do?tipId=" + tipId);
        return mv;
    }
}
