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
     * 获取所有版块信息
     * @return List<Forum>
     */
    private List<Forum> getAllFourms(){
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
        request.setAttribute("forums", this.getAllFourms());
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

    /**
     * 跳转到添加版块页面
     * @return
     */
    @RequestMapping("toAddForumPage.do")
    public ModelAndView toAddForumPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("addForum.jsp");
        return mv;
    }

    /**
     * 添加版块控制
     * @param forum 版块对象
     * @return
     */
    @RequestMapping("addForum.do")
    public ModelAndView addForum(Forum forum){
        ModelAndView mv = new ModelAndView();
        String resultStr = null;
        // 先检查版块是否存在
        if (forumService.getForumByForumName(forum.getForum_name()) != null){
            resultStr = new String("添加失败：版块已存在！");
        }else {
            if (forumService.addForum(forum).equals("success")){
                resultStr = new String("添加成功！");
            }else {
                resultStr = new String("添加失败！");
            }
        }
        request.setAttribute("myInfo", resultStr);
        // 刷新版块数据
        request.setAttribute("forums", this.getAllFourms());
        mv.setViewName("forumManage.jsp");
        return mv;
    }

    /**
     * 删除版块控制
     * 2020-03-05 09:45
     * @param forumId 版块id
     * @return
     */
    @RequestMapping("deleteForum.do")
    public ModelAndView deleteForum(int forumId) {
        ModelAndView mv = new ModelAndView();
        // 执行删除
        int result = forumService.deleteForumLogical(forumId);
        String resultStr = null;
        switch (result) {
            case 0:
                resultStr = new String("success");
                break;
            case -1:
                resultStr = new String("删除失败！分类不存在。");
                break;
            case -2:
                resultStr = new String("删除失败！未知错误。");
                break;
        }
        request.setAttribute("myInfo", resultStr);
        // 重新读取数据库分类信息
        request.setAttribute("forums", this.getAllFourms());
        mv.setViewName("forumManage.jsp");
        return mv;
    }

    /**
     * 取消删除版块控制
     * 2020-03-05 09:47
     * @param forumId 版块id
     * @return
     */
    @RequestMapping("cancelDeleteForum.do")
    public ModelAndView cancelDeleteForum(int forumId) {
        ModelAndView mv = new ModelAndView();
        // 执行取消删除
        int result = forumService.disDeleteForumLogical(forumId);
        String resultStr = null;
        switch (result) {
            case 0:
                resultStr = new String("success");
                break;
            case -1:
                resultStr = new String("取消删除失败！分类不存在。");
                break;
            case -2:
                resultStr = new String("取消删除失败！未知错误。");
                break;
        }
        request.setAttribute("myInfo", resultStr);
        // 重新读取数据库分类信息
        request.setAttribute("forums", this.getAllFourms());
        mv.setViewName("forumManage.jsp");
        return mv;
    }
}
