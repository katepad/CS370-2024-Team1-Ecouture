import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class editClosetView extends JPanel
{
    private final closetView parentPanel;
    private final CardLayout cardLayout;
    private final JTextField titleField;
    private final JLabel titleLabel;
    private final JPanel materialPanel;
    private final Font lato;
    private final ArrayList<JPanel> materialBars;

    //Drop Down Menus
    private JComboBox<String> typeComboBox;
    private JComboBox<String> acquiredComboBox;
    private JComboBox<String> brandComboBox;
    private JComboBox<String> materialComboBox;

    private JLabel typeLabel;
    private JLabel methodLabel;
    private JLabel brandLabel;
    private JLabel materialLabel;


    public editClosetView(Font oswald, Font lato, closetView parentPanel) {
        this.parentPanel = parentPanel;
        this.lato = lato;
        this.materialBars = new ArrayList<>();
        this.setLayout(new CardLayout());
        cardLayout = (CardLayout) this.getLayout();

        //-------------------- create Main Expanded Panel---------------------------------------------------------------
        //create panel
        JPanel expandedPanel = new JPanel();
        expandedPanel.setLayout(new BoxLayout(expandedPanel, BoxLayout.Y_AXIS));
        expandedPanel.setBackground(new Color(235, 219, 195));

        //create title field for user to name item
        titleField = new JTextField("Enter Item Title"); //prompt msg
        titleField.setFont(oswald.deriveFont(Font.BOLD, 24));//style
        titleField.setHorizontalAlignment(JTextField.CENTER);
        titleField.setBackground(Color.WHITE);
        titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Ensure it stretches to fit width
        expandedPanel.add(titleField);

        // Populate the combo boxes from the database
        populateComboBoxes();

        expandedPanel.add(typeComboBox);
        expandedPanel.add(acquiredComboBox);
        expandedPanel.add(brandComboBox);

// ----------------------creating separate panel for material-----------------------------------------------------------

        //create panel
        materialPanel = new JPanel();
        materialPanel.setLayout(new BoxLayout(materialPanel, BoxLayout.Y_AXIS));
        materialPanel.setBackground(new Color(235, 219, 195));

        addMaterialBar(); // call addMaterialBar function

        // Wrap the materialPanel in a scroll pane
        JScrollPane materialScrollPane = new JScrollPane(materialPanel);
        materialScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        materialScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        materialScrollPane.setBorder(BorderFactory.createEmptyBorder());
        materialScrollPane.getViewport().setBackground(new Color(235, 219, 195)); // Match the background

        //redesign scroll bar to match page
        materialScrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI()
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

        // Add the scroll pane to the expanded panel
        expandedPanel.add(materialScrollPane);
        expandedPanel.add(Box.createVerticalStrut(10)); // Add some spacing below the scroll pane

        //-----------------------------------BUTTONS------------------------------------------------------------------//
        //create add material button
        JButton addMaterialButton = new JButton("Add Material");
        addMaterialButton.setFont(lato.deriveFont(Font.BOLD, 18));
        addMaterialButton.setBackground(new Color(255,178,194));
        addMaterialButton.setForeground(Color.BLACK);

        addMaterialButton.setOpaque(true);
        addMaterialButton.setBorderPainted(false);
        addMaterialButton.setContentAreaFilled(true);

        addMaterialButton.addActionListener(e -> addMaterialBar());

        addMaterialButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        expandedPanel.add(addMaterialButton);
        expandedPanel.add(Box.createVerticalStrut(-50));

        //create seperate panel for save and cancel so they are side by side
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(new Color(235, 219, 195));

        //save button
        JButton saveButton = new JButton("Save");
        saveButton.setFont(lato.deriveFont(Font.BOLD, 18));
        saveButton.setBackground(new Color(0,99,73));
        saveButton.setForeground(Color.BLACK);

        //design
        saveButton.setOpaque(true);
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(true);

        //event
        saveButton.addActionListener(e -> saveItem());

        //cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(lato.deriveFont(Font.BOLD, 18));
        cancelButton.setBackground(new Color(239,201,49));
        cancelButton.setForeground(Color.BLACK);

        //design
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setContentAreaFilled(true);

        //event
        cancelButton.addActionListener(e -> cancelItem());

        // Add buttons to the button panel
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(30)); // Add space between buttons
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(saveButton);

        // Add the button panel to the expandedPanel
        expandedPanel.add(buttonPanel);
        expandedPanel.add(Box.createVerticalStrut(-30)); // Spacing below buttons
        //-----------------------------------------------------------------------------------------------------------//


        //-------------------------------------Collapsed panel--------------------------------------------------------//

        //create panel MAIN
        JPanel collapsedPanel = new JPanel(new BorderLayout());
        // collapsedPanel.setLayout(new BoxLayout(collapsedPanel, BoxLayout.Y_AXIS));
        collapsedPanel.setBackground(Color.white);

        // Create a panel for the buttons
        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel2.setOpaque(false);

        //remove item button
        ImageIcon minusIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/minus.png")));
        Image scaledMinusImage = minusIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton deleteButton = new JButton(new ImageIcon(scaledMinusImage));
        deleteButton.setContentAreaFilled(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        deleteButton.setOpaque(false);
        deleteButton.addActionListener(e -> deleteItem());
        buttonPanel2.add(deleteButton);

        //edit item button
        ImageIcon editIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/editButton.png")));
        Image scalededitImage = editIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton editButton = new JButton(new ImageIcon(scalededitImage));
        editButton.setContentAreaFilled(false);
        editButton.setBorderPainted(false);
        editButton.setFocusPainted(false);
        editButton.setOpaque(false);
        buttonPanel2.add(editButton);

        //add button panel 2 to collapsedPanel
        collapsedPanel.add(buttonPanel2, BorderLayout.NORTH);

        // Create a panel for the text labels
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.white);
        textPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10)); // Add some padding

        // Title Label
        titleLabel = new JLabel();
        titleLabel.setFont(oswald.deriveFont(Font.BOLD, 25));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(titleLabel);

        // Style Label
        typeLabel = new JLabel();
        typeLabel.setFont(lato.deriveFont(Font.PLAIN, 14));
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(typeLabel);

        // Acquisition Method Label
        methodLabel = new JLabel();
        methodLabel.setFont(lato.deriveFont(Font.PLAIN, 14));
        methodLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(methodLabel);

        // Brand Label
        brandLabel = new JLabel();
        brandLabel.setFont(lato.deriveFont(Font.PLAIN, 14));
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(brandLabel);

        // Material Label
        materialLabel = new JLabel();
        materialLabel.setFont(lato.deriveFont(Font.PLAIN, 14));
        materialLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(materialLabel);

        //add text panel to collapsedPanel
        collapsedPanel.add(textPanel, BorderLayout.CENTER);

        //-------------------------------------------------------------------------------------------------------------------

        this.add(expandedPanel, "Expanded");
        this.add(collapsedPanel, "Collapsed");
        cardLayout.show(this, "Expanded");

    } //end of constructor

    // Function to populate combo boxes with values from the database
    private void populateComboBoxes() {
        try {

            Connection connect = myJDBC.openConnection();
            //Ensure there is a connection to the database
            if (connect == null || connect.isClosed()) {
                System.out.println("Database connection is not established.");
                return; // Exit the method if connection is not valid
            }

            // create Style Dropdown menu
            String[] type = {"--Style--", "Top", "Sweater + Cardigans", "Jacket", "Jeans", "Shorts", "Skirt", "Dress", "Scarf", "Hat", "Vest", "Shoes"};
            typeComboBox = createStyledComboBox(type, lato);

            // create Acquisition Method Dropdown menu
            String[] acquisitionMethods = {"--Acquisition Method--", "In-Store Bought", "Online Shopping", "In-Store Thrifted", "Online Thrifted", "Handmade"};
            acquiredComboBox = createStyledComboBox(acquisitionMethods, lato);

            // Fetch brands
            String[] brands = getDatabaseValues(connect, "SELECT brand_name FROM brand");
            brandComboBox = createStyledComboBox(brands, lato);

            // Fetch materials
            String[] materials = getDatabaseValues(connect, "SELECT material_type FROM material");
            materialComboBox = createStyledComboBox(materials, lato);

            // Close the database connection
            connect.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper function to execute a query and return the results as an array of Strings
    private String[] getDatabaseValues(Connection connect, String query) {
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


    //----------------------------------------FUNCTION MaterialBar design--------------------------------------------------------
    private void addMaterialBar()
    {
        JPanel materialBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        materialBar.setBackground(new Color(235, 219, 195));

        // Text Field for percentage
        JTextField percentageField = new JTextField(5); // Shortened text box
        percentageField.setFont(lato.deriveFont(Font.PLAIN, 14));
        percentageField.setBackground(new Color(235, 219, 195));
        materialBar.add(percentageField);

        // Percentage label
        JLabel percentLabel = new JLabel("%");
        percentLabel.setFont(lato.deriveFont(Font.PLAIN, 14));
        percentLabel.setForeground(Color.BLACK);
        materialBar.add(percentLabel);

        // Material Dropdown
        populateComboBoxes();
        materialBar.add(materialComboBox);

        materialBars.add(materialBar);
        materialPanel.add(materialBar);
        materialPanel.revalidate();
        materialPanel.repaint();
    }
//----------------------------------------------------------------------------------------------------------------------

    //-------------------------------FUNCTION pull down bar design---------------------------------------------------------------
    private JComboBox<String> createStyledComboBox(String[] options, Font font)
    {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(font.deriveFont(Font.PLAIN, 14));
        comboBox.setBackground(new Color(235, 219, 195));

        return comboBox;
    }
//----------------------------------------------------------------------------------------------------------------------

    //------------------------------FUNCTION to save item-----------------------------------------------------------------
    private void saveItem() {
        // Capture the title
        String savedTitle = titleField.getText();

        if (savedTitle.isEmpty())
        {
            savedTitle = "Untitled Item";
        }
        if(savedTitle.length() > 20)
        {
            savedTitle = savedTitle.substring(0, 17) + "...";
        }
        titleLabel.setText(savedTitle);

        // Get selected values from the dropdowns
        String selectedType = (String) typeComboBox.getSelectedItem();
        String selectedMethod = (String) acquiredComboBox.getSelectedItem();
        String selectedBrand = (String) brandComboBox.getSelectedItem();

        // Update the labels in the collapsed panel
        typeLabel.setText("Style: " + (selectedType != null && !selectedType.equals("--Style--") ? selectedType : "N/A"));
        methodLabel.setText("Acquisition Method: " + (selectedMethod != null && !selectedMethod.equals("--Acquisition Method--") ? selectedMethod : "N/A"));
        brandLabel.setText("Brand: " + (selectedBrand != null && !selectedBrand.equals("--Brand--") ? selectedBrand : "N/A"));

        // Update materials
        StringBuilder materialsText = new StringBuilder("Materials: ");
        for (JPanel materialBar : materialBars)
        {
            JTextField percentageField = (JTextField) materialBar.getComponent(0);
            JComboBox<String> materialComboBox = (JComboBox<String>) materialBar.getComponent(2);

            String percentage = percentageField.getText();
            String material = (String) materialComboBox.getSelectedItem();

            if (!percentage.isEmpty() && material != null && !material.equals("--material--"))
            {
                materialsText.append(percentage).append("% ").append(material).append(", ");
            }
        }

        // Remove trailing comma and space
        if (materialsText.length() > 10)
        {
            materialsText.setLength(materialsText.length() - 2);
        }
        else
        {
            materialsText.append("N/A");
        }

        materialLabel.setText(materialsText.toString());


        // Switch to collapsed view
        cardLayout.show(this, "Collapsed");
        parentPanel.showAllPanels();
        parentPanel.showNavigationBar();
    }
//----------------------------------------------------------------------------------------------------------------------

    //-----------------------------FUNCTION to delete item--------------------------------------------------------------
    private void deleteItem()
    {
        parentPanel.removeItemPanel(this);
    }
    //------------------------------------------------------------------------------------------------------------------

    private void cancelItem()
    {
        parentPanel.removeItemPanel(this);
        cardLayout.show(this, "Collapsed");
        parentPanel.showAllPanels();
        parentPanel.showNavigationBar();
    }

}
