import java.awt.*;
import javax.swing.*;
import java.awt.Color;

public class Forum extends JPanel {
    //constructor
    Forum(Font oswald, Font lato) {
        //-----------------------Set background color and preferred size----------
        this.setBackground(new Color(235, 219, 195));
        this.setLayout(new BorderLayout());  // Use BorderLayout to properly place the navigation bar at the bottom
        //-------------------------------------------------------------------------

        //-------------------CALL NAVIGATION BAR AND SET BOUNDS-----------------------------
        NavigationBar navigationBar = new NavigationBar();
        this.add(navigationBar, BorderLayout.SOUTH);  // Place navigation bar at the bottom (SOUTH)
        // --------------------------------------------------------------------------------------


        //-------------------ADD SOME FAKE ITEMS FOR DEMO-----------------------------
        //-------------------CREATE PANEL (SCROLLABLE PART)-----------------------------
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

        // Wrap the closetItemsPanel inside a JScrollPane (this is the scrollable part)
        JScrollPane scrollPane = new JScrollPane(ChatItemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the scrollable items to the CENTER of the layout
        this.add(scrollPane, BorderLayout.CENTER);
    }
}
