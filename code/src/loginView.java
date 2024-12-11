import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class loginView extends JPanel {

    JButton loginButton = new JButton("LOGIN");
    JButton signupButton = new JButton("SIGN UP");
    JLabel usernameTitle = new JLabel("USERNAME");
    JLabel passwordTitle = new JLabel("PASSWORD");
    JTextField unField = new JTextField();
    JPasswordField pwField = new JPasswordField();
    ImageIcon logo;
    JLabel logoDisplay;
    JLabel loginError = new JLabel("Invalid Username or Password.");
    JLabel loginTitle = new JLabel("LOGIN");

    //Constructor to make Login Panel
    public loginView(Font oswald, Font lato) {

        this.setBackground(new Color(235,219,195));
        this.setPreferredSize(new Dimension(400,800));
        this.setLayout(null);

        //create image for logo to be put at the bottom
        logo = new ImageIcon (Objects.requireNonNull(getClass().getResource("pictures/ecouturelogo2.png")));
        Image scaledLogo = logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Resize image to 100x100
        logo = new ImageIcon(scaledLogo);  // Update logo with resized image
        logoDisplay = new JLabel(logo);

        //edit login page title label to match other page titles.
        loginTitle.setFont(oswald.deriveFont(30f)); //set font and text size
        loginTitle.setForeground(new Color (0, 99, 73)); //set color
        loginTitle.setBounds(50, 165, 200, 200);

        // Set size and position for components
        logoDisplay.setBounds(90, 20, 200, 200);

        usernameTitle.setBounds(50,325,100,30);
        unField.setBounds(50,375,250,30);
        passwordTitle.setBounds(50,425,100,30);
        pwField.setBounds(50,475,250,30);

        loginError.setBounds(50,525,250,30);
        loginButton.setBounds(200, 575, 100, 40);
        signupButton.setBounds(50, 575, 100, 40);

        // Set Label Titles Font: Oswald, size 18, green.
        usernameTitle.setForeground(new Color (0,99,73));
        passwordTitle.setForeground(new Color (0,99,73));
        loginError.setForeground(new Color (0,99,73));
        usernameTitle.setFont(oswald.deriveFont(18f));
        passwordTitle.setFont(oswald.deriveFont(18f));
        loginError.setFont(lato.deriveFont(14f));

        //redesign login button
        loginButton.setBackground(new Color (0,99,73)); //set button color: green
        loginButton.setForeground(new Color(247,248,247)); //set button text color: white
        loginButton.setFont(oswald.deriveFont(18f)); //change button text font to oswald, size 20.
        loginButton.setFocusable(false);

        //redesign signup button
        signupButton.setBackground(new Color (0,99,73)); //set button color: green
        signupButton.setForeground(new Color(247,248,247)); //set button text color: white
        signupButton.setFont(oswald.deriveFont(18f)); //change button text font to oswald, size 20.
        signupButton.setFocusable(false);

        //redesign textboxes
        unField.setBackground(new Color (247,248,247));
        pwField.setBackground(new Color (247,248,247));
        unField.setForeground(Color.BLACK);
        pwField.setForeground(Color.BLACK);
        unField.setFont(lato.deriveFont(16f));
        pwField.setFont(lato.deriveFont(16f));

        //add components to be shown on Panel
        this.add(usernameTitle);
        this.add(passwordTitle);
        this.add(unField);
        this.add(pwField);
        this.add(loginButton);
        this.add(signupButton);
        this.add(logoDisplay);
        this.add(loginError);
        this.add(loginTitle);

        //hide login error
        loginError.setVisible(false); //hide visibility until a login error happens.

        //execute an attempt to login if login is clicked.
        loginButton.addActionListener(e -> userController.loginButtonActionPerformed(oswald, lato, this));

        //execute signup method if signup is clicked.
        signupButton.addActionListener(e -> userController.signupButtonActionPerformed(oswald, lato, this));

        //show panel
        this.setVisible(true);
    }

}