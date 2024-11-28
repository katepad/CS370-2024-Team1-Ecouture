import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.Color;
import java.util.Objects;

public class startPageView extends JPanel {

    //button variables for navigation bar and texts
    JLabel aboutUs, aboutText, MissonText;

    //constructor
    startPageView(Font oswald, Font lato, user user) {

        //-----------------------Set background color and preferred size------------------------------------------------
        this.setBackground(new Color(235, 219, 195));
        this.setPreferredSize(new Dimension(330, 700));
        this.setLayout(null);
        //--------------------------------------------------------------------------------------------------------------


        //-------------------CALL NAVIGATION BAR AND SET BOUNDS---------------------------------------------------------
        // Add the navigation bar at the bottom
        navigationBarView navigationBarView = new navigationBarView(oswald,lato,user);
        navigationBarView.setBounds(0, 709, 500, 55); // Adjust the size and position
        this.add(navigationBarView, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
        //--------------------------------------------------------------------------------------------------------------

        //----------------------- LOGOUT BUTTON ----------------------------------------------------------------------//

        //Add button
        ImageIcon addButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/logOutButton.png")));
        Image addImage = addButtonIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton logoutButton = new JButton();
        logoutButton.setIcon(new ImageIcon(addImage));
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setOpaque(false);
        logoutButton.setBounds(325,25,30,30);
        this.add(logoutButton);

        //logout if pressed.
        logoutButton.addActionListener(e -> logOut(oswald, lato));

        //------------------------------------------------------------------------------------------------------------//


        //--------------------------------------ABOUT US----------------------------------------------------------------
        //create ABOUT US TEXT
        aboutUs = new JLabel("ABOUT US");
        aboutUs.setFont(oswald.deriveFont(30f)); //set font and text size
        aboutUs.setForeground(new Color (0, 99, 73)); //set color
        aboutUs.setBounds(20, -60, 200, 200); //set location
        this.add(aboutUs); //add it

        //create about us TEXT
        aboutText = new JLabel("<html><div style='width:230px;'>" +
                "Hi, " + user.getRealName() +
                "! Welcome to Ecouture, A friendly and " +
                "informative application that will teach you " +
                "about how your shopping habits impact our environment." + "</div></html>");

        aboutText.setFont(oswald.deriveFont(18f)); //set font and text size
        aboutText.setForeground(new Color (0, 99, 73)); //set color of text
        aboutText.setBounds(45, 30, 300, 200); //set location
        this.add(aboutText); //add it
        //----------------------------------------------------------------------------------------------------------------------



        //-----------------------------------------OUR MISSION----------------------------------------------------------
        //create Our Mission Text
        MissonText = new JLabel("<html><div style='text-align: center;'>"
                +"Our mission is to educate the newer generations on sustainable shopping methods. <br>"
                + "With the rise of fast fashion we have seen the negative <br>"
                + "impacts it has had on our environment, from water pollution and deforestation <br>"
                + "to excessive waste. Our mission is to convert people to more eco-friendly practices, <br>"
                + "thereby fostering a healthier planet. <br>"
                + "</div></html>");

        MissonText.setFont(oswald.deriveFont(15f)); //set font and text size
        MissonText.setForeground(new Color (0, 99, 73)); //set color of text
        MissonText.setBounds(18, 415, 350, 280); //set location

        // Create a custom border with rounded corners
        Border roundedBorder = new LineBorder(new Color(0, 99, 73), 5, true); // Rounded edge with thickness 3
        // Add a titled border around the label with the title "OUR MISSION"
        TitledBorder title = new TitledBorder(roundedBorder, "OUR MISSION", TitledBorder.CENTER, TitledBorder.TOP);
        title.setTitleFont(oswald.deriveFont(30f));  // Set custom font size for the title
        title.setTitleColor(new Color(0, 99, 73)); // Set the color of the title
        MissonText.setBorder(title); // Apply the border to the JLabel
        this.add(MissonText); //add it
    //---------------------------------------------------------------------------------------------------------------------



    //----------------------------------IMAGE FOR HOME PAGE CURRENT IMAGE IS PLACE HOLDER!!!! -----------------------------
        //load the image and scale it to the desired size
        ImageIcon fact = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/rec2.png")));
        Image scaledFact = fact.getImage().getScaledInstance(200, 200, 4); // Resize image smoothly
        fact = new ImageIcon(scaledFact);  // Update fact with resized image
        //create a JLabel to display the image
        JLabel factDisplay = new JLabel(fact);
        //set bounds for the image (ensure it fits in the layout)
        factDisplay.setBounds(90, 200, 200, 200); // Adjust bounds based on the new image size
        // Add the image display to the panel
        this.add(factDisplay);

        //---------------------------------------------------------------------------------------------------------------------

    }

    //TODO: put in start page controller
    private void logOut(Font oswald, Font lato) {
        //Switch to the login page
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane().removeAll(); // Clear all components from the current frame
        loginView loginView = new loginView(oswald, lato);
        topFrame.add(loginView, BorderLayout.CENTER); // Add SignupPage to the frame
        topFrame.revalidate(); // Refresh the frame
        topFrame.repaint(); // Repaint the frame
    }
}
