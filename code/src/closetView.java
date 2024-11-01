import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class closetView extends JPanel {
    //constructor
    public closetView(Font oswald, Font lato)
    {
        private final JPanel closetItemsPanel;

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
         closetItemsPanel = new JPanel();
        closetItemsPanel.setLayout(new BoxLayout(closetItemsPanel, BoxLayout.Y_AXIS));
        closetItemsPanel.setBackground(new Color(235, 219, 195));

        //to save space panel will only be extended as items are added
        for(int i = 0; i < closetItemsPanel.getComponentCount(); i++)
        {
            closetItemsPanel.setPreferredSize(new Dimension(400, i)); // Fixed size for items panel// Match background color
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

         button.addActionListener(new ActionListener()
        {
         @Override
         public void actionPerformed(ActionEvent e)
       {
         addNewItemPanel();
       }
});
}
    private void addNewItemPanel()
    {
        JPanel itemPanel = new JPanel();
        itemPanel.setBackground(new Color(192, 168, 144));  // Set a distinct color for the item
        itemPanel.setPreferredSize(new Dimension(200, 100));  // Set a fixed size for each item
        itemPanel.setMaximumSize(new Dimension(200, 100));
        itemPanel.setMinimumSize(new Dimension(200, 100));

        // Placeholder for future item info
        JLabel itemLabel = new JLabel("New Clothing Item");
        itemLabel.setFont(new Font("Lato", Font.BOLD, 16));
        itemPanel.add(itemLabel);

        // Add the new item panel and spacing below each item
        closetItemsPanel.add(itemPanel);
        closetItemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Refresh the display as we add panels
        closetItemsPanel.revalidate();
        closetItemsPanel.repaint();
    }
}
