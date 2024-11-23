import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.xml.transform.Result;
import java.awt.Color;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.mysql.cj.protocol.Resultset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.round;

public class dashboardView extends JPanel {
    static double[] getMaterialAverageForUser() throws SQLException {
        double[] averages = new double[4];
        int userID = user.getUserId();
        //Joins 3 tables of clothes, clothes_material, and material
        String sql = "SELECT " +
                "SUM(m.material_decomp*cm.Percentage)/ SUM(cm.Percentage) AS avg_decomp, " +
                "SUM(m.material_water*cm.Percentage)/SUM(cm.Percentage) AS avg_water, " +
                "SUM(m.material_energy*cm.Percentage)/SUM(cm.Percentage) AS avg_energy, " +
                "SUM(m.material_emission*cm.Percentage)/SUM(cm.Percentage) AS avg_emission " +
                "FROM clothes c " +
                "JOIN clothes_material cm ON c.clothes_ID = cm.clothes_ID " +
                "JOIN material m on cm.material_ID = m.material_ID " +
                "WHERE c.user_ID = ?";
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(sql);
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
    //-------------------------------------Getting Stars from Materials--------------------------------------------------
    //Calculations for stars for material's environment impact
    static int starsForDecomp(double f){
        int range = 0;
        if(f > 4000){
            range = 1;
        }else if(f >= 1000){
            range = 2;
        }else if(f >= 100){
            range = 3;
        }else if(f >= 10){
            range = 4;
        }else if(f < 10){
            range = 5;
        }
        return range;
    }
    static int starsForWater(double f){
        int range = 0;
        if(f > 100000){
            range = 1;
        }else if(f >= 10000){
            range = 2;
        }else if(f >= 1000){
            range = 3;
        }else if(f >= 500){
            range = 4;
        }else if(f < 500){
            range = 5;
        }
        return range;
    }
    static int starsForEnergy(double f){
        int range = 0;
        if(f > 90){
            range = 1;
        }else if(f >= 60){
            range = 2;
        }else if(f >= 30){
            range = 3;
        }else if(f >= 20){
            range = 4;
        }else if(f < 20){
            range = 5;
        }
        return range;
    }
    static int starsForEmission(double f){
        int range = 0;
        if(f > 30){
            range = 1;
        }else if(f >= 20){
            range = 2;
        }else if(f >= 10){
            range = 3;
        }else if(f >= 5){
            range = 4;
        }else if(f < 5){
            range = 5;
        }
        return range;
    }
    //------------------------------------------------------------------------------------------------------------------------------
    //Gets the overall rating of all the clothes in the closet
    static double overallResult(double decomp, double water, double energy, double emission){
        double decompR = starsForDecomp(decomp);
        double waterR = starsForWater(water);
        double energyR = starsForEnergy(energy);
        double emissionR = starsForEmission(emission);
        if(decomp == 0 && water == 0 && energy == 0 && emission == 0){
            return 0;
        }
        double result;
        result = (decompR * .2) + (waterR*.3) + (energyR* .2) + (emissionR * .3);
        return round(result,2);
    }

    static double getCarbonFootprint(int userID) throws SQLException {
        String sql = "SELECT " +
                "SUM(m.material_emission * " +
                "CASE " +
                "WHEN c.clothes_acquisition = 'In-Store Bought' THEN 8.0 " +
                "WHEN c.clothes_acquisition = 'Online Shopping' THEN 10.0 " +
                "WHEN c.clothes_acquisition = 'In-Store Thrifited' THEN 1.0 " +
                "WHEN c.clothes_acquisition = 'Online Thrifited' THEN 3.0 " +
                "WHEN c.clothes_acquisition = 'Handmade' THEN 5.0 " +
                "WHEN c.clothes_acquisition = 'Gifted' THEN 9.0 " +
                "ELSE 0.0 " +
                "END) / COUNT(c.clothes_ID) AS carbon_footprint " +
                "FROM clothes c " +
                "JOIN clothes_material cm ON c.clothes_ID = cm.clothes_ID " +
                "JOIN material m ON cm.material_ID = m.material_ID " +
                "WHERE c.user_ID = ?";
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(sql);
        preparedStatement.setInt(1, userID);
        ResultSet rs = preparedStatement.executeQuery();
        double carbonFootPrint = 0;
        if (rs.next()) {
            carbonFootPrint = rs.getDouble("carbon_footprint");
        }
        return carbonFootPrint;
    }
    //----------------------------------Create Star Rating--------------------------------------------------------------
    //Function to display stars to show how what the rating of the user's closet is
    public void createStarRating(Container container,double rating) {
        int fullstars = (int) rating;
        //Equation to get half star
        double fraction = rating - fullstars;
        int halfStar = 0;
        //If statement to check if there needs to be half star
        if(fraction >= .25 && fraction < .75){
            halfStar = 1;
        }
        //Equation for the rest of the empty stars
        int emptyStars = 5 - fullstars - halfStar;

        //Images icon for full star, half star, empty star
        ImageIcon fullstar = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/fullStars.png")));
        Image full = fullstar.getImage();
        Image image = full.getScaledInstance(15,15,Image.SCALE_SMOOTH);
        ImageIcon fullIcon = new ImageIcon(image);

        ImageIcon halfstar = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/halfStars.png")));
        Image half = halfstar.getImage();
        Image image2 = half.getScaledInstance(15,15,Image.SCALE_SMOOTH);
        ImageIcon halfIcon = new ImageIcon(image2);

        ImageIcon emptystar = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/emptyStars.png")));
        Image empty = emptystar.getImage();
        Image image3 = empty.getScaledInstance(15,15,Image.SCALE_SMOOTH);
        ImageIcon emptyIcon = new ImageIcon(image3);

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
        //Count what material and how many material are there
        String sql = "SELECT material_type, COUNT(*) as count " +
                "FROM clothes c " +
                "JOIN clothes_material cm ON c.clothes_ID = cm.clothes_ID " +
                "JOIN material m on cm.material_ID = m.material_ID "+
                "WHERE c.user_ID = ? " +
                "GROUP by m.material_type";

        PreparedStatement prepared_statement = myJDBC.connect.prepareStatement(sql);
        prepared_statement.setInt(1,userID);
        ResultSet result = prepared_statement.executeQuery();

        //While loop to save what material there are in the closet and how much of it is there
        while(result.next()){
            String material = result.getString("material_type");
            int count = result.getInt("count");
            materials.add(material + ": " + count);
        }
        return materials;
    }
    //---------------------------------------------------------------------------------------------------
//--------------------------------PIE CHART FUNCTION--------------------------------------------------
    public JFreeChart createPieChart(int userID) throws SQLException{
        DefaultPieDataset dataset = new DefaultPieDataset();
        ArrayList<String> materials = getMaterials(userID);
        //Loop to parse the material ArrayList to get what material and the amount of it in the closet
        for(String material : materials){
            String[] parts = material.split(": ");
            String materialTypes = parts[0];
            int count = Integer.parseInt(parts[1]);
            dataset.setValue(materialTypes,count);
        }
        //Sets the title, the legends for what materials are in the closet
        JFreeChart chart = ChartFactory.createPieChart(
                "Material Distribution Breakdown".toUpperCase(),
                dataset,
                true,
                true,
                false
        );
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(235,219,195));
        chart.getTitle().setPaint(new Color(0,99,73));
        chart.getTitle().setFont(new Font("Oswlad",Font.BOLD,20));
        plot.setSectionPaint("Wool", new Color(0, 99,73));
        plot.setSectionPaint("Cotton", new Color(255,178,194));
        plot.setSectionPaint("Polyester", new Color(239,201,49));
        plot.setSectionPaint("Silk", new Color(0,0,0));
        return chart;
    }


    //-------------------------------------------------------------------------------------------------------------------
    //constructor
    dashboardView(Font oswald, Font lato, user user) throws SQLException {
        myJDBC.openConnection();
        //-----------------------Set background color and preferred size------------------------------------------------
        this.setBackground(new Color(235, 219, 195));
        this.setLayout(new BorderLayout());  // Use BorderLayout to properly place the navigation bar at the bottom
        //--------------------------------------------------------------------------------------------------------------

        //-------------------CALL NAVIGATION BAR AND SET BOUNDS---------------------------------------------------------
        navigationBarView navigationBarView = new navigationBarView(oswald, lato, user);
        this.add(navigationBarView, BorderLayout.SOUTH);  // Place navigation bar at the bottom (SOUTH)
        // -------------------------------------------------------------------------------------------------------------
        //-------------------CREATE items to add to panel---------------------------------------------------------------
        JPanel DashPanel = new JPanel();
        DashPanel.setLayout(new BoxLayout(DashPanel, BoxLayout.Y_AXIS));
        DashPanel.setBackground(new Color(235, 219, 195));  // Match background color

        JLabel pieChartLabel = new JLabel("");
        pieChartLabel.setFont(lato.deriveFont(18f));
        pieChartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        DashPanel.add(pieChartLabel);

        int userID = user.getUserId();
        JFreeChart pieChart = createPieChart(userID);
        ChartPanel chartPanel = new ChartPanel(pieChart);
        //chartPanel.set
        chartPanel.setPreferredSize(new Dimension(10, 10));// Set a preferred size for the chart
        chartPanel.setMouseWheelEnabled(true);
        DashPanel.add(chartPanel);

        JLabel Title = new JLabel("Breakdown of Materials:");
        Title.setFont(lato.deriveFont(15f));
        Title.setAlignmentX(Component.LEFT_ALIGNMENT);
        DashPanel.add(Title);

        double[] userAvg = getMaterialAverageForUser();
        double decomp = userAvg[0];
        double water = userAvg[1];
        double energy = userAvg[2];
        double emission = userAvg[3];
        double rating = overallResult(decomp,water,energy,emission);

        //Adds the decomposition time to the DashPanel
        JLabel Decomp = new JLabel();
        Decomp.setText("Decompostion Time: " + userAvg[0] + " months");
        Decomp.setFont(lato.deriveFont(15f));
        DashPanel.add(Decomp);

        //Adds the water usage to the DashPanel
        JLabel Water = new JLabel();
        Water.setText("Water Used: " + userAvg[1] + " liters/kg");
        Water.setFont(lato.deriveFont(15f));
        DashPanel.add(Water);

        //Adds the energy used to the DashPanel
        JLabel Energy = new JLabel();
        Energy.setText("Energy Used: " + userAvg[2] + " MJ/kg");
        Energy.setFont(lato.deriveFont(15f));
        DashPanel.add(Energy);

        //Adds the emission rate to the DashPanel
        JLabel Emission = new JLabel();
        Emission.setText("Emission: " + userAvg[3] + " C02e/kg");
        Emission.setFont(lato.deriveFont(15f));
        DashPanel.add(Emission);

        JLabel Carbon = new JLabel();
        Carbon.setText("Carbon Footprint is: " + getCarbonFootprint(userID) + " kg C02e/item");
        Carbon.setFont(lato.deriveFont(15f));
        DashPanel.add(Carbon);

        //Adds the rating of the user's closet to the DashPanel
        JLabel Rating = new JLabel();
        Rating.setText("Overall Rating " + rating );
        Rating.setFont(lato.deriveFont(15f));
        Rating.setAlignmentX(Component.LEFT_ALIGNMENT);
        DashPanel.add(Rating);

        //This creates the box to show the stars based off of the environmental impact
        createStarRating(DashPanel,rating);

        //----------------------------------------------------------------------------------------------------------------

        //--------------------------Create scroll panel-----------------------------------------------------------------
        // Wrap the closetItemsPanel inside a JScrollPane (this is the scrollable part)
        JScrollPane scrollPane = new JScrollPane(DashPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Change scroll bar appearance
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation)
            {
                return new JButton() {  // Invisible decrease button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation)
            {
                return new JButton() {  // Invisible increase button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected void configureScrollBarColors()
            {
                this.thumbColor = new Color(192, 168, 144); // Color of the scroll thumb
                this.trackColor = new   Color(235, 219, 195); // Color of the scroll track (matching panel background)
            }
        });
        // Add the scrollable items to the CENTER of the layout
        this.add(scrollPane, BorderLayout.CENTER);

        //--------------------------------------------------------------------------------------------------------------

    }
}
