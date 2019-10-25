package mapper;

import domain.Tip;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TipMapper {
    /**
     * 添加贴子
     * @param tip 贴子对象
     * @return
     */
    @Insert("INSERT INTO tip " +
            "(tip_title, tip_content, user_id, tab_id, tip_publishTime, tip_modifyTime ) " +
            "VALUES " +
            "(#{tip_title}, #{tip_content}, #{user_id}, #{tab_id}, #{tip_publishTime}, #{tip_modifyTime} " +
            ")")
    int insTip(Tip tip);

    /**
     * 获取所有贴子
     * @return List<Tip>
     */
    @Select("SELECT * FROM tip")
    List<Tip> selTipAll();

    /**
     * 查询贴子
     * @param tip_id 贴子id
     * @return 贴子对象
     */
    @Select("SELECT * FROM tip WHERE tip_id = #{tip_id}")
    Tip selTipByTipId(int tip_id);

    /**
     * 贴子点击量+1
     * @param tip_id 贴子id
     * @return
     */
    @Update("UPDATE tip SET " +
            "tip_click = tip_click + 1 " +
            "WHERE tip_id = #{tip_id}")
    int updTipClick(int tip_id);

    /**
     * 结贴
     * @param tip_id 贴子id
     * @return
     */
    @Update("UPDATE tip SET " +
            "tip_isKnot = 1 " +
            "WHERE tip_id = #{tip_id}")
    int updTipToKnot(int tip_id);

    /**
     * 修改贴子状态（逻辑删贴）
     * @param tip_id
     * @return
     */
    @Update("UPDATE tip SET " +
            "tip_status = 1 " +
            "WHERE tip_id = #{tip_id}")
    int updTipStatusToDisable(int tip_id);

    /**
     * 修改贴子状态（正常）
     * @param tip_id
     * @return
     */
    @Update("UPDATE tip SET " +
            "tip_status = 0 " +
            "WHERE tip_id = #{tip_id}")
    int updTipStatusToEnable(int tip_id);

    /**
     * 取消结贴
     * @param tip_id 贴子id
     * @return
     */
    @Update("UPDATE tip SET " +
            "tip_isKnot = 0 " +
            "WHERE tip_id = #{tip_id}")
    int updTipToUnKnot(int tip_id);

    /**
     * 获取所有贴子（按更新时间倒序排列）
     * @return List<Tip>
     */
    @Select("SELECT * FROM tip ORDER BY tip_modifyTime DESC")
    List<Tip> selTipAllForModifyTimeDesc();
}
