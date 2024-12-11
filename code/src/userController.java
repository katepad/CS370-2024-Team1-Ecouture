import javax.swing.*;
import java.awt.*;

public class userController {

    public static void loginButtonActionPerformed(Font oswald, Font lato, loginView loginView) {
        //try to log in
        userDAO.login(oswald, lato, loginView);
    }

    //this is for the loginView
    static void signupButtonActionPerformed(Font oswald, Font lato, loginView loginView) {
        //Switch the Signup Page by switching main JFrame
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(loginView);
        topFrame.getContentPane().removeAll(); // Clear all components from the current frame
        signupView signupView = new signupView(oswald, lato);
        topFrame.add(signupView, BorderLayout.CENTER); // Add SignupPage to the frame
        topFrame.revalidate(); // Refresh the frame
        topFrame.repaint(); // Repaint the frame
    }


    static void signupSignupButtonActionPerformed(Font oswald, Font lato, signupView signupView) {
        //when clicked, sign up the user to database.
        userDAO.signup(oswald, lato, signupView);
    }

    //when clicked, go back to the login page.
    static void signupLoginButtonActionPerformed(Font oswald, Font lato, signupView signupView) {
        //Switch the Signup Page by switching main JFrame
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(signupView);
        topFrame.getContentPane().removeAll(); // Clear all components from the current frame
        loginView loginView = new loginView(oswald, lato);
        topFrame.add(loginView, BorderLayout.CENTER); // Add SignupPage to the frame
        topFrame.revalidate(); // Refresh the frame
        topFrame.repaint(); // Repaint the frame
    }

}
