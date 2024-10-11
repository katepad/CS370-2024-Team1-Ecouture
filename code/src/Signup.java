import javax.swing.*;
import java.awt.*;

public class Signup extends JPanel {

    //Labels
    JLabel signupTitle = new JLabel("SIGN UP");
    JButton loginButton = new JButton("LOGIN");
    JButton signupButton = new JButton("SIGN UP");
    JLabel usernameTitle = new JLabel("USERNAME");
    JLabel passwordTitle = new JLabel("PASSWORD");
    JLabel passwordRetypeTitle = new JLabel("RETYPE PASSWORD");
    JLabel fullnameTitle = new JLabel("PREFERRED NAME");

    //Fields to be filled
    JTextField unField = new JTextField();
    JPasswordField pwField = new JPasswordField();
    JPasswordField pwrtField = new JPasswordField();
    JTextField fnField = new JTextField();

    //Error msg.
    JLabel signupError = new JLabel("");
    //"Passwords do not match"
    //"Username already taken"
    //"Please fill in required field"

    //Constructor to make Signup Panel
    Signup(Font oswald, Font lato) {

        this.setBackground(new Color(235, 219, 195));
        this.setPreferredSize(new Dimension(330, 700));
        this.setLayout(null);

        //edit signup page title label to match other page titles.
        signupTitle.setFont(oswald.deriveFont(30f)); //set font and text size
        signupTitle.setForeground(new Color (0, 99, 73)); //set color
        signupTitle.setBounds(20, -60, 200, 200);

        // Set size and position for components

        //titles and input fields
        usernameTitle.setBounds(50, 100, 100, 30);
        unField.setBounds(50, 140, 200, 30);
        passwordTitle.setBounds(50, 200, 100, 30);
        pwField.setBounds(50, 240, 200, 30);
        passwordRetypeTitle.setBounds(50, 300, 200, 30);
        pwrtField.setBounds(50, 340, 200, 30);
        fullnameTitle.setBounds(50, 400, 200, 30);
        fnField.setBounds(50, 440, 200, 30);

        //button and error msg
        loginButton.setBounds(30, 540, 100, 40);
        signupButton.setBounds(180, 540, 100, 40);
        signupError.setBounds(50, 490, 250, 30);

        // Set Label Titles Font: Oswald, size 18, green.

        //green color font for labels
        usernameTitle.setForeground(new Color(0, 99, 73));
        passwordTitle.setForeground(new Color(0, 99, 73));
        signupError.setForeground(new Color(0, 99, 73));
        passwordRetypeTitle.setForeground(new Color(0, 99, 73));
        fullnameTitle.setForeground(new Color(0, 99, 73));

        //type and size of font
        usernameTitle.setFont(oswald.deriveFont(18f));
        passwordTitle.setFont(oswald.deriveFont(18f));
        signupError.setFont(lato.deriveFont(14f));
        passwordRetypeTitle.setFont(oswald.deriveFont(18f));
        fullnameTitle.setFont(oswald.deriveFont(18f));

        //redesign login button
        loginButton.setBackground(new Color(0, 99, 73)); //set button color: green
        loginButton.setForeground(new Color(247, 248, 247)); //set button text color: white
        loginButton.setFont(oswald.deriveFont(18f)); //change button text font to oswald, size 20.
        loginButton.setFocusable(false);

        //redesign signup button
        signupButton.setBackground(new Color(0, 99, 73)); //set button color: green
        signupButton.setForeground(new Color(247, 248, 247)); //set button text color: white
        signupButton.setFont(oswald.deriveFont(18f)); //change button text font to oswald, size 20.
        signupButton.setFocusable(false);

        //redesign textboxes

        //set color of input field box
        unField.setBackground(new Color(247, 248, 247));
        pwField.setBackground(new Color(247, 248, 247));
        pwrtField.setBackground(new Color(247, 248, 247));
        fnField.setBackground(new Color(247, 248, 247));

        //set font color
        unField.setForeground(Color.BLACK);
        pwField.setForeground(Color.BLACK);
        pwrtField.setForeground(Color.BLACK);
        fnField.setForeground(Color.BLACK);

        //set font type and size
        unField.setFont(lato.deriveFont(16f));
        pwField.setFont(lato.deriveFont(16f));
        pwrtField.setFont(lato.deriveFont(16f));
        fnField.setFont(lato.deriveFont(16f));

        //add components to be shown on Panel
        this.add(signupTitle);
        this.add(usernameTitle);
        this.add(passwordTitle);
        this.add(unField);
        this.add(pwField);
        this.add(loginButton);
        this.add(signupButton);
        this.add(signupError);
        this.add(passwordRetypeTitle);
        this.add(pwrtField);
        this.add(fullnameTitle);
        this.add(fnField);

        //hide signup error
        signupError.setVisible(false); //hide visibility until a signup error happens.

        //execute an attempt to login if login is clicked.
        loginButton.addActionListener(e -> loginButtonActionPerformed(e, oswald, lato));

        //execute signup method if signup is clicked.
        signupButton.addActionListener(e -> signupButtonActionPerformed(e, oswald, lato));

        //show panel
        this.setVisible(true);
    }

    //When clicked, sign up the user to database.
    private void signupButtonActionPerformed(Object evt, Font oswald, Font lato) {
        try {
            //TODO: Catch signup errors and set error msg for each scenario.
            //TODO: Write SQL queries to register user to database.
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }

    //When clicked, go back to the login page.
    private void loginButtonActionPerformed(Object evt, Font oswald, Font lato) {
        try {
            //Switch the Signup Page by switching main JFrame
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear all components from the current frame
            Login login = new Login(oswald, lato);
            topFrame.add(login, BorderLayout.CENTER); // Add SignupPage to the frame
            topFrame.revalidate(); // Refresh the frame
            topFrame.repaint(); // Repaint the frame
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }

}