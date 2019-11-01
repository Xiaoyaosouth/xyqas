package domain;

import lombok.Data;

/**
 * 分类实体类
 */
@Data
public class Tab {

    private Forum forum; // 版块
    private int forum_id; // 版块id

    private int tab_id; // 分类id
    private String tab_name; // 分类名
    private int tab_status; // 分类状态

}
