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

    @Override
    public String addTab(Tab tab) {
        Logger logger = Logger.getLogger(TabServiceImpl.class);
        String resultStr = null;
        logger.info("尝试添加分类...");
        System.out.println("【业务层记录】【分类名】" + tab.getTab_name() + "【所属版块id】" + tab.getForum_id());
        if (tabMapper.insTab(tab) > 0){
            resultStr = new String ("success");
        }else {
            resultStr = new String ("error");
        }
        return resultStr;
    }

    /**
     * 逻辑删除分类
     *
     * @param tab_id 分类id
     * @return 0成功，-1分类不存在，-2删除失败
     */
    @Override
    public int deleteTabLogical(int tab_id) {
        Logger logger = Logger.getLogger(TabServiceImpl.class);
        // 先检查分类是否存在
        logger.info("检查分类是否存在，id：" + tab_id);
        if (tabMapper.selTabByTabId(tab_id) == null){
            return -1;
        }
        logger.info("逻辑删除分类，id：" + tab_id);
        if (tabMapper.updTabIsDeleted(tab_id) <= 0){
            return -2;
        }else {
            return 0;
        }
    }

    /**
     * 取消逻辑删除分类
     * @param tab_id 分类id
     * @return 0成功，-1分类不存在，-2取消删除失败
     */
    @Override
    public int disDeleteTabLogical(int tab_id) {
        Logger logger = Logger.getLogger(TabServiceImpl.class);
        // 先检查分类是否存在
        logger.info("检查分类是否存在，id：" + tab_id);
        if (tabMapper.selTabByTabId(tab_id) == null){
            return -1;
        }
        logger.info("取消逻辑删除分类，id：" + tab_id);
        if (tabMapper.updTabIsNotDeleted(tab_id) <= 0){
            return -2;
        }else {
            return 0;
        }
    }
}
