import java.awt.*;
import javax.swing.*;
import java.awt.Color;

public class DashBoard extends JPanel {
    //constructor
    DashBoard(Font oswald, Font lato) {
        //-----------------------Set background color and preferred size----------
        this.setBackground(new Color(235, 219, 195));
        this.setLayout(new BorderLayout());  // Use BorderLayout to properly place the navigation bar at the bottom
        //-------------------------------------------------------------------------

        //-------------------CALL NAVIGATION BAR AND SET BOUNDS-----------------------------
        NavigationBar navigationBar = new NavigationBar(oswald, lato);
        this.add(navigationBar, BorderLayout.SOUTH);  // Place navigation bar at the bottom (SOUTH)
        // --------------------------------------------------------------------------------------

        //-------------------CREATE PANEL (SCROLLABLE PART)-----------------------------
        JPanel DashPanel = new JPanel();
        DashPanel.setLayout(new BoxLayout(DashPanel, BoxLayout.Y_AXIS));
        DashPanel.setBackground(new Color(235, 219, 195));  // Match background color

        for (int i = 1; i <= 50; i++) {
            JLabel itemLabel = new JLabel("Dash " + i);
            itemLabel.setFont(lato.deriveFont(18f));
            itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            DashPanel.add(itemLabel);
            DashPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add some space between items
        }
    
        

        // Wrap the closetItemsPanel inside a JScrollPane (this is the scrollable part)
        JScrollPane scrollPane = new JScrollPane(DashPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

         // Add the scrollable items to the CENTER of the layout
        this.add(scrollPane, BorderLayout.CENTER);

    }
}
