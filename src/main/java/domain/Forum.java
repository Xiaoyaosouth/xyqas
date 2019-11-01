package domain;

import lombok.Data;

/**
 * 版块实体类
 * @author ruankai
 */
@Data
public class Forum {

    private int forum_id; // 版块id
    private String forum_name; // 版块名
    private int forum_status; // 版块状态

}
