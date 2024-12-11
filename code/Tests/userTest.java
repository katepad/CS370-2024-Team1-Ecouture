    package model;

    import org.junit.Test;

    import static org.junit.Assert.*;

    public class userTest {
        private final user user = new user(1, "Jason");

        @Test
        public void testGetUserId() {
            //System.out.println("The user ID is " + user.getUserId());
            assertEquals(1, user.getUserId());
        }

        @Test
        public void getRealName() {
            System.out.println("The name will be Jason");
            assertEquals("Jason", user.getRealName());
        }
    }