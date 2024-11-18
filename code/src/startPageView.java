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

    private user user;

    //constructor
    startPageView(Font oswald, Font lato, user user) {

        //-----------------------Set background color and preferred size----------
        this.setBackground(new Color(235, 219, 195));
        this.setPreferredSize(new Dimension(330, 700));
        this.setLayout(null);
        this.user = user;
        //-------------------------------------------------------------------------

        //-------------------CALL NAVIGATION BAR AND SET BOUNDS-----------------------------
        // Add the navigation bar at the bottom
        navigationBarView navigationBarView = new navigationBarView(oswald, lato, user);
        navigationBarView.setBounds(0, 709, 500, 55); // Adjust the size and position
        this.add(navigationBarView, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
        //--------------------------------------------------------------------------------------


        //--------------------------------------ABOUT US---------------------------------------------------------
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

        aboutText.setFont(lato.deriveFont(15f)); //set font and text size
        aboutText.setForeground(new Color (0, 99, 73)); //set color of text
        aboutText.setBounds(45, 30, 300, 200); //set location
        this.add(aboutText); //add it
//--------------------------------------------------------------------------------------------------------



        //-----------------------------------------OUR MISSION---------------------------------------------------------------
        //create Our Mission Text
        MissonText = new JLabel("<html><div style='width:250px;'>" +
                "Our mission is to educate the newer generations on sustainable" +
                " shopping methods. With the rise of fast fashion we have seen " +
                "the negative impacts it has had on our environment, from water" +
                " pollution and deforestation to excessive waste. Our mission is " +
                "to convert people to more eco-friendly practices, thereby fostering a healthier planet." + "</div></html>");

        MissonText.setFont(lato.deriveFont(15f)); //set font and text size
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
// Load the image and scale it to the desired size
        ImageIcon fact = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/ecouturelogo.png")));
        Image scaledFact = fact.getImage().getScaledInstance(300, 249, 4); // Resize image smoothly
        fact = new ImageIcon(scaledFact);  // Update fact with resized image
// Create a JLabel to display the image
        JLabel factDisplay = new JLabel(fact);
// Set bounds for the image (ensure it fits in the layout)
        factDisplay.setBounds(35, 130, 300, 300); // Adjust bounds based on the new image size
// Add the image display to the panel
        this.add(factDisplay);

//---------------------------------------------------------------------------------------------------------------------

    }
}