import java.sql.Date;


public class forumPost {
    private int userID;
    private int postID;
    private String title;
    private String content;
    private Date postDate;

    // Constructor
    public forumPost(int userID, int postID, String title, String content, Date postDate) {
        this.userID = userID;
        this.postID = postID;
        this.title = title;
        this.content = content;
        this.postDate = postDate;
    }

    //get functions
    public int getPostId() {
        return postID;
    }

    public int getUserId() {
        return userID;
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