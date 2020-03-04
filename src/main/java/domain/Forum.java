package domain;

import lombok.Data;

import java.util.Date;

/**
 * 版块实体类
 * v1.1 2020-02-28 10:18 删除版块状态属性，增加是否删除属性
 * v1.2 2020-03-04 11:54 增加创建时间、修改时间属性
 */
@Data
public class Forum {

    private int forum_id; // 版块id
    private String forum_name; // 版块名
    // private int forum_status; // 版块状态
    private int forum_isDeleted; // 是否删除，0-否，1-是
    private Date forum_createTime; // 创建时间
    private Date forum_modifyTime; // 修改时间

}
