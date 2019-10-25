package controller;

import domain.Forum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.*;

import javax.annotation.Resource;
import javax.servlet.http.*;
import java.util.List;

@Controller
public class ForumController {
    @Resource
    private ForumService forumService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    /**
     * 获取更新数据后的版块信息
     * @return List<Forum>
     */
    private List<Forum> getUpdateFourmData(){
        List<Forum> forumList = forumService.getAllForum();
        return forumList;
    }

    /**
     * 修改大板块名控制
     * @param forum
     * @return
     */
    @RequestMapping("modifyForumInfo.do")
    public ModelAndView modifyForumName(Forum forum){
        ModelAndView mv = new ModelAndView();
        String resultStr = forumService.modifyForum(forum);
        request.setAttribute("myInfo", resultStr);
        request.setAttribute("forums", this.getUpdateFourmData());
        mv.setViewName("forumManage.jsp");
        return mv;
    }

    /**
     * 跳转到修改大板块的页面
     * @param forumId
     * @return
     */
    @RequestMapping("toModifyForumPage.do")
    public ModelAndView toModifyForumPage(int forumId){
        ModelAndView mv = new ModelAndView();
        Forum forum = forumService.getForumByForumId(forumId);
        request.setAttribute("forum", forum);
        mv.setViewName("modifyForum.jsp");
        return mv;
    }
}
