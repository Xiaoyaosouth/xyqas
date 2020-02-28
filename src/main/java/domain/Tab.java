package domain;

import lombok.Data;

/**
 * 分类实体类
 * v1.1 2020-02-28 10:17 删除分类状态属性，增加是否删除属性
 */
@Data
public class Tab {

    private Forum forum; // 版块
    private int forum_id; // 版块id

    private int tab_id; // 分类id
    private String tab_name; // 分类名
    // private int tab_status; // 分类状态
    private int tab_isDeleted; // 是否删除，0-否，1-是

}
