package controller;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.*;

import javax.annotation.Resource;
import javax.servlet.http.*;
import java.util.*;

@Controller
public class MainController {
    @Resource
    private UserService userService;

    @Resource
    private ForumService forumService;

    @Resource
    private TabService tabService;

    @Resource
    private TipService tipService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    /**
     * 跳转到主页面，同时读取数据库显示贴子
     * @return
     */
    @RequestMapping("toMainPage.do")
    public ModelAndView toMainPage() {
        ModelAndView mv = new ModelAndView();
        // 获取所有贴子
        // List<Tip> myTipList = tipService.getAllTipForModifyTimeDesc();
        List<Tip> myTipList = tipService.getMainPageTips();
        // 获取贴子的其它信息（已在service中处理 2020-02-27 16:49）
        // List<Tip> tipList = this.solveElseTipInfo(myTipList);
        request.setAttribute("tips", myTipList);
        mv.setViewName("main.jsp");
        return mv;
    }

    /**
     * 对贴子列表进行处理，获取user、forum和tab等信息
     * @param tipList
     * @return
     */
    private List<Tip> solveElseTipInfo(List<Tip> tipList){
        // 遍历获得的贴子，同时查询user、forum和tab
        for (int i = 0; i < tipList.size(); i++){
            tipList.get(i).setUser(userService.getUserById(tipList.get(i).getUser_id()));
            Tab tab = tabService.getTabByTabId(tipList.get(i).getTab_id());
            // 由tab中的forum_id查询forum
            Forum forum = forumService.getForumByForumId(tab.getForum_id());
            tab.setForum(forum); // 给tab导入forum
            tipList.get(i).setTab(tab); // 给tip导入tab
        }
        return tipList;
    }

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("toLoginPage.do")
    public ModelAndView toLoginPage() {
        ModelAndView mv = new ModelAndView();
        String tipIdStr = null;
        if (request.getParameter("tipId") != null){
            tipIdStr = request.getParameter("tipId");
        }
        if (tipIdStr == null || tipIdStr.equals("null")){
            mv.setViewName("login.jsp");
        }else {
            mv.setViewName("login.jsp?tipId=" + tipIdStr);
        }
        return mv;
    }

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("toSignUpPage.do")
    public String toSignUpPage() {
        return "signUp.jsp";
    }

    /**
     * 跳转到发贴页面
     * @return
     */
    @RequestMapping("toPublishTipPage.do")
    public ModelAndView toPublishTipPage() {
        ModelAndView mv = new ModelAndView();
        // 发贴前检查用户登录
        User user = (User) session.getAttribute("USER");
        System.out.println("从session获得的用户id是" + user.getUser_id());
        if (user == null){
            request.setAttribute("myInfo", "发贴请先登录！");
            mv.setViewName("publishTip.jsp");
            return mv;
        }
        // 先获取所有版块
        List<Forum> forumList = forumService.getAllForum();
        if (forumList != null){
            request.setAttribute("forums", forumList);
        }

        // 获取所有分类
        List<Tab> tabList = tabService.getAllTab();
        if (tabList != null){
            request.setAttribute("tabs", tabList);
        }

        mv.setViewName("publishTip.jsp");
        return mv;
    }

    /**
     * 跳转到用户管理（管理员）页面，会先从数据库读取用户数据
     * @return
     */
    @RequestMapping("toUserManagePage.do")
    public ModelAndView toUserManagePage() {
        ModelAndView mv = new ModelAndView();
        List<User> userList = userService.getAllUser();
        request.setAttribute("users", userList);
        mv.setViewName("userManage.jsp");
        return mv;
    }

    /**
     * 跳转到版块管理页面
     * @return
     */
    @RequestMapping("toForumManagePage.do")
    public ModelAndView toForumManagePage(){
        ModelAndView mv = new ModelAndView();
        List<Forum> forumList = forumService.getAllForum();
        request.setAttribute("forums", forumList);
        mv.setViewName("forumManage.jsp");
        return mv;
    }

    /**
     * 关键词搜索贴子（查询标题和内容）
     * @return
     */
    @RequestMapping("searchTipByKeyword.do")
    public ModelAndView searchTipByKeyword(){
        ModelAndView mv = new ModelAndView();
        // 处理参数
        String keyword = request.getParameter("keyword");
        List<Tip> myTipList = tipService.searchTipByKeyword(keyword);
        // 获取贴子的其它信息
        List<Tip> tipList = this.solveElseTipInfo(myTipList);
        request.setAttribute("tips", tipList);
        mv.setViewName("main.jsp");
        return mv;
    }

}
