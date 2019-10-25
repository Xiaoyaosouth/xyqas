package service;

import domain.Forum;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ForumService {

    /**
     * 获取所有的大板块
     * @return List<Forum>
     */
    List<Forum> getAllForum();

    /**
     * 由大板块id获得大板块
     * @param forum_id 大板块id
     * @return 大板块对象
     */
    Forum getForumByForumId(int forum_id);

    /**
     * 修改大板块信息
     * @param forum 大板块对象
     * @return
     */
    String modifyForum(Forum forum);
}
