package service.impl;

import domain.Reply;
import domain.Tip;
import mapper.ReplyMapper;
import mapper.TipMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import service.ReplyService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService{
    @Resource
    private ReplyMapper replyMapper;

    @Resource
    private TipMapper tipMapper;

    @Override
    public String addReply(Reply reply) {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        logger.info("【用户回复贴子】");
        String resultStr = null;
        // 插入数据到回复表
        if (replyMapper.insReply(reply) > 0){
            // 发表回复成功则令贴子回复数+1
            tipMapper.updReplisAddOne(reply.getTip());
            // 刷新贴子更新时间
            reply.getTip().setTip_modifyTime(reply.getReply_publishTime());
            tipMapper.updModifyTime(reply.getTip());
            return "发表回复成功！";
        }else {
            return "发表回复失败！";
        }
    }

    @Override
    public List<Reply> getReplyByTipId(int tip_id) {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        logger.info("尝试获取贴子id为" + tip_id + "的所有回复...");
        List<Reply> replyList = replyMapper.selReplyByTipId(tip_id);
        if (replyList != null){
            return  replyList;
        }
        return null;
    }

}
