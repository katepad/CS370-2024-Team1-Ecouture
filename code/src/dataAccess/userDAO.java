package dataAccess;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;

import view.loginView;
import view.signupView;
import view.startPageView;
import model.user;
import model.userManagement;

public class userDAO {

    public static void login(Font oswald, Font lato, loginView loginView){
        try {
            //Ensure there is a connection to the database
            if (myJDBC.connect == null || myJDBC.connect.isClosed()) {
                System.out.println("Database connection is not established.");
                return; // Exit the method if connection is not valid
            }

            // Get input from username and password fields
            String username = loginView.unField.getText();
            String password = new String(loginView.pwField.getPassword()); // Use pwField for password

            String hashed = userManagement.hashPassword(password);


            // SQL query to match user data
            String sql = "SELECT user_ID, user_username, user_realname FROM user WHERE user_username=? AND user_password=?";
            PreparedStatement statement = myJDBC.connect.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, hashed);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                // Retrieve user details from the ResultSet
                int userId = rs.getInt("user_ID");
                String realName = rs.getString("user_realname");

                // Initialize User object
                user loggedInUser = new user(userId, realName);

                // Switch the Start Page by passing the User object
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(loginView);
                topFrame.getContentPane().removeAll(); // Clear all components from the current frame
                startPageView startPage = new startPageView(oswald, lato, loggedInUser); // Pass User object
                topFrame.add(startPage, BorderLayout.CENTER); // Add StartPage to the frame
                topFrame.revalidate(); // Refresh the frame
                topFrame.repaint(); // Repaint the frame

                System.out.println("YAY! You're logged in! Welcome, " + realName);
            } else {
                // Show login error message
                loginView.loginError.setVisible(true);
                loginView.unField.setText("");
                loginView.pwField.setText("");
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void signup(Font oswald, Font lato, signupView signupView) {
        try {

            Connection connect = myJDBC.openConnection();
            //Ensure there is a connection to the database
            if (connect == null || connect.isClosed()) {
                System.out.println("Database connection is not established.");
                return; // Exit the method if connection is not valid
            }

            boolean isError = userManagement.validateSignup(signupView); //check for any errors in the signup fields.

            //If fields are successful...
            if (!isError) { //if there are no errors,
                signupView.signupError.setText("");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] messageDigest = md.digest(signupView.password.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexstring = new StringBuilder();
                for(byte b : messageDigest){
                    hexstring.append(String.format("%02x",b));
                }
                signupView.password = new String(hexstring);
                Statement statement = myJDBC.connect.createStatement();
                String sql = "INSERT INTO user (user_username, user_password, user_realname) VALUES ('" + signupView.username + "', '" + signupView.password + "', '" + signupView.fullname + "')";
                statement.executeUpdate(sql);

                System.out.println("Signup Successful");

                userManagement.confirmSignup(oswald, lato, signupView);
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }
}
