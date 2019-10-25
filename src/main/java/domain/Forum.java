package domain;

/**
 * 大板块实体类
 */
public class Forum {

    private int forum_id; // 大板块id
    private String forum_name; // 大板块名

    public int getForum_id() {
        return forum_id;
    }

    public void setForum_id(int forum_id) {
        this.forum_id = forum_id;
    }

    public String getForum_name() {
        return forum_name;
    }

    public void setForum_name(String forum_name) {
        this.forum_name = forum_name;
    }
}
