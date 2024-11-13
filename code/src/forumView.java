import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class forumView extends JPanel {

    //constructor
    forumView(Font oswald, Font lato) {
        //-----------------------Set background color and preferred size------------------------------------------------
        this.setBackground(new Color(235, 219, 195));
        this.setLayout(new BorderLayout());  // Use BorderLayout to properly place the navigation bar at the bottom
        //--------------------------------------------------------------------------------------------------------------

        //------------------------------- create add button ----------------------------------------------------------//
        ImageIcon addButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/plus2.png")));
        Image image = addButton.getImage();
        Image newImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImage);

        JButton addPostButton = new JButton(newIcon);

        addPostButton.setContentAreaFilled(false);
        addPostButton.setBorderPainted(false);
        addPostButton.setFocusPainted(false);
        addPostButton.setOpaque(false);

        // Create a header panel to hold the button and keep it at the top
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Right-align the button
        headerPanel.setBackground(new Color(235, 219, 195)); // Match background color

        headerPanel.add(addPostButton);

        this.add(headerPanel, BorderLayout.NORTH); // Add header panel at the top
        //---------------------------------------------------------------------------------------------------------------


        //-------------------CALL NAVIGATION BAR AND SET BOUNDS---------------------------------------------------------
        navigationBarView navigationBarView = new navigationBarView(oswald, lato);
        this.add(navigationBarView, BorderLayout.SOUTH);  // Place navigation bar at the bottom (SOUTH)
        // -------------------------------------------------------------------------------------------------------------

        //-------------------CREATE items to go in panel----------------------------------------------------------------
        JPanel ChatItemsPanel = new JPanel();
        ChatItemsPanel.setLayout(new BoxLayout(ChatItemsPanel, BoxLayout.Y_AXIS));
        ChatItemsPanel.setBackground(new Color(235, 219, 195));  // Match background color

        // Add multiple items to the closetItemsPanel to simulate closet items
        for (int i = 1; i <= 50; i++) {
            JLabel itemLabel = new JLabel("Chat " + i);
            itemLabel.setFont(lato.deriveFont(18f));
            itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            ChatItemsPanel.add(itemLabel);
            ChatItemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add some space between items
        }

        createScroll(ChatItemsPanel);
        addPostButton.addActionListener(e -> addPostButtonActionPerformed(e, oswald, lato));
    }

    private void createScroll(JPanel chatItemsPanel) {
        //--------------------------Create scroll panel-----------------------------------------------------------------
        // Wrap the closetItemsPanel inside a JScrollPane (this is the scrollable part)
        JScrollPane scrollPane = new JScrollPane(chatItemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Change scroll bar appearance
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation)
            {
                return new JButton() {  // Invisible decrease button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation)
            {
                return new JButton() {  // Invisible increase button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected void configureScrollBarColors()
            {
                this.thumbColor = new Color(192, 168, 144); // Color of the scroll thumb
                this.trackColor = new Color(235, 219, 195); // Color of the scroll track (matching panel background)
            }
        });
        // Add the scrollable items to the CENTER of the layout
        this.add(scrollPane, BorderLayout.CENTER);

    }

    //TODO: Move to Controller.
    private void addPostButtonActionPerformed(Object evt, Font oswald, Font lato) {
        try {
            //Switch to the editForumView Page
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear all components from the current frame
            editForumView editForumView = new editForumView(oswald, lato);
            topFrame.add(editForumView, BorderLayout.CENTER); // Add editForumView to the frame
            topFrame.revalidate(); // Refresh the frame
            topFrame.repaint(); // Repaint the frame
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }
}
