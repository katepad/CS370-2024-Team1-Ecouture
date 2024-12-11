package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Color;
import java.sql.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import dataAccess.clothingItemDAO;
import dataAccess.myJDBC;
import model.user;

public class dashboardView extends JPanel {
    //constructor
    dashboardView(Font oswald, Font lato, user user) throws SQLException {
        clothingItemDAO clothingItemDAO = new clothingItemDAO();
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
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(235, 219, 195));

        //add space outside of header panel
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(235, 219, 195)), //Outer border with different thickness
                BorderFactory.createEmptyBorder(8, 0, 8, 10) //Inner padding
        ));

        //add Page title
        JLabel pageTitle = new JLabel("MY DASHBOARD");
        pageTitle.setFont(oswald.deriveFont(20f));
        pageTitle.setForeground(new Color(0,99,73));
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(pageTitle);
        this.add(headerPanel,BorderLayout.NORTH);

        JPanel DashPanel = new JPanel();
        DashPanel.setLayout(new BoxLayout(DashPanel, BoxLayout.Y_AXIS));
        DashPanel.setBackground(new Color(235, 219, 195));  // Match background color

        JLabel pieChartLabel = new JLabel("");
        pieChartLabel.setFont(lato.deriveFont(18f));
        pieChartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //DashPanel.add(pieChartLabel);

        int userID = user.getUserId();

        //Stores all of user's info average into an array
        double[] userAvg = clothingItemDAO.getMaterialAverageForUser();
        double water = userAvg[1];
        double energy = userAvg[2];

        //Gets the brand rating
        double brandRating = clothingItemDAO.getBrandRating(userID);
        //Rating for materials
        double rating = clothingItemDAO.getMaterialAverage();
        //Creates the piechart
        JFreeChart piechart = clothingItemDAO.createPieChart(userID);
        ChartPanel chartPanel = new ChartPanel(piechart);
        chartPanel.setMouseWheelEnabled(true);//Lets the piechart with mouse wheel
        chartPanel.setPreferredSize(new Dimension(180,300));
        chartPanel.add(Box.createVerticalStrut(20));

        //Piechart for the acquistion method
        JFreeChart piechart2 = clothingItemDAO.createPieChartAcquisition(userID);
        ChartPanel chartPanel1 = new ChartPanel(piechart2);
        chartPanel1.setMouseWheelEnabled(true);
        chartPanel1.setPreferredSize(new Dimension(180,300));
        chartPanel1.add(Box.createVerticalStrut(20));


        //----------------------------------------------------------------------------------------------------------------

        //--------------------------Create scroll panel-----------------------------------------------------------------
        // Wrap the closetItemsPanel inside a JScrollPane (this is the scrollable part)
        JPanel contentPanel = new JPanel();
        JPanel overallRatingPanel = new JPanel();
        JPanel brandRatingPanel = new JPanel();
        JPanel materialRatingPanel = new JPanel();
        JPanel carbonPanel = new JPanel();
        JPanel waterPanel = new JPanel();
        JPanel energyPanel = new JPanel();
        JPanel pie = new JPanel();
        JPanel acq = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        overallRatingPanel.setLayout(new BoxLayout(overallRatingPanel, BoxLayout.Y_AXIS));
        brandRatingPanel.setLayout(new BoxLayout(brandRatingPanel, BoxLayout.Y_AXIS));
        materialRatingPanel.setLayout(new BoxLayout(materialRatingPanel, BoxLayout.Y_AXIS));
        carbonPanel.setLayout(new BoxLayout(carbonPanel, BoxLayout.Y_AXIS));
        waterPanel.setLayout(new BoxLayout(waterPanel, BoxLayout.Y_AXIS));
        energyPanel.setLayout(new BoxLayout(energyPanel,BoxLayout.Y_AXIS));
        pie.setLayout(new BoxLayout(pie, BoxLayout.Y_AXIS));
        acq.setLayout(new BoxLayout(acq,BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane,BorderLayout.CENTER);

        contentPanel.setBackground(new Color(235,219,195));

        //Displays the overall rating of the closet(material_rating + brand_rating)
        JLabel Sustain = new JLabel();
        JLabel orating = new JLabel();
        Sustain.setText("<html>OVERALL SUSTAINABILITY RATING:</html>");
        Sustain.setFont((oswald.deriveFont(20f)));
        Sustain.setForeground(new Color(0,99,73));
        orating.setText((clothingItemDAO.sustainRating(rating,brandRating) + "/5"));
        orating.setFont(oswald.deriveFont(20f));
        orating.setAlignmentX(Component.LEFT_ALIGNMENT);
        overallRatingPanel.add(Sustain);
        overallRatingPanel.add(orating);
        clothingItemDAO.createStarRating(overallRatingPanel, clothingItemDAO.sustainRating(rating, brandRating));
        overallRatingPanel.add(Box.createVerticalStrut(20));
        overallRatingPanel.setBorder(BorderFactory.createLineBorder(Color.white));

        contentPanel.add(overallRatingPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        //Displays brand rating
        JLabel Brand = new JLabel();
        JLabel bRating = new JLabel();
        Brand.setText("BRAND RATING:");
        Brand.setFont(oswald.deriveFont(20f));
        Brand.setForeground(new Color(0,99,73));
        bRating.setText(brandRating+"/5");
        bRating.setFont(oswald.deriveFont(20f));
        bRating.setAlignmentX(Component.LEFT_ALIGNMENT);
        brandRatingPanel.add(Brand);
        brandRatingPanel.add(bRating);
        clothingItemDAO.createStarRating(brandRatingPanel,brandRating);
        brandRatingPanel.add(Box.createVerticalStrut(20));
        brandRatingPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        contentPanel.add(brandRatingPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        //Displays the material rating
        JLabel mRating = new JLabel();
        JLabel Material = new JLabel();
        mRating.setText("MATERIAL RATING:");
        mRating.setFont(oswald.deriveFont(20f));
        mRating.setForeground(new Color(0, 99,73));
        Material.setText(rating+"/5");
        Material.setFont(oswald.deriveFont(20f));
        Material.setAlignmentX(Component.LEFT_ALIGNMENT);
        materialRatingPanel.add(mRating);
        materialRatingPanel.add(Material);
        clothingItemDAO.createStarRating(materialRatingPanel, rating);
        materialRatingPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(materialRatingPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        //Displays the carbon footprint
        JLabel Carbon = new JLabel();
        JLabel carbonRating = new JLabel();
        Carbon.setText("CARBON FOOTPRINT IS:");
        Carbon.setFont(oswald.deriveFont(20f));
        Carbon.setForeground(new Color(0, 99,73));
        carbonRating.setText(clothingItemDAO.getCarbonFootprint(userID) + " kg C02e/item");
        carbonRating.setFont(oswald.deriveFont(20f));
        carbonRating.setAlignmentX(Component.LEFT_ALIGNMENT);
        carbonPanel.add(Carbon);
        carbonPanel.add(carbonRating);
        carbonPanel.add(Box.createVerticalStrut(20));

        contentPanel.add(carbonPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        //Displays the water usage
        JLabel Water = new JLabel();
        JLabel waterRating = new JLabel();
        Water.setText("WATER USED:");
        Water.setFont(oswald.deriveFont(20f));
        Water.setForeground(new Color(0,99,73));
        waterRating.setText(water + " liters/kg");
        waterRating.setFont(oswald.deriveFont(20f));
        waterRating.setAlignmentX(Component.LEFT_ALIGNMENT);
        waterPanel.add(Water);
        waterPanel.add(waterRating);
        waterPanel.add(Box.createVerticalStrut(20));

        contentPanel.add(waterPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        //Displays the energy usage
        JLabel Energy = new JLabel();
        JLabel energyRating = new JLabel();
        Energy.setText("ENERGY USED:");
        Energy.setFont(oswald.deriveFont(20f));
        Energy.setForeground(new Color(0,99,73));
        energyRating.setText(energy + " MJ/kg");
        energyRating.setFont(oswald.deriveFont(20f));
        energyRating.setAlignmentX(Component.LEFT_ALIGNMENT);
        energyPanel.add(Energy);
        energyPanel.add(energyRating);
        energyPanel.add(Box.createVerticalStrut(20));

        contentPanel.add(energyPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        pie.add(chartPanel);
        contentPanel.add(pie);
        contentPanel.add(Box.createVerticalStrut(20));

        acq.add(chartPanel1);
        contentPanel.add(acq);
        contentPanel.add(Box.createVerticalStrut(20));

        contentPanel.revalidate();
        contentPanel.repaint();




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


        //--------------------------------------------------------------------------------------------------------------
    }
}
