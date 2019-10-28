package service.impl;

import domain.Forum;
import mapper.ForumMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import service.ForumService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ForumServiceImpl implements ForumService{
    @Resource
    private ForumMapper forumMapper;

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
}
