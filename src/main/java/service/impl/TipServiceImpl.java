package service.impl;

import domain.Tip;
import mapper.TipMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import service.TipService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TipServiceImpl implements TipService{
    @Resource
    private TipMapper tipMapper;

    @Override
    public String addTip(Tip tip) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("id为" + tip.getUser_id() + "的用户尝试发表贴子...");
        int result = tipMapper.insTip(tip);
        if (result > 0){
            return "success";
        }else{
            return "error";
        }
    }

    /**
     * （已弃用）
     * @return
     */
    @Override
    public List<Tip> getAllTip() {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试获取所有贴子信息...");
        List<Tip> tipList = tipMapper.selTipAll();
        if (tipList != null){
            return tipList;
        }
        return null;
    }

    @Override
    public Tip getTipByTipId(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试获取ID为" + tip_id + "的贴子信息...");
        Tip tip = tipMapper.selTipByTipId(tip_id);
        if (tip != null){
            return tip;
        }
        return null;
    }

    @Override
    public String addTipClick(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试给ID为" + tip_id + "的贴子点击量+1...");
        int result = tipMapper.updTipClick(tip_id);
        if (result > 0){
            return "success";
        }else{
            return "error";
        }
    }

    @Override
    public String disableTip(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试逻辑删除id为" + tip_id + "的贴子...");
        int result = tipMapper.updTipStatusToDisable(tip_id);
        if (result > 0){
            return "success";
        }else {
            return "error";
        }
    }

    @Override
    public String knotTip(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试结贴id为" + tip_id + "的贴子...");
        int result = tipMapper.updTipToKnot(tip_id);
        if (result > 0){
            return "success";
        }else {
            return "error";
        }
    }

    @Override
    public String enableTip(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试将id为" + tip_id + "的贴子恢复正常...");
        int result = tipMapper.updTipToUnKnot(tip_id);
        if (result > 0){
            return "success";
        }else {
            return "error";
        }
    }

    @Override
    public List<Tip> getAllTipForModifyTimeDesc() {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        // 先更新贴子回复数
        this.updateAllReplies();
        // 获取所有贴子信息
        logger.info("尝试获取所有贴子，并按更新时间倒序排列");
        List<Tip> tipList = tipMapper.selTipAllForModifyTimeDesc();
        if (tipList != null){
            return tipList;
        }
        return null;
    }

    @Override
    public int countReplyByTipId(int tip_id) {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        logger.info("尝试统计贴子id" + tip_id + "的回复数...");
        int result = tipMapper.selReplyCountedByTipId(tip_id);
        return result;
    }

    /**
     * 刷新贴子更新时间
     * @param tip_id
     * @param tip_modifyTime
     */
    @Override
    public int updateModifyTimeByTipId(int tip_id, Date tip_modifyTime){
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        logger.info("尝试刷新贴子id为" + tip_id + "的更新时间：" + tip_modifyTime);
        int result = tipMapper.updModifyTimeByTipid(tip_id, tip_modifyTime);
        return result;
    }

    @Override
    public int updateRepliesByTipId(int tip_id) {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        logger.info("尝试更新贴子id为" + tip_id + "的回复数");
        int result = tipMapper.updRepliesByTipId(tip_id);
        return result;
    }

    @Override
    public int updateAllReplies() {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        int result = 0; // 保存更新结果
        logger.info("尝试获取所有贴子的id");
        List<Integer> tipIdsList = tipMapper.selTipIds();
        // 逐个贴子更新
        for (int i = 0; i < tipIdsList.size(); i++){
            logger.info("尝试更新贴子id为" + tipIdsList.get(i) + "的回复数");
            int tmpResult = tipMapper.updRepliesByTipId(tipIdsList.get(i));
            if (tmpResult > 0){
                result ++;
            }
        }
        return result;
    }

}
