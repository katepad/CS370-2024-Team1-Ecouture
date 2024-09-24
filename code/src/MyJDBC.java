import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyJDBC {

    private static Connection connection;

    public static void connectDatabase(){
        try {

            //connect to local SQL workbench.
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_ecouture", "root", "cs370");

            //TEST: test to see if connected.
            System.out.println("connected");

        } catch (Exception e){
            //if connection is unsuccessful...
            System.out.println("didn't connect");
            e.printStackTrace();
        }
    }

    static void displayClothes(){
        try {

            //create an object that helps execute SQL queries.
            Statement statement = connection.createStatement();
            //create a result set that will store information based on statement query.
            ResultSet resultSet = statement.executeQuery("select * from clothing");

            while (resultSet.next()) { //"while there are items still left in the result set..."
                System.out.println(resultSet.getString("clothing_material")); //print the information stored in result set by query.
            }

        } catch (Exception e){
            //if connection is unsuccessful...
            e.printStackTrace();
        }
    }

}
