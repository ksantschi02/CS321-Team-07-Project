public class Admin extends User {
    private boolean admin;

    /*
     * Constructor that just invokes User constructor
     */
    public Admin(String username, String password) {
        super(username, password);
        this.admin = false;
    }

    /*
     * check if the admin is listed as admin in database
     */
    public boolean checkAdmin() {
        boolean found = false;

        // look through xml data for admin flag on this user, set found

        if (found) {
            this.admin = true;
        }

        return found;
    }

    /*
     * remove a user from xml data
     */
    public boolean deleteUser(String username) {
        boolean found = false;

        // search for username in data, set found

        if (found) {
            // edit xml to completely remove user
        }

        return found;
    }
}
