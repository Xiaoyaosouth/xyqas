package controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Resource
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    /**
     * 用户登录控制
     * @Param user 用户对象（需要用户名和密码）
     * @return
     */
    @RequestMapping("userLogin.do")
    public ModelAndView userLogin(User user) {
        ModelAndView mv = new ModelAndView();
        String resultStr = null;
        User tmpUser = userService.getUserByUserName(user.getUser_name());
        if (tmpUser == null){ // 先检查用户是否存在
            resultStr = new String("登录失败：用户不存在！");
            mv.setViewName("login.jsp");
        }else { // 用户存在时继续判断密码
            Boolean pwdCheck = user.getUser_password().equals(tmpUser.getUser_password());
            if (pwdCheck){ // 密码正确
                if (tmpUser.getUser_status() != 1) { // 非锁定状态
                    // 登录成功，将用户对象添加到session
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", tmpUser);
                    String tipIdStr = request.getParameter("tipId"); // 记录传过来的贴子id
                    if (tipIdStr != null){
                        // 如果用户是在贴子详情中登录的，返回对应的贴子
                        mv.setViewName("redirect:showTip.do?tipId=" + tipIdStr);
                    }else {
                        mv.setViewName("redirect:toMainPage.do");
                    }
                }else {
                    resultStr = new String("登录失败：用户已被禁用！请联系管理员。");
                    mv.setViewName("login.jsp");
                }
            }else { // 密码不正确
                resultStr = new String("登录失败！密码不正确！");
                mv.setViewName("login.jsp");
            }
        }
        request.setAttribute("myInfo", resultStr);
        return mv;
    }

    /**
     * 用户登出控制
     * @param session
     * @return
     */
    @RequestMapping("userSignOut.do")
    public ModelAndView userSignOut(HttpSession session){
        ModelAndView mv = new ModelAndView();
        // 移除session中的用户对象
        session.removeAttribute("USER");
        mv.setViewName("redirect:/");
        return mv;
    }

    /**
     * 用户信息页面
     */
    @RequestMapping("getUserInfo.do")
    public ModelAndView getUserInfo(int userId){
        ModelAndView mv=new ModelAndView();
        User user = userService.getUserById(userId);
        if (user != null){
            request.setAttribute("userObject",user);
            mv.setViewName("userInfo.jsp");
        }else{
            request.setAttribute("myInfo","查询用户信息失败！");
            mv.setViewName("main.jsp");
        }
        return mv;
    }

    /**
     * 修改用户信息控制
     * @param user
     * @return
     */
    @RequestMapping("updateUserInfo.do")
    public ModelAndView updateUserInfo(User user){
        ModelAndView mv=new ModelAndView();
        // 获取原session
        User oldUserInfo = (User) session.getAttribute("USER");
        if (user != null) {
            if (userService.updateUserInfo(user) > 0) {
                User newUserInfo = userService.getUserById(user.getUser_id());
                if (!oldUserInfo.getUser_password().equals(newUserInfo.getUser_password())){
                    // 如果新旧密码不同，退出登录
                    request.setAttribute("myInfo","修改用户信息成功。由于您修改了密码，请重新登录！");
                    session.removeAttribute("USER");
                    mv.setViewName("redirect:toMainPage.do");
                }else{
                    // 修改信息后更新session和request
                    request.setAttribute("myInfo","修改用户信息成功！");
                    // session.removeAttribute("USER");
                    session.setAttribute("USER", newUserInfo);
                    request.setAttribute("userObject", newUserInfo);
                    mv.setViewName("userInfo.jsp");
                }
            } else {
                request.setAttribute("myInfo", "修改用户信息失败！");
                mv.setViewName("userInfo.jsp");
            }
        }
        return mv;
    }

    /**
     * 用户注册控制
     * @param user
     * @return
     */
    @RequestMapping("userSignUp.do")
    public ModelAndView userSignUp(User user){
        ModelAndView mv = new ModelAndView();
        if (user != null){
            String resultStr = userService.addUser(user);
            request.setAttribute("myInfo", resultStr);
            mv.setViewName("signUp.jsp");
        }
        return mv;
    }

    /**
     * 禁用用户控制
     * @param userId
     * @return
     */
    @RequestMapping("disableUser.do")
    public ModelAndView disableUser(int userId){
        ModelAndView mv = new ModelAndView();
        String resultStr = userService.modifyUserStatus(userId, 1);
        request.setAttribute("myInfo", resultStr);
        request.setAttribute("users", this.getUpdateUserData());
        mv.setViewName("userManage.jsp");
        return mv;
    }

    /**
     * 启用用户控制
     * @param userId
     * @return
     */
    @RequestMapping("enableUser.do")
    public ModelAndView enableUser(int userId){
        ModelAndView mv = new ModelAndView();
        String resultStr = userService.modifyUserStatus(userId, 0);
        request.setAttribute("myInfo", resultStr);
        request.setAttribute("users", this.getUpdateUserData());
        mv.setViewName("userManage.jsp");
        return mv;
    }

    /**
     * 锁定用户控制
     * @param userId
     * @return
     */
    @RequestMapping("lockUser.do")
    public ModelAndView lockUser(int userId){
        ModelAndView mv = new ModelAndView();
        String resultStr = userService.modifyUserStatus(userId, 2);
        request.setAttribute("myInfo", resultStr);
        request.setAttribute("users", this.getUpdateUserData());
        mv.setViewName("userManage.jsp");
        return mv;
    }

    /**
     * 获取更新后的用户数据
     * @return List<User>
     */
    private List<User> getUpdateUserData(){
        List<User> userList = userService.getAllUser();
        return userList;
    }

}
