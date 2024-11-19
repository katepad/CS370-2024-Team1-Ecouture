import java.sql.*;
import java.util.ArrayList;

public class forumPostDAO {

    public ArrayList<forumPost> getAllPosts() {
        ArrayList<forumPost> posts = new ArrayList<>();
        String query = "SELECT user_ID, post_ID, post_title, post_content, post_date FROM post ORDER BY post_ID DESC";

        try (Connection conn = myJDBC.openConnection();  // Replace with your JDBC connection utility
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int userID = rs.getInt("user_ID");
                int postID = rs.getInt("post_ID");
                String title = rs.getString("post_title");
                String content = rs.getString("post_content");
                Date postDate = rs.getDate("post_date");

                // Create a forumPost object and add it to the list
                posts.add(new forumPost(userID, postID, title, content, postDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }
}