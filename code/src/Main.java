
public class Main {

    public static void main(String[] args){

        MyFrame myFrame = new MyFrame(); //create a Frame
        MyJDBC.openConnection(); //connect to Local Database
        Runtime.getRuntime().addShutdownHook(new Thread(MyJDBC::closeConnection)); //close DB connection before exiting

    }

}