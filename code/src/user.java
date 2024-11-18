
public class user {
    private int userId;
    private String username;
    private String realName;

    public user(int userId, String username, String realName) {
        this.userId = userId;
        this.username = username;
        this.realName = realName;
    }

    // Get functions
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getRealName() {
        return realName;
    }

}