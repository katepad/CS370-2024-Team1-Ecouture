import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class closetView extends JPanel {

    //constructor
    public closetView(Font oswald, Font lato, user user)
    {
        //establish panel
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(235, 219, 195));

        //----------------------------- HEADER PANEL, ADD BUTTON, TITLE, NAV BAR -------------------------------------//
        //Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(235, 219, 195));

        //add Page title
        JLabel pageTitle = new JLabel("MY CLOSET");
        pageTitle.setForeground(new Color(0, 99, 73));
        pageTitle.setFont(oswald.deriveFont(20f));
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(pageTitle, BorderLayout.CENTER);

        //Add button
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

        navigationBarView navigationBar = new navigationBarView(oswald, lato, user);
        this.add(navigationBar, BorderLayout.SOUTH);
        //------------------------------------------------------------------------------------------------------------//


        //------------------------ CLOTHES ITEM PANEL (panel that's going to hold everything) ------------------------//
        //this panel holds all the item panels
        JPanel clothesItemPanel = new JPanel();
        clothesItemPanel.setLayout(new BoxLayout(clothesItemPanel, BoxLayout.Y_AXIS));
        clothesItemPanel.setBackground(new Color(247, 248, 247));  //make white background
        //------------------------------------------------------------------------------------------------------------//

        //create clothes items in main panel
        clothesItemPanel.removeAll(); //clear all existing components before populating clothes items.

        closetManagement.populateClothesItem(user.getUserId(), oswald, lato, clothesItemPanel, this, user);

        //put clothesItemPanel in scroll pane
        createScroll(clothesItemPanel);

        //------------------------------ create bottom of panel to get rid of white space ----------------------------//
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(bottomPanel.getWidth(), 10));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomPanel.setBackground(new Color(235, 219, 195));
        clothesItemPanel.add(bottomPanel, BorderLayout.SOUTH);
        //------------------------------------------------------------------------------------------------------------//

        //add button action listener
        addButton.addActionListener(e -> closetController.addNewItemPanelButtonClicked(oswald, lato, user, this));

    } //end of closetView constructor

    //create the scroll panel for the clothes items
    private void createScroll(JPanel clothesItemPanel) {

        //--------------------------Create scroll panel---------------------------------------------------------------//
        //Put chatItemsPanel inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(clothesItemPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //have a vertical scroll
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); //no horizontal scroll
        scrollPane.setBorder(null);

        //Change scroll bar appearance
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {  //invisible decrease button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new JButton() {  //invisible increase button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(192, 168, 144); //color of the scroll thumb
                this.trackColor = new Color(235, 219, 195); //color of the scroll track (matching panel background)
            }
        });

        //Add the scrollable items to the CENTER of the layout
        this.add(scrollPane, BorderLayout.CENTER);
    }

}