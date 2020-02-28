package domain;

import lombok.Data;

/**
 * 版块实体类
 * v1.1 2020-02-28 10:18 删除版块状态属性，增加是否删除属性
 */
@Data
public class Forum {

    private int forum_id; // 版块id
    private String forum_name; // 版块名
    // private int forum_status; // 版块状态
    private int forum_isDeleted; // 是否删除，0-否，1-是

}
