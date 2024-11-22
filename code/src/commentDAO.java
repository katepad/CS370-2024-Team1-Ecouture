import java.sql.*;
import java.util.ArrayList;

public class commentDAO {

    public ArrayList<comment> getCommentsByPostId(int postId) {
        ArrayList<comment> comments = new ArrayList<>();
        String query = "SELECT * FROM comment WHERE post_ID = ? ORDER BY post_ID DESC";
        //select all comments for a specific post

        try (Connection conn = myJDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, postId);

            //get values for each comment part
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    comment comment = new comment(
                            rs.getInt("comment_ID"),
                            rs.getInt("post_ID"),
                            rs.getInt("user_ID"),
                            rs.getString("comment_content"),
                            rs.getString("comment_date")
                    );
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public void addComment(comment comment) {

        String query = "INSERT INTO comment (post_ID, user_ID, comment_content, comment_date) VALUES ('" + comment.getPostId() + "', '" + comment.getUserId() + "', '" + comment.getContent() + "', '" + comment.getDate() + "')";

        try (Connection conn = myJDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
