import dataAccess.myJDBC;
import view.myFrame;

public class Main {

    public static void main(String[] args){
        myFrame.createFonts();
        myFrame myFrame = new myFrame(); //create a Frame
        myJDBC.openConnection(); //connect to Local Database
        Runtime.getRuntime().addShutdownHook(new Thread(myJDBC::closeConnection)); //close DB connection before exiting
        myFrame.setVisible(true);
    }

}