
public class user {
    private static int userId;
    private final String realName;

    public user(int userId, String realName) {
        user.userId = userId;
        this.realName = realName;
    }

    // Get functions
    public static int getUserId() {
        return userId;
    }

    public String getRealName() {
        return realName;
    }

}