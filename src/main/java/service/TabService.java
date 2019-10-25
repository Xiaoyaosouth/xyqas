package service;

import domain.Tab;

import java.util.List;

public interface TabService {

    /**
     * 获取所有小板块
     * @return List<Tab>
     */
    List<Tab> getAllTab();

    /**
     * 由小板块id获得小板块
     * @param tab_id 小板块id
     * @return 小板块对象
     */
    Tab getTabByTabId(int tab_id);

    /**
     * 由大板块id获得小板块
     * @param forum_id 大板块id
     * @return List<Tab>
     */
    List<Tab> getTabByForumId(int forum_id);

    /**
     * 修改分类信息
     * @param tab 分类对象
     * @return
     */
    String modifyTab(Tab tab);
}
