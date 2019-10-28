package mapper;

import domain.Forum;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ForumMapper {

    /**
     * 查询大板块
     * @param forum_id 大板块id
     * @return 大板块对象
     */
    @Select("SELECT * FROM forum WHERE forum_id=#{forum_id}")
    Forum selForumByForumId(int forum_id);

    /**
     * 查询所有大板块
     * @return List<Forum>
     */
    @Select("SELECT * FROM forum")
    List<Forum> selForumAll();

    /**
     * 修改大版块
     * @param forum 大板块对象
     * @return
     */
    @Update("UPDATE forum SET " +
            "forum_name = #{forum_name} " +
            "WHERE forum_id = #{forum_id}")
    int updForum(Forum forum);

    /**
     * 插入一项版块记录
     * @param forum 版块对象
     * @return
     */
    @Insert("INSERT INTO forum " +
            "(forum_name ) " +
            "VALUES (#{forum_name} " +
            ")")
    int insForum(Forum forum);

    /**
     * 根据版块名查询版块
     * @param forum_name 版块名
     * @return 版块对象
     */
    @Select("SELECT * FROM forum WHERE forum_name = #{fourm_name}")
    Forum selForumByForumName(String forum_name);
}
