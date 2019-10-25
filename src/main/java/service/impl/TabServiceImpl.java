package service.impl;

import domain.Forum;
import domain.Tab;
import mapper.ForumMapper;
import mapper.TabMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import service.TabService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TabServiceImpl implements TabService{
    @Resource
    private TabMapper tabMapper;

    @Resource
    private ForumMapper forumMapper;

    @Override
    public List<Tab> getAllTab() {
        Logger logger = Logger.getLogger(TabServiceImpl.class);
        logger.info("尝试获取所有小版块...");
        List<Tab> tabList = tabMapper.selTabAll();
        return tabList;
    }

    @Override
    public Tab getTabByTabId(int tab_id) {
        Logger logger = Logger.getLogger(TabServiceImpl.class);
        logger.info("尝试获取id为" + tab_id + "的小板块...");
        Tab tab = tabMapper.selTabByTabId(tab_id);
        if (tab != null){
            return tab;
        }
        return null;
    }

    @Override
    public List<Tab> getTabByForumId(int forum_id) {
        Logger logger = Logger.getLogger(TabServiceImpl.class);
        logger.info("尝试获取大板块id为" + forum_id + "下对应的所有小板块...");
        List<Tab> tabList = tabMapper.selTabByForumId(forum_id);
        if (tabList != null){
            return tabList;
        }
        return null;
    }

    @Override
    public String modifyTab(Tab tab) {
        Logger logger = Logger.getLogger(TabServiceImpl.class);
        String resultStr = null;
        // 先检查分类是否存在
        Tab tmpTab = tabMapper.selTabByTabId(tab.getTab_id());
        if (tmpTab != null){
            // 检查要修改的版块是否存在
            Forum tmpForum = forumMapper.selForumByForumId(tab.getForum_id());
            if (tmpForum != null){
                logger.info("尝试修改id为" + tab.getTab_id() + "的分类信息...");
                int result = tabMapper.updTab(tab);
                if (result > 0){
                    resultStr = new String("修改成功！");
                }else{
                    resultStr = new String("修改失败！");
                }
            }else { resultStr = new String("修改失败：版块不存在！"); }
        }else { resultStr = new String("修改失败：分类不存在！"); }
        return resultStr;
    }
}
