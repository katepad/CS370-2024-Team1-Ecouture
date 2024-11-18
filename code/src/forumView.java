import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class forumView extends JPanel {

    private final user user;

    // Constructor
    forumView(Font oswald, Font lato, user user) {
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
        JLabel pageTitle = new JLabel("Forum Page");
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
        chatItemsPanel.setBackground(new Color(235, 219, 195));  // Match background color

        // Add posts dynamically
        for (forumPost post : posts) {
            String postText = "<html><b>" + post.getTitle() + "</b><br>" + post.getPostDate() + "<br>" + post.getContent() + "</html>";
            JLabel postLabel = new JLabel(postText);
            postLabel.setFont(lato.deriveFont(18f));
            postLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            chatItemsPanel.add(postLabel);
            chatItemsPanel.add(Box.createRigidArea(new Dimension(0, 30)));  // Add spacing between posts
        }

        // Wrap the panel in a scroll pane
        createScroll(chatItemsPanel);

        // Add post button action listener
        addPostButton.addActionListener(e -> addPostButtonActionPerformed(e, oswald, lato));
    }

    // Create the scroll panel for the forum posts
    private void createScroll(JPanel chatItemsPanel) {
        //--------------------------Create scroll panel-----------------------------------------------------------------
        // Wrap the chatItemsPanel inside a JScrollPane (this is the scrollable part)
        JScrollPane scrollPane = new JScrollPane(chatItemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Change scroll bar appearance
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {  // Invisible decrease button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new JButton() {  // Invisible increase button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(192, 168, 144); // Color of the scroll thumb
                this.trackColor = new Color(235, 219, 195); // Color of the scroll track (matching panel background)
            }
        });

        // Add the scrollable items to the CENTER of the layout
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Action for add post button click
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
