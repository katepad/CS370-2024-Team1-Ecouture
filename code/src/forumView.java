import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class forumView extends JPanel {

    private static user user;

    //constructor
    forumView(Font oswald, Font lato, user user) throws SQLException {
        //----------------------- Set background color and preferred size --------------------------------------------//
        this.setBackground(new Color(235, 219, 195));
        this.setLayout(new BorderLayout());  //Use BorderLayout to properly place the navigation bar at the bottom
        //------------------------------------------------------------------------------------------------------------//

        forumView.user = user; //get logged in user obj.

        //------------------------------- Create add button and title ------------------------------------------------//
        ImageIcon addButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/plus2.png")));
        Image image = addButton.getImage();
        Image newImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImage);

        JButton addPostButton = new JButton(newIcon);
        addPostButton.setContentAreaFilled(false);
        addPostButton.setBorderPainted(false);
        addPostButton.setFocusPainted(false);
        addPostButton.setOpaque(false);

        //Create title
        JLabel pageTitle = new JLabel("ECOUTURE COMMUNITY");
        pageTitle.setForeground(new Color(0, 99, 73));
        pageTitle.setFont(oswald.deriveFont(20f));
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);

        //Create top panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(235, 219, 195));

        //Add them to panel
        headerPanel.add(addPostButton, BorderLayout.EAST);
        headerPanel.add(pageTitle, BorderLayout.CENTER);

        //Set panel to top of screen
        this.add(headerPanel, BorderLayout.NORTH);

        //------------------- CALL NAVIGATION BAR AND SET BOUNDS -----------------------------------------------------//
        navigationBarView navigationBarView = new navigationBarView(oswald, lato, user);
        this.add(navigationBarView, BorderLayout.SOUTH);  //place navigation bar at the bottom (SOUTH)
        //-------------------------------------------------------------------------------------------------------------

        //--------------------------------- create posts panel to go in main panel -----------------------------------//
        forumPostDAO postDAO = new forumPostDAO(); //initialize forum post DAO
        ArrayList<forumPost> posts = postDAO.getAllPosts(); //get posts

        //create panel to display posts
        JPanel chatItemsPanel = new JPanel();
        chatItemsPanel.setLayout(new BoxLayout(chatItemsPanel, BoxLayout.Y_AXIS));
        chatItemsPanel.setBackground(new Color(235, 219, 195)); //make beige background

        //create the individual panels for the individual forum posts.
        chatItemsPanel.removeAll(); //clear all existing components before populating forum posts.
        populateForumPosts(posts, oswald, lato, chatItemsPanel);

        //put chatItemsPanel in scroll pane
        createScroll(chatItemsPanel);

        //ad post button action listener
        addPostButton.addActionListener(e -> forumController.addPostClicked(oswald, lato, this, user));
    }

    void populateForumPosts(ArrayList<forumPost> posts, Font oswald, Font lato, JPanel chatItemsPanel){
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
                        //get poster's user name from the result set
                        posterName = rs.getString("user_username");
                    }
                }

            } catch (SQLException e) {
                System.out.println("SQL error: " + e.getMessage());
                posterName = "Unknown"; //default value in case of an error
            }

            createChatPanel(oswald, lato, post, posterName, chatItemsPanel);
        }
    }

    void createChatPanel(Font oswald, Font lato, forumPost post, String posterName, JPanel chatItemsPanel) {

        //----------------------------------------------- BUTTON PANEL -----------------------------------------------//

        if (post.getUserId() == user.getUserId()) { //show button panel only for posts that belong to logged-in user
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            //set a fixed height of 40 and let width adjust automatically
            buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(5, 10, 0, 10, new Color(235, 219, 195)), //Outer border with different thickness
                    BorderFactory.createEmptyBorder(10, 10, 10, 10) //Inner padding
            ));

            //remove item button
            ImageIcon minusIcon = new ImageIcon(Objects.requireNonNull(forumView.class.getResource("/pictures/minus.png")));
            Image scaledMinusImage = minusIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            JButton deleteButton = new JButton(new ImageIcon(scaledMinusImage));
            deleteButton.setContentAreaFilled(false);
            deleteButton.setBorderPainted(false);
            deleteButton.setFocusPainted(false);
            deleteButton.setOpaque(false);
            deleteButton.addActionListener(e -> createDeleteConfirmationBox(post, oswald, lato));
            buttonPanel.add(deleteButton);

            //edit item button
            ImageIcon editIcon = new ImageIcon(Objects.requireNonNull(forumView.class.getResource("/pictures/editButton.png")));
            Image scalededitImage = editIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            JButton editButton = new JButton(new ImageIcon(scalededitImage));
            editButton.setContentAreaFilled(false);
            editButton.setBorderPainted(false);
            editButton.setFocusPainted(false);
            editButton.setOpaque(false);
            editButton.addActionListener(e -> forumController.editPost(post, oswald, lato, this, user));
            buttonPanel.add(editButton);

            buttonPanel.setBackground(new Color(247, 248, 247));

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

        //if there is a buttonPanel showing
        if (post.getUserId() == user.getUserId()) {
            postPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 10, 0, 10, new Color(235, 219, 195)), //Outer border with different thickness
                    BorderFactory.createEmptyBorder(10, 10, 10, 10) //Inner padding
            ));
        } else {
            postPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(10, 10, 0, 10, new Color(235, 219, 195)), //Outer border with different thickness
                    BorderFactory.createEmptyBorder(10, 10, 10, 10) //Inner padding
            ));
        }

        chatItemsPanel.add(postPanel);

        //------------------------------------------------------------------------------------------------------------//


        //----------------------------------------------- COMMENT PANEL ----------------------------------------------//
        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));

        //comment button
        ImageIcon editIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/commentIcon.png")));
        Image scaledCommentImage = editIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton commentButton = new JButton(new ImageIcon(scaledCommentImage));

        commentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        commentButton.setContentAreaFilled(false);
        commentButton.setBorderPainted(false);
        commentButton.setFocusPainted(false);
        commentButton.setOpaque(false);


        commentPanel.add(commentButton);

        commentButton.addActionListener(e -> createComment(post, oswald, lato, commentPanel, commentButton));

        commentPanel.setBackground(new Color(247, 248, 247));
        //add padding inside the panel and a border
        commentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 10, 0, 0, new Color(235, 219, 195)), //outer border with different thickness
                BorderFactory.createEmptyBorder(10, 10, 10, 10) //inner padding
        ));

        commentDAO.populatePostComments(lato, commentPanel, post);
        chatItemsPanel.add(commentPanel);

        //--------------------------- WRAP SCROLL PANEL AROUND COMMENT PANEL ---------------------------------//
        JScrollPane scrollPane = new JScrollPane(commentPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //scroll pane set height to fixed size 120
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        //border margin for the scroll bar
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                scrollPane.getBorder(),
                BorderFactory.createMatteBorder(0, 0, 10, 10, new Color(235, 219, 195))
        ));

        //Change scroll bar appearance
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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
                this.thumbColor = Color.WHITE; //color of the scroll thumb
                this.trackColor = new Color(247, 248, 247); //color of the scroll track (matching panel background)
            }
        });
        chatItemsPanel.add(scrollPane);
        //------------------------------------------------------------------------------------------------------------//
    }

    private void createDeleteConfirmationBox(forumPost post, Font oswald, Font lato) {

        //create a JDialog confirmation box for deletion
        JDialog deleteConfirmBox = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Delete Forum Post", true);
        deleteConfirmBox.setLayout(new BorderLayout());
        deleteConfirmBox.setSize(300, 150);
        deleteConfirmBox.setBackground(new Color(247, 248, 247));
        deleteConfirmBox.setLocationRelativeTo(this); //Center deleteConfirmBox relative to the frame

        //create the confirmation message
        JLabel confirmationTitle = new JLabel("<html><div style='width:150px; text-align: center;'> DELETE POST? </div></html>");
        JLabel confirmationMessage = new JLabel("<html><div style='width:150px; text-align: center;'><b>Are you sure you want to delete \"" + post.getTitle() + "\"?</b></div></html>");

        //create option buttons
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
        //noButton design
        noButton.setBackground(new Color (239,201,49)); //set button color: green
        noButton.setForeground(Color.BLACK); //set button text color: white
        noButton.setFont(oswald.deriveFont(14f)); //change button text font to oswald, size 20.
        noButton.setFocusable(false);
        //yesButton design
        yesButton.setBackground(new Color (255,178,194)); //set button color: green
        yesButton.setForeground(Color.BLACK); //set button text color: white
        yesButton.setFont(oswald.deriveFont(14f)); //change button text font to oswald, size 20.
        yesButton.setFocusable(false);
        //------------------------------------------------------------------------------------------------------------//

        noButton.addActionListener(e -> deleteConfirmBox.dispose()); //remove dialog on "NO"
        yesButton.addActionListener(e -> {
            deleteConfirmBox.dispose(); //remove dialog
            forumController.deletePost(post, oswald, lato, this, user); //delete post if "YES"
        });

        //Add components to the dialog
        buttonPanel.add(noButton);
        buttonPanel.add(yesButton);
        deleteConfirmBox.add(confirmationMessage, BorderLayout.CENTER);
        deleteConfirmBox.add(buttonPanel, BorderLayout.SOUTH);

        deleteConfirmBox.setVisible(true); //show the delete confirmation box
    }

    //create the scroll panel for the forum posts
    private void createScroll(JPanel chatItemsPanel) {

        //-------------------------- Create scroll panel -------------------------------------------------------------//
        //Put chatItemsPanel inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(chatItemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //have a vertical scroll
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); //no horizontal scroll
        scrollPane.setBackground(new Color(235, 219, 195));
        scrollPane.setBorder(null);

        //Change scroll bar appearance
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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

        //Add the scrollable items to the CENTER of the layout
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void createComment(forumPost post, Font oswald, Font lato, JPanel commentPanel, JButton commentButton) {

        JPanel newCommentPanel = new JPanel();
        newCommentPanel.setLayout(new BorderLayout());
        commentPanel.setLayout(new BorderLayout());

        //comment text field design
        JTextField commentField = new JTextField("");
        commentField.setFont(lato.deriveFont(12f));
        commentField.setForeground(Color.BLACK);

        //submit comment button design
        JButton submitCommentButton = new JButton("SUBMIT");
        submitCommentButton.setBackground((new Color(0, 99, 73)));
        submitCommentButton.setForeground(new Color(247, 248, 247));
        submitCommentButton.setFont(oswald.deriveFont(12f));

        newCommentPanel.add(commentField, BorderLayout.CENTER);
        newCommentPanel.add(submitCommentButton, BorderLayout.EAST);

        commentPanel.add(newCommentPanel, BorderLayout.NORTH);
        commentButton.setVisible(false);

        newCommentPanel.setVisible(true);
        newCommentPanel.revalidate();
        newCommentPanel.repaint();

        commentPanel.revalidate();
        commentPanel.repaint();

        submitCommentButton.addActionListener(e -> {
            String content = commentField.getText().replace("'", "''");
            if (!content.isEmpty()) {
                commentDAO commentDAO = new commentDAO();
                comment newComment = new comment(0, post.getPostId(), user.getUserId(), content, LocalDateTime.now().toString());
                commentDAO.addComment(newComment);

                commentPanel.removeAll();
                commentPanel.add(commentButton);
                commentButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                commentDAO.populatePostComments(lato, commentPanel, post);

                //switch from comment typing mode to button.

            } else {

                //revalidate the comment panel to reset scroll panel
                commentPanel.removeAll();
                commentPanel.add(commentButton);
                commentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                commentDAO.populatePostComments(lato, commentPanel, post);

                //switch from comment button to typing mode.
            }
            newCommentPanel.setVisible(false);
            commentButton.setVisible(true);
            commentPanel.revalidate();
            commentPanel.repaint();
        });
    }

}