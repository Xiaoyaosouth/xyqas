package service;

import domain.Reply;

import java.util.List;

public interface ReplyService {

    /**
     * 发表回复
     * @return
     */
    String addReply(Reply reply);

    /**
     * 由贴子id获取回复
     * @param tip_id 贴子id
     * @return List<Reply>
     */
    List<Reply> getReplyByTipId(int tip_id);

}
