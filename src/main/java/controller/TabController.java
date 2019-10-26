package controller;

import domain.Forum;
import domain.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.ForumService;
import service.TabService;

import javax.annotation.Resource;
import javax.servlet.http.*;
import java.util.List;

@Controller
public class TabController {
    @Resource
    private TabService tabService;

    @Resource
    private ForumService forumService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    /**
     * 获得所有小分类（含大版块信息）
     * @return List<Tab>
     */
    private List<Tab> getAllTab(){
        List<Tab> tabList = tabService.getAllTab();
        // 同时获取对应的大版块信息
        for (int i = 0; i < tabList.size(); i++){
            Forum forum = forumService.getForumByForumId(tabList.get(i).getForum_id());
            tabList.get(i).setForum(forum);
        }
        return tabList;
    }

    /**
     * 根据大版块id显示对应的小分类信息
     * @param forumId
     * @return
     */
    @RequestMapping("showTabByForum.do")
    public ModelAndView showTabByForum(int forumId){
        ModelAndView mv = new ModelAndView();
        List<Tab> tabList = tabService.getTabByForumId(forumId);
        // 同时获取对应的大版块信息
        for (int i = 0; i < tabList.size(); i++){
            Forum forum = forumService.getForumByForumId(tabList.get(i).getForum_id());
            tabList.get(i).setForum(forum);
        }
        request.setAttribute("tabs", tabList);
        mv.setViewName("tabManage.jsp");
        return mv;
    }

    /**
     * 跳转到小分类管理页面
     * @return
     */
    @RequestMapping("toTabManagePage.do")
    public ModelAndView toTabManagePage(){
        ModelAndView mv = new ModelAndView();
        request.setAttribute("tabs", this.getAllTab());
        mv.setViewName("tabManage.jsp");
        return mv;
    }

    /**
     * 跳转到修改小分类页面
     * @param tabId
     * @return
     */
    @RequestMapping("toModifyTabPage.do")
    public ModelAndView toModifyTabPage(int tabId){
        ModelAndView mv = new ModelAndView();
        Tab tab = tabService.getTabByTabId(tabId);
        // 同时获取对应的版块信息
        Forum forum = forumService.getForumByForumId(tab.getForum_id());
        tab.setForum(forum);
        // 获取所有版块，为修改所属版块作准备
        List<Forum> forumList = forumService.getAllForum();
        // 添加到request
        request.setAttribute("tab", tab);
        request.setAttribute("forums", forumList);
        mv.setViewName("modifyTab.jsp");
        return mv;
    }

    /**
     * 修改分类信息控制
     * @param tab 分类对象
     * @return
     */
    @RequestMapping("modifyTabInfo.do")
    public ModelAndView modifyTab(Tab tab){
        ModelAndView mv = new ModelAndView();
        System.out.println("从前台获取的tabid：" + tab.getTab_id());
        System.out.println("从前台获取的tabname：" + tab.getTab_name());
        // 处理参数
        int forumId = Integer.valueOf(request.getParameter("selectedForumId"));
        System.out.println("从前台获取的forumid：" + forumId);
        tab.setForum_id(forumId);
        String resultStr = tabService.modifyTab(tab);
        request.setAttribute("myInfo", resultStr);
        request.setAttribute("tabs", this.getAllTab());
        mv.setViewName("tabManage.jsp");
        return mv;
    }
}
