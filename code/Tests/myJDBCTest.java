    package dataAccess;

    import org.junit.Test;

    import javax.swing.*;
    import java.awt.*;
    import java.sql.Connection;
    import java.sql.Date;

    import model.forumPost;

    import static org.junit.Assert.*;

    public class myJDBCTest{
        private commentDAO commentDAO = new commentDAO();
        private final Font lato = new Font("lato", Font.PLAIN, 12);
        private final JPanel jPanel = new JPanel();
        private final Date postDate = new Date(System.currentTimeMillis());
        private final forumPost post = new forumPost(1,1,"Test post", "Hello!", postDate);
        private final forumPost post2 = new forumPost(2,2,"Test post", "Hello!", postDate);
        private final forumPost post3 = new forumPost(3,3,"Test post", "Hello!", postDate);
        private final forumPost post4 = new forumPost(4,4,"Test post", "Hello!", postDate);
        private final forumPost post5 = new forumPost(5,5,"Test post", "Hello!", postDate);

        @Test //Checking how long it takes to populate post comments, if time > 5 then it fails
        public void timeForPopulateComments() {
            //Start time of the connection
            long startTime = System.nanoTime();
            commentDAO.populatePostComments(lato, jPanel, post);
            commentDAO.populatePostComments(lato, jPanel, post2);
            commentDAO.populatePostComments(lato, jPanel, post3);
            commentDAO.populatePostComments(lato, jPanel, post4);
            commentDAO.populatePostComments(lato, jPanel, post5);
            //End time of the connection
            long endTime = System.nanoTime();
            //Take the difference and check if it's less than 1 second
            long timeToConnect = (endTime - startTime)/1000000;
            assertTrue("Time to populate is",timeToConnect < 3000);
        }

    }
