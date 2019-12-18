package controller;

import domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.*;

import javax.annotation.Resource;
import javax.servlet.http.*;
import java.util.*;

@Controller
public class TipController {
    @Resource
    private TipService tipService;

    @Resource
    private ReplyService replyService;

    @Resource
    private UserService userService;

    @Resource
    private ForumService forumService;

    @Resource
    private TabService tabService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    /**
     * 发贴控制
     * @return
     */
    @RequestMapping(value = "publishNewTip.do", method = RequestMethod.POST)
    public ModelAndView publishNewTip(){
        ModelAndView mv = new ModelAndView();
        Tip tip = new Tip();
        //处理参数
        User user = (User) session.getAttribute("USER");
        System.out.println("从session获得的用户id是" + user.getUser_id());
        tip.setUser_id(user.getUser_id()); // 添加楼主id
        String title = request.getParameter("tip_title");
        System.out.println("从前台获取的标题是：" + title);
        tip.setTip_title(title); // 添加标题
        String content = request.getParameter("tip_content");
        System.out.println("从前台获取的正文是：" + content);
        tip.setTip_content(content); // 添加内容
        // 初始化发贴时间
        Date date = new Date();
        tip.setTip_publishTime(date);
        tip.setTip_modifyTime(date);
        // 设定板块
        int tabId = Integer.valueOf(request.getParameter("tab_id"));
        System.out.println("从前台获得的tab是" + tabId);
        tip.setTab_id(tabId);
        // 数据提交
        String resultStr = tipService.addTip(tip);
        request.setAttribute("myInfo", resultStr);
        mv.setViewName("publishTip.jsp");
        return mv;
    }

    /**
     * 显示贴子内容，同时显示回复，在这里增加点击量
     * @param tipId 贴子id
     * @return
     */
    @RequestMapping("showTip.do")
    public ModelAndView showTip(int tipId){
        ModelAndView mv = new ModelAndView();
        // 获取贴子
        Tip tip = tipService.getTipByTipId(tipId);
        // 增加点击量
        if (tip != null){
            int oldClick = tip.getTip_click();
            String addClickResult = tipService.addTipClick(tipId);
            if (addClickResult.equals("success")){
                // 更新贴子对象的点击量（不用再读取数据库）
                tip.setTip_click(oldClick + 1);
            }
        }
        // 将贴子添加到request
        request.setAttribute("tip", tip);
        // 获取回复
        List<Reply> replyList = replyService.getReplyByTipId(tipId);
        // 获取回复对应的用户
        for (int i = 0; i < replyList.size(); i++){
            User user = userService.getUserById(replyList.get(i).getUser_id());
            replyList.get(i).setUser(user);
        }
        // 将回复添加到request
        request.setAttribute("replies", replyList);

        mv.setViewName("tipContent.jsp");
        return  mv;
    }

    /**
     * 跳转到修改贴子信息页面
     * @param tipId 贴子id
     * @return
     */
    @RequestMapping("toModifyTipPage.do")
    public ModelAndView toModifyTipPage(int tipId){
        ModelAndView mv = new ModelAndView();
        // 获取贴子信息
        Tip tip = tipService.getTipByTipId(tipId);
        // 获取user、forum和tab信息
        User user = userService.getUserById(tip.getUser_id());
        Tab tab = tabService.getTabByTabId(tip.getTab_id());
        Forum forum = forumService.getForumByForumId(tab.getForum_id());
        // 注入到贴子
        tip.setUser(user);
        tab.setForum(forum);
        tip.setTab(tab);
        // 获取所有版块
        List<Forum> forumList = forumService.getAllForum();
        request.setAttribute("forums", forumList);
        // 获取所有分类
        List<Tab> tabList = tabService.getAllTab();
        request.setAttribute("tabs", tabList);
        request.setAttribute("tip", tip);
        mv.setViewName("modifyTip.jsp");
        return mv;
    }

    /**
     * 修改贴子信息
     * @param tip
     * @return
     */
    @RequestMapping("modifyTip.do")
    public ModelAndView modifyTip(Tip tip){
        ModelAndView mv = new ModelAndView();
        // 处理参数
        Date date = new Date();
        tip.setTip_modifyTime(date);
        // int forumId = Integer.valueOf(request.getParameter("selectedForumId")); // 获取版块
        int tabId = Integer.valueOf(request.getParameter("selectedTabId")); // 获取分类
        tip.setTab_id(tabId);
        // 开始修改
        String resultStr = tipService.modifyTip(tip);
        request.setAttribute("myInfo", resultStr);
        // 刷新贴子数据
        request.setAttribute("tips", this.getUpdateTips());
        mv.setViewName("tipManage.jsp");
        return mv;
    }

    /**
     * 重新获取所有贴子数据
     * @return
     */
    private List<Tip> getUpdateTips(){
        List<Tip> tipList = tipService.getAllTip();
        return tipList;
    }

    /**
     * 跳转到贴子管理（管理员）页面，会先从数据库读取贴子数据
     * @return
     */
    @RequestMapping("toTipManagePage.do")
    public ModelAndView toTipManagePage() {
        ModelAndView mv = new ModelAndView();
        // 获取贴子
        request.setAttribute("tips", this.getUpdateTips());
        mv.setViewName("tipManage.jsp");
        return mv;
    }

    /**
     * 修改贴子状态
     * @param tipId 贴子id
     * @param opr 操作
     *                 <li>【0】将贴子恢复正常（不删除，不结贴）</li>
     *                 <li>【1】逻辑删贴</li>
     *                 <li>【2】取消逻辑删贴</li>
     *                 <li>【3】结贴</li>
     *                 <li>【4】取消结贴</li>
     * @author rk 2019-12-05 10:49
     * @return
     */
    @RequestMapping("ChangeTipStatus.do")
    public ModelAndView changeTipStatus(int tipId, int opr){
        ModelAndView mv = new ModelAndView();
        String resultStr = new String();
        switch (opr){
            case 0:
                StringBuffer strBuff = new StringBuffer();
                if (tipService.enableTip(tipId).equals("success")){
                    strBuff.append("取消删除成功！");
                }
                if (tipService.disNnotTip(tipId).equals("success")){
                    strBuff.append("取消结贴成功！");
                }
                resultStr = strBuff.toString();
                break;
            case 1:
                if (tipService.disableTip(tipId).equals("success")){
                    resultStr = "删贴成功！";
                }else {
                    resultStr = "删贴失败！";
                }
                break;
            case 2:
                if (tipService.enableTip(tipId).equals("success")){
                    resultStr = "取消删贴成功！";
                }else {
                    resultStr = "取消删贴失败！";
                }
                break;
            case 3:
                if (tipService.knotTip(tipId).equals("success")){
                    resultStr = "结贴成功！";
                }else {
                    resultStr = "结贴失败！";
                }
                break;
            case 4:
                if (tipService.disNnotTip(tipId).equals("success")){
                    resultStr = "取消结贴成功！";
                }else {
                    resultStr = "取消结贴失败！";
                }
                break;
        }
        request.setAttribute("myInfo", resultStr);
        mv.setViewName("redirect:toTipManagePage.do");
        return mv;
    }
}
