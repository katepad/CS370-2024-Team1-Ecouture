package model;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import view.loginView;
import view.signupView;
import dataAccess.myJDBC;

public class userManagement {

    public static String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //Hash password to send to database to check
        MessageDigest md = MessageDigest.getInstance("SHA-256");//Using SHA256sum hashing algorithm
        byte[] messageDigest = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexstring = new StringBuilder();//The new string that will be hashed
        for(byte b : messageDigest){
            hexstring.append(String.format("%02x",b)); //Format of hashing
        }
        return hexstring.toString();
    }

    public static boolean validateSignup(signupView signupView) throws SQLException {
        // To Ensure real name has no special characters
        signupView.fullname = signupView.fnField.getText(); //get name
        Pattern fullnameP = Pattern.compile("[^a-zA-Z -]"); //pattern finds special characters
        Matcher fullnameM = fullnameP.matcher(signupView.fullname); //match the string to pattern
        boolean isInvalidRealname = fullnameM.find(); //boolean to see if there is a match

        // To Ensure password fields match
        signupView.password = new String(signupView.pwField.getPassword()); //get password
        String passwordRetyped = new String(signupView.pwrtField.getPassword()); //get retyped password

        // Ensure username field has no special characters
        signupView.username = signupView.unField.getText(); //Get username
        Pattern usernameP = Pattern.compile("[^a-zA-Z0-9._]"); //pattern finds special characters
        Matcher usernameM = usernameP.matcher(signupView.username); //match the string to pattern
        boolean isInvalidUsername = usernameM.find(); //boolean to see if there is a match

        //Ensure the username is not taken.
        Statement statement = myJDBC.connect.createStatement();
        //sql query to match user data
        String sql = "Select * from user where user_username='" + signupView.username + "'";
        //find results of user data that matches the inputs from user table
        ResultSet rs = statement.executeQuery(sql);

        if (signupView.username.isEmpty() || signupView.password.isEmpty() || passwordRetyped.isEmpty() || signupView.fullname.isEmpty()) {
            signupView.signupError.setText("Must fill out all fields");
            signupView.signupError.setVisible(true);
            return true;
        } else if (rs.next()) {
            signupView.signupError.setText("Username is already taken");
            signupView.signupError.setVisible(true);
            return true;
        } else if (isInvalidUsername) {
            signupView.signupError.setText("Your username has an invalid character");
            signupView.signupError.setVisible(true);
            return true;
        } else if (!signupView.password.equals(passwordRetyped)) { //if passwords do not match,
            signupView.signupError.setText("Your passwords do not match");
            signupView.signupError.setVisible(true);
            return true;
        } else if (isInvalidRealname) {
            signupView.signupError.setText("Your name has an invalid character");
            signupView.signupError.setVisible(true);
            return true;
        }
        return false;
    }

    public static void confirmSignup(Font oswald, Font lato, signupView signupView) {

        // ------------------------------- CREATE SIGNUP MESSAGE WITH DIALOG MODAL -------------------------------//
        JDialog SignupMessage = new JDialog((Frame) SwingUtilities.getWindowAncestor(signupView), "Post Update", true);
        SignupMessage.setLayout(new BorderLayout());
        SignupMessage.setSize(300, 150);
        SignupMessage.setBackground(new Color(247, 248, 247));
        SignupMessage.setLocationRelativeTo(signupView); //center updateMessage Box relative to the editForumView

        //create the confirmation message and OK button
        JLabel SignupMessageText = new JLabel("<html><div style='width:150px; text-align: center;'><b>You have signed up successfully!</b></div></html>");
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");

        //------------------------------------------- redesign labels --------------------------------------------//
        SignupMessageText.setHorizontalAlignment(SwingConstants.CENTER);
        SignupMessageText.setFont(lato.deriveFont(18f));
        //--------------------------------------------------------------------------------------------------------//

        //------------------------------------------- redesign buttons -------------------------------------------//
        //ok
        okButton.setBackground(new Color (0, 99, 73)); //set button color to green
        okButton.setForeground(new Color (247, 248, 247)); //set button text color to white
        okButton.setFont(oswald.deriveFont(14f)); //change button text font to oswald, size 14.
        okButton.setFocusable(false);
        //--------------------------------------------------------------------------------------------------------//

        okButton.addActionListener(e -> SignupMessage.dispose()); // Close dialog on "OK"

        //add components to the Signup confirmation
        SignupMessage.add(SignupMessageText, BorderLayout.CENTER);
        buttonPanel.add(okButton, BorderLayout.CENTER);
        SignupMessage.add(buttonPanel, BorderLayout.SOUTH);

        SignupMessage.setVisible(true); //show Signup confirmation box

        //--------------------------------------------------------------------------------------------------------//

        //Switch the Signup Page by switching main JFrame
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(signupView);
        topFrame.getContentPane().removeAll(); // Clear all components from the current frame
        loginView loginView = new loginView(oswald, lato);
        topFrame.add(loginView, BorderLayout.CENTER); // Add SignupPage to the frame
        topFrame.revalidate(); // Refresh the frame
        topFrame.repaint(); // Repaint the frame

    }

}
