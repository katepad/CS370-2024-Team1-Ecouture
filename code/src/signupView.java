import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    JTextField unField = new JTextField();
    JPasswordField pwField = new JPasswordField();
    JPasswordField pwrtField = new JPasswordField();
    JTextField fnField = new JTextField();

    //Error msg.
    JLabel signupError = new JLabel("");
    boolean isError = true;

    //variables to store field inputs:
    String fullname;
    String password;
    String username;

    //Constructor to make Signup Panel
    signupView(Font oswald, Font lato) {

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
        loginButton.addActionListener(e -> loginButtonActionPerformed(e, oswald, lato));

        //execute signup method if signup is clicked.
        signupButton.addActionListener(e -> signupButtonActionPerformed(e));

        //show panel
        this.setVisible(true);

    }

    //TODO: Move to Controller.
    private void confirmSignup() {

        //remove the signup for login and signup
        signupButton.setVisible(false);

        signupError.setText("You have successfully made an account!");
        signupError.setVisible(true);

    }

    //TODO: Move to Controller.
    private void validateSignup() throws SQLException {
        // To Ensure real name has no special characters
        fullname = fnField.getText(); //get name
        Pattern fullnameP = Pattern.compile("[^a-zA-Z -]"); //pattern finds special characters
        Matcher fullnameM = fullnameP.matcher(fullname); //match the string to pattern
        boolean isInvalidRealname = fullnameM.find(); //boolean to see if there is a match

        // To Ensure password fields match
        password = new String(pwField.getPassword()); //get password
        String passwordRetyped = new String(pwrtField.getPassword()); //get retyped password

        // Ensure username field has no special characters
        username = unField.getText(); //Get username
        Pattern usernameP = Pattern.compile("[^a-zA-Z0-9._]"); //pattern finds special characters
        Matcher usernameM = usernameP.matcher(username); //match the string to pattern
        boolean isInvalidUsername = usernameM.find(); //boolean to see if there is a match

        //Ensure the username is not taken.
        Statement statement = myJDBC.connect.createStatement();
        //sql query to match user data
        String sql = "Select * from user where user_username='" + username + "'";
        //find results of user data that matches the inputs from user table
        ResultSet rs = statement.executeQuery(sql);

        if (username.isEmpty() || password.isEmpty() || passwordRetyped.isEmpty() || fullname.isEmpty()) {
            signupError.setText("Must fill out all fields");
            signupError.setVisible(true);
            isError = true;
        } else if (rs.next()) {
            signupError.setText("Username is already taken");
            signupError.setVisible(true);
            isError = true;
        } else if (isInvalidUsername) {
            signupError.setText("Your username has an invalid character");
            signupError.setVisible(true);
            isError = true;
        } else if (!password.equals(passwordRetyped)) { //if passwords do not match,
            signupError.setText("Your passwords do not match");
            signupError.setVisible(true);
            isError = true;
        } else if (isInvalidRealname) {
            signupError.setText("Your name has an invalid character");
            signupError.setVisible(true);
            isError = true;
        } else {
            isError = false;
        }
    }

    //TODO: Move to Controller.
    //When clicked, sign up the user to database.
    private void signupButtonActionPerformed(Object evt) {
        try {

            Connection connect = myJDBC.openConnection();
            //Ensure there is a connection to the database
            if (connect == null || connect.isClosed()) {
                System.out.println("Database connection is not established.");
                return; // Exit the method if connection is not valid
            }

            validateSignup(); //check for any errors in the signup fields.

            //If fields are successful...
            if (!isError) { //if there are no errors,
                signupError.setText("");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] messageDigest = md.digest(password.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexstring = new StringBuilder();
                for(byte b : messageDigest){
                    hexstring.append(String.format("%02x",b));
                }
                password = new String(hexstring);
                Statement statement = myJDBC.connect.createStatement();
                String sql = "INSERT INTO user (user_username, user_password, user_realname) VALUES ('" + username + "', '" + password + "', '" + fullname + "')";
                statement.executeUpdate(sql);

                System.out.println("Signup Successful");

                confirmSignup();
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }

    //TODO: Move to Controller.
    //When clicked, go back to the login page.
    private void loginButtonActionPerformed(Object evt, Font oswald, Font lato) {
        try {
            //Switch the Signup Page by switching main JFrame
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear all components from the current frame
            loginView loginView = new loginView(oswald, lato);
            topFrame.add(loginView, BorderLayout.CENTER); // Add SignupPage to the frame
            topFrame.revalidate(); // Refresh the frame
            topFrame.repaint(); // Repaint the frame
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }

}