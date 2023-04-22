package program_users;

import java.util.*;

/**
 * The Admin class extends the User class for the admin flagged users that
 * have the access to delete other users.
 * @author Jacob Warren
 */
public class Admin extends User {
    private ArrayList<User> users; // list of all users

    /**
     * Constructor that just invokes User constructor
     * @param username of the Admin
     * @param password of the Admin
     * @param users to be accessed
     */
    public Admin(String username, String password, ArrayList<User> users) {
        super(username, password);
        this.users = users;
    }

    /**
     * remove a user from database
     * @param username of the User to be deleted
     * @return whether they were deleted
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
