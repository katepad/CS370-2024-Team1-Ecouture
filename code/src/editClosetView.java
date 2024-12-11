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
            String[] brands = clothingItemDAO.getDbValues(connect, "SELECT brand_name FROM brand");
            brandComboBox = createStyledComboBox(brands, lato);

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
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
        saveButton.addActionListener(e -> closetController.saveItemClicked(oswald, lato, this, user));
        this.add(saveButton);

        //cancel button
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setFont(oswald.deriveFont(18f));
        cancelButton.setBackground(new Color(0, 99, 73)); //green button
        cancelButton.setForeground(new Color(247, 248, 247)); //white text
        cancelButton.setBounds(50, 650, 100, 40);
        cancelButton.addActionListener(e -> closetController.cancelItem(oswald, lato, this, user));
        this.add(cancelButton);
        //------------------------------------------------------------------------------------------------------------//

    } //end of editClosetView

    //styled combo box (pull down menu's)
    JComboBox<String> createStyledComboBox(String[] options, Font lato) {
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

        clothingItemDAO.getAllClothes(this, lato);

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

}