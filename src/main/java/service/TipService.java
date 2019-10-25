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
     * 结贴，让贴子不能再修改或回复
     * @param tip_id
     * @return
     */
    String knotTip(int tip_id);

    /**
     * 贴子恢复正常
     * @param tip_id 贴子id
     * @return
     */
    String enableTip(int tip_id);

    /**
     * 获取所有贴子（按更新时间倒序排列）
     * @return List<Tip>
     */
    List<Tip> getAllTipForModifyTimeDesc();
}
