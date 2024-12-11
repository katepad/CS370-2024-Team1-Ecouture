package model;

import javax.swing.*;
import java.awt.*;

import view.forumView;
import view.editForumView;
import dataAccess.forumPostDAO;

public class forumManagement {

    public static void editPostWithEditView(forumPost post, Font oswald, Font lato, forumView forumView, user user){
        try {
            //switch to the editForumView Page
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(forumView);
            topFrame.getContentPane().removeAll(); //clear all components from the current frame

            //create editForumView panel with the forum content preloaded
            editForumView editForumView = new editForumView(oswald, lato, user);
            editForumView.ptField.setText(post.getTitle()); //preload the post title
            editForumView.pcField.setText(post.getContent()); //preload the post content

            //create separate submit button for updating. (to not create a duplicate).
            editForumView.submitButton.setVisible(false);
            JButton updateButton = new JButton("UPDATE");
            updateButton.setBounds(200, 540 + 80, 100, 40);
            updateButton.setBackground(new Color(0, 99, 73)); //Button color: green
            updateButton.setForeground(new Color(247, 248, 247)); //Button text color: white
            updateButton.setFont(oswald.deriveFont(18f)); //Font: Oswald, size 18
            updateButton.setFocusable(false);
            editForumView.add(updateButton);

            //add editForumView to the frame
            topFrame.add(editForumView, BorderLayout.CENTER);
            topFrame.revalidate(); //refresh the frame
            topFrame.repaint(); //repaint the frame

            //update the post in the database when the user hits the submit button
            updateButton.addActionListener(e -> forumPostDAO.updatePost(post, editForumView.ptField.getText(), editForumView.pcField.getText(), oswald, lato, editForumView, forumView, user));
        } catch (Exception e) {
            System.out.println("Error while editing post: " + e.getMessage());
        }
    }

}
