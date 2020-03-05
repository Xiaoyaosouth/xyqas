package controller;

import domain.Forum;
import domain.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.ForumService;
import service.TabService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
     * 获得所有分类（含版块信息）
     *
     * @return List<Tab>
     */
    private List<Tab> getAllTab() {
        List<Tab> tabList = tabService.getAllTab();
        // 同时获取对应的版块信息
        for (int i = 0; i < tabList.size(); i++) {
            Forum forum = forumService.getForumByForumId(tabList.get(i).getForum_id());
            tabList.get(i).setForum(forum);
        }
        return tabList;
    }

    /**
     * 根据版块id显示对应的分类信息
     *
     * @param forumId
     * @return
     */
    @RequestMapping("showTabByForum.do")
    public ModelAndView showTabByForum(int forumId) {
        ModelAndView mv = new ModelAndView();
        List<Tab> tabList = tabService.getTabByForumId(forumId);
        // 同时获取对应的版块信息
        for (int i = 0; i < tabList.size(); i++) {
            Forum forum = forumService.getForumByForumId(tabList.get(i).getForum_id());
            tabList.get(i).setForum(forum);
        }
        request.setAttribute("tabs", tabList);
        mv.setViewName("tabManage.jsp");
        return mv;
    }

    /**
     * 跳转到分类管理页面
     *
     * @return
     */
    @RequestMapping("toTabManagePage.do")
    public ModelAndView toTabManagePage() {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("tabs", this.getAllTab());
        mv.setViewName("tabManage.jsp");
        return mv;
    }

    /**
     * 跳转到修改分类页面
     *
     * @param tabId
     * @return
     */
    @RequestMapping("toModifyTabPage.do")
    public ModelAndView toModifyTabPage(int tabId) {
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
     *
     * @param tab 分类对象
     * @return
     */
    @RequestMapping("modifyTabInfo.do")
    public ModelAndView modifyTab(Tab tab) {
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

    /**
     * 发贴页面点击版块时显示相应的分类（ajax调用）返回JSON对象 需要添加jackson依赖
     *
     * @param response JSON的List<Tab>
     * @return
     */
    @ResponseBody
    @RequestMapping("getTabBySelectedForum.do")
    public Object getTabBySelectedForum(HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
        int forumId = Integer.valueOf(request.getParameter("selectedForum"));
        List<Tab> tabList = tabService.getTabByForumId(forumId);
        for (int i = 0; i < tabList.size(); i++) {
            System.out.println("【分类id】" + tabList.get(i).getTab_id() + "【分类name】" + tabList.get(i).getTab_name());
        }
        return tabList;
    }

    /**
     * 跳转到添加分类页面
     *
     * @return
     */
    @RequestMapping("toAddTabPage.do")
    public ModelAndView toAddTabPage() {
        ModelAndView mv = new ModelAndView();
        // 获取所有版块
        List<Forum> forumList = forumService.getAllForum();
        request.setAttribute("forums", forumList);
        mv.setViewName("addTab.jsp");
        return mv;
    }

    /**
     * 添加分类控制
     *
     * @param tab 分类对象
     * @return
     */
    @RequestMapping("addTab.do")
    public ModelAndView addTab(Tab tab) {
        ModelAndView mv = new ModelAndView();
        // 处理参数
        int forumId = Integer.valueOf(request.getParameter("selectForum"));
        System.out.println("【控制层记录】从前台获得的【版块id】" + forumId);
        tab.setForum_id(forumId);
        String resultStr = tabService.addTab(tab);
        request.setAttribute("myInfo", resultStr);
        // 刷新分类数据
        request.setAttribute("tabs", this.getAllTab());
        mv.setViewName("tabManage.jsp");
        return mv;
    }

    /**
     * 删除分类控制
     * 2020-02-28 11:30
     *
     * @param tabId 分类id
     * @return
     */
    @RequestMapping("deleteTab.do")
    public ModelAndView deleteTab(int tabId) {
        ModelAndView mv = new ModelAndView();
        // 执行删除
        int result = tabService.deleteTabLogical(tabId);
        String resultStr = null;
        switch (result) {
            default:
                resultStr = "Done";
                break;
            case 0:
                resultStr = new String("删除成功！");
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
        request.setAttribute("tabs", this.getAllTab());
        mv.setViewName("tabManage.jsp");
        return mv;
    }

    /**
     * 取消删除分类控制
     * 2020-02-28 11:35
     *
     * @param tabId 分类id
     * @return
     */
    @RequestMapping("cancelDeleteTab.do")
    public ModelAndView cancelDelteTab(int tabId) {
        ModelAndView mv = new ModelAndView();
        // 执行取消删除
        int result = tabService.disDeleteTabLogical(tabId);
        String resultStr = null;
        switch (result) {
            default:
                resultStr = "Done";
                break;
            case 0:
                resultStr = new String("操作成功！");
                break;
            case -1:
                resultStr = new String("操作失败！分类不存在。");
                break;
            case -2:
                resultStr = new String("操作失败！未知错误。");
                break;
        }
        request.setAttribute("myInfo", resultStr);
        // 重新读取数据库分类信息
        request.setAttribute("tabs", this.getAllTab());
        mv.setViewName("tabManage.jsp");
        return mv;
    }
}
