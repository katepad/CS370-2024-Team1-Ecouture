import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class closetView extends JPanel
{



    private final navigationBarView navigationBar;
    private final JPanel headerPanel;
    private final JLabel PageTitle;

    //layout manager so we can switch between panel windows
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private final JPanel collapsedPanelZone; // holds the item panels (can not get it to work without it)
    private final ArrayList<JPanel> collapsedPanels; //array of item panels

    private editClosetView activeEditPanel; //expanded panel from editClosetView




    // Constructor
    public closetView(Font oswald, Font lato, user user)
    {
       //establish panel
        this.setLayout(new BorderLayout()); //using BorderLayout because using a panel within for organization
        this.setBackground(new Color(235, 219, 195));

        collapsedPanels = new ArrayList<>();
        activeEditPanel = null; // No active edit panel initially (creating new item in editClosetView)



//---------------------------HEADER PANEL, ADD BUTTON, TITLE, NAV BAR------------------------------------------------------------
        // Header panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(235, 219, 195));

        // add Page title
        PageTitle = new JLabel("Closet Page");
        PageTitle.setFont(oswald.deriveFont(20f));
        PageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(PageTitle, BorderLayout.CENTER);

        // Add button
        ImageIcon addButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/plus2.png")));
        Image addImage = addButtonIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton addButton = new JButton();
        addButton.setIcon(new ImageIcon(addImage));
        addButton.setContentAreaFilled(false);
        addButton.setBorderPainted(false);
        addButton.setFocusPainted(false);
        addButton.setOpaque(false);
        headerPanel.add(addButton, BorderLayout.EAST);

        //add header panel with title and button
        this.add(headerPanel, BorderLayout.NORTH);

        navigationBar = new navigationBarView(oswald, lato, user);
        this.add(navigationBar, BorderLayout.SOUTH);

//----------------------------------------------------------------------------------------------------------------------

//------------------------------CARDLAYOUT METHOD, COLLAPSEDPANELZONE(panel that's gonna hold everything)----------------
        // Initialize
        cardLayout = new CardLayout(); //initialize method
        cardPanel = new JPanel(cardLayout); //switches from editCloset to closet

        //this panel holds all the item panels
        collapsedPanelZone = new JPanel();
        collapsedPanelZone.setLayout(new BoxLayout(collapsedPanelZone, BoxLayout.Y_AXIS));
        collapsedPanelZone.setBackground(new Color(235, 219, 195));
//----------------------------------------------------------------------------------------------------------------------

//---------------------------------------SCROLL BAR---------------------------------------------------------------------
        JScrollPane scrollPanel = new JScrollPane(collapsedPanelZone);
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

        cardPanel.add(scrollPanel, "CollapsedPanels");
//----------------------------------------------------------------------------------------------------------------------

        // Add button action listener
        addButton.addActionListener(e -> addNewItemPanel(oswald, lato));

        // Add the cardPanel to the center of the layout
        this.add(cardPanel, BorderLayout.CENTER);

        // Show collapsed panels by default
        cardLayout.show(cardPanel, "CollapsedPanels");

    }//end of closetView








   //this just creates the item panels
    public JPanel createCollapsedPanel(String title, String type, String acquisition, String brand, String materials, Font oswald, Font lato)
    {
        //MAIN ITEM PANEL
        JPanel collapsedPanel = new JPanel(new BorderLayout());
        collapsedPanel.setBackground(Color.white);

        // Set fixed size for the collapsed panel
        collapsedPanel.setMaximumSize(new Dimension(350, 200));
        collapsedPanel.setPreferredSize(new Dimension(350, 200));

        // Add margin around the panel
        collapsedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

//-------------------------------------REMOVE BUTTON, EDIT BUTTON-------------------------------------------------------
        //PANEL WITHIN COLLAPSEDPANEL FOR BUTTONS
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);

        ImageIcon minusIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/minus.png")));
        Image scaledMinusImage = minusIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton deleteButton = new JButton(new ImageIcon(scaledMinusImage));
        deleteButton.setContentAreaFilled(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        deleteButton.setOpaque(false);
        deleteButton.addActionListener(e -> {
                    collapsedPanels.remove(collapsedPanel); // Remove from list
                    collapsedPanelZone.remove(collapsedPanel); // Remove from UI
                    collapsedPanelZone.revalidate();
                    collapsedPanelZone.repaint();
                });
        buttonPanel.add(deleteButton);

        ImageIcon editIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/editButton.png")));
        Image scalededitImage = editIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton editButton = new JButton(new ImageIcon(scalededitImage));
        editButton.setContentAreaFilled(false);
        editButton.setBorderPainted(false);
        editButton.setFocusPainted(false);
        editButton.setOpaque(false);
        editButton.addActionListener(e -> {
            addNewItemPanel(oswald, lato);
            cardPanel.remove(collapsedPanel); // Ensure collapsedPanel is removed before switching
        });
        buttonPanel.add(editButton);

        collapsedPanel.add(buttonPanel, BorderLayout.NORTH);
//----------------------------------------------------------------------------------------------------------------------

        //PANEL WITHIN COLLAPSEDPANEL TO HOLD TEXT
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); //centers the text and items added will fall under on another
        textPanel.setBackground(Color.WHITE);

        //set title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(oswald.deriveFont(oswald.BOLD, 25));
        textPanel.add(titleLabel);

        //set type
        JLabel typeLabel = new JLabel("Style: " + type);
        typeLabel.setFont(lato.deriveFont(Font.PLAIN, 14));
        textPanel.add(typeLabel);

        //set method
        JLabel acquisitionLabel = new JLabel("Acquisition Method: " + acquisition);
        acquisitionLabel.setFont(lato.deriveFont(Font.PLAIN, 14));
        textPanel.add(acquisitionLabel);

        //set brand
        JLabel brandLabel = new JLabel("Brand: " + brand);
        brandLabel.setFont(lato.deriveFont(Font.PLAIN, 14));
        textPanel.add(brandLabel);

        //set material
        JLabel materialLabel = new JLabel("Materials: " + materials);
        materialLabel.setFont(lato.deriveFont(Font.PLAIN, 14));
        textPanel.add(materialLabel);

        //center it on main collapsed panel
        collapsedPanel.add(textPanel, BorderLayout.CENTER);

        return collapsedPanel;
    }



    //editClosetView passes user input here
    public void saveItemPanel(String title, String type, String acquisition, String brand, String materials, Font oswald, Font lato)
    {
        // Remove the active edit panel
        if (activeEditPanel != null)
        {
            cardPanel.remove(activeEditPanel);
            activeEditPanel = null;
        }

        //create panels with user info from editClosetView
        JPanel collapsedPanel = createCollapsedPanel(title, type, acquisition, brand, materials, oswald, lato);

        // Add the new collapsed panel to the container
        collapsedPanels.add(collapsedPanel); //add to array
        collapsedPanelZone.add(collapsedPanel); //add with others to display

        //design stuff
        collapsedPanelZone.add(Box.createRigidArea(new Dimension(0, 10))); // Vertical spacing
        collapsedPanelZone.revalidate();
        collapsedPanelZone.repaint();

        // Switch back to the collapsed panel view
        cardLayout.show(cardPanel, "CollapsedPanels"); //switch to closetView

        showNavigationBar(); //and header
    }



    //calls editClosetView so user can add more items
    private void addNewItemPanel(Font oswald, Font lato)
    {
        //condition to make sure editCloseView is not active
        if (activeEditPanel != null)
        {
            cardPanel.remove(activeEditPanel); //remove it
            activeEditPanel = null; //and set it to null
        }

        // Create a new edit panel
        activeEditPanel = new editClosetView(oswald, lato, this);
        cardPanel.add(activeEditPanel, "EditPanel"); //add it

        cardLayout.show(cardPanel, "EditPanel"); //switch to editClosetView

        // Hide navigation bar and header
        hideNavigationBar();
    }




    // Show navigation bar and header
    public void showNavigationBar()
    {
        navigationBar.setVisible(true);
        headerPanel.setVisible(true);
    }



    public void hideNavigationBar()
    {
        navigationBar.setVisible(false);
        headerPanel.setVisible(false);
    }


    public void cancelPanel()
    {
        // Remove the active edit panel
        if (activeEditPanel != null)
        {
            cardPanel.remove(activeEditPanel);
            activeEditPanel = null; // Reset the reference
        }

        // Switch back to the collapsed panel view
        cardLayout.show(cardPanel, "CollapsedPanels");

        // Show the navigation bar and header again
        showNavigationBar();
    }


}

