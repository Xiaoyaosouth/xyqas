package service;

import domain.Tip;

import java.util.List;

public interface TipService {

    /**
     * 添加贴子
     * @param tip
     */
    String addTip(Tip tip);

    /**
     * 获取所有贴子
     * @return List<Tip>
     */
    List<Tip> getAllTip();

    /**
     * 由贴子id获得贴子
     * @param tip_id 贴子id
     * @return 贴子对象
     */
    Tip getTipByTipId(int tip_id);

    /**
     * 给贴子点击量+1
     * @param tip_id 贴子id
     * @return
     */
    String addTipClick(int tip_id);

    /**
     * 逻辑删贴
     * @param tip_id
     * @return
     */
    String disableTip(int tip_id);

    /**
     * 取消逻辑删贴
     * @param tip_id 贴子id
     * @return
     */
    String enableTip(int tip_id);

    /**
     * 结贴，让贴子不能再修改或回复
     * @param tip_id
     * @return
     */
    String knotTip(int tip_id);

    /**
     * 取消结贴
     * 2019-12-04 15:36
     * @param tip_id 贴子id
     * @return
     */
    String disNnotTip(int tip_id);

    /**
     * 获取所有贴子（按更新时间倒序排列）
     * @return List<Tip>
     */
    List<Tip> getAllTipForModifyTimeDesc();

    /**
     * 统计回复数
     * @param tip_id 贴子id
     * @return
     */
    int countReplyByTipId(int tip_id);

    /**
     * 刷新贴子更新时间
     * @param tip 贴子对象
     * @return
     */
    int updateModifyTime(Tip tip);

    /**
     * 更新贴子回复量
     * @param tip_id 贴子id
     * @return
     */
    int updateRepliesByTipId(int tip_id);

    /**
     * 更新所有贴子的回复数
     * @return
     */
    int updateAllReplies();

    /**
     * 搜索标题、内容含某关键词的贴子
     * @param keyword 关键词
     * @return List<Tip>
     */
    List<Tip> searchTipByKeyword(String keyword);

    /**
     * 修改贴子信息
     * @param tip 贴子对象
     * @return
     */
    String modifyTip(Tip tip);

    /**
     * 贴子置顶
     * 2020-02-27 09:48
     * @param tip_id 贴子id
     * @return
     */
    String doTopTip(int tip_id);

    /**
     * 取消置顶
     * 2020-02-27 09:49
     * @param tip_id 贴子id
     * @return
     */
    String disTopTip(int tip_id);
}
