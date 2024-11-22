import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class editClosetView extends JPanel {

    //Labels
    JLabel editClosetTitle = new JLabel("ADD NEW CLOTHING ITEM");
    JLabel clothesTitle = new JLabel("Title:");
    JLabel clothesTypeTitle = new JLabel("Type:");
    JLabel clothesAcquisitionTitle = new JLabel("Acquired by:");
    JLabel clothesBrandTitle = new JLabel("Brand:");
    JLabel clothesMaterialTitle = new JLabel("Material(s):");
    JLabel clothesErrorMsg = new JLabel("Error...");

    //input fields
    final JTextField titleField; //user input title
    JComboBox<String> typeComboBox; //user selected clothing item
    JComboBox<String> acquiredComboBox; //user selected acquired method
    JComboBox<String> brandComboBox; //user selected brand
    JComboBox<String> materialComboBox; //user selected material
    JTextField percentageField;
    private JPanel materialPanel; //user input material
    final ArrayList<JPanel> materialBars; //multiple materials

    //variables to hold the input to save clothing objects
    String title;
    String type;
    String acquireMethod;
    String brand;

    //save button
    JButton saveButton = new JButton("SUBMIT");

    private final user user;

    public editClosetView(Font oswald, Font lato, user user) {
        //initialize
        this.user = user;
        this.materialBars = new ArrayList<>();

        this.setLayout(null); //set to null so we can set our own bounds
        this.setBackground(new Color(235, 219, 195)); //color of panel

        //----------------------------------------------- LABEL DESIGN -----------------------------------------------//

        //set positions for the labels
        //title of the page
        editClosetTitle.setBounds(25, 30, 350, 30);
        //sections
        clothesTitle.setBounds(25, 100, 100, 30);
        clothesTypeTitle.setBounds(25, 180, 350, 30);
        clothesAcquisitionTitle.setBounds(25, 260, 350, 30);
        clothesBrandTitle.setBounds(25, 340, 350, 30);
        clothesMaterialTitle.setBounds(25, 440, 350, 30);
        //error message
        clothesErrorMsg.setBounds(50, 600, 250, 30);
        clothesErrorMsg.setVisible(false); //make error message invisible initially

        //set all title labels to color green
        editClosetTitle.setForeground(new Color(0, 99, 73));
        clothesTitle.setForeground(new Color(0, 99, 73));
        clothesTypeTitle.setForeground(new Color(0, 99, 73));
        clothesAcquisitionTitle.setForeground(new Color(0, 99, 73));
        clothesBrandTitle.setForeground(new Color(0, 99, 73));
        clothesMaterialTitle.setForeground(new Color(0, 99, 73));
        clothesErrorMsg.setForeground(new Color(0, 99, 73));

        //set font and size of the labels
        editClosetTitle.setFont(oswald.deriveFont(30f));
        clothesTitle.setFont(oswald.deriveFont(18f));
        clothesTypeTitle.setFont(oswald.deriveFont(18f));
        clothesAcquisitionTitle.setFont(oswald.deriveFont(18f));
        clothesBrandTitle.setFont(oswald.deriveFont(18f));
        clothesMaterialTitle.setFont(oswald.deriveFont(18f));
        clothesErrorMsg.setFont(lato.deriveFont(16f));

        this.add(editClosetTitle);
        this.add(clothesTitle);
        this.add(clothesTypeTitle);
        this.add(clothesAcquisitionTitle);
        this.add(clothesBrandTitle);
        this.add(clothesMaterialTitle);
        this.add(clothesErrorMsg);

        //------------------------------------------------------------------------------------------------------------//

        //------------------------------ TITLE, TYPE, METHOD, BRAND --------------------------------------------------//

        //Title Field
        titleField = new JTextField();
        titleField.setFont(lato.deriveFont(16f));
        titleField.setBackground(new Color(247, 248, 247));
        titleField.setForeground(Color.BLACK);
        titleField.setBounds(25, 130, 300, 30);
        this.add(titleField);

        //Drop-downs
        String[] type = {"Top", "Sweater/Cardigan", "Jacket", "Jeans", "Pants",
                "Shorts", "Skirt", "Dress", "Scarf", "Hat", "Vest", "Shoes"};
        typeComboBox = createStyledComboBox(type, lato);
        typeComboBox.setBounds(25, 220, 300, 30);
        this.add(typeComboBox);

        String[] method = {"In-Store Bought", "Online Shopping", "In-Store Thrift", "Online Thrift",
                "Handmade", "Gifted"};
        acquiredComboBox = createStyledComboBox(method, lato);
        acquiredComboBox.setBounds(25, 300, 300, 30);
        this.add(acquiredComboBox);

        //populate the brand combo boxes from the database
        try {

            Connection connect = myJDBC.openConnection();
            //ensure there is a connection to the database
            if (connect == null || connect.isClosed()) {
                System.out.println("Database connection is not established.");
                return; //exit the method if connection is not valid
            }

            //fetch brands
            String[] brands = getDbValues(connect, "SELECT brand_name FROM brand");
            brandComboBox = createStyledComboBox(brands, lato);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        brandComboBox.setBounds(25, 380, 300, 30);
        brandComboBox.setBackground(new Color(247, 248, 247));
        this.add(brandComboBox);

        //------------------------------------------------------------------------------------------------------------//

        //------------------------------------- MATERIAL -------------------------------------------------------------//
        //create a separate panel for material
        materialPanel = new JPanel();
        materialPanel.setLayout(new BoxLayout(materialPanel, BoxLayout.Y_AXIS)); //so they stack one after the other
        materialPanel.setBackground(new Color(235, 219, 195));

        addMaterialBar(null, null, lato); //Add initial material bar

        JScrollPane materialScrollPane = new JScrollPane(materialPanel);
        materialScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        materialScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        materialScrollPane.setBorder(BorderFactory.createEmptyBorder());
        materialScrollPane.getViewport().setBackground(new Color(235, 219, 195));
        materialScrollPane.setBounds(25, 480, 300, 100);
        //redesign scroll bar to match page
        materialScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {  //Invisible decrease button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new JButton() {  //Invisible increase button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(192, 168, 144); //Color of the scroll thumb
                this.trackColor = new Color(235, 219, 195); //Color of the scroll track (matching panel background)
            }
        });
        this.add(materialScrollPane);
        //----------------------------------------------------------------------------------------------------------------//

        //------------------------------- MATERIAL BUTTON, SAVE BUTTON, CANCEL BUTTON ------------------------------------//

        //tooltip design
        UIManager.put("ToolTip.background", new Color(247, 248, 247)); //Light yellow
        UIManager.put("ToolTip.foreground", Color.BLACK);
        UIManager.put("ToolTip.font", new Font("Lato", Font.PLAIN, 14));
        UIManager.put("ToolTip.border", BorderFactory.createLineBorder(Color.BLACK, 1));
        ToolTipManager.sharedInstance().setInitialDelay(300); //show after 200ms

        //create add material button
        ImageIcon editIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/plus2.png")));
        Image scalededitImage = editIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        JButton addMaterialButton = new JButton(new ImageIcon(scalededitImage));
        addMaterialButton.setContentAreaFilled(false);
        addMaterialButton.setBorderPainted(false);
        addMaterialButton.setFocusPainted(false);
        addMaterialButton.setOpaque(false);
        //set bounds of add material button
        addMaterialButton.setBounds(120, 440, 25, 25);
        //help the user know what the button does with a tool tip
        addMaterialButton.setToolTipText("Click to add more clothing material!");

        addMaterialButton.addActionListener(e -> addMaterialBar(null, null, lato)); //add material on click.
        this.add(addMaterialButton);

        //save button
        saveButton.setFont(oswald.deriveFont(18f));
        saveButton.setBackground(new Color(0, 99, 73)); //green button
        saveButton.setForeground(new Color(247, 248, 247)); //white text
        saveButton.setBounds(200, 650, 100, 40);
        saveButton.addActionListener(e -> saveItem(oswald, lato));
        this.add(saveButton);

        //cancel button
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setFont(oswald.deriveFont(18f));
        cancelButton.setBackground(new Color(0, 99, 73)); //green button
        cancelButton.setForeground(new Color(247, 248, 247)); //white text
        cancelButton.setBounds(50, 650, 100, 40);
        cancelButton.addActionListener(e -> cancelItem(oswald, lato));
        this.add(cancelButton);
        //------------------------------------------------------------------------------------------------------------//

    } //end of editClosetView

    //styled combo box (pull down menu's)
    private JComboBox<String> createStyledComboBox(String[] options, Font lato) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(lato.deriveFont(14f));
        comboBox.setBackground(new Color(247, 248, 247));
        return comboBox;
    }

    //add materials to clothing item
    void addMaterialBar(String material, String percentage, Font lato) {
        JPanel materialBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        materialBar.setBackground(new Color(235, 219, 195));

        //create the percentage field and set its initial value
        percentageField = new JTextField(5);
        percentageField.setFont(lato.deriveFont(Font.PLAIN, 14));
        percentageField.setText(percentage); //preload percentage
        //prevent any other char than a digit from being typed to the text field by user
        percentageField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent keyEvt) {
                char keyChar = keyEvt.getKeyChar();
                if (!Character.isDigit(keyChar)) {
                    keyEvt.consume();
                }
            }
        });

        materialBar.add(percentageField);

        JLabel percentLabel = new JLabel("%");
        percentLabel.setFont(lato.deriveFont(Font.PLAIN, 14));
        materialBar.add(percentLabel);

        try {
            Connection connect = myJDBC.openConnection();
            //ensure there is a connection to the database
            if (connect == null || connect.isClosed()) {
                System.out.println("Database connection is not established.");
                return; //exit the method if connection is not valid
            }

            //get materials
            String[] materials = getDbValues(connect, "SELECT material_type FROM material");
            materialComboBox = createStyledComboBox(materials, lato);
            materialComboBox.setBackground(new Color(247, 248, 247));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //preload material in the combo box if its being updated
        if (material != null && !material.isEmpty()) {
            materialComboBox.setSelectedItem(material); //preload material
        }

        materialBar.add(materialComboBox);

        materialBars.add(materialBar);
        materialPanel.add(materialBar);
        materialPanel.revalidate();
        materialPanel.repaint();
    }

    //helper function to execute a query and return the results as an array of Strings
    private String[] getDbValues(Connection connect, String query) {
        ArrayList<String> values = new ArrayList<>();

        try (Statement statement = connect.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                values.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values.toArray(new String[0]);
    }

    //cancel and remove panel
    private void cancelItem(Font oswald, Font lato) {
        try {
            //switch back to the closetView Panel.
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); //clear all components from the current frame
            closetView closetView = new closetView(oswald, lato, user); //initialize new page.
            topFrame.add(closetView, BorderLayout.CENTER); //add forumView Panel to the frame
            topFrame.revalidate(); //refresh the frame
            topFrame.repaint(); //repaint the frame
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }

    private void saveItem(Font oswald, Font lato) {
        try {
            Connection connect = myJDBC.openConnection();
            if (connect == null || connect.isClosed()) {
                System.out.println("Database connection is not established.");
                return;
            }

            //Escape apostrophes to avoid SQL syntax error
            title = titleField.getText().replace("'", "''");
            type = (String) typeComboBox.getSelectedItem();
            acquireMethod = (String) acquiredComboBox.getSelectedItem();
            brand = (String) brandComboBox.getSelectedItem();
            brand = brand.replace("'", "''");

            //Check if title is empty
            if (title.isEmpty()) {
                clothesErrorMsg.setVisible(true);
                clothesErrorMsg.setText("Please name your clothing item!");
                return;
            }

            //initialize temp var for clothingID
            int clothesID = -1; //placeholder for clothes ID before saving to database

            //create the ClothingItem object
            clothingItem clothingItem = new clothingItem(title, type, acquireMethod, brand, user.getUserId(), clothesID);

            //add materials to the clothing item
            for (JPanel materialBar : materialBars) { //Loop through all material bars
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

            //save the ClothingItem to the database
            clothingItemDAO clothingItemDAO = new clothingItemDAO();
            boolean saveSuccess = clothingItemDAO.saveClothingItem(clothingItem);

            if (saveSuccess) {
                //if saving is successful, retrieve the clothesID (if auto-generated) and update the ClothingItem
                try (Connection conn = myJDBC.openConnection()) {
                    String sql = "INSERT INTO clothes (clothes_name, clothes_type, brand, clothes_acquisition, user_id) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                        stmt.setString(1, title);
                        stmt.setString(2, type);
                        stmt.setString(3, brand);
                        stmt.setString(4, acquireMethod);
                        stmt.setInt(5, user.getUserId());

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //close the item form
                cancelItem(oswald, lato);

            } else {
                System.out.println("Error saving the clothing item.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}