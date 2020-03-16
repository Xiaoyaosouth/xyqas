package mapper;

import domain.Tab;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TabMapper {

    /**
     * 由版块ID查询所有分类
     *
     * @param forum_id 版块id
     * @return List<Tab>
     */
    @Select("SELECT * FROM tab WHERE forum_id = #{forum_id}")
    List<Tab> selTabByForumId(int forum_id);

    /**
     * 查询分类（根据分类id）
     *
     * @param tab_id 分类id
     * @return 分类对象
     */
    @Select("SELECT * FROM tab WHERE tab_id = #{tab_id}")
    Tab selTabByTabId(int tab_id);

    /**
     * 查询所有分类
     *
     * @return
     */
    @Select("SELECT * FROM tab")
    List<Tab> selTabAll();

    /**
     * 修改分类信息
     * v1.1 2020-03-16 22:20 更新修改时间
     * @param tab 分类对象
     * @return
     */
    @Update("UPDATE tab SET " +
            "tab_name = #{tab_name}, forum_id = #{forum_id}, tab_modifyTime = #{tab_modifyTime} " +
            "WHERE tab_id = #{tab_id}")
    int updTab(Tab tab);

    /**
     * 插入一项分类记录
     *
     * @return
     */
    @Insert("INSERT INTO tab " +
            "(tab_name, forum_id) " +
            "VALUES (#{tab_name}, #{forum_id} " +
            ")")
    int insTab(Tab tab);

    /**
     * 逻辑删除分类
     * v1.0 2020-02-28 10:22
     *
     * @param tab_id 分类id
     * @return
     */
    @Update("UPDATE tab SET tab_isDeleted = 1 " +
            "WHERE tab_id = #{tab_id}")
    int updTabIsDeleted(int tab_id);

    /**
     * 取消逻辑删除分类
     * v1.0 2020-02-28 10:24
     *
     * @param tab_id 分类id
     * @return
     */
    @Update("UPDATE tab SET tab_isDeleted = 0 " +
            "WHERE tab_id = #{tab_id}")
    int updTabIsNotDeleted(int tab_id);

    /**
     * 根据版块id查询所有未删除的分类
     * 2020-03-05 18:58
     *
     * @param forum_id 版块id
     * @return List<Tab>
     */
    @Select("SELECT * FROM tab WHERE tab_isDeleted = 0 AND forum_id = #{forum_id}")
    List<Tab> selAllTabUnDeletedByForumId(int forum_id);

    /**
     * 根据版块id查询所有未删除的分类id
     * 2020-03-05 19:00
     * @param forum_id 版块id
     * @return List<Integer>
     */
    @Select("SELECT tab_id FROM tab WHERE tab_isDeleted = 0 AND forum_id = #{forum_id}")
    List<Integer> selAllTabIdUnDeletedByForumId(int forum_id);

    /**
     * 根据版块id查询所有【已删除】的分类id
     * 2020-03-16 21:24
     * @param forum_id 版块id
     * @return List<Integer>
     */
    @Select("SELECT tab_id FROM tab WHERE tab_isDeleted = 1 AND forum_id = #{forum_id}")
    List<Integer> selAllTabIdIsDeletedByForumId(int forum_id);

    /**
     * 根据版块id逻辑删除所有分类
     * 2020-03-05 19:03
     * @param forum_id 版块id
     * @return
     */
    @Update("UPDATE tab SET tab_isDeleted = 1 WHERE forum_id = #{forum_id}")
    int updAllTabIsDeletedByForumId(int forum_id);

    /**
     * 根据版块id取消逻辑删除所有分类
     * 2020-03-05 19:03
     * @param forum_id 版块id
     * @return
     */
    @Update("UPDATE tab SET tab_isDeleted = 0 WHERE forum_id = #{forum_id}")
    int updAllTabIsNotDeletedByForumId(int forum_id);
}
