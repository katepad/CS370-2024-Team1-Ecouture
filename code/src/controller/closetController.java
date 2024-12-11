package controller;

import javax.swing.*;
import java.awt.*;

import model.closetManagement;
import model.user;
import view.closetView;
import view.editClosetView;


public class closetController {

    //cancel and switch editClosetView to closetView
    public static void cancelItem(Font oswald, Font lato, editClosetView editClosetView, user user) {
        try {
            //switch back to the closetView Panel.
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(editClosetView);
            topFrame.getContentPane().removeAll(); //clear all components from the current frame
            closetView closetView = new closetView(oswald, lato, user); //initialize new page.
            topFrame.add(closetView, BorderLayout.CENTER); //add forumView Panel to the frame
            topFrame.revalidate(); //refresh the frame
            topFrame.repaint(); //repaint the frame
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }

    public static void saveItemClicked(Font oswald, Font lato, editClosetView editClosetView, user user) {
        closetManagement.saveItemLogic(oswald, lato, editClosetView, user);
    }

    //switch to the editClosetView so user can add more clothes items to their closet.
    public static void addNewItemPanelButtonClicked(Font oswald, Font lato, user user, closetView closetView)
    {
        //switch frame from main closet to editing panel
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(closetView);
        topFrame.getContentPane().removeAll(); //clear all components from the current frame
        editClosetView editClosetView = new editClosetView(oswald, lato, user);
        topFrame.add(editClosetView, BorderLayout.CENTER); //add editClosetView to the frame
        topFrame.revalidate();
        topFrame.repaint();
    }

}
