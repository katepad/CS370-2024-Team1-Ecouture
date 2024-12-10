import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class dashboardDAO {
    private final user user;
    private final myJDBC myJDBC;

    public dashboardDAO(user user, myJDBC myJDBC){
        this.user = user;
        this.myJDBC = myJDBC;
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
    static double sustainRating(double mResult, double bRating){
        //Equation to calculate the overall rating of user's closet
        return  (mResult+bRating)/2;
    }
    //----------------------------------Create Star Rating--------------------------------------------------------------
    //Function to display stars to show how what the rating of the user's closet is
    public void createStarRating(Container container,double rating) {
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
            JLabel starLables = new JLabel(fullIcon);
            starBox.add(starLables);
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

        //While loop that adds clothes_acquistion, this will be later used to get the percentage of each clothes_acquisition
        while(rs.next()){
            total += rs.getInt("total_clothes");
        }

        //Move to first of the ResultSet object
        rs.beforeFirst();

        //While loop that will add what type of clothes_acquisition, how much of each clothes_acquisiton, and the relative percentage of all clothes_type.
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
        //Sets the background of piechart to standard color
        plot.setBackgroundPaint(new Color(235, 219, 195));

        //Sets the title color to logo color
        chart.getTitle().setPaint(new Color(0, 99, 73));

        //Sets the font
        chart.getTitle().setFont(new Font("Oswlad", Font.BOLD, 20));

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

        //Sets the title, the legends for what type of acqusition are in the closet
        JFreeChart chart = ChartFactory.createPieChart(
                "Acquisition Method".toUpperCase(),
                dataset,
                true,
                true,
                false
        );
        PiePlot plot = (PiePlot) chart.getPlot();

        //Loop to parse the material ArrayList to get what acquisition method and the amount of it in the closet
        //Does this by looping through the helper function arraylist, loop will split acqusition type by the :
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

        //Sets the background of piechart to standard color
        plot.setBackgroundPaint(new Color(235, 219, 195));

        //Sets the title color to logo color
        chart.getTitle().setPaint(new Color(0, 99, 73));

        //Sets the font
        chart.getTitle().setFont(new Font("Oswlad", Font.BOLD, 20));

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