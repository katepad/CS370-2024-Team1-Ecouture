package view;

import javax.swing.*;
import java.awt.*;

import controller.userController;

public class signupView extends JPanel {

    //Labels
    JLabel signupTitle = new JLabel("SIGN UP");
    JButton loginButton = new JButton("LOGIN");
    JButton signupButton = new JButton("SIGN UP");
    JLabel usernameTitle = new JLabel("USERNAME");
    JLabel passwordTitle = new JLabel("PASSWORD");
    JLabel passwordRetypeTitle = new JLabel("RETYPE PASSWORD");
    JLabel fullnameTitle = new JLabel("PREFERRED NAME");

    //Fields to be filled
    public JTextField unField = new JTextField();
    public JPasswordField pwField = new JPasswordField();
    public JPasswordField pwrtField = new JPasswordField();
    public JTextField fnField = new JTextField();

    //Error msg.
    public JLabel signupError = new JLabel("");

    //variables to store field inputs:
    public String fullname;
    public String password;
    public String username;

    //Constructor to make Signup Panel
    public signupView(Font oswald, Font lato) {

        //-----------------------Set background color and preferred size------------------------------------------------
        this.setBackground(new Color(235, 219, 195));
        this.setPreferredSize(new Dimension(400, 800));
        this.setLayout(null);
        //--------------------------------------------------------------------------------------------------------------

        //edit signup page title label to match other page titles.
        signupTitle.setFont(oswald.deriveFont(30f)); //set font and text size
        signupTitle.setForeground(new Color (0, 99, 73)); //set color
        signupTitle.setBounds(50, -60+80, 200, 200);

        // Set size and position for components

        //titles and input fields
        usernameTitle.setBounds(50, 100+80, 100, 30);
        unField.setBounds(50, 140+80, 250, 30);
        passwordTitle.setBounds(50, 200+80, 100, 30);
        pwField.setBounds(50, 240+80, 250, 30);
        passwordRetypeTitle.setBounds(50, 300+80, 200, 30);
        pwrtField.setBounds(50, 340+80, 250, 30);
        fullnameTitle.setBounds(50, 400+80, 300, 30);
        fnField.setBounds(50, 440+80, 250, 30);

        //button and error msg
        signupError.setBounds(50, 490+80, 250, 30);
        loginButton.setBounds(50, 540+80, 100, 40);
        signupButton.setBounds(200, 540+80, 100, 40);

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
        this.add(loginButton, 2);
        this.add(signupButton, 2);
        this.add(signupError);
        this.add(passwordRetypeTitle);
        this.add(pwrtField);
        this.add(fullnameTitle);
        this.add(fnField);

        //hide signup error
        signupError.setVisible(false); //hide visibility until a signup error happens.

        //execute an attempt to login if login is clicked.
        loginButton.addActionListener(e -> userController.signupLoginButtonActionPerformed(oswald, lato, this));

        //execute signup method if signup is clicked.
        signupButton.addActionListener(e -> userController.signupSignupButtonActionPerformed(oswald, lato, this));

        //show panel
        this.setVisible(true);

    }

}