package service.impl;

import domain.Tip;
import mapper.TipMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import service.TipService;

import javax.annotation.Resource;
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

    @Override
    public List<Tip> getAllTip() {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试获取所有贴子...");
        List<Tip> tipList = tipMapper.selTipAll();
        if (tipList != null){
            return tipList;
        }
        return null;
    }

    @Override
    public Tip getTipByTipId(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试获取ID为" + tip_id + "的贴子...");
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
        int result = tipMapper.updTipStatusToEnable(tip_id);
        if (result > 0){
            return "success";
        }else {
            return "error";
        }
    }

    @Override
    public List<Tip> getAllTipForModifyTimeDesc() {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试获取所有贴子，并按更新时间倒序排列...");
        List<Tip> tipList = tipMapper.selTipAllForModifyTimeDesc();
        if (tipList != null){
            return tipList;
        }
        return null;
    }
}
