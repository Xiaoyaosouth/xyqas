package domain;

import java.util.Date;

/**
 * 回复实体类
 */
public class Reply {

    private User user; // 用户
    private Tip tip; // 贴子

    private int user_id; // 回复用户的id
    private int tip_id; // 被回复的贴子id

    private int reply_id; // 回复id
    private String reply_content; // 回复内容
    private Date reply_publishTime; // 回复发表时间

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTip_id() {
        return tip_id;
    }

    public void setTip_id(int tip_id) {
        this.tip_id = tip_id;
    }

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public Date getReply_publishTime() {
        return reply_publishTime;
    }

    public void setReply_publishTime(Date reply_publishTime) {
        this.reply_publishTime = reply_publishTime;
    }
}
