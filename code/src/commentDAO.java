import javax.swing.*;
import java.awt.*;
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
            System.out.println("SQL error: " + e.getMessage());
        }
        return comments;
    }

    public void addComment(comment comment) {

        String query = "INSERT INTO comment (post_ID, user_ID, comment_content, comment_date) VALUES ('" + comment.getPostId() + "', '" + comment.getUserId() + "', '" + comment.getContent() + "', '" + comment.getDate() + "')";

        try (Connection conn = myJDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }


    static void populatePostComments(Font lato, JPanel commentPanel, forumPost post) {
        //set layout to BoxLayout for vertical alignment
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));

        //-------------------------------------- display existing comments ---------------------------------------//
        commentDAO commentDao = new commentDAO();
        ArrayList<comment> comments = commentDao.getCommentsByPostId(post.getPostId());

        for (comment comment : comments) {
            String posterName = ""; //var to hold poster's username
            String query = "SELECT user_username FROM user WHERE user_ID = ?"; //query to get the poster's username

            try (Connection conn = myJDBC.openConnection(); //replace with your JDBC connection utility
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                //set the user_ID parameter from the post
                stmt.setInt(1, comment.getUserId());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        //get poster's user name from the result set
                        posterName = rs.getString("user_username");
                    }
                }

            } catch (SQLException e) {
                System.out.println("SQL error: " + e.getMessage());
                posterName = "Unknown"; //default value in case of an error
            }

            JLabel commentLabel = new JLabel("<html><div style='width:200px;'><b>" +
                    comment.getContent() + "</b><br>" +
                    "posted by " + posterName + " | " + comment.getDate() + "<br><br></div></html>");
            commentLabel.setFont(lato.deriveFont(12f));
            commentLabel.setForeground(Color.BLACK);
            commentLabel.setAlignmentX(Component.CENTER_ALIGNMENT); //align comments to the left
            commentPanel.add(commentLabel);
            commentPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Add spacing between comments
        }
    }
}
