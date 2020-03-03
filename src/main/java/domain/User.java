package domain;

import lombok.Data;

import java.util.Date;

/**
 * 用户实体类
 * v1.1 2020-03-03 12:01 增加注册时间属性，修改属性注释
 */
@Data
public class User {
    private int user_id; // 用户id
    private String user_name; // 用户名
    private String user_nick; // 昵称
    private String user_password; // 密码
    private int user_status; // 状态，0-正常，1-禁用，2-锁定
    private int user_type; // 权限，0-超级管理员，1-管理员，2-普通用户
    private Date user_regTime; // 注册时间
}
