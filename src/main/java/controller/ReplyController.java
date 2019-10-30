package controller;

import domain.Reply;
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
        // 处理参数
        int userid = Integer.valueOf(request.getParameter("user_id"));
        int tipid = Integer.valueOf(request.getParameter("tip_id"));
        reply.setUser_id(userid);
        reply.setTip_id(tipid);
        /**
        // 处理页面弹出用户输入的脚本
        StringBuffer sb = new StringBuffer();
        sb.append("<c:out value=\"");
        sb.append(reply.getReply_content());
        sb.append("\"></c:out>");
        reply.setReply_content(sb.toString());
         */
        // 回复产生时间
        Date date = new Date();
        reply.setReply_publishTime(date);
        String resultStr = replyService.addReply(reply);
        // 刷新贴子更新时间
        tipService.updateModifyTimeByTipId(tipid, date);
        // 更新贴子回复数
        tipService.updateRepliesByTipId(tipid);
        request.setAttribute("myInfo", resultStr);
        mv.setViewName("redirect:showTip.do?tipId=" + tipid);
        return mv;
    }
}
