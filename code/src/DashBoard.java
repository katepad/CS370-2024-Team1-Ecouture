import java.awt.*;
import javax.swing.*;
import java.awt.Color;

public class DashBoard extends JPanel {
    //constructor
    DashBoard(Font oswald, Font lato) {
        //-----------------------Set background color and preferred size------------------------------------------------
        this.setBackground(new Color(235, 219, 195));
        this.setLayout(new BorderLayout());  // Use BorderLayout to properly place the navigation bar at the bottom
        //--------------------------------------------------------------------------------------------------------------

        //-------------------CALL NAVIGATION BAR AND SET BOUNDS---------------------------------------------------------
        NavigationBar navigationBar = new NavigationBar(oswald, lato);
        this.add(navigationBar, BorderLayout.SOUTH);  // Place navigation bar at the bottom (SOUTH)
        // -------------------------------------------------------------------------------------------------------------

        //-------------------CREATE items to add to panel---------------------------------------------------------------
        JPanel DashPanel = new JPanel();
        DashPanel.setLayout(new BoxLayout(DashPanel, BoxLayout.Y_AXIS));
        DashPanel.setBackground(new Color(235, 219, 195));  // Match background color

        // Add multiple items to the closetItemsPanel to simulate closet items
        for (int i = 1; i <= 50; i++) {
            JLabel itemLabel = new JLabel("Dash " + i);
            itemLabel.setFont(lato.deriveFont(18f));
            itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            DashPanel.add(itemLabel);
            DashPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add some space between items
        }
      //----------------------------------------------------------------------------------------------------------------

        //--------------------------Create scroll panel-----------------------------------------------------------------
        // Wrap the closetItemsPanel inside a JScrollPane (this is the scrollable part)
        JScrollPane scrollPane = new JScrollPane(DashPanel);
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

        //--------------------------------------------------------------------------------------------------------------

    }
}
