package dataAccess;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import model.forumPost;
import view.editForumView;
import model.user;
import view.forumView;

public class forumPostDAO {

    public ArrayList<forumPost> getAllPosts() {
        ArrayList<forumPost> posts = new ArrayList<>();
        //query to get all post details.
        String query = "SELECT user_ID, post_ID, post_title, post_content, post_date FROM post ORDER BY post_ID DESC";

        try (Connection conn = myJDBC.openConnection();
             //execute the query
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            //add post to the array of posts belonging to logged user.
            while (rs.next()) {
                int userID = rs.getInt("user_ID");
                int postID = rs.getInt("post_ID");
                String title = rs.getString("post_title");
                String content = rs.getString("post_content");
                Date postDate = rs.getDate("post_date");

                //create a forumPost object and add it to the list
                posts.add(new forumPost(userID, postID, title, content, postDate));
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return posts;
    }

    public static void submitPost(Font oswald, Font lato, editForumView editForumView, user user){
        try {

            Connection connect = myJDBC.openConnection();
            //Ensure there is a connection to the database
            if (connect == null || connect.isClosed()) {
                System.out.println("Database connection is not established.");
                return; //exit the method if connection is not valid
            }

            //If fields are successful...
            editForumView.title = editForumView.ptField.getText().replace("'", "''"); //escape apostrophes to avoid sql ' errors.
            editForumView.content = editForumView.pcField.getText().replace("'", "''"); //escape apostrophes to avoid sql ' errors.

            if (editForumView.title.isEmpty() || editForumView.content.isEmpty()) {
                editForumView.postErrorMsg.setVisible(true);
                editForumView.postErrorMsg.setText("One of the fields is left blank.");
            } else { //if there are no errors,
                Statement statement = myJDBC.connect.createStatement();
                editForumView.date = Date.valueOf(LocalDate.now()); //java.sql.Date for SQL compatibility

                //save post into database
                String sql = "INSERT INTO post (post_title, post_content, post_date, user_ID) VALUES ('" + editForumView.title + "', '" + editForumView.content + "', '" + editForumView.date + "', '" + editForumView.userID + "')";
                statement.executeUpdate(sql);

                //Switch back to the forumView Panel.
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(editForumView);
                topFrame.getContentPane().removeAll(); //Clear all components from the current frame
                forumView forumView = new forumView(oswald, lato, user);
                topFrame.add(forumView, BorderLayout.CENTER); //Add forumView Panel to the frame
                topFrame.revalidate(); //Refresh the frame
                topFrame.repaint(); //Repaint the frame

                System.out.println("Post Successful");
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }

    public static void updatePost(forumPost post, String newTitle, String newContent, Font oswald, Font lato, JPanel editForumView, forumView forumView, user user) {
        try (Connection conn = myJDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE post SET post_title = ?, post_content = ? WHERE post_ID = ?")) {
            stmt.setString(1, newTitle);
            stmt.setString(2, newContent);
            stmt.setInt(3, post.getPostId());
            stmt.executeUpdate();

            //------------------------------- CREATE UPDATE MESSAGE WITH DIALOG MODAL -------------------------------//
            JDialog updateMessage = new JDialog((Frame) SwingUtilities.getWindowAncestor(forumView), "Post Update", true);
            updateMessage.setLayout(new BorderLayout());
            updateMessage.setSize(300, 150);
            updateMessage.setBackground(new Color(247, 248, 247));
            updateMessage.setLocationRelativeTo(editForumView); //center updateMessage Box relative to the editForumView

            //create the confirmation message and OK button
            JLabel updateMessageText = new JLabel("<html><div style='width:150px; text-align: center;'><b>Post updated successfully!</b></div></html>");
            JPanel buttonPanel = new JPanel();
            JButton okButton = new JButton("OK");

            //------------------------------------------- redesign labels --------------------------------------------//
            updateMessageText.setHorizontalAlignment(SwingConstants.CENTER);
            updateMessageText.setFont(lato.deriveFont(18f));
            //--------------------------------------------------------------------------------------------------------//

            //------------------------------------------- redesign buttons -------------------------------------------//
            //ok
            okButton.setBackground(new Color (0, 99, 73)); //set button color to green
            okButton.setForeground(new Color (247, 248, 247)); //set button text color to white
            okButton.setFont(oswald.deriveFont(14f)); //change button text font to oswald, size 14.
            okButton.setFocusable(false);
            //--------------------------------------------------------------------------------------------------------//

            okButton.addActionListener(e -> updateMessage.dispose()); //Close dialog on "OK"

            //add components to the update confirmation
            updateMessage.add(updateMessageText, BorderLayout.CENTER);
            buttonPanel.add(okButton, BorderLayout.CENTER);
            updateMessage.add(buttonPanel, BorderLayout.SOUTH);

            updateMessage.setVisible(true); //show update confirmation box

            //--------------------------------------------------------------------------------------------------------//

            //get the JFrame that contains editForumView
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(editForumView);
            if (topFrame != null) {
                topFrame.getContentPane().removeAll(); //Clear all components from the current frame
                forumView newforumView = new forumView(oswald, lato, user); //Re-create the forumView
                topFrame.add(newforumView, BorderLayout.CENTER); //Add forumView to the frame
                topFrame.revalidate(); //Refresh the frame
                topFrame.repaint(); //Repaint the frame
            } else {
                //handle the case when topFrame is still null
                System.err.println("Failed to get the parent JFrame.");
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    public static void deletePost(forumPost post, Font oswald, Font lato, forumView forumView, user user){
        try (Connection conn = myJDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM post WHERE post_ID = ?")) {
            stmt.setInt(1, post.getPostId());
            stmt.executeUpdate();

            System.out.println("post deleted successfully!");

            //----------------------------------- Refresh the forumView panel. -----------------------------------//
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(forumView);
            topFrame.getContentPane().removeAll(); //Clear all components from the current frame
            forumView newforumView = new forumView(oswald, lato, user);
            topFrame.add(newforumView, BorderLayout.CENTER); //Add forumView Panel to the frame
            topFrame.revalidate(); //Refresh the frame
            topFrame.repaint(); //Repaint the frame
            //----------------------------------------------------------------------------------------------------//

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }
}