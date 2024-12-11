    package dataAccess;

    import org.junit.Test;

    import java.sql.Connection;
    import java.sql.SQLException;

    import static org.junit.Assert.*;

    public class myJDBCTest {
        private Connection connection;
        private myJDBC jdbc;

        @Test
        public void testOpenConnection() {
            jdbc = new myJDBC();
            connection = jdbc.openConnection();
            assertNotNull("Connected successfully!",connection);
            System.out.println("Connected successfully");
        }

        @Test
        public void testCloseConnection() throws SQLException {
            jdbc.closeConnection();
            assertNull(connection);
            System.out.println("Connection is closed");
        }
    }