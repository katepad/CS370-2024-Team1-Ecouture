import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Closet extends JPanel {
    //constructor
    Closet(Font oswald, Font lato)
    {
        //-----------------------Set background color and preferred size----------
        this.setBackground(new Color(235, 219, 195));
        this.setLayout(new BorderLayout());  // Use BorderLayout to properly place the navigation bar at the bottom
        //-------------------------------------------------------------------------

        //-------------------CALL NAVIGATION BAR AND SET BOUNDS-----------------------------
        NavigationBar navigationBar = new NavigationBar(oswald, lato);
        this.add(navigationBar, BorderLayout.SOUTH);  // Place navigation bar at the bottom (SOUTH)
        // --------------------------------------------------------------------------------------


        //-------------------ADD SOME FAKE CLOSET ITEMS FOR DEMO-----------------------------
        //-------------------CREATE CLOSET ITEMS PANEL (SCROLLABLE PART)-----------------------------
        JPanel closetItemsPanel = new JPanel();
        closetItemsPanel.setLayout(new BoxLayout(closetItemsPanel, BoxLayout.Y_AXIS));
        closetItemsPanel.setBackground(new Color(235, 219, 195));  // Match background color

        // Add multiple items to the closetItemsPanel to simulate closet items
        for (int i = 1; i <= 50; i++) {
            JLabel itemLabel = new JLabel("Closet Item " + i);
            itemLabel.setFont(lato.deriveFont(18f));
            itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            closetItemsPanel.add(itemLabel);
            closetItemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add some space between items
        }

        // Wrap the closetItemsPanel inside a JScrollPane (this is the scrollable part)
        JScrollPane scrollPane = new JScrollPane(closetItemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the scrollable items to the CENTER of the layout
        this.add(scrollPane, BorderLayout.CENTER);
/*
        //Adding search bar to find article of clothing
        JTextField searchField = new JTextField(2);
        //JButton searchButton = new JButton("Search");
        searchField.setBounds(20, 50, 50, 50);
        searchField.setSize(10, 5);
        //searchButton.setBounds(20, 100, 100, 50);

        searchField.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String searchText = searchField.getText();
                System.out.println(searchText);
            }
        });
       // this.add(searchButton);
        this.add(searchField);
*/
    }




}
