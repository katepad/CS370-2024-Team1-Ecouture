import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;


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

            //Stores all of user's info average into an array
            double[] userAvg = getMaterialAverageForUser();
            //double decomp = userAvg[0];
            double water = userAvg[1];
            double energy = userAvg[2];
            //double emission = userAvg[3];
            //Gets the brand rating
            double brandRating = getBrandRating(userID);
            //Rating for materials
            double rating = getMaterialAverage();
            //Creates the piechart
            JFreeChart piechart = createPieChart(userID);
            ChartPanel chartPanel = new ChartPanel(piechart);
            chartPanel.setMouseWheelEnabled(true);//Lets the piechart with mouse wheel
            chartPanel.setPreferredSize(new Dimension(1,1));

            //Piechart for the acquistion method
            JFreeChart piechart2 = createPieChartAcquisition(userID);
            ChartPanel chartPanel1 = new ChartPanel(piechart2);
            chartPanel1.setMouseWheelEnabled(true);
            chartPanel1.setPreferredSize(new Dimension(1,1));


            //----------------------------------------------------------------------------------------------------------------

            //--------------------------Create scroll panel-----------------------------------------------------------------
            // Wrap the closetItemsPanel inside a JScrollPane (this is the scrollable part)
            JPanel contentPanel = new JPanel();
            JScrollPane scrollPane = new JScrollPane(contentPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            contentPanel.setBackground(new Color(235, 219,195));

            //Displays the overall rating of the closet(material_rating + brand_rating)
            JLabel Sustain = new JLabel();
            Sustain.setText("Overall sustainability is " + sustainRating(rating,brandRating) + "/5");
            Sustain.setFont((lato.deriveFont(15f)));
            contentPanel.add(Sustain);
            createStarRating(contentPanel,sustainRating(rating,brandRating));

            //Displays brand rating
            JLabel Brand = new JLabel();
            Brand.setText("Brand rating is " + brandRating + "/5");
            Brand.setFont(lato.deriveFont(15f));
            contentPanel.add(Brand);
            createStarRating(contentPanel,brandRating);

            //Displays the material rating
            JLabel mRating = new JLabel();
            mRating.setText("Overall Rating of Materials: " + rating);
            mRating.setFont(lato.deriveFont(15f));
            contentPanel.add(mRating);
            createStarRating(contentPanel,rating);

            //Displays the carbon footprint
            JLabel Carbon = new JLabel();
            Carbon.setText("Carbon Footprint is: " + getCarbonFootprint(userID) + " kg C02e/item");
            Carbon.setFont(lato.deriveFont(15f));
            contentPanel.add(Carbon);

            //Displays the water usage
            JLabel Water = new JLabel();
            Water.setText("Water Used: " + water + " liters/kg");
            Water.setFont(lato.deriveFont(15f));
            contentPanel.add(Water);

            //Displays the energy usage
            JLabel Energy = new JLabel();
            Energy.setText("Energy Used: " + energy + " MJ/kg");
            Energy.setFont(lato.deriveFont(15f));
            contentPanel.add(Energy);


            JLabel Title = new JLabel("Breakdown of Materials:");
            Title.setFont(lato.deriveFont(15f));
            Title.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(Title);

            contentPanel.add(chartPanel);

        JLabel aTitle = new JLabel("Acquistion of Clothes:");
        aTitle.setFont(lato.deriveFont(15f));
        aTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(aTitle);
        contentPanel.add(chartPanel1);

            // Change scroll bar appearance
            scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
                @Override
                protected JButton createDecreaseButton(int orientation) {
                    return new JButton() {  // Invisible decrease button
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(0, 0);
                        }
                    };
                }

                @Override
                protected JButton createIncreaseButton(int orientation) {
                    return new JButton() {  // Invisible increase button
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(0, 0);
                        }
                    };
                }

                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = new Color(192, 168, 144); // Color of the scroll thumb
                    this.trackColor = new Color(235, 219, 195); // Color of the scroll track (matching panel background)
                }
            });
            // Add the scrollable items to the CENTER of the layout
            this.add(scrollPane, BorderLayout.CENTER);


            //--------------------------------------------------------------------------------------------------------------
    }
    static double[] getMaterialAverageForUser() throws SQLException {
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
    static double getMaterialAverage() throws SQLException {
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
    //-------------------------------------Getting Stars from Materials--------------------------------------------------
    //Calculations for stars for material's environment impact
    static int starsForDecomp(double f){
        int range = 0;
        if(f > 4000){
            range = 1;
        }else if(f >= 1000 && f < 4000){
            range = 2;
        }else if(f >= 100 && f < 1000){
            range = 3;
        }else if(f >= 10 && f < 100){
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
        }else if(f >= 10000 && f < 100000){
            range = 2;
        }else if(f >= 1000 && f < 10000){
            range = 3;
        }else if(f >= 500 && f < 1000){
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
        }else if(f >= 60 && f < 90){
            range = 2;
        }else if(f >= 30 && f < 60){
            range = 3;
        }else if(f >= 20 && f < 30){
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
        }else if(f >= 20 && f < 30){
            range = 2;
        }else if(f >= 10 && f < 20){
            range = 3;
        }else if(f >= 5 && f < 10){
            range = 4;
        }else if(f < 5){
            range = 5;
        }
        return range;
    }
    //------------------------------------------------------------------------------------------------------------------------------

    static double getCarbonFootprint(int userID) throws SQLException {
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
    static double getBrandRating(int userID) throws SQLException{
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
                Color.decode("#FBE4C7"),
                Color.decode("#FFB2C2"),
                Color.decode("#FFA07A"),
                Color.decode("#EFC931"),
                Color.decode("#DED980"),
                Color.decode("#DED980"),
                Color.decode("#8FA6A3"),
                Color.decode("#66A68C"),
                Color.decode("#33856B"),
                Color.decode("#006349"),
                Color.decode("#004F47")
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
                Color.decode("#FBE4C7"),
                Color.decode("#FFA07A"),
                Color.decode("#DED980"),
                Color.decode("#8FA6A3"),
                Color.decode("#33856B"),
                Color.decode("#004F47")
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
    //-------------------------------------------------------------------------------------------------------------------
}

