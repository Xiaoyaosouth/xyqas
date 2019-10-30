package mapper;

import domain.Tip;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
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

    /**
     * 统计贴子回复数
     * @param tip_id 贴子id
     * @return
     */
    @Select("SELECT COUNT(reply_id) FROM reply " +
            "WHERE tip_id = #{tip_id}")
    int selReplyCountedByTipId(int tip_id);

    /**
     * 刷新贴子更新时间
     * @param tip_id 贴子id
     * @param tip_modifyTime 贴子更新时间
     * @return
     */
    @Update("UPDATE tip SET " +
            "tip_modifyTime = #{arg1} " +
            "WHERE tip_id = #{arg0}")
    int updModifyTimeByTipid(int tip_id, Date tip_modifyTime);

    /**
     * 查询所有贴子id
     * @return List<Integer>含有所有贴子的id
     */
    @Select("SELECT tip_id FROM tip")
    List<Integer> selTipIds();

    /**
     * 更新贴子回复数
     * @param tip_id 贴子id
     * @return
     */
    @Update("UPDATE tip SET tip_replies = " +
            "(SELECT COUNT(reply_id) FROM reply WHERE tip_id = #{tip_id}) " +
            "WHERE tip_id = #{tip_id}")
    int updRepliesByTipId(int tip_id);

    /**
     * 查询标题、内容包含某关键词的贴子
     * @param keyword 关键词
     * @return List<Tip>
     */
    @Select("SELECT * FROM tip " +
            "WHERE tip_title LIKE '%${_parameter}%' " +
            "OR tip_content LIKE '%${_parameter}%'")
    List<Tip> selTipByKeyword(String keyword);

    /**
     * 修改贴子信息
     * @param tip 贴子对象
     * @return
     * @version 1.1 2019-10-29 17:31
     */
    @Update("UPDATE tip SET " +
            "tip_title = #{tip_title}, " +
            "tip_content = #{tip_content}, " +
            "tip_modifyTime = #{tip_modifyTime}, " +
            "tip.tab_id = ${tab_id} " +
            "WHERE tip_id = ${tip_id}")
    int updTip(Tip tip);
}
