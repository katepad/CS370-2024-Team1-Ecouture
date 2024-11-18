import java.sql.Date;

public class forumPost {
    private int id;
    private String title;
    private String content;
    private Date postDate;

    // Constructor
    public forumPost(int id, String title, String content, Date postDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postDate = postDate;
    }

    // Getters
    public int getforumId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getPostDate() {
        return postDate;
    }
}