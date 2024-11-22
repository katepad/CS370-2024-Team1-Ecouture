public class comment {

    private int commentId;
    private int postId;
    private int userId;
    private String content;
    private String date;

    public comment(int commentId, int postId, int userId, String content, String date) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.date = date;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

}
