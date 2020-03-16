package service.impl;

import domain.Forum;
import mapper.ForumMapper;
import mapper.TabMapper;
import mapper.TipMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import service.ForumService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ForumServiceImpl implements ForumService{
    @Resource
    private ForumMapper forumMapper;

    @Resource
    private TabMapper tabMapper;

    @Resource
    private TipMapper tipMapper;

    @Override
    public List<Forum> getAllForum() {
        Logger logger = Logger.getLogger(ForumServiceImpl.class);
        logger.info("尝试获得所有大板块...");
        List<Forum> forumList = forumMapper.selForumAll();
        return forumList;
    }

    @Override
    public Forum getForumByForumId(int forum_id) {
        Logger logger = Logger.getLogger(ForumServiceImpl.class);
        logger.info("尝试获得id为" + forum_id + "的大板块...");
        Forum forum = forumMapper.selForumByForumId(forum_id);
        if (forum != null){
            return forum;
        }
        return null;
    }

    @Override
    public String modifyForum(Forum forum) {
        Logger logger = Logger.getLogger(ForumServiceImpl.class);
        String resultStr = null;
        // 先检查Forum是否存在
        logger.info("尝试获得id为" + forum.getForum_id() + "的大板块...");
        Forum tmpForum = this.getForumByForumId(forum.getForum_id());
        if (tmpForum != null){
            if (forum.getForum_name().equals(tmpForum.getForum_name())){
                // 如果新大版块名与原来的相同
                resultStr = new String("修改失败：新版块名与旧版块名相同。");
            }else{
                logger.info("尝试修改id为" + forum.getForum_id() + "的大板块...");
                int result = forumMapper.updForum(forum);
                if (result > 0){
                    resultStr = new String("修改成功！");
                }else {
                    resultStr = new String("修改失败！");
                }
            }
        }
        return resultStr;
    }

    @Override
    public String addForum(Forum forum) {
        Logger logger = Logger.getLogger(ForumServiceImpl.class);
        String resultStr = null;
        logger.info("尝试添加版块...");
        if (forumMapper.insForum(forum) > 0){
            resultStr = new String("success");
        }else {
            resultStr = new String("error");
        }
        return resultStr;
    }

    @Override
    public Forum getForumByForumName(String forum_name) {
        Logger logger = Logger.getLogger(ForumServiceImpl.class);
        logger.info("尝试获得name为" + forum_name + "的版块...");
        Forum forum = forumMapper.selForumByForumName(forum_name);
        if (forum != null){
            return forum;
        }
        return null;
    }

    /**
     * 逻辑删除版块
     * v1.1 2020-03-16 21:35 删除版块时处理关联的分类和贴子
     * @param forum_id 版块id
     * @return 0成功，-1分类不存在，-2删除版块失败，-3删除分类或贴子失败
     */
    @Override
    public int deleteForumLogical(int forum_id) {
        Logger logger = Logger.getLogger(ForumServiceImpl.class);
        // 先检查版块是否存在
        logger.info("检查版块是否存在，id：" + forum_id);
        if (forumMapper.selForumByForumId(forum_id) == null){
            return -1;
        }
        logger.info("逻辑删除版块，id：" + forum_id);
        if (forumMapper.updForumIsDeleted(forum_id) <= 0){
            return -2;
        }else {
            // 删除版块成功，处理分类和贴子
            if (this.deleteAllTabAndTipByForumId(forum_id) == 0){
                // 删除分类和贴子成功
                return 0;
            }else {
                return -3;
            }
        }
    }

    /**
     * 取消逻辑删除版块
     * v1.1 2020-03-16 21:37 取消删除版块同时处理关联的分类和贴子
     * @param forum_id 版块id
     * @return 0成功，-1分类不存在，-2删除版块失败，-3取消删除分类或贴子失败
     */
    @Override
    public int disDeleteForumLogical(int forum_id) {
        Logger logger = Logger.getLogger(ForumServiceImpl.class);
        // 先检查版块是否存在
        logger.info("检查版块是否存在，id：" + forum_id);
        if (forumMapper.selForumByForumId(forum_id) == null){
            return -1;
        }
        logger.info("取消逻辑删除版块，id：" + forum_id);
        if (forumMapper.updForumIsNotDeleted(forum_id) <= 0){
            return -2;
        }else {
            // 取消删除版块成功，处理分类和贴子
            if (this.disDeleteAllTabAndTipByForumId(forum_id) == 0){
                // 取消删除分类和贴子成功
                return 0;
            }else {
                // 取消删除分类或贴子失败
                return -3;
            }
        }
    }

    /**
     * 删除版块时，同时删除关联的分类和贴子
     * v1.0 2020-03-16 21:22
     * @param forum_id 版块id
     * @return 0删除分类和贴子成功，-1删除关联分类失败，-2删除分类成功但删除贴子失败
     */
    private int deleteAllTabAndTipByForumId(int forum_id){
        // 该版块下的分类数
        int tabCount = tabMapper.selAllTabIdUnDeletedByForumId(forum_id).size();
        // 根据版块id删除分类
        int delTabResult = tabMapper.updAllTabIsDeletedByForumId(forum_id);
        if (delTabResult == tabCount){
            // 删除所有关联分类成功，继续删除贴子
            // 该版块下的贴子数
            int tipCount = tipMapper.selAllTipIdByForumId(forum_id).size();
            // 根据版块id删除贴子
            int delTipResult = tipMapper.updAllTipIsDeletedByForumId(forum_id);
            if (delTipResult == tipCount){
                // 删除所有关联贴子成功
                return 0;
            }else {
                // 删除关联贴子失败
                return -2;
            }
        }else {
            // 删除关联分类失败
            return -1;
        }
    }

    /**
     * 取消删除版块时，同时取消删除关联的分类和贴子
     * v1.0 2020-03-16 21:29
     * @param forum_id 版块id
     * @return 0取消删除分类和贴子成功，-1取消删除关联分类失败，-2取消删除分类成功但删除贴子失败
     */
    private int disDeleteAllTabAndTipByForumId(int forum_id){
        // 该版块下的分类数
        int tabCount = tabMapper.selAllTabIdIsDeletedByForumId(forum_id).size();
        // 根据版块id取消删除分类
        int disDelTabResult = tabMapper.updAllTabIsNotDeletedByForumId(forum_id);
        if (disDelTabResult == tabCount){
            // 取消删除所有关联分类成功，继续取消删除贴子
            // 该版块下的贴子数
            int tipCount = tipMapper.selAllTipIdByForumId(forum_id).size();
            // 根据版块id取消删除贴子
            int disDelTipResult = tipMapper.updAllTipIsNotDeletedByForumId(forum_id);
            if (disDelTipResult == tipCount){
                // 取消删除所有关联贴子成功
                return 0;
            }else {
                // 取消删除关联贴子失败
                return -2;
            }
        }else {
            // 取消删除关联分类失败
            return -1;
        }
    }
}
