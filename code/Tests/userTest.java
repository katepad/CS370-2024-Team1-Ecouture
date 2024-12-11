    package model;

    import dataAccess.myJDBC;
    import org.junit.Test;

    import static org.junit.Assert.*;
    import model.user;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;

    public class userTest {
        private user user;

        public user getUserID(int userID) throws SQLException {
            Connection connection = myJDBC.openConnection();
            //SQL statement that will get user_id and name from the user table where the user is based on user id
            String sql = "SELECT user_ID, user_realname FROM user WHERE user_ID = ?";
            //Send the statement to the database
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //Set the parameter of user_id = ? to userID
            preparedStatement.setInt(1, userID);
            //Execute the statement
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                //Stroes the id from user_id column into id
                int id = rs.getInt("user_id");
                //stores the real name from the user_realname column into real_name
                String real_name = rs.getString("user_realname");
                //returns the new user
                return new user(id, real_name);
            }
            return null;
        }

        @Test //Getting the userId
        public void testGetUserInformation() throws SQLException {
            user = getUserID(1);
            assertNotNull(user);
            assertEquals(1, user.getUserId());
            assertEquals("Real name should be Jason", "Jason", user.getRealName());
            System.out.print("User id is " + user.getUserId() + " and the real name is " + user.getRealName());
        }
    }
