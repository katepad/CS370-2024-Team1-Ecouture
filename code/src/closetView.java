import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

public class closetView extends JPanel {

    private final user user;

    //constructor
    public closetView(Font oswald, Font lato, user user)
    {
        //establish panel
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(235, 219, 195));
        this.user = user;

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
        //get clothing items for specific user and put into an array
        ArrayList<clothingItem> clothingItems = clothingItemDAO.getAllClothes(user.getUserId());

        //this panel holds all the item panels
        JPanel clothesItemPanel = new JPanel();
        clothesItemPanel.setLayout(new BoxLayout(clothesItemPanel, BoxLayout.Y_AXIS));
        clothesItemPanel.setBackground(new Color(247, 248, 247));  //make white background
        //------------------------------------------------------------------------------------------------------------//

        //create clothes items in main panel
        clothesItemPanel.removeAll(); //clear all existing components before populating clothes items.

        populateClothesItem(user.getUserId(), oswald, lato, clothesItemPanel, clothingItems);

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
        addButton.addActionListener(e -> addNewItemPanel(oswald, lato, user));

    } //end of closetView constructor

    //this populates clothing items belonging to the logged-in user.
    public void populateClothesItem(int userId, Font oswald, Font lato, JPanel clothesItemPanel, ArrayList<clothingItem> clothingItems) {
        //get clothing items for the logged-in user
        clothingItems = clothingItemDAO.getAllClothes(userId);

        //Add clothes items dynamically to the panel
        for (clothingItem clothingItem : clothingItems) {
            //pass the clothingItem and clothesItemPanel to a function that creates another panel for each clothing item
            createClothingPanel(oswald, lato, clothingItem, clothesItemPanel);
        }
    }

    //function that creates another panel for each clothing item
    private void createClothingPanel(Font oswald, Font lato, clothingItem clothingItem, JPanel clothesItemPanel) {

        //----------------------------------------------- BUTTON PANEL -----------------------------------------------//
        //create a panel to add delete and edit buttons
        //create a panel to add delete and edit buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); //align to the right
        //Set a fixed height of 40 and let width adjust automatically
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        //delete item button
        ImageIcon minusIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/minus.png")));
        Image scaledMinusImage = minusIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton deleteButton = new JButton(new ImageIcon(scaledMinusImage));
        deleteButton.setContentAreaFilled(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        deleteButton.setOpaque(false);
        deleteButton.addActionListener(e -> createDeleteConfirmationBox(clothingItem, oswald, lato));
        buttonPanel.add(deleteButton);

        //edit item button
        ImageIcon editIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/editButton.png")));
        Image scalededitImage = editIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton editButton = new JButton(new ImageIcon(scalededitImage));
        editButton.setContentAreaFilled(false);
        editButton.setBorderPainted(false);
        editButton.setFocusPainted(false);
        editButton.setOpaque(false);
        editButton.addActionListener(e -> editClothingItem(clothingItem, oswald, lato));
        buttonPanel.add(editButton);

        buttonPanel.setBackground(new Color(247, 248, 247));

        //add padding inside the panel and a border
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                //outer border
                BorderFactory.createMatteBorder(5, 10, 0, 10, new Color(235, 219, 195)),
                //inner padding
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        clothesItemPanel.add(buttonPanel);
        //------------------------------------------------------------------------------------------------------------//


        //---------------------------------- ITEM PANEL (for each clothing item)  ------------------------------------//
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS)); //arrange labels vertically
        itemPanel.setBackground(new Color(247, 248, 247)); //set background for the clothes item panel

        //get all elements of the clothing item
        String clothingName = "<html><div style='width:250px;'>" + clothingItem.getTitle().toUpperCase() + "</div></html>";
        String clothingDetails = "<html><div style='width:250px;'>" +
                "Type: " + clothingItem.getType() +
                "<br>Brand: " + clothingItem.getBrand() +
                "<br>Acquisition Method: " + clothingItem.getAcquireMethod() +
                "<br>Material(s):<br>" +
                "</div></html>";

        List<String> materials = clothingItem.getMaterials();
        List<Integer> percentages = clothingItem.getPercentages();
        StringBuilder materialString = new StringBuilder("<html><div style='width:250px;'>");

        for (int i = 0; i < materials.size(); i++) {
            materialString.append(materials.get(i))
                    .append(" (").append(percentages.get(i)).append("%)")
                    .append("<br>");
        }

        materialString.append("</div></html>");

        JLabel clothingNameLabel = new JLabel(clothingName);
        JLabel clothingDetailsLabel = new JLabel(clothingDetails);
        JLabel clothingMaterialsLabel = new JLabel(materialString.toString());

        clothingNameLabel.setFont(oswald.deriveFont(18f));
        clothingNameLabel.setForeground(new Color(0, 99, 73));

        clothingDetailsLabel.setFont(lato.deriveFont(16f));
        clothingDetailsLabel.setForeground(Color.BLACK);

        clothingMaterialsLabel.setFont(lato.deriveFont(16f));
        clothingMaterialsLabel.setForeground(Color.BLACK);

        clothingNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        clothingDetailsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        clothingMaterialsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //add the labels to the item panel
        itemPanel.add(clothingNameLabel);
        itemPanel.add(clothingDetailsLabel);
        itemPanel.add(clothingMaterialsLabel);

        itemPanel.setBorder(BorderFactory.createCompoundBorder(
                //outer border
                BorderFactory.createMatteBorder(0, 10, 10, 10, new Color(235, 219, 195)),
                //inner padding
                BorderFactory.createEmptyBorder(10, 10, 30, 10)
        ));

        clothesItemPanel.add(itemPanel);
        //------------------------------------------------------------------------------------------------------------//
    }

    //switch to the editClosetView so user can add more clothes items to their closet.
    private void addNewItemPanel(Font oswald, Font lato, user user)
    {
        //switch frame from main closet to editing panel
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane().removeAll(); //clear all components from the current frame
        editClosetView editClosetView = new editClosetView(oswald, lato, user);
        topFrame.add(editClosetView, BorderLayout.CENTER); //add editClosetView to the frame
        topFrame.revalidate();
        topFrame.repaint();
    }

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

    private void createDeleteConfirmationBox(clothingItem clothingItem, Font oswald, Font lato) {

        //create a JDialog confirmation box for deletion
        JDialog deleteConfirmBox = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Delete Clothing Item", true);
        deleteConfirmBox.setLayout(new BorderLayout());
        deleteConfirmBox.setSize(300, 150);
        deleteConfirmBox.setBackground(new Color(247, 248, 247));
        deleteConfirmBox.setLocationRelativeTo(this); //Center deleteConfirmBox relative to the frame

        //create the confirmation message
        JLabel confirmationTitle = new JLabel("<html><div style='width:150px; text-align: center;'> DELETE CLOTHING ITEM? </div></html>");
        JLabel confirmationMessage = new JLabel("<html><div style='width:150px; text-align: center;'><b>Are you sure you want to delete " + clothingItem.getTitle() + "?</b></div></html>");

        //create option buttons
        JPanel buttonPanel = new JPanel();
        JButton noButton = new JButton("NO");
        JButton yesButton = new JButton("YES");

        //--------------------------------------------- redesign labels ----------------------------------------------//
        confirmationTitle.setHorizontalAlignment(SwingConstants.CENTER);
        confirmationTitle.setFont(oswald.deriveFont(18f));
        confirmationTitle.setForeground(new Color(0, 99, 73));
        confirmationMessage.setHorizontalAlignment(SwingConstants.CENTER);
        confirmationMessage.setFont(lato.deriveFont(18f));
        //------------------------------------------------------------------------------------------------------------//

        //--------------------------------------------- redesign buttons ---------------------------------------------//
        //noButton design
        noButton.setBackground(new Color (239,201,49)); //set button color: green
        noButton.setForeground(Color.BLACK); //set button text color: white
        noButton.setFont(oswald.deriveFont(14f)); //change button text font to oswald, size 20.
        noButton.setFocusable(false);
        //yesButton design
        yesButton.setBackground(new Color (255,178,194)); //set button color: green
        yesButton.setForeground(Color.BLACK); //set button text color: white
        yesButton.setFont(oswald.deriveFont(14f)); //change button text font to oswald, size 20.
        yesButton.setFocusable(false);
        //------------------------------------------------------------------------------------------------------------//

        noButton.addActionListener(e -> deleteConfirmBox.dispose()); //remove dialog on "NO"
        yesButton.addActionListener(e -> {
            deleteConfirmBox.dispose(); //remove dialog
            deleteClothingItem(clothingItem, oswald, lato); //delete clothing item if "YES"
        });

        //Add components to the dialog
        buttonPanel.add(noButton);
        buttonPanel.add(yesButton);
        deleteConfirmBox.add(confirmationMessage, BorderLayout.CENTER);
        deleteConfirmBox.add(buttonPanel, BorderLayout.SOUTH);

        deleteConfirmBox.setVisible(true); //show the delete confirmation box
    }

    private void deleteClothingItem(clothingItem clothingItem, Font oswald, Font lato) {

        //connect to the database and execute the deletion query
        try (Connection conn = myJDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM clothes WHERE clothes_ID = ?")) {

            //get clothing ID.
            int clothesID = clothingItem.getClothesID();
            System.out.println(clothesID);

            //set the clothing item's ID in the prepared statement
            stmt.setInt(1, clothesID);
            stmt.executeUpdate(); //execute the deletion query

            System.out.println("Clothing item deleted successfully!");

            //refresh the closet view after deletion
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); //clear the current view
            closetView closetView = new closetView(oswald, lato, user); //recreate closet view
            topFrame.add(closetView, BorderLayout.CENTER); //add the updated closet view
            topFrame.revalidate(); //refresh the frame
            topFrame.repaint(); //repaint the frame

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editClothingItem(clothingItem clothingItem, Font oswald, Font lato) {
        //Get the current clothing item details and populate the edit form

        try {
            //switch to the editClothingItem Page
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); //Clear all components from the current frame

            //create editClothingItem view with preloaded details
            editClosetView editClosetView = new editClosetView(oswald, lato, user);
            editClosetView.titleField.setText(clothingItem.getTitle()); //Preload title
            editClosetView.typeComboBox.setSelectedItem(clothingItem.getType()); //Preload type
            editClosetView.acquiredComboBox.setSelectedItem(clothingItem.getAcquireMethod()); //Preload acquisition method
            editClosetView.brandComboBox.setSelectedItem(clothingItem.getBrand()); //Preload brand

            //preload materials and percentages
            if (!clothingItem.getMaterials().isEmpty()) {
                //Preload the first material bar
                String firstMaterial = clothingItem.getMaterials().get(0);
                String firstPercentage = String.valueOf(clothingItem.getPercentages().get(0));
                editClosetView.materialComboBox.setSelectedItem(firstMaterial); //Preload material
                editClosetView.percentageField.setText(firstPercentage); //Preload percentage

                //Preload additional material bars if any
                for (int i = 1; i < clothingItem.getMaterials().size(); i++) {
                    String material = clothingItem.getMaterials().get(i);
                    String percentage = String.valueOf(clothingItem.getPercentages().get(i));
                    editClosetView.addMaterialBar(material, percentage, lato);
                }
            }

            //hide save button and replace it with an update button
            editClosetView.saveButton.setVisible(false);
            JButton updateButton = new JButton("UPDATE");
            updateButton.setBounds(200, 650, 100, 40);
            updateButton.setBackground(new Color(0, 99, 73));
            updateButton.setForeground(new Color(247, 248, 247));
            updateButton.setFont(oswald.deriveFont(18f));
            updateButton.setFocusable(false);
            editClosetView.add(updateButton);

            //add the editClosetView to the frame
            topFrame.add(editClosetView, BorderLayout.CENTER);
            topFrame.revalidate(); //refresh the frame
            topFrame.repaint(); //repaint the frame

            //update the clothing item in the database when the user hits the submit button
            updateButton.addActionListener(e -> updateClothingItem(clothingItem, editClosetView, oswald, lato));
        } catch (Exception e) {
            System.out.println("Error while editing clothing item: " + e.getMessage());
        }
    }

    private void updateClothingItem(clothingItem item, editClosetView editClosetView, Font oswald, Font lato) {
        try (Connection conn = myJDBC.openConnection()) {
            conn.setAutoCommit(false); //Begin transaction

            //Extract updated details from editClosetView
            String newTitle = editClosetView.titleField.getText().trim();
            String newType = (String) editClosetView.typeComboBox.getSelectedItem();
            String newBrand = (String) editClosetView.brandComboBox.getSelectedItem(); //Brand name
            String newAcquireMethod = (String) editClosetView.acquiredComboBox.getSelectedItem();

            //Get the brand_ID from the brand table
            int brandID = -1;
            try (PreparedStatement getBrandIDStmt = conn.prepareStatement(
                    "SELECT brand_ID FROM brand WHERE brand_name = ?")) {
                getBrandIDStmt.setString(1, newBrand);
                try (ResultSet rs = getBrandIDStmt.executeQuery()) {
                    if (rs.next()) {
                        brandID = rs.getInt("brand_ID");
                    } else {
                        throw new SQLException("Brand not found: " + newBrand);
                    }
                }
            }

            //get updated materials with their corresponding material_IDs
            List<Integer> materialIDs = new ArrayList<>(); //to hold material id
            List<Integer> materialPercentages = new ArrayList<>(); //to hold the percentage

            for (JPanel materialBar : editClosetView.materialBars) {
                Component firstComponent = materialBar.getComponent(0); //percentage field
                Component secondComponent = materialBar.getComponent(2); //material combo box

                System.out.println("Component 0: " + (firstComponent != null ? firstComponent.getClass().getSimpleName() : "null"));
                System.out.println("Component 1: " + (secondComponent != null ? secondComponent.getClass().getSimpleName() : "null"));

                if (firstComponent instanceof JTextField && secondComponent instanceof JComboBox) {
                    JTextField percentageField = (JTextField) firstComponent;
                    JComboBox<String> materialComboBox = (JComboBox<String>) secondComponent;

                    String materialName = (String) materialComboBox.getSelectedItem();

                    //TODO: ACCOUNT FOR IF THERE IS NO VALUE FOR PERCENTAGE ANYMORE

                    int percentage = Integer.parseInt(percentageField.getText().trim());

                    //Get the material_ID for the selected materialName
                    int materialID = -1;
                    try (PreparedStatement stmt = conn.prepareStatement(
                            "SELECT material_ID FROM material WHERE material_type = ?")) {
                        stmt.setString(1, materialName);
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) {
                                materialID = rs.getInt("material_ID");
                            } else {
                                throw new SQLException("Material not found: " + materialName);
                            }
                        }
                    }
                    materialIDs.add(materialID);
                    materialPercentages.add(percentage);
                } else {
                    String errorMessage = "Unexpected component types in materialBar: "
                            + "Expected JTextField and JComboBox, but found "
                            + (firstComponent != null ? firstComponent.getClass().getSimpleName() : "null") + " and "
                            + (secondComponent != null ? secondComponent.getClass().getSimpleName() : "null");
                    throw new IllegalStateException(errorMessage);
                }
            }

            //update main clothing item details
            try (PreparedStatement updateClothingStmt = conn.prepareStatement(
                    "UPDATE clothes SET clothes_name = ?, clothes_type = ?, brand_ID = ?, clothes_acquisition = ? WHERE clothes_ID = ?")) {
                updateClothingStmt.setString(1, newTitle);
                updateClothingStmt.setString(2, newType);
                updateClothingStmt.setInt(3, brandID); //Use the brand_ID
                updateClothingStmt.setString(4, newAcquireMethod);
                updateClothingStmt.setInt(5, item.getClothesID());
                updateClothingStmt.executeUpdate();
            }

            //clear existing materials for the item
            try (PreparedStatement deleteMaterialsStmt = conn.prepareStatement(
                    "DELETE FROM clothes_material WHERE clothes_ID = ?")) {
                deleteMaterialsStmt.setInt(1, item.getClothesID());
                deleteMaterialsStmt.executeUpdate();
            }

            //insert updated materials with material_ID
            try (PreparedStatement insertMaterialStmt = conn.prepareStatement(
                    "INSERT INTO clothes_material (clothes_ID, material_ID, percentage) VALUES (?, ?, ?)")) {
                for (int i = 0; i < materialIDs.size(); i++) {
                    insertMaterialStmt.setInt(1, item.getClothesID());
                    insertMaterialStmt.setInt(2, materialIDs.get(i));  //Use material_ID
                    insertMaterialStmt.setInt(3, materialPercentages.get(i));
                    insertMaterialStmt.addBatch(); //Add to batch for efficiency
                }
                insertMaterialStmt.executeBatch();
            }

            //commit the transaction
            conn.commit();

            //display success message
            JDialog updateMessage = new JDialog((Frame) SwingUtilities.getWindowAncestor(editClosetView), "Clothing Item Update", true);
            updateMessage.setLayout(new BorderLayout());
            updateMessage.setSize(300, 150);
            updateMessage.setBackground(new Color(247, 248, 247));
            updateMessage.setLocationRelativeTo(editClosetView);

            JLabel updateMessageText = new JLabel("<html><div style='width:150px; text-align: center;'><b>Clothing item updated successfully!</b></div></html>");
            updateMessageText.setHorizontalAlignment(SwingConstants.CENTER);
            updateMessageText.setFont(lato.deriveFont(18f));

            JButton okButton = new JButton("OK");
            okButton.setBackground(new Color(0, 99, 73));
            okButton.setForeground(new Color(247, 248, 247));
            okButton.setFont(oswald.deriveFont(14f));
            okButton.setFocusable(false);
            okButton.addActionListener(e -> updateMessage.dispose());

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(okButton);

            updateMessage.add(updateMessageText, BorderLayout.CENTER);
            updateMessage.add(buttonPanel, BorderLayout.SOUTH);
            updateMessage.setVisible(true);

            //get the JFrame that contains editClosetView
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(editClosetView);
            if (topFrame != null) {
                topFrame.getContentPane().removeAll(); //Clear all components from the current frame
                closetView closetView = new closetView(oswald, lato, user); //Re-create the closetView
                topFrame.add(closetView, BorderLayout.CENTER); //Add closetView to the frame
                topFrame.revalidate(); //Refresh the frame
                topFrame.repaint(); //Repaint the frame
            } else {
                //handle the case when topFrame is still null
                System.err.println("Failed to get the parent JFrame.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            //handle rollback in case of error
            try (Connection conn = myJDBC.openConnection()) {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
    }

}
