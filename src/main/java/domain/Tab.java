package domain;

/**
 * 小板块实体类
 */
public class Tab {

    private Forum forum; // 大板块
    private int forum_id; // 小板块对应的大板块id

    private int tab_id; // 小板块id
    private String tab_name; // 小板块名

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public int getForum_id() {
        return forum_id;
    }

    public void setForum_id(int forum_id) {
        this.forum_id = forum_id;
    }

    public int getTab_id() {
        return tab_id;
    }

    public void setTab_id(int tab_id) {
        this.tab_id = tab_id;
    }

    public String getTab_name() {
        return tab_name;
    }

    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
    }
}
