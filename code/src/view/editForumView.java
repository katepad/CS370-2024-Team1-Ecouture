package view;

import java.awt.*;
import java.sql.Date;
import javax.swing.*;

import model.user;
import controller.forumController;

public class editForumView extends JPanel {
    //Labels
    JLabel editForumTitle = new JLabel("CREATE FORUM POST");
    JLabel postTitle = new JLabel("Title:");
    JLabel postContent = new JLabel("What would you like to say?");
    public JLabel postErrorMsg = new JLabel("Error...");

    //Buttons
    JButton cancelButton = new JButton("CANCEL");
    public JButton submitButton = new JButton("SUBMIT");

    //Fields to be filled
    public JTextField ptField = new JTextField();
    public JTextArea pcField = new JTextArea();
    JScrollPane scrollPane; //added for scrollable text area

    //variables to hold the inputs
    public String title;
    public String content;
    public Date date;
    public int userID;

    //Constructor
    public editForumView(Font oswald, Font lato, user user) {
        //--------------------------------------- BG COLOR AND PANEL SIZE --------------------------------------------//
        this.setBackground(new Color(235, 219, 195));
        this.setPreferredSize(new Dimension(400, 800));
        this.setLayout(null);
        //------------------------------------------------------------------------------------------------------------//

        userID = user.getUserId();

        //----------------------------------------------- LABEL DESIGN -----------------------------------------------//
        editForumTitle.setBounds(25, 30, 350, 30);
        postTitle.setBounds(25, 100, 100, 30);
        postContent.setBounds(25, 170, 250, 30);
        postErrorMsg.setBounds(25, 490 + 80, 250, 30);

        //Set a green color font for labels
        editForumTitle.setForeground(new Color(0, 99, 73));
        postTitle.setForeground(new Color(0, 99, 73));
        postContent.setForeground(new Color(0, 99, 73));
        postErrorMsg.setForeground(new Color(0, 99, 73));

        //Type and size of font
        editForumTitle.setFont(oswald.deriveFont(30f));
        postTitle.setFont(oswald.deriveFont(18f));
        postContent.setFont(oswald.deriveFont(18f));
        postErrorMsg.setFont(lato.deriveFont(16f));
        //------------------------------------------------------------------------------------------------------------//

        //---------------------------------------------- BUTTON DESIGN -----------------------------------------------//
        cancelButton.setBounds(50, 540 + 80, 100, 40);
        submitButton.setBounds(200, 540 + 80, 100, 40);

        //Redesign cancel button
        cancelButton.setBackground(new Color(0, 99, 73)); //Button color: green
        cancelButton.setForeground(new Color(247, 248, 247)); //Button text color: white
        cancelButton.setFont(oswald.deriveFont(18f)); //Font: Oswald, size 18
        cancelButton.setFocusable(false);

        //Redesign submit button
        submitButton.setBackground(new Color(0, 99, 73)); //Button color: green
        submitButton.setForeground(new Color(247, 248, 247)); //Button text color: white
        submitButton.setFont(oswald.deriveFont(18f)); //Font: Oswald, size 18
        submitButton.setFocusable(false);
        //------------------------------------------------------------------------------------------------------------//

        //----------------------------------------------- INPUT DESIGN ------------------------------------------------//
        ptField.setBounds(25, 130, 250, 30);
        ptField.setBackground(new Color(247, 248, 247));
        ptField.setForeground(Color.BLACK);
        ptField.setFont(lato.deriveFont(16f));

        //Configure pcField as a scrollable, wrapping text area with padding
        pcField.setLineWrap(true); //Enable line wrapping
        pcField.setWrapStyleWord(true); //Wrap lines at word boundaries
        pcField.setFont(lato.deriveFont(16f));
        pcField.setBackground(new Color(247, 248, 247));
        pcField.setForeground(Color.BLACK);
        pcField.setMargin(new Insets(10, 10, 10, 10)); //Add padding inside the text area

        //Add pcField to a JScrollPane
        scrollPane = new JScrollPane(pcField);
        scrollPane.setBounds(25, 200, 332, 350);
        //------------------------------------------------------------------------------------------------------------//

        postErrorMsg.setVisible(false);

        this.add(editForumTitle);
        this.add(postTitle);
        this.add(postContent);
        this.add(ptField);
        this.add(scrollPane);
        this.add(cancelButton);
        this.add(submitButton);
        this.add(postErrorMsg);
        this.setVisible(true);

        //--------------------------------------------- ACTION LISTENER ----------------------------------------------//
        //cancel button to go to next page
        cancelButton.addActionListener(e -> forumController.cancelButtonClicked(oswald, lato, this, user));

        //submit button to create a post and save to db
        submitButton.addActionListener(e -> forumController.submitButtonClicked(oswald, lato, this, user));
        //------------------------------------------------------------------------------------------------------------//
    }

}
