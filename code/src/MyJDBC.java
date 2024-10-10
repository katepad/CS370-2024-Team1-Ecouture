import java.sql.Connection;
import java.sql.DriverManager;

public class MyJDBC {

    public static Connection connect;

    public static void openConnection(){
        try {
            //connect to local SQL workbench.
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_ecouture", "root", "cs370");

            //TEST: test to see if connected.
            System.out.println("connected to my local database successfully!");

        } catch (Exception e){
            //if connection is unsuccessful...
            System.out.println("didn't connect");
        }
    }

    public static void closeConnection(){
        if (connect != null) { //if there has been an established connection,
            try {
                connect.close(); //close connection
                System.out.println("connection closed.");
            } catch (Exception e) {
                //if closing connection is not successful...
                System.out.println("cannot close connection.");
            }
        }
    }

}
