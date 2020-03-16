package mapper;

import domain.Forum;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ForumMapper {

    /**
     * 查询版块（根据版块id）
     * @param forum_id 版块id
     * @return 版块对象
     */
    @Select("SELECT * FROM forum WHERE forum_id=#{forum_id}")
    Forum selForumByForumId(int forum_id);

    /**
     * 查询所有版块
     * @return List<Forum>
     */
    @Select("SELECT * FROM forum")
    List<Forum> selForumAll();

    /**
     * 修改版块
     * v1.1 2020-03-16 21:58 更新修改时间
     * @param forum 版块对象
     * @return
     */
    @Update("UPDATE forum SET " +
            "forum_name = #{forum_name},forum_modifyTime = #{forum_modifyTime} " +
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

    /**
     * 逻辑删除版块
     * v1.0 2020-02-28 10:25
     * @param forum_id 版块id
     * @return
     */
    @Update("UPDATE forum SET forum_isDeleted = 1 " +
            "WHERE forum_id = #{forum_id}")
    int updForumIsDeleted(int forum_id);

    /**
     * 取消逻辑删除版块
     * v1.0 2020-02-28 10:27
     * @param forum_id 版块id
     * @return
     */
    @Update("UPDATE forum SET forum_isDeleted = 0 " +
            "WHERE forum_id = #{forum_id}")
    int updForumIsNotDeleted(int forum_id);
}
