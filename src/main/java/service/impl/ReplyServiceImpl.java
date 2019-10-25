package service.impl;

import domain.Reply;
import mapper.ReplyMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import service.ReplyService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService{
    @Resource
    private ReplyMapper replyMapper;

    @Override
    public String addReply(Reply reply) {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        logger.info("id为" + reply.getUser_id() + "的用户尝试发表回复...");
        int result = replyMapper.insReply(reply);
        if (result > 0){
            return "success";
        }else {
            return "error";
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
