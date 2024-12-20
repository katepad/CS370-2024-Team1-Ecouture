package view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

public class myFrame extends JFrame {

    public static Font lato;
    public static Font oswald;

    public myFrame() { //constructor to create a JFrame.
        this.setTitle("Ecouture"); //Title of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //if you close the frame, exit the program
        this.setResizable(false); //prevents frame from being resized
        this.setSize(400, 800); //set size of JFrame.
        this.getContentPane().setBackground(new Color(235,219,195));

        //add image logo for Frame
        ImageIcon logo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/ecouturelogo2.png"))); //create an image icon
        this.setIconImage(logo.getImage());

        //allow border layouts
        this.setLayout(new BorderLayout());

        //start Frame on Login Page by Default
        loginView loginViewPanel = new loginView(oswald, lato);
        this.add(loginViewPanel,BorderLayout.CENTER);

        //show contents of Frame
        this.setVisible(true); //actually show the JFrame
    }

    public static void createFonts() {

        //Create Lato font
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            lato = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/Lato-Regular.ttf")); // assign Lato font to variable
            ge.registerFont(lato); // Register Lato to fonts
        } catch (IOException | FontFormatException e) {
            System.out.println("Cannot create the Lato Font.");
        }

        //Create Oswald Font
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            oswald = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/Oswald-SemiBold.ttf")); // assign Oswald font to variable
            ge.registerFont(oswald); // Register Oswald to fonts
        } catch (IOException | FontFormatException e) {
            System.out.println("Cannot create the Oswald Font.");
        }
    }

}