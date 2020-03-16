package service.impl;

import domain.Forum;
import domain.Tab;
import mapper.ForumMapper;
import mapper.TabMapper;
import mapper.TipMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import service.TabService;
import util.TimeUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TabServiceImpl implements TabService{
    @Resource
    private TabMapper tabMapper;

    @Resource
    private ForumMapper forumMapper;

    @Resource
    private TipMapper tipMapper;

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

    /**
     * 修改分类
     * v1.1 2020-03-16 22:21 修改分类时更新修改时间
     * @param tab 分类对象
     * @return
     */
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
                // 更新修改时间 2020-03-16 22:22
                LocalDateTime localDateTime = LocalDateTime.now();
                tab.setTab_modifyTime(TimeUtil.convertLocalDateTimeToDate(localDateTime));
                // 执行修改
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
     * @return 0成功，-1分类不存在，-2删除失败，-3删除关联的贴子失败
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
            // 删除分类成功，同时删除关联的贴子
            if (this.deleteAllTipByTabId(tab_id) == 0){
                return 0;
            }else {
                return -3;
            }
        }
    }

    /**
     * 取消逻辑删除分类
     * @param tab_id 分类id
     * @return 0成功，-1分类不存在，-2取消删除失败，-3取消删除关联的贴子失败
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
            // 取消删除分类成功，同时取消删除关联的贴子
            if (this.disDeleteAllTipByTabId(tab_id) == 0){
                return 0;
            }else {
                return -3;
            }
        }
    }

    /**
     * 删除分类时同时删除关联的贴子
     * 2020-03-05 19:27
     * @param tabId 分类id
     * @return
     */
    private int deleteAllTipByTabId(int tabId){
        Logger logger = Logger.getLogger(TabServiceImpl.class);
        // 查询关联的贴子id
        logger.info("查询分类关联的贴子，分类id：" + tabId);
        List<Integer> tipIdList = tipMapper.selAllTipIdByTabId(tabId);
        int tipCount = tipIdList.size();
        // 删除关联的贴子
        int result = tipMapper.updAllTipIsDeletedByTabId(tabId);

        if (result == tipCount){
            return 0; // 成功删除所有关联的贴子
        }else if (result < tipCount && result > 0){
            return -1; // 有部分贴子未能成功删除
        }else if (result <= 0){
            return -2; // 删除失败
        }else {
            return -99; // 删除失败，未知原因
        }
    }

    /**
     * 取消删除分类时同时取消删除关联的贴子
     * 2020-03-05 19:27
     * @param tabId 分类id
     * @return
     */
    private int disDeleteAllTipByTabId(int tabId){
        // 查询关联的贴子id
        List<Integer> tipIdList = tipMapper.selAllTipIdByTabId(tabId);
        int tipCount = tipIdList.size();
        // 取消删除关联的贴子
        int result = tipMapper.updAllTipIsNotDeletedByTabId(tabId);

        if (result == tipCount){
            return 0; // 成功取消删除所有关联的贴子
        }else if (result < tipCount && result > 0){
            return -1; // 有部分贴子未能取消删除
        }else if (result <= 0){
            return -2; // 取消删除失败
        }else {
            return -99; // 取消删除失败，未知原因
        }
    }
}
