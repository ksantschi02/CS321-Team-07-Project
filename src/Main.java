import data_retrieval.*;
import UI_screens.*;

public class Main {
    public static void main(String[] args)
    {
        Database data = new Database();
        data.readGameList();
        data.readUserList();
        new LoginPage(data.getGameList(), data.getUserList(), data);
    }
}
