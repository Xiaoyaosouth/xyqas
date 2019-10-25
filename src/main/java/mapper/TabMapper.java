package mapper;

import domain.Tab;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TabMapper {

    /**
     * 由大板块ID查询小板块
     * @param forum_id 大板块id
     * @return List<Tab>
     */
    @Select("SELECT * FROM tab WHERE forum_id=#{forum_id}")
    List<Tab> selTabByForumId(int forum_id);

    /**
     * 查询小板块
     * @param tab_id 小板块id
     * @return 小板块对象
     */
    @Select("SELECT * FROM tab WHERE tab_id=#{tab_id}")
    Tab selTabByTabId(int tab_id);

    /**
     * 查询所有小板块
     * @return
     */
    @Select("SELECT * FROM tab")
    List<Tab> selTabAll();

    /**
     * 修改分类信息
     * @param tab 分类对象
     * @return
     */
    @Update("UPDATE tab SET " +
            "tab_name = #{tab_name}, forum_id = #{forum_id} " +
            "WHERE tab_id = #{tab_id}")
    int updTab(Tab tab);
}
