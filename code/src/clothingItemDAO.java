import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class clothingItemDAO {

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
            e.printStackTrace();
        }
        return clothingItems;
    }

}