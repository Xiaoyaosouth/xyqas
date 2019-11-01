package domain;

import lombok.Data;

import java.util.Date;

/**
 * 贴子实体类
 */
@Data
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
    private int tip_replies; // 贴子回复数

}
