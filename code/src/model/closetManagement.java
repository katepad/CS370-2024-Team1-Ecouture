package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import view.closetView;
import view.editClosetView;
import dataAccess.clothingItemDAO;


public class closetManagement {

    public static void saveItemLogic(Font oswald, Font lato, editClosetView editClosetView, user user) {

        //Escape apostrophes to avoid SQL syntax error
        editClosetView.title = editClosetView.titleField.getText().replace("'", "''");
        editClosetView.type = (String) editClosetView.typeComboBox.getSelectedItem();
        editClosetView.acquireMethod = (String) editClosetView.acquiredComboBox.getSelectedItem();
        editClosetView.brand = (String) editClosetView.brandComboBox.getSelectedItem();
        editClosetView.brand = editClosetView.brand.replace("'", "''");

        //Check if title is empty
        if (editClosetView.title.isEmpty()) {
            editClosetView.clothesErrorMsg.setVisible(true);
            editClosetView.clothesErrorMsg.setText("Please name your clothing item!");
            return;
        }

        //initialize temp var for clothingID
        int clothesID = -1; //placeholder for clothes ID before saving to database

        //create the ClothingItem object
        clothingItem clothingItem = new clothingItem(editClosetView.title, editClosetView.type, editClosetView.acquireMethod, editClosetView.brand, user.getUserId(), clothesID);

        //add materials to the clothing item
        for (JPanel materialBar : editClosetView.materialBars) { //Loop through all material bars
            JComboBox<String> materialCombo = (JComboBox<String>) materialBar.getComponent(2);
            JTextField percentageField = (JTextField) materialBar.getComponent(0);

            String percentageText = percentageField.getText().trim();

            //skip empty or invalid percentage fields
            if (percentageText.isEmpty()) {
                continue; //skip to the next material bar
            }

            int percentage = Integer.parseInt(percentageText); //validate the input is numeric
            String material = (String) materialCombo.getSelectedItem();
            clothingItem.addMaterial(material, percentage); //add material and percentage
        }

        clothingItemDAO.saveClothingItemAfterEditing(clothingItem, editClosetView, oswald, lato, user);
    }

    //this populates clothing items belonging to the logged-in user.
    public static void populateClothesItem(int userId, Font oswald, Font lato, JPanel clothesItemPanel, closetView closetView, user user) {
        //get clothing items for the logged-in user
        ArrayList<clothingItem> clothingItems = clothingItemDAO.getAllClothes(userId);

        //Add clothes items dynamically to the panel
        for (clothingItem clothingItem : clothingItems) {
            //pass the clothingItem and clothesItemPanel to a function that creates another panel for each clothing item
            createClothingPanel(oswald, lato, clothingItem, clothesItemPanel, closetView, user);
        }
    }

    //function that creates another panel for each clothing item
    static void createClothingPanel(Font oswald, Font lato, clothingItem clothingItem, JPanel clothesItemPanel, closetView closetView, user user) {

        //----------------------------------------------- BUTTON PANEL -----------------------------------------------//
        //create a panel to add delete and edit buttons
        //create a panel to add delete and edit buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); //align to the right
        //Set a fixed height of 40 and let width adjust automatically
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        //delete item button
        ImageIcon minusIcon = new ImageIcon(Objects.requireNonNull(closetManagement.class.getResource("/pictures/minus.png")));
        Image scaledMinusImage = minusIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton deleteButton = new JButton(new ImageIcon(scaledMinusImage));
        deleteButton.setContentAreaFilled(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        deleteButton.setOpaque(false);
        deleteButton.addActionListener(e -> createDeleteConfirmationBox(clothingItem, oswald, lato, closetView, user));
        buttonPanel.add(deleteButton);

        //edit item button
        ImageIcon editIcon = new ImageIcon(Objects.requireNonNull(closetManagement.class.getResource("/pictures/editButton.png")));
        Image scalededitImage = editIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton editButton = new JButton(new ImageIcon(scalededitImage));
        editButton.setContentAreaFilled(false);
        editButton.setBorderPainted(false);
        editButton.setFocusPainted(false);
        editButton.setOpaque(false);
        editButton.addActionListener(e -> editClothingItem(clothingItem, oswald, lato, closetView, user));
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

    private static void createDeleteConfirmationBox(clothingItem clothingItem, Font oswald, Font lato, closetView closetView, user user) {

        //create a JDialog confirmation box for deletion
        JDialog deleteConfirmBox = new JDialog((Frame) SwingUtilities.getWindowAncestor(closetView), "Delete Clothing Item", true);
        deleteConfirmBox.setLayout(new BorderLayout());
        deleteConfirmBox.setSize(300, 150);
        deleteConfirmBox.setBackground(new Color(247, 248, 247));
        deleteConfirmBox.setLocationRelativeTo(closetView); //Center deleteConfirmBox relative to the frame

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
        noButton.setBackground(new Color(239, 201, 49)); //set button color: green
        noButton.setForeground(Color.BLACK); //set button text color: white
        noButton.setFont(oswald.deriveFont(14f)); //change button text font to oswald, size 20.
        noButton.setFocusable(false);
        //yesButton design
        yesButton.setBackground(new Color(255, 178, 194)); //set button color: green
        yesButton.setForeground(Color.BLACK); //set button text color: white
        yesButton.setFont(oswald.deriveFont(14f)); //change button text font to oswald, size 20.
        yesButton.setFocusable(false);
        //------------------------------------------------------------------------------------------------------------//

        noButton.addActionListener(e -> deleteConfirmBox.dispose()); //remove dialog on "NO"
        yesButton.addActionListener(e -> {
            deleteConfirmBox.dispose(); //remove dialog
            clothingItemDAO.deleteClothingItemFromDB(clothingItem, oswald, lato, closetView, user); //delete clothing item if "YES"
        });

        //Add components to the dialog
        buttonPanel.add(noButton);
        buttonPanel.add(yesButton);
        deleteConfirmBox.add(confirmationMessage, BorderLayout.CENTER);
        deleteConfirmBox.add(buttonPanel, BorderLayout.SOUTH);

        deleteConfirmBox.setVisible(true); //show the delete confirmation box
    }

    private static void editClothingItem(clothingItem clothingItem, Font oswald, Font lato, closetView closetView, user user) {
        //Get the current clothing item details and populate the edit form

        try {
            //switch to the editClothingItem Page
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(closetView);
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
            updateButton.addActionListener(e -> clothingItemDAO.updateClothingItem(clothingItem, editClosetView, oswald, lato, user));
        } catch (Exception e) {
            System.out.println("Error while editing clothing item: " + e.getMessage());
        }
    }

}
