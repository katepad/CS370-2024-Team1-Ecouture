import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class forumView extends JPanel {

    private final user user;

    // Constructor
    forumView(Font oswald, Font lato, user user) throws SQLException {
        //----------------------- Set background color and preferred size -----------------------------------------------
        this.setBackground(new Color(235, 219, 195));
        this.setLayout(new BorderLayout());  // Use BorderLayout to properly place the navigation bar at the bottom
        //--------------------------------------------------------------------------------------------------------------

        this.user = user;

        //------------------------------- Create add button and title --------------------------------------------------//
        ImageIcon addButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/plus2.png")));
        Image image = addButton.getImage();
        Image newImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImage);

        JButton addPostButton = new JButton(newIcon);
        addPostButton.setContentAreaFilled(false);
        addPostButton.setBorderPainted(false);
        addPostButton.setFocusPainted(false);
        addPostButton.setOpaque(false);

        // Create title
        JLabel pageTitle = new JLabel("ECOUTURE COMMUNITY");
        pageTitle.setForeground(new Color(0, 99, 73));
        pageTitle.setFont(oswald.deriveFont(20f));
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);

        // Create top panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(235, 219, 195));

        // Add them to panel
        headerPanel.add(addPostButton, BorderLayout.EAST);
        headerPanel.add(pageTitle, BorderLayout.CENTER);

        // Set panel to top of screen
        this.add(headerPanel, BorderLayout.NORTH);

        //------------------- CALL NAVIGATION BAR AND SET BOUNDS -------------------------------------------------------
        navigationBarView navigationBarView = new navigationBarView(oswald, lato, user);
        this.add(navigationBarView, BorderLayout.SOUTH);  // Place navigation bar at the bottom (SOUTH)
        // -------------------------------------------------------------------------------------------------------------

        //------------------- CREATE items to go in panel -------------------------------------------------------------
        forumPostDAO postDAO = new forumPostDAO(); // Initialize DAO
        ArrayList<forumPost> posts = postDAO.getAllPosts(); // Fetch posts

        // Create panel to display posts
        JPanel chatItemsPanel = new JPanel();
        chatItemsPanel.setLayout(new BoxLayout(chatItemsPanel, BoxLayout.Y_AXIS));
        chatItemsPanel.setBackground(new Color(247, 248, 247));  //make white background

        //create the individual panels for the individual forum posts.
        chatItemsPanel.removeAll(); //clear all existing components before populating forum posts.
        populateForumPosts(posts, oswald, lato, chatItemsPanel);

        //put chatItemsPanel in scroll pane
        createScroll(chatItemsPanel);

        //ad post button action listener
        addPostButton.addActionListener(e -> addPostButtonActionPerformed(e, oswald, lato));
    }

    private void populateForumPosts(ArrayList<forumPost> posts, Font oswald, Font lato, JPanel chatItemsPanel){
        //add posts dynamically
        for (forumPost post : posts) {

            String posterName = ""; //var to hold poster's username

            String query = "SELECT user_username FROM user WHERE user_ID = ?"; //query to get the poster's username

            try (Connection conn = myJDBC.openConnection(); //replace with your JDBC connection utility
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                //set the user_ID parameter from the post
                stmt.setInt(1, post.getUserId());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        //get poster's real name from the result set
                        posterName = rs.getString("user_username");
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                posterName = "Unknown"; //default value in case of an error
            }

            createChatPanel(oswald, lato, post, posterName, chatItemsPanel);

        }
    }

    private void createChatPanel(Font oswald, Font lato, forumPost post, String posterName, JPanel chatItemsPanel) {

        //----------------------------------------------- BUTTON PANEL -----------------------------------------------//

        if (post.getUserId() == this.user.getUserId()) { //show button panel only for posts that belong to logged-in user
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));

            //remove item button
            ImageIcon minusIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/minus.png")));
            Image scaledMinusImage = minusIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            JButton deleteButton = new JButton(new ImageIcon(scaledMinusImage));
            deleteButton.setContentAreaFilled(false);
            deleteButton.setBorderPainted(false);
            deleteButton.setFocusPainted(false);
            deleteButton.setOpaque(false);
            deleteButton.addActionListener(e -> createDeleteConfirmationBox(post, oswald, lato));
            buttonPanel.add(deleteButton);

            //edit item button
            ImageIcon editIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/editButton.png")));
            Image scalededitImage = editIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            JButton editButton = new JButton(new ImageIcon(scalededitImage));
            editButton.setContentAreaFilled(false);
            editButton.setBorderPainted(false);
            editButton.setFocusPainted(false);
            editButton.setOpaque(false);
            editButton.addActionListener(e -> editPost(post, oswald, lato));
            buttonPanel.add(editButton);

            buttonPanel.setBackground(new Color(247, 248, 247));

            //add padding inside the panel and a border
            buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(5, 10, 0, 10, new Color(235, 219, 195)), //outer border with different thickness
                    BorderFactory.createEmptyBorder(10, 10, 10, 10) //inner padding
            ));

            chatItemsPanel.add(buttonPanel);
        }
        //------------------------------------------------------------------------------------------------------------//


        //-------------------------------------------- FORUM POST PANEL ----------------------------------------------//
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS)); //arrange labels vertically
        postPanel.setBackground(new Color(247, 248, 247)); //set background for the post panel

        //get all elements of the post
        String postTitle = "<html><div style='width:250px;'>" + post.getTitle().toUpperCase() + "</div></html>";
        String postDetails = "<html>Posted by " + posterName + " on " + post.getPostDate() + "</html>";
        String postContent = "<html><div style='width:250px;'>" + post.getContent() + "</div></html>";

        JLabel postTitleLabel = new JLabel(postTitle);
        JLabel postDetailsLabel = new JLabel(postDetails);
        JLabel postContentLabel = new JLabel(postContent);

        postTitleLabel.setFont(oswald.deriveFont(18f));
        postTitleLabel.setForeground(new Color(0, 99, 73));

        postDetailsLabel.setFont(lato.deriveFont(10f));
        postDetailsLabel.setForeground(Color.BLACK);

        postContentLabel.setFont(lato.deriveFont(16f));
        postContentLabel.setForeground(Color.BLACK);

        postTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        postDetailsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        postContentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //add the labels to the post panel
        postPanel.add(postTitleLabel);
        postPanel.add(postDetailsLabel);
        postPanel.add(postContentLabel);

        if (post.getUserId() == this.user.getUserId()) { // if there is a buttonPanel showing
            postPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 10, 5, 10, new Color(235, 219, 195)), // Outer border with different thickness
                    BorderFactory.createEmptyBorder(10, 10, 10, 10) // Inner padding
            ));
        } else {
            postPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(5, 10, 5, 10, new Color(235, 219, 195)), // Outer border with different thickness
                    BorderFactory.createEmptyBorder(10, 10, 10, 10) // Inner padding
            ));
        }

        chatItemsPanel.add(postPanel);
        //------------------------------------------------------------------------------------------------------------//
    }

    //TODO: add to the controller
    private void editPost(forumPost post, Font oswald, Font lato) {
        try {
            //switch to the editForumView Page
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); //clear all components from the current frame

            //create editForumView panel with the forum content preloaded
            editForumView editForumView = new editForumView(oswald, lato, user);
            editForumView.ptField.setText(post.getTitle()); //preload the post title
            editForumView.pcField.setText(post.getContent()); //preload the post content

            //create separate submit button for updating. (to not create a duplicate).
            editForumView.submitButton.setVisible(false);
            JButton updateButton = new JButton("UPDATE");
            updateButton.setBounds(200, 540 + 80, 100, 40);
            updateButton.setBackground(new Color(0, 99, 73)); // Button color: green
            updateButton.setForeground(new Color(247, 248, 247)); // Button text color: white
            updateButton.setFont(oswald.deriveFont(18f)); // Font: Oswald, size 18
            updateButton.setFocusable(false);
            editForumView.add(updateButton);

            //add editForumView to the frame
            topFrame.add(editForumView, BorderLayout.CENTER);
            topFrame.revalidate(); //refresh the frame
            topFrame.repaint(); //repaint the frame

            // update the post in the database when the user hits the submit button
            updateButton.addActionListener(e -> updatePost(post, editForumView.ptField.getText(), editForumView.pcField.getText(), topFrame, oswald, lato));
        } catch (Exception e) {
            System.out.println("Error while editing post: " + e.getMessage());
        }
    }

    //TODO: add to the controller
    private void updatePost(forumPost post, String newTitle, String newContent, JFrame frame, Font oswald, Font lato) {
        try (Connection conn = myJDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE post SET post_title = ?, post_content = ? WHERE post_ID = ?")) {
            stmt.setString(1, newTitle);
            stmt.setString(2, newContent);
            stmt.setInt(3, post.getPostId());
            stmt.executeUpdate();

            // ------------------------------- CREATE UPDATE MESSAGE WITH DIALOG MODAL -------------------------------//
            JDialog updateMessage = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Post Update", true);
            updateMessage.setLayout(new BorderLayout());
            updateMessage.setSize(300, 150);
            updateMessage.setBackground(new Color(247, 248, 247));
            updateMessage.setLocationRelativeTo(frame); //center updateMessage Box relative to the editForumView

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

            okButton.addActionListener(e -> updateMessage.dispose()); // Close dialog on "OK"

            //add components to the update confirmation
            updateMessage.add(updateMessageText, BorderLayout.CENTER);
            buttonPanel.add(okButton, BorderLayout.CENTER);
            updateMessage.add(buttonPanel, BorderLayout.SOUTH);

            updateMessage.setVisible(true); //show update confirmation box

            //--------------------------------------------------------------------------------------------------------//

            //return to forum view
            frame.getContentPane().removeAll(); //clear all components
            forumView forumView = new forumView(oswald, lato, user);
            frame.add(forumView, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating post: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createDeleteConfirmationBox(forumPost post, Font oswald, Font lato) {

        //create a JDialog confirmation box for deletion
        JDialog deleteConfirmBox = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Delete Forum Post", true);
        deleteConfirmBox.setLayout(new BorderLayout());
        deleteConfirmBox.setSize(300, 150);
        deleteConfirmBox.setBackground(new Color(247, 248, 247));
        deleteConfirmBox.setLocationRelativeTo(this); // Center deleteConfirmBox relative to the parent panel

        // Create the confirmation message
        JLabel confirmationTitle = new JLabel("<html><div style='width:150px; text-align: center;'> DELETE POST? </div></html>");
        JLabel confirmationMessage = new JLabel("<html><div style='width:150px; text-align: center;'><b>Are you sure you want to delete \"" + post.getTitle() + "\"?</b></div></html>");

        // Create buttons
        JPanel buttonPanel = new JPanel();
        JButton noButton = new JButton("NO");
        JButton yesButton = new JButton("YES");

        //--------------------------------------------- redesign labels ----------------------------------------------//
        confirmationTitle.setHorizontalAlignment(SwingConstants.CENTER);
        confirmationTitle.setFont(oswald.deriveFont(18f));
        confirmationTitle.setForeground(new Color(0, 99, 73));
        confirmationMessage.setHorizontalAlignment(SwingConstants.CENTER);
        confirmationMessage.setFont(lato.deriveFont(18f));
        //------------------------------------------------------------------------------------------------------------//


        //--------------------------------------------- redesign buttons ---------------------------------------------//
        //no
        noButton.setBackground(new Color (239,201,49)); //set button color: green
        noButton.setForeground(Color.BLACK); //set button text color: white
        noButton.setFont(oswald.deriveFont(14f)); //change button text font to oswald, size 20.
        noButton.setFocusable(false);
        //yes
        yesButton.setBackground(new Color (255,178,194)); //set button color: green
        yesButton.setForeground(Color.BLACK); //set button text color: white
        yesButton.setFont(oswald.deriveFont(14f)); //change button text font to oswald, size 20.
        yesButton.setFocusable(false);
        //------------------------------------------------------------------------------------------------------------//

        noButton.addActionListener(e -> deleteConfirmBox.dispose()); //remove dialog on "NO"
        yesButton.addActionListener(e -> {
            deleteConfirmBox.dispose(); //remove dialog
            deletePost(post, oswald, lato); //delete post if "YES"
        });

        // Add components to the dialog
        buttonPanel.add(noButton);
        buttonPanel.add(yesButton);
        deleteConfirmBox.add(confirmationMessage, BorderLayout.CENTER);
        deleteConfirmBox.add(buttonPanel, BorderLayout.SOUTH);

        deleteConfirmBox.setVisible(true); //show the delete confirmation box
    }

    //TODO: add to the controller
    private void deletePost(forumPost post, Font oswald, Font lato) {
        try (Connection conn = myJDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM post WHERE post_ID = ?")) {
            stmt.setInt(1, post.getPostId());
            stmt.executeUpdate();

            System.out.println("post deleted successfully!");

            //----------------------------------- Refresh the forumView panel. -----------------------------------//
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear all components from the current frame
            forumView forumView = new forumView(oswald, lato, user);
            topFrame.add(forumView, BorderLayout.CENTER); // Add forumView Panel to the frame
            topFrame.revalidate(); // Refresh the frame
            topFrame.repaint(); // Repaint the frame
            //----------------------------------------------------------------------------------------------------//

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create the scroll panel for the forum posts
    private void createScroll(JPanel chatItemsPanel) {

        //--------------------------Create scroll panel---------------------------------------------------------------//
        //Put chatItemsPanel inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(chatItemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //have a vertical scroll
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); //no horizontal scroll

        //Change scroll bar appearance
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {  //invisible decrease button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new JButton() {  //invisible increase button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(192, 168, 144); //color of the scroll thumb
                this.trackColor = new Color(235, 219, 195); //color of the scroll track (matching panel background)
            }
        });

        // Add the scrollable items to the CENTER of the layout
        this.add(scrollPane, BorderLayout.CENTER);
    }

    //TODO: add to the controller
    private void addPostButtonActionPerformed(Object evt, Font oswald, Font lato) {
        try {
            // Switch to the editForumView Page
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear all components from the current frame
            editForumView editForumView = new editForumView(oswald, lato, user);
            topFrame.add(editForumView, BorderLayout.CENTER); // Add editForumView to the frame
            topFrame.revalidate(); // Refresh the frame
            topFrame.repaint(); // Repaint the frame
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }
}
