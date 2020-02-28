package domain;

import lombok.Data;

import java.util.Date;

/**
 * 回复实体类
 * v1.1 2020-02-28 10:20 删除回复状态属性，增加是否删除属性
 */
@Data
public class Reply {

    private User user; // 用户
    private Tip tip; // 贴子

    private int user_id; // 回复用户的id
    private int tip_id; // 被回复的贴子id

    private int reply_id; // 回复id
    private String reply_content; // 回复内容
    private Date reply_publishTime; // 回复发表时间
    private Date reply_modifyTime; // 回复修改时间
    // private int reply_status; // 回复状态
    private int reply_isDeleted; // 是否删除，0-否，1-是

}
