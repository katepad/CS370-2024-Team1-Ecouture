import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class editClosetView extends JPanel
{

    private final closetView parentPanel; //parent class
    private final JTextField titleField; //user input title
    private final JComboBox<String> typeComboBox; //user selected clothing item
    private final JComboBox<String> acquiredComboBox; //user selected aquired method
    private final JComboBox<String> brandComboBox; //user selected brand
    private final JPanel materialPanel; //user input material
    private final ArrayList<JPanel> materialBars; //multiple materials
    private final Font lato;

    public editClosetView(Font oswald, Font lato, closetView parentPanel)
    {
        //initialize
        this.parentPanel = parentPanel;
        this.lato = lato;
        this.materialBars = new ArrayList<>();

        this.setLayout(null); //set to null so we can set our own bounds
        this.setBackground(new Color(235, 219, 195)); //color of panel

//------------------------------TITLE, TYPE, METHOD, BRAND--------------------------------------------------------------
        // Title Field
        titleField = new JTextField("Enter Item Title");
        titleField.setFont(oswald.deriveFont(Font.BOLD, 24));
        titleField.setBackground(Color.WHITE);
        titleField.setHorizontalAlignment(SwingConstants.CENTER);
        titleField.setBounds(50, 20, 300, 40);
        this.add(titleField);

      // Populate the combo boxes from the database
        populateComboBoxes();

        // Drop-downs
       String[] type = {"--Style--", "Top", "Sweater + Cardigans", "Jacket", "Jeans",
                        "Shorts", "Skirt", "Dress", "Scarf", "Hat", "Vest", "Shoes"};
       typeComboBox = createStyledComboBox(type, lato);
       typeComboBox.setBounds(50, 100, 300, 30);
        this.add(typeComboBox);

        String[] method = {"--Acquisition Method--", "Thrifted + Second Hand",
                            "Gifted", "Online", "In-store"};
        acquiredComboBox = createStyledComboBox(method, lato);
        acquiredComboBox.setBounds(50, 200, 300, 30);
        this.add(acquiredComboBox);

        String[] brand = {"--Brand--", "Abercrombie & Fitch", "Adidas", "Alo Yoga",
                "Artizia", "Brandy Melville", "Fashion Nova", "Forever 21",
                "Francesca's", "Free People", "Guess", "H&M", "Hollister",
                "Hot Topic", "Lululemon", "Motel Rocks", "Nasty Gal", "Nike",
                "PacSun", "PrettyLittleThing", "Princess Polly", "Romwe", "Shein",
                "Steve Madden", "Tilly's", "Uniqlo", "Urban Outfitters", "Zumiez"};
        brandComboBox = createStyledComboBox(brand, lato);
        brandComboBox.setBounds(50, 300, 300, 30);
        this.add(brandComboBox);
//----------------------------------------------------------------------------------------------------------------------

//--------------------------------MATERIAL------------------------------------------------------------------------------
        //create a separate panel for material
        materialPanel = new JPanel();
        materialPanel.setLayout(new BoxLayout(materialPanel, BoxLayout.Y_AXIS)); //so they stack one after the other
        materialPanel.setBackground(new Color(235, 219, 195));

        addMaterialBar(); // Add initial material bar

        JScrollPane materialScrollPane = new JScrollPane(materialPanel);
        materialScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        materialScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        materialScrollPane.setBorder(BorderFactory.createEmptyBorder());
        materialScrollPane.getViewport().setBackground(new Color(235, 219, 195));
        materialScrollPane.setBounds(50, 400, 300, 100);
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
        this.add(materialScrollPane);
//----------------------------------------------------------------------------------------------------------------------

//---------------------------------------MATERIAL BUTTON, SAVE BUTTON, CANCEL BUTTON------------------------------------
        //create add material button
        JButton addMaterialButton = new JButton("Add Material");
        addMaterialButton.setFont(lato.deriveFont(Font.BOLD, 18));
        addMaterialButton.setBackground(new Color(255,178,194));
        addMaterialButton.setForeground(Color.BLACK);
        addMaterialButton.setOpaque(true);
        addMaterialButton.setBorderPainted(false);
        addMaterialButton.setContentAreaFilled(true);
        addMaterialButton.setBounds(80, 550, 200, 40);
        addMaterialButton.addActionListener(e -> addMaterialBar());
        this.add(addMaterialButton);

        //save button
        JButton saveButton = new JButton("Save");
        saveButton.setFont(lato.deriveFont(Font.BOLD, 18));
        saveButton.setBackground(new Color(0,99,73));
        saveButton.setForeground(Color.BLACK);
        saveButton.setOpaque(true);
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(true);
        saveButton.setBounds(50, 650, 120, 40);
        saveButton.addActionListener(e -> saveItem());
        this.add(saveButton);

        //cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(lato.deriveFont(Font.BOLD, 18));
        cancelButton.setBackground(new Color(239,201,49));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setContentAreaFilled(true);
        cancelButton.setBounds(200, 650, 120, 40);
        cancelButton.addActionListener(e -> cancelItem());
        this.add(cancelButton);
//----------------------------------------------------------------------------------------------------------------------

    }//end of editClosetView

    // Styled combo box (pull down menu's)
    private JComboBox<String> createStyledComboBox(String[] options, Font font)
    {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(font.deriveFont(Font.PLAIN, 14));
        comboBox.setBackground(new Color(235, 219, 195));
        return comboBox;
    }

    // Add a new material bar
    private void addMaterialBar()
    {
        JPanel materialBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        materialBar.setBackground(new Color(235, 219, 195));

        JTextField percentageField = new JTextField(5);
        percentageField.setFont(lato.deriveFont(Font.PLAIN, 14));
        materialBar.add(percentageField);

        JLabel percentLabel = new JLabel("%");
        percentLabel.setFont(lato.deriveFont(Font.PLAIN, 14));
        materialBar.add(percentLabel);

        JComboBox<String> materialComboBox = createStyledComboBox(new String[]{"--material--", "Organic Cotton",
                "Cotton", "Polyester", "Wool", "Nylon", "Silk"}, lato);
        materialBar.add(materialComboBox);

        materialBars.add(materialBar);
        materialPanel.add(materialBar);
        materialPanel.revalidate();
        materialPanel.repaint();
    }

    //save user input
    private void saveItem()
    {
        //store user selections/input into new variables
        String title = titleField.getText();
        String type = (String) typeComboBox.getSelectedItem();
        String acquisition = (String) acquiredComboBox.getSelectedItem();
        String brand = (String) brandComboBox.getSelectedItem();

        StringBuilder materials = new StringBuilder();
        for (JPanel bar : materialBars)
        {
            JTextField percentageField = (JTextField) bar.getComponent(0);
            JComboBox<String> materialComboBox = (JComboBox<String>) bar.getComponent(2);
            String percentage = percentageField.getText();
            String material = (String) materialComboBox.getSelectedItem();

            if (!percentage.isEmpty() && material != null && !material.equals("--material--"))
            {
                materials.append(percentage).append("% ").append(material).append(", ");
            }
        }

        if (materials.length() > 2)
        {
            materials.setLength(materials.length() - 2); // Remove trailing comma
        } else
        {
            materials.append("N/A");
        }

        // Pass data back to closetView and reset
        parentPanel.saveItemPanel(
                title.isEmpty() ? "Untitled Item" : title,
                type == null || type.equals("--Style--") ? "N/A" : type,
                acquisition == null || acquisition.equals("--Acquisition Method--") ? "N/A" : acquisition,
                brand == null || brand.equals("--Brand--") ? "N/A" : brand,
                materials.toString(),
                lato,
                lato
        );
    }


    // Cancel and remove panel
    private void cancelItem()
    {
        parentPanel.cancelPanel();
    }


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

}

