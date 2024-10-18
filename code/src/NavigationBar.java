import javax.swing.*;
import java.awt.*;

//class to use throughout all other programs
public class NavigationBar extends JPanel {

    public NavigationBar() {

        // Set the background color
        this.setBackground(new Color(0, 99, 73));

       // Set the layout to FlowLayout for automatic horizontal alignment
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5)); // alignment, h


//----------------------------------CREATE HOME BUTTON------------------------------------------------------------------------------------------
        ImageIcon homeIcon = new ImageIcon(getClass().getResource("/pictures/homeIcon.png"));//get image from pictures folder
        Image image = homeIcon.getImage(); // transform it into an Image object
        Image scaledImage = image.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH); // Scale to 45x45 (matching button size)
        ImageIcon scaledHomeIcon = new ImageIcon(scaledImage);
        JButton homeButton = new JButton(scaledHomeIcon);
        // Remove button borders and text to make the image fill the button
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        homeButton.setContentAreaFilled(false);
//------------------------------------------------------------------------------------------------------------------------------------------------



//------------------------------CREATE FORUM BUTTON-----------------------------------------------------------------------------------------------
        ImageIcon forumIcon = new ImageIcon(getClass().getResource("/pictures/forumIcon.png"));
        Image image2 = forumIcon.getImage();
        Image scaledImage2 = image2.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
        ImageIcon scaledForumIcon = new ImageIcon(scaledImage2);
        JButton forumButton = new JButton(scaledForumIcon);
        forumButton.setBorderPainted(false);
        forumButton.setFocusPainted(false);
        forumButton.setContentAreaFilled(false);
//-------------------------------------------------------------------------------------------------------------------------------------------------



//---------------------------------CREATE CLOSET BUTTON--------------------------------------------------------------------------------------------
       ImageIcon closetIcon = new ImageIcon(getClass().getResource("/pictures/closetIcon.png"));
       Image image3 = closetIcon.getImage();
       Image scaledImage3 = image3.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
       ImageIcon scaledClosetIcon = new ImageIcon(scaledImage3);
       JButton closetButton = new JButton(scaledClosetIcon);
       closetButton.setBorderPainted(false);
       closetButton.setFocusPainted(false);
       closetButton.setContentAreaFilled(false);
//-------------------------------------------------------------------------------------------------------------------------------------------------



//------------------------------------------------CREATE DASHBOARD BUTTON---------------------------------------------------------------------------
       ImageIcon dashIcon = new ImageIcon(getClass().getResource("/pictures/dashIcon.png"));
       Image image4 = dashIcon.getImage();
       Image scaledImage4 = image4.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
       ImageIcon scaledDashIcon = new ImageIcon(scaledImage4);
       JButton dashButton = new JButton(scaledDashIcon);
       dashButton.setBorderPainted(false);
       dashButton.setFocusPainted(false);
       dashButton.setContentAreaFilled(false);
//-------------------------------------------------------------------------------------------------------------------------------------------------



//-------------------------------------------CREATE REVIEW BUTTON-----------------------------------------------------------------------------------
        ImageIcon reviewIcon = new ImageIcon(getClass().getResource("/pictures/reviewIcon.png"));
        Image image5 = reviewIcon.getImage();
        Image scaledImage5 = image5.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
        ImageIcon scaledReviewIcon = new ImageIcon(scaledImage5);
        JButton reviewButton = new JButton(scaledReviewIcon);
        reviewButton.setBorderPainted(false);
        reviewButton.setFocusPainted(false);
        reviewButton.setContentAreaFilled(false);
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



            // Set preferred size to ensure the navigation bar is displayed
            this.setPreferredSize(new Dimension(400, 60));

    }
}
