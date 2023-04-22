import data_retrieval.*;
import UI_screens.*;

public class Main {
    public static void main(String[] args)
    {

        Database data = new Database(); //creates a database object to store the XML info
        data.readGameList(); //reads in the list of games from file
        data.readUserList(); //read in the list of users from file
        new LoginPage(data.getGameList(), data.getUserList(), data); //creates the LoginPage for the user to log in, and loads in all the file data through the parameters
    }
}
