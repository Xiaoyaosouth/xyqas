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
     * 逻辑删贴
     * 2019-12-04 15:02 修改入参tip_status为tip_isDeleted
     * 2019-12-04 15:03 修改方法名updTipStatusToDisable为updTipIsDeleted
     * @param tip_id 贴子id
     * @return
     */
    @Update("UPDATE tip SET " +
            "tip_isDeleted = 1 " +
            "WHERE tip_id = #{tip_id}")
    int updTipIsDeleted(int tip_id);

    /**
     * 取消逻辑删贴
     * 2019-12-04 15:02 修改入参tip_status为tip_isDeleted
     * 2019-12-04 15:20 修改方法名updTipStatusToEnable为updTipIsNotDeleted
     * @param tip_id 贴子id
     * @return
     */
    @Update("UPDATE tip SET " +
            "tip_isDeleted = 0 " +
            "WHERE tip_id = #{tip_id}")
    int updTipIsNotDeleted(int tip_id);

    /**
     * 结贴操作
     * @param tip_id 贴子id
     * @return
     */
    @Update("UPDATE tip SET " +
            "tip_isKnot = 1 " +
            "WHERE tip_id = #{tip_id}")
    int updTipToKnot(int tip_id);

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
     * @param tip 贴子对象（包含id和修改时间）
     * @return
     */
    @Update("UPDATE tip SET " +
            "tip_modifyTime = #{tip_modifyTime} " +
            "WHERE tip_id = ${tip_id}")
    int updModifyTime(Tip tip);

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

    /**
     * 修改贴子回复数（+1）
     * @param tip 贴子对象
     * @return
     */
    @Update("UPDATE tip SET " +
            "tip_replies = tip_replies + 1 " +
            "WHERE tip_id = ${tip_id}")
    int updReplisAddOne(Tip tip);
}
