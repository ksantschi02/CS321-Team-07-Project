package program_users;

import java.util.*

public class Admin extends User {
    private ArrayList<User> users;
    /*
     * Constructor that just invokes User constructor
     */
    public Admin(String username, String password, ArrayList<User> users) {
        super(username, password);
        this.users = users;
    }

    /*
     * remove a user from xml data
     */
    public boolean deleteUser(String username) {
        boolean found = false;

        for (User u : users) {
            if (u.getUser().equals(username)) {
                found = true;
                users.remove(u);
            }
        }

        return found;
    }
}
