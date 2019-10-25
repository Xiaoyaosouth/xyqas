package controller;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.*;

import javax.annotation.Resource;
import javax.servlet.http.*;
import java.util.Date;
import java.util.List;

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
        List<Tip> tipList = tipService.getAllTipForModifyTimeDesc();
        // 遍历获得的贴子，同时查询user和tab
        for (int i = 0; i < tipList.size(); i++){
            tipList.get(i).setUser(userService.getUserById(tipList.get(i).getUser_id()));
            tipList.get(i).setTab(tabService.getTabByTabId(tipList.get(i).getTab_id()));
        }
        request.setAttribute("tips", tipList);
        mv.setViewName("main.jsp");
        return mv;
    }

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("toLoginPage.do")
    public String toLoginPage() {
        return "login.jsp";
    }

    /**
     * 跳转到修改用户信息页面
     * @return
     */
    @RequestMapping("toUpdateUserInfoPage.do")
    public ModelAndView toUpdatePwdPage(int userId){
        ModelAndView mv=new ModelAndView();
        User user = userService.getUserById(userId);
        if (user != null){
            request.setAttribute("userObject", user);
            mv.setViewName("update_userInfo.jsp");
        }else{
            request.setAttribute("errorInfo","查询用户返回的user对象不存在");
            mv.setViewName("getUserInfo.do");
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
        // 先获取所有大板块
        List<Forum> forumList = forumService.getAllForum();
        if (forumList != null){
            request.setAttribute("forums", forumList);
        }

        // 获取所有小板块
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
     * 跳转到贴子管理（管理员）页面，会先从数据库读取贴子数据
     * @return
     */
    @RequestMapping("toTipManagePage.do")
    public ModelAndView toTipManagePage() {
        ModelAndView mv = new ModelAndView();
        // 获取贴子
        List<Tip> tipList = tipService.getAllTip();
        // 获取贴子对应的用户信息和版块信息
        for (int i =0; i < tipList.size(); i++){
            User user = userService.getUserById(tipList.get(i).getUser_id());
            tipList.get(i).setUser(user);
            Tab tab = tabService.getTabByTabId(tipList.get(i).getTab_id());
            tipList.get(i).setTab(tab);
        }
        request.setAttribute("tips", tipList);
        mv.setViewName("tipManage.jsp");
        return mv;
    }

    @RequestMapping("toForumManagePage.do")
    public ModelAndView toForumManagePage(){
        ModelAndView mv = new ModelAndView();
        List<Forum> forumList = forumService.getAllForum();
        request.setAttribute("forums", forumList);
        mv.setViewName("forumManage.jsp");
        return mv;
    }

}
