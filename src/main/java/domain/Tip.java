package domain;

import java.util.Date;

/**
 * 贴子实体类
 */
public class Tip {

    private User user; // 用户
    private Tab tab; // 小板块
    private int user_id; // 楼主id
    private int tab_id; // 小板块id

    private int tip_id; // 贴子id
    private String tip_title; // 标题
    private String tip_content; // 内容
    private Date tip_publishTime; // 发表时间
    private Date tip_modifyTime; // 修改时间
    private int tip_click; // 点击量
    private int tip_status; // 贴子状态

    private int tip_isKnot; // 是否结贴

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTab_id() {
        return tab_id;
    }

    public void setTab_id(int tab_id) {
        this.tab_id = tab_id;
    }

    public int getTip_id() {
        return tip_id;
    }

    public void setTip_id(int tip_id) {
        this.tip_id = tip_id;
    }

    public String getTip_title() {
        return tip_title;
    }

    public void setTip_title(String tip_title) {
        this.tip_title = tip_title;
    }

    public String getTip_content() {
        return tip_content;
    }

    public void setTip_content(String tip_content) {
        this.tip_content = tip_content;
    }

    public Date getTip_publishTime() {
        return tip_publishTime;
    }

    public void setTip_publishTime(Date tip_publishTime) {
        this.tip_publishTime = tip_publishTime;
    }

    public Date getTip_modifyTime() {
        return tip_modifyTime;
    }

    public void setTip_modifyTime(Date tip_modifyTime) {
        this.tip_modifyTime = tip_modifyTime;
    }

    public int getTip_click() {
        return tip_click;
    }

    public void setTip_click(int tip_click) {
        this.tip_click = tip_click;
    }

    public int getTip_status() {
        return tip_status;
    }

    public void setTip_status(int tip_status) {
        this.tip_status = tip_status;
    }

    public int getTip_isKnot() {
        return tip_isKnot;
    }

    public void setTip_isKnot(int tip_isKnot) {
        this.tip_isKnot = tip_isKnot;
    }
}
