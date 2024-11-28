import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
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
        loginButton.addActionListener(e -> loginButtonActionPerformed(e, oswald, lato));

        //execute signup method if signup is clicked.
        signupButton.addActionListener(e -> signupButtonActionPerformed(e, oswald, lato));

        //show panel
        this.setVisible(true);
    }

    public void loginButtonActionPerformed(ActionEvent evt, Font oswald, Font lato) {
        try (Connection conn = myJDBC.openConnection()) {
            //Ensure there is a connection to the database
            if (myJDBC.connect == null || myJDBC.connect.isClosed()) {
                System.out.println("Database connection is not established.");
                return; // Exit the method if connection is not valid
            }

            // Get input from username and password fields
            String username = unField.getText();
            String password = new String(pwField.getPassword()); // Use pwField for password

            //TODO: put the hashing in a separate function called hashPassword()
            // Hash password using SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            password = hexString.toString();

            // SQL query to match user data
            String sql = "SELECT user_ID, user_username, user_realname FROM user WHERE user_username=? AND user_password=?";
            PreparedStatement statement = myJDBC.connect.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                // Retrieve user details from the ResultSet
                int userId = rs.getInt("user_ID");
                String realName = rs.getString("user_realname");

                // Initialize User object
                user loggedInUser = new user(userId, username, realName);

                // Switch the Start Page by passing the User object
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.getContentPane().removeAll(); // Clear all components from the current frame
                startPageView startPage = new startPageView(oswald, lato, loggedInUser); // Pass User object
                topFrame.add(startPage, BorderLayout.CENTER); // Add StartPage to the frame
                topFrame.revalidate(); // Refresh the frame
                topFrame.repaint(); // Repaint the frame

                System.out.println("YAY! You're logged in! Welcome, " + realName);
            } else {
                // Show login error message
                loginError.setVisible(true);
                unField.setText("");
                pwField.setText("");
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }

    private void signupButtonActionPerformed(Object evt, Font oswald, Font lato) {
        try {
            //Switch the Signup Page by switching main JFrame
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear all components from the current frame
            signupView signupView = new signupView(oswald, lato);
            topFrame.add(signupView, BorderLayout.CENTER); // Add SignupPage to the frame
            topFrame.revalidate(); // Refresh the frame
            topFrame.repaint(); // Repaint the frame
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }

    private void hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //Hash password to send to database to check
        MessageDigest md = MessageDigest.getInstance("SHA-256");//Using SHA256sum hashing algorithm
        byte[] messageDigest = md.digest(password.getBytes("UTF-8"));
        StringBuilder hexstring = new StringBuilder();//The new string that will be hashed
        for(byte b : messageDigest){
            hexstring.append(String.format("%02x",b)); //Format of hashing
        }
        password = new String(hexstring);
    }

}
