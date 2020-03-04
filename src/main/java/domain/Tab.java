package domain;

import lombok.Data;

import java.util.Date;

/**
 * 分类实体类
 * v1.1 2020-02-28 10:17 删除分类状态属性，增加是否删除属性
 * v1.2 2020-03-04 10:51 增加创建时间、修改时间属性
 */
@Data
public class Tab {

    private Forum forum; // 版块
    private int forum_id; // 版块id

    private int tab_id; // 分类id
    private String tab_name; // 分类名
    // private int tab_status; // 分类状态
    private int tab_isDeleted; // 是否删除，0-否，1-是
    private Date tab_createTime; // 创建时间
    private Date tab_modifyTime; // 修改时间

}
