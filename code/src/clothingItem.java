import java.sql.*;
import java.util.ArrayList;

public class clothingItem {

    private int userID;
    private String clothingName;
    private int clothingBrandID;
    private String clothingType;
    private String acquisition;
    private ArrayList<materials> clothingMaterials;

    // Constructor
    public clothingItem(int userID, String clothingName, int clothingBrandID, String clothingType, String acquisition) {
        this.userID = userID;
        this.clothingName = clothingName;
        this.clothingBrandID = clothingBrandID;
        this.clothingType = clothingType;
        this.acquisition = acquisition;
        this.clothingMaterials = new ArrayList<>(); // Initialize the list
    }

    // Getter methods
    public int getUserID() {
        return userID;
    }

    public String getClothingName() {
        return clothingName;
    }

    public int getClothingBrand() {
        return clothingBrandID;
    }

    public String getClothingType() {
        return clothingType;
    }

    public String getAcquisition() {
        return acquisition;
    }

    public ArrayList<materials> getClothingMaterials() {
        return clothingMaterials;
    }

    // Method to retrieve the different materials for a specific clothing item
    public void loadMaterials(Connection conn, int clothingID) {
        // SQL query to get materials for the clothing item
        String query = "SELECT m.material_ID, m.material_type, cm.material_percent " +
                "FROM clothes_material cm " +
                "JOIN material m ON cm.material_ID = m.material_ID " +
                "WHERE cm.clothes_ID = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            // Set the clothing ID as a parameter
            statement.setInt(1, clothingID);

            // Execute the query and get the result set
            try (ResultSet rs = statement.executeQuery()) {
                // Clear existing materials (if any)
                clothingMaterials.clear();

                // Loop through the result set and create material objects
                while (rs.next()) {
                    int materialID = rs.getInt("material_ID");
                    String materialType = rs.getString("material_type");
                    int materialPercent = rs.getInt("material_percent");

                    // Create material object and add it to the list
                    materials materials = new materials(materialID, materialType, materialPercent);
                    clothingMaterials.add(materials);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}