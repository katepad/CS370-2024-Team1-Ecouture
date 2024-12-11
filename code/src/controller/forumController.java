package controller;

import javax.swing.*;
import java.awt.*;

import view.forumView;
import view.editForumView;
import model.forumManagement;
import model.user;
import dataAccess.forumPostDAO;
import model.forumPost;

public class forumController {

    //--------------------------------------- EDIT FORUM VIEW ACTION HANDLERS ----------------------------------------//
    //cancel button clicked from editForumView
    public static void cancelButtonClicked(Font oswald, Font lato, editForumView editForumView, user user) {
        try {
            //Switch back to the forumView Panel.
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(editForumView);
            topFrame.getContentPane().removeAll(); //Clear all components from the current frame
            forumView forumView = new forumView(oswald, lato, user);
            topFrame.add(forumView, BorderLayout.CENTER); //Add forumView Panel to the frame
            topFrame.revalidate(); //Refresh the frame
            topFrame.repaint(); //Repaint the frame
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }

    //submit button clicked from editForumView
    public static void submitButtonClicked(Font oswald, Font lato, editForumView editForumView, user user) {
        forumPostDAO.submitPost(oswald, lato, editForumView, user);
    }
    //----------------------------------------------------------------------------------------------------------------//


    //------------------------------------ MAIN FORUM VIEW ACTION HANDLERS -------------------------------------------//
    public static void editPost(forumPost post, Font oswald, Font lato, forumView forumView, user user) {
        //let the user go back to editForumView to edit their post.
        forumManagement.editPostWithEditView(post, oswald, lato, forumView, user);
    }

    public static void deletePost(forumPost post, Font oswald, Font lato, forumView forumView, user user) {
        forumPostDAO.deletePost(post, oswald, lato,forumView, user);
    }

    public static void addPostClicked(Font oswald, Font lato, forumView forumView, user user) {
        try {
            //Switch to the editForumView Page
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(forumView);
            topFrame.getContentPane().removeAll(); //clear all components from the current frame
            editForumView editForumView = new editForumView(oswald, lato, user);
            topFrame.add(editForumView, BorderLayout.CENTER); //add editForumView to the frame
            topFrame.revalidate(); //refresh the frame
            topFrame.repaint(); //repaint the frame
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }
    //----------------------------------------------------------------------------------------------------------------//
}