import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.util.Objects;

public class closetView extends JPanel {
    //constructor
    public closetView(Font oswald, Font lato)
    {
        //-----------------------Set background color and preferred size------------------------------------------------
        this.setBackground(new Color(235, 219, 195));
        this.setLayout(new BorderLayout());  // Use BorderLayout to properly place the navigation bar at the bottom
        //--------------------------------------------------------------------------------------------------------------

        //------------------------create add and remove button----------------------------------------------------------
        ImageIcon addButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/plus2.png")));
        Image image = addButton.getImage();
        Image newImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImage);

        JButton button = new JButton(newIcon);

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        ImageIcon minusButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/minus.png")));
        Image image1 = minusButton.getImage();
        Image newImage1 = image1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon newIcon1 = new ImageIcon(newImage1);

        JButton button1 = new JButton(newIcon1);

        button1.setContentAreaFilled(false);
        button1.setBorderPainted(false);
        button1.setFocusPainted(false);
        button1.setOpaque(false);

        // Create a header panel to hold the button and keep it at the top
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Right-align the button
        headerPanel.setBackground(new Color(235, 219, 195)); // Match background color

        headerPanel.add(button);
        headerPanel.add(button1);

        this.add(headerPanel, BorderLayout.NORTH); // Add header panel at the top
        //---------------------------------------------------------------------------------------------------------------

        //-------------------CALL NAVIGATION BAR AND SET BOUNDS---------------------------------------------------------
        navigationBarView navigationBarView = new navigationBarView(oswald, lato);
        this.add(navigationBarView, BorderLayout.SOUTH);  // Place navigation bar at the bottom (SOUTH)
        // -------------------------------------------------------------------------------------------------------------

        //-------------------CREATE CLOSET ITEMS PANEL------------------------------------------------------------------
        JPanel closetItemsPanel = new JPanel();
        closetItemsPanel.setLayout(new BoxLayout(closetItemsPanel, BoxLayout.Y_AXIS));
        closetItemsPanel.setBackground(new Color(235, 219, 195));  // Match background color

        String[] material = {"Blank2", "blank2", "Blank3"};
        String[] type = {"Shirt", "Sweater", "Dress", "Pants", "Jeans", "Shorts", "Skirts"};

        // Add multiple items to the closetItemsPanel to simulate closet items
        for (int i = 1; i <= 50; i++)
        {
            JLabel itemLabel = new JLabel("Clothing " + i);
            itemLabel.setFont(lato.deriveFont(18f));
            itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            closetItemsPanel.add(itemLabel);
            closetItemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add some space between items
        }

        //--------------------------Create scroll panel-----------------------------------------------------------------
        // Wrap the closetItemsPanel inside a JScrollPane (this is the scrollable part)
        JScrollPane scrollPane = new JScrollPane(closetItemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Change scroll bar appearance
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI()
        {
            @Override
            protected JButton createDecreaseButton(int orientation)
            {
                return new JButton() {  // Invisible decrease button
                    @Override
                    public Dimension getPreferredSize()
                    {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation)
            {
                return new JButton()
                {  // Invisible increase button
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
