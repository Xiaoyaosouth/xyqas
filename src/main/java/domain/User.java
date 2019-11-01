package domain;

import lombok.Data;

/**
 * 用户实体类
 * @author ruankai
 */
@Data
public class User {
    private int user_id; // 用户id
    private String user_name; // 用户名
    private String user_nick; // 昵称
    private String user_password; // 密码
    private int user_status; // 状态
    private int user_type; // 权限
}
