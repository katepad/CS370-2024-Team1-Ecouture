import java.sql.*;
import java.util.ArrayList;

public class forumPostDAO {

    public ArrayList<forumPost> getAllPosts() {
        ArrayList<forumPost> posts = new ArrayList<>();
        String query = "SELECT user_id, post_title, post_content, post_date FROM post ORDER BY post_ID DESC";

        try (Connection conn = myJDBC.openConnection();  // Replace with your JDBC connection utility
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String title = rs.getString("post_title");
                String content = rs.getString("post_content");
                Date postDate = rs.getDate("post_date");

                // Create a forumPost object and add it to the list
                posts.add(new forumPost(id, title, content, postDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }
}