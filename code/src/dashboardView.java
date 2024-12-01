import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;
import java.util.Objects;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.*;
import org.jfree.data.statistics.BoxAndWhiskerItem;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.round;

public class dashboardView extends JPanel {
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
        //DashPanel.add(pieChartLabel);

        int userID = user.getUserId();
        JFreeChart pieChart = createPieChart(userID);
        ChartPanel chartPanel = new ChartPanel(pieChart);
        //chartPanel.set
        chartPanel.setPreferredSize(new Dimension(10, 10));// Set a preferred size for the chart
        chartPanel.setMouseWheelEnabled(true);

        JLabel Title = new JLabel("Breakdown of Materials:");
        Title.setFont(lato.deriveFont(15f));
        Title.setAlignmentX(Component.LEFT_ALIGNMENT);
        DashPanel.add(Title);

        double[] userAvg = getMaterialAverageForUser();
        double decomp = userAvg[0];
        double water = userAvg[1];
        double energy = userAvg[2];
        double emission = userAvg[3];
        double brandRating = getBrandRating(userID);
        double rating = overallResult(decomp,water,energy,emission);
        JFreeChart box = createBoxPlotDecomp(userID);
        ChartPanel chartPanel1 = new ChartPanel(box);
        chartPanel1.setPreferredSize(new Dimension(1,1));
        chartPanel1.setRangeZoomable(true);
        chartPanel1.setDomainZoomable(true);
        chartPanel1.setMouseWheelEnabled(true);


        JLabel Rating = new JLabel();
        Rating.setText("Overall Rating of Materials: " + rating );
        Rating.setFont(lato.deriveFont(15f));
        Rating.setAlignmentX(Component.LEFT_ALIGNMENT);
        DashPanel.add(Rating);
        createStarRating(DashPanel,rating);

        JLabel Brand = new JLabel();
        Brand.setText("Brand rating is " + brandRating + "/5");
        Brand.setFont(lato.deriveFont(15f));
        DashPanel.add(Brand);

        //Adds the decomposition time to the DashPanel
        JLabel Decomp = new JLabel();
        Decomp.setText("Decompostion Time: " + userAvg[0] + " months");
        Decomp.setFont(lato.deriveFont(15f));
        //DashPanel.add(Decomp);
        //chartPanel1.setSize(1,1);
        DashPanel.add(chartPanel1);

        //Adds the water usage to the DashPanel
        JLabel Water = new JLabel();
        Water.setText("Water Used: " + userAvg[1] + " liters/kg");
        Water.setFont(lato.deriveFont(15f));
        //DashPanel.add(Water);
        JFreeChart box2 = createBoxPlotWater(userID);
        ChartPanel chartPanel2 = new ChartPanel(box2);
        chartPanel2.setPreferredSize(new Dimension(1,1));
        chartPanel2.setRangeZoomable(true);
        chartPanel2.setDomainZoomable(true);
        chartPanel2.setMouseWheelEnabled(true);
        DashPanel.add(chartPanel2);

        //Adds the energy used to the DashPanel
        JLabel Energy = new JLabel();
        Energy.setText("Energy Used: " + userAvg[2] + " MJ/kg");
        Energy.setFont(lato.deriveFont(15f));
        JFreeChart box3 = createBoxPlotEnergy(userID);
        ChartPanel chartPanel3 = new ChartPanel(box3);
        chartPanel3.setPreferredSize(new Dimension(1,1));
        chartPanel3.setRangeZoomable(true);
        chartPanel3.setDomainZoomable(true);
        chartPanel3.setMouseWheelEnabled(true);
        DashPanel.add(chartPanel3);

        /*
        JLabel Emission = new JLabel();
        Emission.setText("Emission: " + userAvg[3] + " C02e/kg");
        Emission.setFont(lato.deriveFont(15f));
        DashPanel.add(Emission);

        JLabel Carbon = new JLabel();
        Carbon.setText("Carbon Footprint is: " + getCarbonFootprint(userID) + " kg C02e/item");
        Carbon.setFont(lato.deriveFont(15f));
        DashPanel.add(Carbon);
         */

        JFreeChart box5 = boxPlotCarbon(userID);
        ChartPanel chartPanel4 = new ChartPanel(box5);
        chartPanel4.setPreferredSize(new Dimension(1,1));
        chartPanel4.setMouseWheelEnabled(true);
        DashPanel.add(chartPanel4);


        //Adds the rating of the user's closet to the DashPanel

        //This creates the box to show the stars based off of the environmental impact
        DashPanel.add(chartPanel);
        DashPanel.revalidate();
        DashPanel.repaint();

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
    static double[] getMaterialAverageForUser() throws SQLException {
        double[] averages = new double[5];
        int userID = user.getUserId();
        //Joins 3 tables of clothes, clothes_material, and material
        String sql = "SELECT " +
                "SUM(m.material_decomp*cm.Percentage)/ SUM(cm.Percentage) AS avg_decomp, " +
                "SUM(m.material_water*cm.Percentage)/SUM(cm.Percentage) AS avg_water, " +
                "SUM(m.material_energy*cm.Percentage)/SUM(cm.Percentage) AS avg_energy, " +
                "SUM(m.material_emission*cm.Percentage)/SUM(cm.Percentage) AS avg_emission, " +
                "b.brand_rating " +
                "FROM clothes c " +
                "JOIN clothes_material cm ON c.clothes_ID = cm.clothes_ID " +
                "JOIN material m ON cm.material_ID = m.material_ID " +
                "JOIN brand b ON c.brand_ID = b.brand_ID " +
                "WHERE c.user_ID = ? " +
                "GROUP BY b.brand_rating";
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
            averages[4] = resultSet.getDouble("brand_rating");
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
        double carbon = 0;
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
        if(rs.next()) {
            carbon+=(rs.getDouble("carbon_footprint"));
        }
        return carbon;
    }
    static double getBrandRating(int userID) throws SQLException{
        String sql = "SELECT AVG(b.brand_rating) AS avg_brand_rating " +
                     "FROM clothes c " +
                     "JOIN brand b on c.brand_ID = b.brand_ID " +
                     "WHERE c.user_ID = ?";
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(sql);
        preparedStatement.setInt(1,userID);
        ResultSet rs = preparedStatement.executeQuery();

        double avg_Brand = 0;
        if(rs.next()){
            avg_Brand = rs.getDouble("avg_brand_rating");
        }
        return avg_Brand;
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
        double totalPercentage = 0;
        //Count what material and how many material are there
        String sql = "SELECT m.material_type, SUM(cm.Percentage) as total_percentage " +
                "FROM clothes c " +
                "JOIN clothes_material cm ON c.clothes_ID = cm.clothes_ID " +
                "JOIN material m on cm.material_ID = m.material_ID "+
                "WHERE c.user_ID = ? " +
                "GROUP by m.material_type";

        PreparedStatement prepared_statement = myJDBC.connect.prepareStatement(
                sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );
        prepared_statement.setInt(1,userID);
        ResultSet result = prepared_statement.executeQuery();

        //While loop to save what material there are in the closet and how much of it is there
        while(result.next()){
            double materialPercentage = result.getDouble("total_percentage");
            totalPercentage+=materialPercentage;
        }
        result.beforeFirst();
        while(result.next()){
            String material = result.getString("material_type");
            double materialPercentage = result.getDouble("total_percentage");
            double relativePercentage = (materialPercentage / totalPercentage) * 100;
            materials.add(material + ": " + String.format("%.2f", relativePercentage) + "%");
        }
        return materials;
    }
    //---------------------------------------------------------------------------------------------------
//--------------------------------PIE CHART FUNCTION--------------------------------------------------
    public JFreeChart createPieChart(int userID) throws SQLException{
        DefaultPieDataset dataset = new DefaultPieDataset();
        ArrayList<String> materials = getMaterials(userID);
        //Loop to parse the material ArrayList to get what material and the amount of it in the closet
        Color[] colors = new Color[]{
                new Color(0, 99,73),
                new Color(255,178,194),
                new Color(239,201,49),
                Color.decode("#2F4F4F"),
                Color.decode("#FFB6C1"),
                Color.decode("#FFD700"),
                Color.decode("#ADD8E6"),
                Color.decode("#FF6F61")
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
        for(int i = 0; i < materials.size();i++) {
            String material = materials.get(i);
            String[] parts = material.split(": ");
            String materialTypes = parts[0];
            double percentage = Double.parseDouble(parts[1].replace("%", ""));
            dataset.setValue(materialTypes, percentage);
            plot.setSectionPaint(materialTypes, colors[i % colors.length]);
        }
        plot.setBackgroundPaint(new Color(235,219,195));
        chart.getTitle().setPaint(new Color(0,99,73));
        chart.getTitle().setFont(new Font("Oswlad",Font.BOLD,20));
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}%"));
        plot.setLabelBackgroundPaint(new Color(255,178,194));
        plot.setLabelOutlinePaint(Color.BLACK);
        plot.setLabelOutlineStroke(new BasicStroke(1.0f));
        plot.setLabelShadowPaint(new Color(0,0,0,64));
        plot.setLabelFont(new Font("Lato",Font.PLAIN,12));
        return chart;
    }
    public ArrayList<Double> getDecomp(int userID) throws SQLException {
        ArrayList<Double> decomp = new ArrayList<>();
        String sql = "Select m.material_decomp FROM clothes c " +
                "JOIN clothes_material cm on c.clothes_ID = cm.clothes_ID "+
                "JOIN material m on cm.material_ID = m.material_ID " +
                "WHERE c.user_ID = ?";
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(sql);
        preparedStatement.setInt(1,userID);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            decomp.add(rs.getDouble("material_decomp"));
        }
        return decomp;
    }
    public ArrayList<Double> getWater(int userID) throws SQLException {
        ArrayList<Double> Water = new ArrayList<>();
        String sql = "Select m.material_water FROM clothes c " +
                "JOIN clothes_material cm on c.clothes_ID = cm.clothes_ID "+
                "JOIN material m on cm.material_ID = m.material_ID " +
                "WHERE c.user_ID = ?";
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(sql);
        preparedStatement.setInt(1,userID);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            Water.add(rs.getDouble("material_water"));
        }
        return Water;
    }
    static ArrayList<Double> getCarbonFootprintList(int userID) throws SQLException {
        ArrayList<Double> carbonList = new ArrayList<>();
        String sql = "SELECT " +
                "c.clothes_ID, "+
                "c.clothes_acquisition, "+
                "m.material_emission * "+
                "CASE " +
                "WHEN c.clothes_acquisition = 'In-Store Bought' THEN 8.0 " +
                "WHEN c.clothes_acquisition = 'Online Shopping' THEN 10.0 " +
                "WHEN c.clothes_acquisition = 'In-Store Thrifited' THEN 1.0 " +
                "WHEN c.clothes_acquisition = 'Online Thrifited' THEN 3.0 " +
                "WHEN c.clothes_acquisition = 'Handmade' THEN 5.0 " +
                "WHEN c.clothes_acquisition = 'Gifted' THEN 9.0 " +
                "ELSE 0.0 " +
                "END AS carbon_footprint " +
                "FROM clothes c " +
                "JOIN clothes_material cm ON c.clothes_ID = cm.clothes_ID " +
                "JOIN material m ON cm.material_ID = m.material_ID " +
                "WHERE c.user_ID = ?";
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(sql);
        preparedStatement.setInt(1, userID);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            double carbonF = rs.getDouble("carbon_footprint");
            carbonList.add(carbonF);
        }
        return carbonList;
    }
    public ArrayList<Double> getEmission(int userID) throws SQLException {
        ArrayList<Double> Emission = new ArrayList<>();
        String sql = "Select m.material_emission FROM clothes c " +
                "JOIN clothes_material cm on c.clothes_ID = cm.clothes_ID "+
                "JOIN material m on cm.material_ID = m.material_ID " +
                "WHERE c.user_ID = ?";
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(sql);
        preparedStatement.setInt(1,userID);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            Emission.add(rs.getDouble("material_emission"));
        }
        return Emission;
    }
    public ArrayList<Double> getEnergy(int userID) throws SQLException {
        ArrayList<Double> energy = new ArrayList<>();
        String sql = "Select m.material_energy FROM clothes c " +
                "JOIN clothes_material cm on c.clothes_ID = cm.clothes_ID "+
                "JOIN material m on cm.material_ID = m.material_ID " +
                "WHERE c.user_ID = ?";
        PreparedStatement preparedStatement = myJDBC.connect.prepareStatement(sql);
        preparedStatement.setInt(1,userID);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            energy.add(rs.getDouble("material_energy"));
        }
        return energy;
    }
    public JFreeChart createBoxPlotDecomp(int userID) throws SQLException{
        ArrayList<Double> decomp = getDecomp(userID);
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
            Collections.sort(decomp);
            double min = decomp.getFirst();
            double mean = decomp.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            double max = decomp.getLast();
            double median = getMedian(decomp);
            double q1 = getPercentile(decomp, 25);
            double q3 = getPercentile(decomp, 75);
            double IQR = q3 - q1;
            double minOutlier = (q1-(1.5*IQR));
            double maxOutlier = (q3+(1.5*IQR));
            BoxAndWhiskerItem boxAndWhiskerItem = new BoxAndWhiskerItem(mean, median,q1,q3,min,max,minOutlier,maxOutlier, new ArrayList<>());
            dataset.add(boxAndWhiskerItem,"Decomposition Time","Materials");
        JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(
            "Decomposition Time Distrubition".toUpperCase(),
                "Material",
                "Decomposition Times (months)",
                dataset,
                false
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(235,219,195));
        chart.getTitle().setPaint(new Color(0,99,73));
        chart.getTitle().setFont(new Font("Oswlad",Font.BOLD,20));
        plot.setOrientation(PlotOrientation.HORIZONTAL);
        BoxAndWhiskerRenderer renderer = (BoxAndWhiskerRenderer) plot.getRenderer();
        renderer.setMedianVisible(true);
        renderer.setMeanVisible(true);
        renderer.setSeriesPaint(0, new Color(0,99,73));
        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
        numberAxis.setAutoRangeIncludesZero(false);
        numberAxis.setUpperBound(decomp.getLast());
        chartPanel.setPreferredSize(new Dimension(1,1));
        return chart;
    }
    public JFreeChart createBoxPlotWater(int userID) throws SQLException{
        ArrayList<Double> water = getWater(userID);
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        Collections.sort(water);
        double min = water.getFirst();
        double mean = water.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double max = water.getLast();
        double median = getMedian(water);
        double q1 = getPercentile(water, 25);
        double q3 = getPercentile(water, 75);
        double IQR = q3 - q1;
        double minOutlier = (q1-(1.5*IQR));
        double maxOutlier = (q3+(1.5*IQR));
        BoxAndWhiskerItem boxAndWhiskerItem = new BoxAndWhiskerItem(mean, median,q1,q3,min,max,minOutlier,maxOutlier, new ArrayList<>());
        dataset.add(boxAndWhiskerItem,"Water Usage","Materials");
        JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(
                "Water Usage Distrubition".toUpperCase(),
                "Material",
                "Water Usage (liters/kg)",
                dataset,
                false
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(235,219,195));
        chart.getTitle().setPaint(new Color(0,99,73));
        chart.getTitle().setFont(new Font("Oswlad",Font.BOLD,20));
        plot.setOrientation(PlotOrientation.HORIZONTAL);
        BoxAndWhiskerRenderer renderer = (BoxAndWhiskerRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0,99,73));
        renderer.setMedianVisible(true);
        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
        numberAxis.setAutoRangeIncludesZero(false);
        numberAxis.setUpperBound(max);
        chartPanel.setPreferredSize(new Dimension(1,1));
        return chart;
    }
    public JFreeChart createBoxPlotEnergy(int userID) throws SQLException{
        ArrayList<Double> Energy = getEnergy(userID);
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        Collections.sort(Energy);
        double min = Energy.getFirst();
        double mean = Energy.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double max = Energy.getLast();
        double median = getMedian(Energy);
        double q1 = getPercentile(Energy, 25);
        double q3 = getPercentile(Energy, 75);
        double IQR = q3 - q1;
        double minOutlier = (q1-(1.5*IQR));
        double maxOutlier = (q3+(1.5*IQR));
        BoxAndWhiskerItem boxAndWhiskerItem = new BoxAndWhiskerItem(mean, median,q1,q3,min,max,minOutlier,maxOutlier, new ArrayList<>());
        dataset.add(boxAndWhiskerItem,"Energy Usage","Materials");
        JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(
                "Energy Usage".toUpperCase(),
                "Material",
                "Energy Usage (MJ/kg)",
                dataset,
                false
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(235,219,195));
        chart.getTitle().setPaint(new Color(0,99,73));
        chart.getTitle().setFont(new Font("Oswlad",Font.BOLD,20));
        plot.setOrientation(PlotOrientation.HORIZONTAL);
        BoxAndWhiskerRenderer renderer = (BoxAndWhiskerRenderer) plot.getRenderer();
        renderer.setMedianVisible(true);
        renderer.setMeanVisible(true);
        renderer.setSeriesPaint(0, new Color(0,99,73));
        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
        numberAxis.setAutoRangeIncludesZero(false);
        chartPanel.setPreferredSize(new Dimension(1,1));
        return chart;
    }
   public JFreeChart boxPlotCarbon(int userID) throws SQLException{
        ArrayList<Double> carbon = getCarbonFootprintList(userID);
        Collections.sort(carbon);
        double min = carbon.getFirst();
        double max = carbon.getLast();
        double median = getMedian(carbon);
        double mean = carbon.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double q1 = getPercentile(carbon,25);
        double q3 = getPercentile(carbon,75);
        double IQR = q3-q1;
        double minOutlier = (q1-(1.5*IQR));
        double maxOutlier = (q3+(1.5*IQR));
       BoxAndWhiskerItem boxAndWhiskerItem = new BoxAndWhiskerItem(mean, median,q1,q3,min,max,minOutlier,maxOutlier, new ArrayList<>());
       DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
       dataset.add(boxAndWhiskerItem,"Carbon FootPrint","Materials");
       JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(
               "Carbon Footprint".toUpperCase(),
               "Material",
               "Carboon Footprint Emission (kg/C02e)",
               dataset,
               false
       );

       ChartPanel chartPanel = new ChartPanel(chart);
       CategoryPlot plot = chart.getCategoryPlot();
       plot.setBackgroundPaint(new Color(235,219,195));
       chart.getTitle().setPaint(new Color(0,99,73));
       chart.getTitle().setFont(new Font("Oswlad",Font.BOLD,20));
       plot.setOrientation(PlotOrientation.HORIZONTAL);
       BoxAndWhiskerRenderer renderer = (BoxAndWhiskerRenderer) plot.getRenderer();
       renderer.setMedianVisible(true);
       renderer.setMeanVisible(true);
       renderer.setSeriesPaint(0, new Color(0,99,73));
       NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
       numberAxis.setAutoRangeIncludesZero(false);
       chartPanel.setPreferredSize(new Dimension(1,1));
       return chart;
    }
    private double getMedian(ArrayList<Double> data){
        int size = data.size();
        if(size == 0){
            return 0.0;
        }
        Collections.sort(data);
        if(size % 2 == 1){
            return data.get(size/2);
        }else{
            return(data.get(size/2-1)) + data.get(size/2)/2;
        }
    }

    private double getPercentile(ArrayList<Double> data, int percentile){
        int index = (int) Math.ceil(percentile/100.0 * data.size())-1;
        return data.get(index-1);
    }

    //-------------------------------------------------------------------------------------------------------------------
}

