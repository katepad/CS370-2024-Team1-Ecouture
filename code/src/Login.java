import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JPanel {

    JButton loginButton = new JButton("LOGIN");
    JButton signupButton = new JButton("SIGN UP");
    JLabel usernameTitle = new JLabel("USERNAME");
    JLabel passwordTitle = new JLabel("PASSWORD");
    JTextField unField = new JTextField();
    JPasswordField pwField = new JPasswordField();
    ImageIcon logo;
    JLabel logoDisplay;
    JLabel loginError = new JLabel("Invalid Username or Password.");

    //Constructor to make Login Panel
    Login (Font oswald, Font lato) {

        this.setBackground(new Color(235,219,195));
        this.setPreferredSize(new Dimension(330,700));
        this.setLayout(null);

        //create image
        logo = new ImageIcon (getClass().getResource("pictures/ecouturelogo2.png"));
        Image scaledLogo = logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Resize image to 100x100
        logo = new ImageIcon(scaledLogo);  // Update logo with resized image
        logoDisplay = new JLabel(logo);

        // Set size and position for components
        logoDisplay.setBounds(55, 0, 200, 200);
        usernameTitle.setBounds(50,225,100,30);
        passwordTitle.setBounds(50,325,100,30);
        unField.setBounds(50,275,200,30);
        pwField.setBounds(50,375,200,30);
        loginButton.setBounds(30, 475, 100, 40);
        signupButton.setBounds(180, 475, 100, 40);
        loginError.setBounds(50,425,250,30);

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

        //hide login error
        loginError.setVisible(false); //hide visibility until a login error happens.

        //add action listener to login button to execute
        loginButton.addActionListener(e -> loginButtonActionPerformed(e, oswald, lato));

        //show panel
        this.setVisible(true);
    }

    private void loginButtonActionPerformed(ActionEvent evt, Font oswald, Font lato) {

        try {
            // Ensure database connection is valid
            if (MyJDBC.connect == null || MyJDBC.connect.isClosed()) {
                System.out.println("Database connection is not established.");
                return; // Exit the method if connection is not valid
            }

            //get input from username field
            String username = unField.getText();
            //get input from password field
            String password = new String(pwField.getPassword()); // Use pwField for password

            //create a statement to execute
            Statement statement = MyJDBC.connect.createStatement();
            //sql query to match user data
            String sql = "Select * from user where user_username='" + username + "' and user_password='" + password + "'";
            //find results of user data that matches the inputs from user table
            ResultSet rs = statement.executeQuery(sql);

            if(rs.next()) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.getContentPane().removeAll(); // Clear all components from the frame
                StartPage startPage = new StartPage(oswald, lato);
                topFrame.add(startPage, BorderLayout.CENTER); // Add StartPage to the frame
                topFrame.revalidate(); // Refresh the frame
                topFrame.repaint(); // Repaint the frame
                System.out.println("YAY! You're logged in!");
            } else {
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


}
