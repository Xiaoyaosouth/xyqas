package controller;

import domain.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.ReplyService;

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

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    @RequestMapping("publishReply.do")
    public ModelAndView addReply(Reply reply){
        ModelAndView mv = new ModelAndView();
        // 处理参数
        int userid = Integer.valueOf(request.getParameter("user_id"));
        int tipid = Integer.valueOf(request.getParameter("tip_id"));
        System.out.println("从前台获取的回复用户id为：" + userid);
        System.out.println("从前台获取的贴子id为：" + tipid);
        System.out.println("从前台获取的回复内容为：" + reply.getReply_content());
        reply.setUser_id(userid);
        reply.setTip_id(tipid);
        // 初始化回贴时间
        Date date = new Date();
        reply.setReply_publishTime(date);
        String resultStr = replyService.addReply(reply);
        request.setAttribute("myInfo", resultStr);
        mv.setViewName("redirect:showTip.do?tipId=" + tipid);
        return mv;
    }
}
