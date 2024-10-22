import javax.swing.*;
import java.awt.*;
import java.util.Objects;


//class to use throughout all other programs
public class NavigationBar extends JPanel {

    public NavigationBar(Font oswald, Font lato) {

        // Set the background color
        this.setBackground(new Color(0, 99, 73));

        // Set the layout to FlowLayout for automatic horizontal alignment
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5)); // alignment, h


//----------------------------------CREATE HOME BUTTON------------------------------------------------------------------------------------------
        ImageIcon homeIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/homeIcon.png")));//get image from pictures folder
        Image image = homeIcon.getImage(); // transform it into an Image object
        Image scaledImage = image.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH); // Scale to 45x45 (matching button size)
        ImageIcon scaledHomeIcon = new ImageIcon(scaledImage);
        JButton homeButton = new JButton(scaledHomeIcon);
        // Remove button borders and text to make the image fill the button
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        homeButton.setContentAreaFilled(false);

        homeButton.addActionListener(e -> homeButtonActionPerformed(e, oswald, lato));

//------------------------------------------------------------------------------------------------------------------------------------------------


//------------------------------CREATE FORUM BUTTON-----------------------------------------------------------------------------------------------
        ImageIcon forumIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/forumIcon.png")));
        Image image2 = forumIcon.getImage();
        Image scaledImage2 = image2.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
        ImageIcon scaledForumIcon = new ImageIcon(scaledImage2);
        JButton forumButton = new JButton(scaledForumIcon);
        forumButton.setBorderPainted(false);
        forumButton.setFocusPainted(false);
        forumButton.setContentAreaFilled(false);

        forumButton.addActionListener(e -> forumButtonActionPerformed(e, oswald, lato));
//-------------------------------------------------------------------------------------------------------------------------------------------------


//---------------------------------CREATE CLOSET BUTTON--------------------------------------------------------------------------------------------
        ImageIcon closetIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/closetIcon.png")));
        Image image3 = closetIcon.getImage();
        Image scaledImage3 = image3.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
        ImageIcon scaledClosetIcon = new ImageIcon(scaledImage3);
        JButton closetButton = new JButton(scaledClosetIcon);
        closetButton.setBorderPainted(false);
        closetButton.setFocusPainted(false);
        closetButton.setContentAreaFilled(false);

        closetButton.addActionListener(e -> closetButtonActionPerformed(e, oswald, lato));
//-------------------------------------------------------------------------------------------------------------------------------------------------


//------------------------------------------------CREATE DASHBOARD BUTTON---------------------------------------------------------------------------
        ImageIcon dashIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/dashIcon.png")));
        Image image4 = dashIcon.getImage();
        Image scaledImage4 = image4.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
        ImageIcon scaledDashIcon = new ImageIcon(scaledImage4);
        JButton dashButton = new JButton(scaledDashIcon);
        dashButton.setBorderPainted(false);
        dashButton.setFocusPainted(false);
        dashButton.setContentAreaFilled(false);

        //attach closet button to closet button
        dashButton.addActionListener(e -> dashButtonActionPerformed(e, oswald, lato));
//-------------------------------------------------------------------------------------------------------------------------------------------------


//-------------------------------------------CREATE REVIEW BUTTON-----------------------------------------------------------------------------------
        ImageIcon reviewIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/reviewIcon.png")));
        Image image5 = reviewIcon.getImage();
        Image scaledImage5 = image5.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
        ImageIcon scaledReviewIcon = new ImageIcon(scaledImage5);
        JButton reviewButton = new JButton(scaledReviewIcon);
        reviewButton.setBorderPainted(false);
        reviewButton.setFocusPainted(false);
        reviewButton.setContentAreaFilled(false);

        //attach closet button to clost button
        reviewButton.addActionListener(e -> reviewButtonActionPerformed(e, oswald, lato));
//-------------------------------------------------------------------------------------------------------------------------------------------------

        // Set square size for each button (e.g., 45x45)
        Dimension squareSize = new Dimension(45, 45);
        homeButton.setPreferredSize(squareSize);
        forumButton.setPreferredSize(squareSize);
        closetButton.setPreferredSize(squareSize);
        dashButton.setPreferredSize(squareSize);
        reviewButton.setPreferredSize(squareSize);


        // Add buttons to the panel
        this.add(homeButton);
        this.add(forumButton);
        this.add(closetButton);
        this.add(dashButton);
        this.add(reviewButton);

    }


    private void homeButtonActionPerformed(Object evt, Font oswald, Font lato) {
        try {
            //Switch the StartPage to StartPage
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear current page from the current frame
            StartPage startpage = new StartPage(oswald, lato);
            topFrame.add(startpage, BorderLayout.CENTER); // Add StartPage to the frame
            topFrame.revalidate(); // Refresh the frame
            topFrame.repaint(); // Repaint the frame
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
            System.out.println("Can't reopen start page");
        }
    }

    private void forumButtonActionPerformed(Object evt, Font oswald, Font lato) {
        try {
            //Switch the StartPage to Forum
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear current page from the current frame
            Forum forumpage = new Forum(oswald, lato);
            topFrame.add(forumpage, BorderLayout.CENTER); // Add Forum Page to the frame
            topFrame.revalidate(); // Refresh the frame
            topFrame.repaint(); // Repaint the frame
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
            System.out.println("Can't open forum page");
        }
    }

    private void closetButtonActionPerformed(Object evt, Font oswald, Font lato) {
        try {
            //Switch the StartPage to Closet
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear current page from the current frame
            Closet closetpage = new Closet(oswald, lato);
            topFrame.add(closetpage, BorderLayout.CENTER); // Add Forum Page to the frame
            topFrame.revalidate(); // Refresh the frame
            topFrame.repaint(); // Repaint the frame
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
            System.out.println("Can't open closet page");
        }
    }

   private void dashButtonActionPerformed(Object evt, Font oswald, Font lato) {
        try {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear current page from the current frame
            DashBoard Dash = new DashBoard(oswald, lato);
            topFrame.add(Dash, BorderLayout.CENTER); // Add Page to the frame
            topFrame.revalidate(); // Refresh the frame
            topFrame.repaint(); // Repaint the frame
            
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
            System.out.println("Can't open dashboard page");
        }
    }

    private void reviewButtonActionPerformed(Object evt, Font oswald, Font lato) {
        try {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear current page from the current frame
            Review Review = new Review(oswald, lato);
            topFrame.add(Review, BorderLayout.CENTER); // Add Page to the frame
            topFrame.revalidate(); // Refresh the frame
            topFrame.repaint(); // Repaint the frame
            
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
            System.out.println("Can't open Review page");
        }
    }

}
