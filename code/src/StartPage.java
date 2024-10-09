import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.JButton;

//WORK ON ATTACHING BUTTONS TO NEXT ACTION AND LOAD INCONS ON JBUTTONS

public class StartPage extends JPanel
{
  //button variables for navigation bar and texts
 JButton homeButton, forumButton, closetButton, dashboardButton, reviewButton;
 JLabel aboutUs, aboutText, MissonText;

 //constructor
    StartPage(Font oswald, Font lato)
    {
    //-----------------------Set background color and preferred size----------
        this.setBackground(new Color(235, 219, 195));
        this.setPreferredSize(new Dimension(330, 700));
        this.setLayout(null);
   //-------------------------------------------------------------------------



   //-------------------CALL NAVIGATION BAR-----------------------------
        // Add the navigation bar at the bottom
        NavigationBar navigationBar = new NavigationBar();
        navigationBar.setBounds(0, 720, 500, 60); // Adjust the size and position
        this.add(navigationBar);
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
        aboutText = new JLabel("<html><div style='width:200px;'>" +
                                           "Lorem ipsum dolor sit amet, consecteir adipiscing elit," +
                                           "sed do eisumod tmpora incidndij it " +
                                           "labore et dolore magna aliqua" + "</div></html>");

        aboutText.setFont(lato.deriveFont(15f)); //set font and text size
        aboutText.setForeground(new Color (0, 99, 73)); //set color of text
        aboutText.setBounds(160, 30, 200, 200); //set location
        this.add(aboutText); //add it
//--------------------------------------------------------------------------------------------------------



 //-----------------------------------------OUR MISSION---------------------------------------------------------------
        //create Our Mission Text
        MissonText = new JLabel("<html><div style='width:200px;'>" +
                                            "Our mission is to do blahbalh blah and with that well blah blah" +
                                            "well blah blah blah blah and lorem ipusm folor sit amet blah " +
                                            "consexyir adiposicing elir sed do wiusomd temoir incidnt ut balh " +
                                            " kabire ey dolroe magna aliwue. ullamco alboris nsisi ut by balh " +
                                            "aliquoid ex ea commaodo consequat. disus aute irrur doleo blah at" + "</div></html>");

        MissonText.setFont(lato.deriveFont(15f)); //set font and text size
        MissonText.setForeground(new Color (0, 99, 73)); //set color of text
        MissonText.setBounds(30, 290, 300, 300); //set location

       // Create a custom border with rounded corners
        Border roundedBorder = new LineBorder(new Color(0, 99, 73), 3, true); // Rounded edge with thickness 3
       // Add a titled border around the label with the title "OUR MISSION"
        TitledBorder title = new TitledBorder(roundedBorder, "OUR MISSION", TitledBorder.CENTER, TitledBorder.TOP);
        title.setTitleFont(oswald.deriveFont(30f));  // Set custom font size for the title
        title.setTitleColor(new Color(0, 99, 73)); // Set the color of the title
        MissonText.setBorder(title); // Apply the border to the JLabel
        this.add(MissonText); //add it
//---------------------------------------------------------------------------------------------------------------------



//----------------------------------IMAGE FOR HOME PAGE CURRENT IMAGE IS PLACE HOLDER!!!! -----------------------------
        ImageIcon logo = new ImageIcon (getClass().getResource("pictures/ecouturelogo2.png"));
        Image scaledLogo = logo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Resize image to 100x100
        logo = new ImageIcon(scaledLogo);  // Update logo with resized image
        JLabel logoDisplay = new JLabel(logo);
        logoDisplay.setBounds(-20, 10, 200, 200);
        this.add(logoDisplay);
//---------------------------------------------------------------------------------------------------------------------

    }
}
