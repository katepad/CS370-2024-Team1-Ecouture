package dataAccess;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

import model.clothingItem;
import view.editClosetView;
import view.closetView;
import model.user;
import controller.closetController;


public class clothingItemDAO {


    public static void updateClothingItem(clothingItem item, editClosetView editClosetView, Font oswald, Font lato, user user) {

        try (Connection conn = myJDBC.openConnection()) {
            conn.setAutoCommit(false); //Begin transaction

            //Extract updated details from editClosetView
            String newTitle = editClosetView.titleField.getText().trim();
            String newType = (String) editClosetView.typeComboBox.getSelectedItem();
            String newBrand = (String) editClosetView.brandComboBox.getSelectedItem(); //Brand name
            String newAcquireMethod = (String) editClosetView.acquiredComboBox.getSelectedItem();

            //Get the brand_ID from the brand table
            int brandID;
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

                    int percentage = Integer.parseInt(percentageField.getText().trim());

                    //Get the material_ID for the selected materialName
                    int materialID;
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
            System.out.println("SQL error: " + e.getMessage());
            //handle rollback in case of error
            try (Connection conn = myJDBC.openConnection()) {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("SQL error: " + e.getMessage());
            }
        }
    }

    public static void deleteClothingItemFromDB(clothingItem clothingItem, Font oswald, Font lato, closetView closetView, user user) {

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
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(closetView);
            topFrame.getContentPane().removeAll(); //clear the current view
            closetView newclosetView = new closetView(oswald, lato, user); //recreate closet view
            topFrame.add(newclosetView, BorderLayout.CENTER); //add the updated closet view
            topFrame.revalidate(); //refresh the frame
            topFrame.repaint(); //repaint the frame

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    public static void saveClothingItemAfterEditing(clothingItem clothingItem, editClosetView editClosetView, Font oswald, Font lato, user user) {
        try {
            Connection connect = myJDBC.openConnection();
            //ensure there is a connection to the database
            if (connect == null || connect.isClosed()) {
                System.out.println("Database connection is not established.");
                return;
            }

            //save the ClothingItem to the database
            clothingItemDAO clothingItemDAO = new clothingItemDAO();
            boolean saveSuccess = clothingItemDAO.saveClothingItem(clothingItem);

            if (saveSuccess) {
                //if saving is successful, retrieve the clothesID (if auto-generated) and update the ClothingItem
                try (Connection conn = myJDBC.openConnection()) {
                    String sql = "INSERT INTO clothes (clothes_name, clothes_type, brand, clothes_acquisition, user_id) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                        stmt.setString(1, editClosetView.title);
                        stmt.setString(2, editClosetView.type);
                        stmt.setString(3, editClosetView.brand);
                        stmt.setString(4, editClosetView.acquireMethod);
                        stmt.setInt(5, user.getUserId());

                    }
                } catch (SQLException e) {
                    System.out.println("SQL error: " + e.getMessage());
                }

                //close the item form
                closetController.cancelItem(oswald, lato, editClosetView, user);

            } else {
                System.out.println("Error saving the clothing item.");
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    public static void getAllClothes(editClosetView editClosetView, Font lato) {
        try {
            Connection connect = myJDBC.openConnection();
            //ensure there is a connection to the database
            if (connect == null || connect.isClosed()) {
                System.out.println("Database connection is not established.");
                return; //exit the method if connection is not valid
            }

            //get materials
            String[] materials = getDbValues(connect, "SELECT material_type FROM material");
            editClosetView.materialComboBox = editClosetView.createStyledComboBox(materials, lato);
            editClosetView.materialComboBox.setBackground(new Color(247, 248, 247));

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    //helper function to execute a query and return the results as an array of Strings
    public static String[] getDbValues(Connection connect, String query) {
        ArrayList<String> values = new ArrayList<>();

        try (Statement statement = connect.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                values.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return values.toArray(new String[0]);
    }

    public boolean saveClothingItem(clothingItem clothingItem) throws SQLException {

        //query to get brand ID that matches selected brand name
        String getBrandID = "SELECT brand_ID FROM brand WHERE brand_name = ?";
        //query to save all single valued columns into clothes table in db
        String insertClothesSQL = "INSERT INTO clothes (clothes_name, clothes_type, clothes_acquisition, brand_ID, user_ID) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = myJDBC.openConnection();  //open connection
             PreparedStatement getBrandStmt = conn.prepareStatement(getBrandID)) {

            //error handling
            getBrandStmt.setString(1, clothingItem.getBrand());
            try (ResultSet brandResultSet = getBrandStmt.executeQuery()) {
                if (!brandResultSet.next()) {
                    throw new SQLException("Brand not found: " + clothingItem.getBrand());
                }

                int brandId = brandResultSet.getInt("brand_ID"); //var to store brand ID

                try (PreparedStatement clothesStmt = conn.prepareStatement(insertClothesSQL, Statement.RETURN_GENERATED_KEYS)) {
                    clothesStmt.setString(1, clothingItem.getTitle());
                    clothesStmt.setString(2, clothingItem.getType());
                    clothesStmt.setString(3, clothingItem.getAcquireMethod());
                    clothesStmt.setInt(4, brandId);
                    clothesStmt.setInt(5, clothingItem.getUserId());

                    clothesStmt.executeUpdate(); //exe statement

                    try (ResultSet resultSet = clothesStmt.getGeneratedKeys()) {
                        if (!resultSet.next()) {
                            throw new SQLException("Failed to retrieve clothing ID.");
                        }

                        int clothingId = resultSet.getInt(1); //get the clothing ID

                        //function to save materials in clothes_material table
                        saveMaterials(conn, clothingId, clothingItem.getMaterials(), clothingItem.getPercentages());
                        return true;
                    }
                }
            }
        }
    }

    //function to save materials into the clothes_material table
    private void saveMaterials(Connection conn, int clothingId, List<String> materials, List<Integer> percentages) throws SQLException {

        //query to get material_ID for each material selected by user
        String getMaterialIDQuery = "SELECT material_ID FROM material WHERE material_type = ?";
        //query to insert material into clothes_material table
        String insertMaterialsQuery = "INSERT INTO clothes_material (clothes_ID, material_ID, percentage) VALUES (?, ?, ?)";

        try (PreparedStatement getMaterialStmt = conn.prepareStatement(getMaterialIDQuery);
             PreparedStatement insertMaterialsStmt = conn.prepareStatement(insertMaterialsQuery)) {

            //to insert as many materials as there are material bar panels
            for (int i = 0; i < materials.size(); i++) {
                getMaterialStmt.setString(1, materials.get(i));
                try (ResultSet materialResultSet = getMaterialStmt.executeQuery()) {
                    if (!materialResultSet.next()) {
                        throw new SQLException("Material not found: " + materials.get(i));
                    }

                    int materialId = materialResultSet.getInt("material_ID");

                    insertMaterialsStmt.setInt(1, clothingId);
                    insertMaterialsStmt.setInt(2, materialId);
                    insertMaterialsStmt.setInt(3, percentages.get(i));
                    insertMaterialsStmt.addBatch();
                }
            }

            insertMaterialsStmt.executeBatch();
        }
    }
    public static ArrayList<clothingItem> getAllClothes(int userId) {

        //array to store all clothing items belonging to logged-in user
        ArrayList<clothingItem> clothingItems = new ArrayList<>();

        //query to join the brand table and get clothes data for a specific user.
        String query = "SELECT c.user_ID, c.clothes_ID, c.clothes_name, c.clothes_type, " +
                "c.clothes_acquisition, b.brand_name, cm.material_ID, m.material_type, cm.percentage " +
                "FROM clothes c " + //select needed information from clothes
                "LEFT JOIN brand b ON c.brand_ID = b.brand_ID " +  //join brand table and clothes table
                "LEFT JOIN clothes_material cm ON c.clothes_ID = cm.clothes_ID " + //join clothes_material and clothes tables
                "LEFT JOIN material m ON cm.material_ID = m.material_ID " + //join clothes_material and material tables
                "WHERE c.user_ID = ? " + //only show clothes belonging to logged-in user with user_ID
                "ORDER BY c.clothes_ID DESC"; //order clothes by descending order

        try (Connection conn = myJDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId); //set the userId parameter

            try (ResultSet rs = stmt.executeQuery()) {
                int previousClothesID = -1;
                clothingItem currentClothingItem = null;

                while (rs.next()) {
                    int clothesId = rs.getInt("clothes_ID");

                    //if we're on a new clothing item, create a new object
                    if (clothesId != previousClothesID) {
                        if (currentClothingItem != null) {
                            clothingItems.add(currentClothingItem); //add the previous clothing item to the list
                        }
                        String clothesName = rs.getString("clothes_name");
                        String clothesType = rs.getString("clothes_type");
                        String clothesAcquisition = rs.getString("clothes_acquisition");
                        String brandName = rs.getString("brand_name");

                        //create a new clothing item
                        currentClothingItem = new clothingItem(clothesName, clothesType, clothesAcquisition, brandName, userId, clothesId);
                    }

                    //add material and percentages
                    String materialType = rs.getString("material_type");
                    int percentage = rs.getInt("percentage");
                    if (materialType != null && !materialType.isEmpty()) {
                        currentClothingItem.addMaterial(materialType, percentage);
                    }

                    previousClothesID = clothesId;
                }

                //add the last clothing item
                if (currentClothingItem != null) {
                    clothingItems.add(currentClothingItem);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return clothingItems;
    }
    public double[] getMaterialAverageForUser() throws SQLException {
        double[] averages = new double[4];
        int userID = user.getUserId();
        //Joins 3 tables of clothes, clothes_material, and material, then in SQL, add all columns of that data and takes the average
        //Also has percentages in mind since clothes could have different compositions
        String sql = "SELECT " +
                "SUM(m.material_decomp*cm.Percentage)/ SUM(cm.Percentage) AS avg_decomp, " +
                "SUM(m.material_water*cm.Percentage)/SUM(cm.Percentage) AS avg_water, " +
                "SUM(m.material_energy*cm.Percentage)/SUM(cm.Percentage) AS avg_energy, " +
                "SUM(m.material_emission*cm.Percentage)/SUM(cm.Percentage) AS avg_emission " +
                "FROM clothes c " +
                "JOIN clothes_material cm ON c.clothes_ID = cm.clothes_ID " +
                "JOIN material m ON cm.material_ID = m.material_ID " +
                "WHERE c.user_ID = ?";

        //Sends the string to SQL as a statement
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(sql);
        //Uses the user_ID of the user logged in so it will not access other user's closets
        preparedStatement.setInt(1, userID);
        //Sending the prepared statements to the database and executing them
        ResultSet resultSet = preparedStatement.executeQuery();
        //Put average of closet's clothes into the array
        if (resultSet.next()) {
            averages[0] = resultSet.getDouble("avg_decomp");
            averages[1] = resultSet.getDouble("avg_water");
            averages[2] = resultSet.getDouble("avg_energy");
            averages[3] = resultSet.getDouble("avg_emission");
        }

        return averages;
    }
    public double getMaterialAverage() throws SQLException {
        int userID = user.getUserId();
        double material = 0;
        //Joins 3 tables of clothes, clothes_material, and material and gets the overall rating of the material
        String sql = "SELECT " +
                "SUM(m.material_rating*cm.Percentage)/ SUM(cm.Percentage) AS avg_material " +
                "FROM clothes c " +
                "JOIN clothes_material cm ON c.clothes_ID = cm.clothes_ID " +
                "JOIN material m ON cm.material_ID = m.material_ID " +
                "WHERE c.user_ID = ?";

        //Sends the string to SQL as a statement
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(sql);
        //Uses the user_ID of the user logged in so it will not access other user's closets
        preparedStatement.setInt(1, userID);
        //Sending the prepared statements to the database and executing them
        ResultSet resultSet = preparedStatement.executeQuery();
        //Stores the total average of the material into a variable called material
        if (resultSet.next()) {
            material = resultSet.getDouble("avg_material");
        }

        return material;
    }
    //------------------------------------------------------------------------------------------------------------------------------

    public double getCarbonFootprint(int userID) throws SQLException {
        double carbon = 0;
        //Joins table of material, clothes to calculate carbon footprint which is emission * clothes_acquisition
        //Case statements to check for what the user input as clothes_acquisition
        String sql = "SELECT " +
                "SUM(m.material_emission * " +
                "CASE " +
                "WHEN c.clothes_acquisition = 'In-Store Bought' THEN 8.0 " +
                "WHEN c.clothes_acquisition = 'Online Shopping' THEN 10.0 " +
                "WHEN c.clothes_acquisition = 'In-Store Thrift' THEN 1.0 " +
                "WHEN c.clothes_acquisition = 'Online Thrift' THEN 3.0 " +
                "WHEN c.clothes_acquisition = 'Handmade' THEN 5.0 " +
                "WHEN c.clothes_acquisition = 'Gifted' THEN 9.0 " +
                "ELSE 0.0 " +
                "END) / COUNT(c.clothes_ID) AS carbon_footprint " +
                "FROM clothes c " +
                "JOIN clothes_material cm ON c.clothes_ID = cm.clothes_ID " +
                "JOIN material m ON cm.material_ID = m.material_ID " +
                "WHERE c.user_ID = ?";

        //Send the string to SQL as a statement
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(sql);
        //Uses the user_ID of the user logged in so it will not access other user's closets
        preparedStatement.setInt(1, userID);
        //Execute the statements
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            carbon+=(rs.getDouble("carbon_footprint"));
        }
        return carbon;
    }
    public double getBrandRating(int userID) throws SQLException{
        //Joining brand table with clothes table
        String sql = "SELECT AVG(b.brand_rating) AS avg_brand_rating " +
                "FROM clothes c " +
                "JOIN brand b on c.brand_ID = b.brand_ID " +
                "WHERE c.user_ID = ? AND b.brand_name NOT LIKE '%Other'";

        //Sends the string to SQL as a statement
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(sql);
        //Uses the user_ID of the user logged in so it will not access other user's closets
        preparedStatement.setInt(1,userID);
        //Execute the statement
        ResultSet rs = preparedStatement.executeQuery();

        double avg_Brand = 0;
        if(rs.next()){
            avg_Brand = rs.getDouble("avg_brand_rating");
        }
        return avg_Brand;
    }
    public static double sustainRating(double mResult, double bRating){
        //Equation to calculate the overall rating of user's closet
        return  (mResult+bRating)/2;
    }
    //----------------------------------Create Star Rating--------------------------------------------------------------
    //Function to display stars to show how what the rating of the user's closet is
    public void createStarRating(Container container, double rating) {
        int fullstars = (int) rating;

        //Equation to get half star
        double fraction = rating - fullstars;

        int halfStar = 0;

        //If statement to check if there needs to be half star
        if(fraction >= .01 && fraction < .99){
            halfStar = 1;
        }
        //Equation for the rest of the empty stars
        int emptyStars = 5 - fullstars - halfStar;

        //Images icon for full star, half star, empty star, gets the image, scale and smooths it out
        ImageIcon fullstar = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/fullStars.png")));
        Image full = fullstar.getImage();
        Image image = full.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        ImageIcon fullIcon = new ImageIcon(image);

        ImageIcon halfstar = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/halfStars.png")));
        Image half = halfstar.getImage();
        Image image2 = half.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        ImageIcon halfIcon = new ImageIcon(image2);

        ImageIcon emptystar = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/emptyStars.png")));
        Image empty = emptystar.getImage();
        Image image3 = empty.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        ImageIcon emptyIcon = new ImageIcon(image3);

        //Creates the box to display the stars
        Box starBox = Box.createHorizontalBox();
        starBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Loops to add stars into a box to display it
        for (int i = 0; i < fullstars; i++) {
            JLabel starLabels = new JLabel(fullIcon);
            starBox.add(starLabels);
        }
        for(int i = 0; i < halfStar; i++){
            JLabel halfLabel = new JLabel(halfIcon);
            starBox.add(halfLabel);
        }
        for (int i = 0; i < emptyStars; i++) {
            JLabel Emptystar = new JLabel(emptyIcon);
            starBox.add(Emptystar);
        }
        container.add(starBox);
    }
    //--------------------------------------------------------------------------------------------------

    //--------------------------------Get Material Function----------------------------------------------
    public ArrayList<String> getMaterials(int userID) throws SQLException{
        ArrayList<String> materials = new ArrayList<>();
        double totalPercentage = 0;
        //Count what material and how many material are there, joining material, clothes_material
        String sql = "SELECT m.material_type, SUM(cm.Percentage) as total_percentage " +
                "FROM clothes c " +
                "JOIN clothes_material cm ON c.clothes_ID = cm.clothes_ID " +
                "JOIN material m on cm.material_ID = m.material_ID "+
                "WHERE c.user_ID = ? " +
                "GROUP by m.material_type";

        //Sends the string to SQL as a statement as well does not care for changes
        PreparedStatement prepared_statement = myJDBC.connect.prepareStatement(
                sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );
        //Uses the user_ID of the user logged in so it will not access other user's closets
        prepared_statement.setInt(1,userID);
        //Executes the statement
        ResultSet result = prepared_statement.executeQuery();

        //While loop to save what material there are in the closet and how much of it is there
        while(result.next()){
            double materialPercentage = result.getDouble("total_percentage");
            totalPercentage+=materialPercentage;
        }
        //Moves to the front of the ResultSet object
        result.beforeFirst();

        //While loop that stores what type of material into the arraylist and get the percentage of what the clothes are made of
        while(result.next()){
            String material = result.getString("material_type");
            double materialPercentage = result.getDouble("total_percentage");
            double relativePercentage = (materialPercentage / totalPercentage) * 100;
            materials.add(material + ": " + String.format("%.2f", relativePercentage) + "%");
        }
        return materials;
    }
    public ArrayList<String> getAcquisition(int userID) throws SQLException{
        ArrayList<String> acquisition = new ArrayList<>();
        double total = 0;
        //Statement that counts the types of acquisition
        String sql = "SELECT c.clothes_acquisition, COUNT(c.clothes_ID) AS total_clothes "+
                "FROM clothes c " +
                "WHERE c.user_ID = ? "+
                "GROUP BY c.clothes_acquisition";

        //Sends the string to SQL as a statement as well does not care for changes
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(
                sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );

        //Uses the user_ID of the user logged in so it will not access other user's closets
        preparedStatement.setInt(1,userID);
        //Execute statement
        ResultSet rs = preparedStatement.executeQuery();

        //While loop that adds clothes_acquisition, this will be later used to get the percentage of each clothes_acquisition
        while(rs.next()){
            total += rs.getInt("total_clothes");
        }

        //Move to first of the ResultSet object
        rs.beforeFirst();

        //While loop that will add what type of clothes_acquisition, how much of each clothes_acquisition, and the relative percentage of all clothes_type.
        // Stores the result into the arraylist
        while(rs.next()){
            String acq = rs.getString("clothes_acquisition");
            int totalClothes = rs.getInt("total_clothes");
            double relative = (totalClothes/total) * 100;
            acquisition.add(acq + ": " + String.format("%.2f", relative) + "%");
        }
        return acquisition;
    }
    //---------------------------------------------------------------------------------------------------
//--------------------------------PIE CHART FUNCTION--------------------------------------------------
    public JFreeChart createPieChart(int userID) throws SQLException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        ArrayList<String> materials = getMaterials(userID);
        //Array for which color to cycle through when constructing pie chart
        Color[] colors = new Color[]{
                Color.decode("#B3A1D9"),
                Color.decode("#FFB2C2"),
                Color.decode("#FFA07A"),
                Color.decode("#EFC931"),
                Color.decode("#DED980"),
                Color.decode("#DED980"),
                Color.decode("#8FA6A3"),
                Color.decode("#66A68C"),
                Color.decode("#33856B"),
                Color.decode("#006349"),
                Color.decode("#004F47"),
                Color.decode("#5F4B8B")
        };
        //Sets the title, the legends for what materials are in the closet
        JFreeChart chart = ChartFactory.createPieChart(
                "Material Distribution Breakdown".toUpperCase(),
                dataset,
                true,
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();

        //Loop to parse the material ArrayList to get what material and the amount of it in the closet
        //Does this by looping through the helper function arraylist, loop will split material type by the :
        //Then replaces the % sign with an empty space, then adds it to the dataset
        //Then sets the section paint by using the index of the loop with the color array to cycle through those colors
        for (int i = 0; i < materials.size(); i++) {
            String material = materials.get(i);
            String[] parts = material.split(": ");
            String materialTypes = parts[0];
            double percentage = Double.parseDouble(parts[1].replace("%", ""));
            dataset.setValue(materialTypes, percentage);
            plot.setSectionPaint(materialTypes, colors[i % colors.length]);
        }
        //Sets the background of pie chart to standard color
        plot.setBackgroundPaint(new Color(235, 219, 195));

        //Sets the title color to logo color
        chart.getTitle().setPaint(new Color(0, 99, 73));

        //Sets the font
        chart.getTitle().setFont(new Font("Oswald", Font.BOLD, 20));

        //Shows the percentage of all materials, instead of having to hover it
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}%"));

        plot.setLabelBackgroundPaint(new Color(255, 178, 194));
        plot.setLabelOutlinePaint(Color.BLACK);
        plot.setLabelOutlineStroke(new BasicStroke(1.0f));
        plot.setLabelShadowPaint(new Color(0, 0, 0, 64));
        plot.setLabelFont(new Font("Lato", Font.PLAIN, 12));
        return chart;
    }

    public JFreeChart createPieChartAcquisition(int userID) throws SQLException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        ArrayList<String> acquisition = getAcquisition(userID);
        //Color Array to cycle through
        Color[] colors = new Color[]{
                Color.decode("#FFB2C2"),
                Color.decode("#EFC931"),
                Color.decode("#DED980"),
                Color.decode("#66A68C"),
                Color.decode("#006349"),
                Color.decode("#5F4B8B")
        };

        //Sets the title, the legends for what type of acquisition are in the closet
        JFreeChart chart = ChartFactory.createPieChart(
                "Acquisition Method".toUpperCase(),
                dataset,
                true,
                true,
                false
        );
        PiePlot plot = (PiePlot) chart.getPlot();

        //Loop to parse the material ArrayList to get what acquisition method and the amount of it in the closet
        //Does this by looping through the helper function arraylist, loop will split acquisition type by the :
        //Then replaces the % sign with an empty space, then adds it to the dataset
        //Then sets the section paint by using the index of the loop with the color array to cycle through those colors
        for (int i = 0; i < acquisition.size(); i++) {
            String acq = acquisition.get(i);
            String[] parts = acq.split(": ");
            String acquisitionTypes = parts[0];
            double percentage = Double.parseDouble(parts[1].replace("%", ""));
            dataset.setValue(acquisitionTypes, percentage);
            plot.setSectionPaint(acquisitionTypes, colors[i % colors.length]);
        }

        //Sets the background of pie chart to standard color
        plot.setBackgroundPaint(new Color(235, 219, 195));

        //Sets the title color to logo color
        chart.getTitle().setPaint(new Color(0, 99, 73));

        //Sets the font
        chart.getTitle().setFont(new Font("Oswald", Font.BOLD, 20));

        //Shows the percentage of all materials, instead of having to hover it
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}%"));

        plot.setLabelBackgroundPaint(new Color(255, 178, 194));
        plot.setLabelOutlinePaint(Color.BLACK);
        plot.setLabelOutlineStroke(new BasicStroke(1.0f));
        plot.setLabelShadowPaint(new Color(0, 0, 0, 64));
        plot.setLabelFont(new Font("Lato", Font.PLAIN, 12));
        return chart;
    }
}