import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MyFrame extends JFrame {

    MyFrame() { //constructor to create a JFrame.

        this.setTitle("Ecouture"); //Title of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //if you close the frame, exit the program
        this.setResizable(false); //prevents frame from being resized
        this.setSize(330, 700); //set size of JFrame.
        this.setVisible(true); //actually show the JFrame
        this.getContentPane().setBackground(new Color(235,219,195));

        ImageIcon logo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pictures/ecouturelogo2.png"))); //create an image icon
        this.setIconImage(logo.getImage());
    }

}
