import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

import static javax.swing.Box.createRigidArea;

public class closetView extends JPanel {
    private final JPanel closetItemsPanel;
    private final navigationBarView navigationBar;
    private final JPanel headerPanel;
    private JScrollPane scrollPanel;
    private final JLabel PageTitle;
    private final ArrayList<editClosetView> panels;


    // Constructor
    closetView(Font oswald, Font lato)
    {

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(235, 219, 195));

        //array list to hold panels of clothing items
        panels = new ArrayList<>();

        //------------------------ Create Add Button and closet title panel---------------------------------------------
        //create button with image
        ImageIcon addButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/plus2.png")));
        Image addImage = addButtonIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        JButton addButton = new JButton();
        addButton.setIcon(new ImageIcon(addImage));

        addButton.setContentAreaFilled(false);
        addButton.setBorderPainted(false);
        addButton.setFocusPainted(false);
        addButton.setOpaque(false);


        //create title
        PageTitle = new JLabel("Closet Page");
        PageTitle.setFont(oswald.deriveFont(20f));
        PageTitle.setHorizontalAlignment(SwingConstants.CENTER);

        //create top panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(235, 219, 195));

        //add them to panel
        headerPanel.add(addButton, BorderLayout.EAST);
        headerPanel.add(PageTitle, BorderLayout.CENTER);

        //set panel to top of screen
        this.add(headerPanel, BorderLayout.NORTH);
//----------------------------------------------------------------------------------------------------------------------

//-----------------------------------new panel to hold items------------------------------------------------------------

        closetItemsPanel = new JPanel();
        closetItemsPanel.setLayout(new BoxLayout(closetItemsPanel, BoxLayout.Y_AXIS));
        closetItemsPanel.setBackground(new Color(235, 219, 195));

        //create scroll bar
        scrollPanel = new JScrollPane(closetItemsPanel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //redesign scroll bar to match page
        scrollPanel.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI()
        {
            @Override
            protected JButton createDecreaseButton(int orientation)
            {
                return new JButton()
                {  // Invisible decrease button
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
                    public Dimension getPreferredSize()
                    {
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

        this.add(scrollPanel, BorderLayout.CENTER);
//----------------------------------------------------------------------------------------------------------------------

// ------------------call Navigation Bar--------------------------------------------------------------------------------
        navigationBar = new navigationBarView(oswald, lato);
        this.add(navigationBar, BorderLayout.SOUTH);
//----------------------------------------------------------------------------------------------------------------------

// -----------------Add ActionListener to the add button----------------------------------------------------------------
        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addNewItemPanel(oswald, lato);
            }
        });
    } //end of constructor

    //----------------------------FUNCTION create new clothing item----------------------------------------------------------------
    private void addNewItemPanel(Font oswald, Font lato)
    {
        navigationBar.setVisible(false); //hide nav bar
        headerPanel.setVisible(false); //hide top panel

        hideAllPanels(); //call function to hide existing clothing items

        // Create and add the new item panel
        editClosetView newItemPanel = new editClosetView(oswald, lato, this);

        panels.add(newItemPanel);
        closetItemsPanel.add(newItemPanel);
        closetItemsPanel.add(createRigidArea(new Dimension(0, 10)));
        closetItemsPanel.revalidate();
        closetItemsPanel.repaint();
    }
//----------------------------------------------------------------------------------------------------------------------

    //------------------------------- FUNCTION hide existing clothing items when adding a new one------------------------
    public void hideAllPanels()
    {
        for (editClosetView panel : panels)
        {
            panel.setVisible(false);
        }
    }
//----------------------------------------------------------------------------------------------------------------------

    //---------------------------FUNCTION show existing clothing items------------------------------------------------------
    public void showAllPanels()
    {
        for(editClosetView panel : panels)
        {

            panel.setMaximumSize(new Dimension(350, 200));

            panel.setVisible(true);
        }
    }
//----------------------------------------------------------------------------------------------------------------------


    //----------------------------FUNCTION display navigation bar and add button---------------------------------------------
    public void showNavigationBar()
    {
        navigationBar.setVisible(true);
        headerPanel.setVisible(true);
    }
    //--------------------------------------------------------------------------------------------------------------------

    //------------------------FUNCTION delete existing panels all together--------------------------------------------------
    public void removeItemPanel(editClosetView panel)
    {
        closetItemsPanel.remove(panel);
        panels.remove(panel);
        closetItemsPanel.revalidate();
        closetItemsPanel.repaint();
    }
//----------------------------------------------------------------------------------------------------------------------
}
