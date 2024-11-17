import java.awt.*;
import javax.swing.*;
import java.awt.Color;

public class reviewView extends JPanel {

    private final JPanel headerPanel;
    private final JLabel PageTitle;
    
    //constructor
    reviewView(Font oswald, Font lato) {

         //-----------------------Set background color and preferred size and title------------------------------------------------
        this.setBackground(new Color(235, 219, 195));
        this.setLayout(new BorderLayout());  // Use BorderLayout to properly place the navigation bar at the bottom

        //create title
        PageTitle = new JLabel("Review Page");
        PageTitle.setFont(oswald.deriveFont(20f));
        PageTitle.setHorizontalAlignment(SwingConstants.CENTER);

        //create top panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(235, 219, 195));

        //add them to panel
        headerPanel.add(PageTitle, BorderLayout.CENTER);

        //set panel to top of screen
        this.add(headerPanel, BorderLayout.NORTH);

        //------------------------------------------------------------------------------

        //-------------------CALL NAVIGATION BAR AND SET BOUNDS---------------------------------------------------------
        navigationBarView navigationBarView = new navigationBarView(oswald, lato);
        this.add(navigationBarView, BorderLayout.SOUTH);  // Place navigation bar at the bottom (SOUTH)
        // -------------------------------------------------------------------------------------------------------------

        //-------------------CREATE items to go in panel----------------------------------------------------------------
        JPanel ReviewPanel = new JPanel();
        ReviewPanel.setLayout(new BoxLayout(ReviewPanel, BoxLayout.Y_AXIS));
        ReviewPanel.setBackground(new Color(235, 219, 195));  // Match background color

        // Add multiple items to the closetItemsPanel to simulate closet items
        for (int i = 1; i <= 50; i++) {
            JLabel itemLabel = new JLabel("Review " + i);
            itemLabel.setFont(lato.deriveFont(18f));
            itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            ReviewPanel.add(itemLabel);
            ReviewPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add some space between items
        }
        //---------------------------------------------------------------------------------------------------------------

        //--------------------------Create scroll panel-----------------------------------------------------------------
        // Wrap the closetItemsPanel inside a JScrollPane (this is the scrollable part)
        JScrollPane scrollPane = new JScrollPane(ReviewPanel);
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
