import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private boolean loggedIn;
    // private ArrayList<Collection> collections;


    /*
     * default constructor
     */
    public User() {
        this.username = null;
        this.password = null;
        this.loggedIn = false;
        // collections = new ArrayList<>();
    }

    /*
     * Creates User at login attempt
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedIn = false;
        // collections = new ArrayList<>();
    }

    /*
     * Checks if login attempt is a valid user in database (couldn't we make this part of the constructor?)
     */
    public boolean checkUser() {
        boolean found = false;

        // check xml data and update found

        if (found) {
            this.loggedIn = true;
            // for collections in database owned by user, this.collections.add(collection)
        }

        return found;
    }

    /*
     * I don't think this method should exist. The login page or underlying class should write to the database.
     */
    public void writeUser() {
        // write this.username to database
        // write this.password to database
    }

    /*
     * Not sure how much this method should interact directly with the database
     */
    public void logoutUser() {
        // write new or edited collections to database
        this.loggedIn = false;
    }

    /*
     * get the username; likely for the purpose of reviews
     */
    public String getUser() {
        return this.username;
    }

    /*
     * set the username; alternative method for setting up a user at log in
     */
    public boolean setUser(String username) {
        boolean valid = false;

        // check if username is a valid String, set valid

        if (valid) {
            this.username = username;
        }

        return valid;
    }

    /*
     * get the password; possibly used by login page for writing to the database or checking user against the database
     */
    public String getPassword() {
        return this.password;
    }

    /*
     * set the password; alternative method for setting up a user at log in
     */
    public boolean setPassword(String password) {
        boolean valid = false;

        // check if password is valid String, set valid

        if (valid) {
            this.password = password;
        }

        return valid;
    }

    /*
     * Add a collection to user and database
     */
    // public void addCollection(Collection collection) {
    //    this.collections.add(collection);
    //    // write collection to database, possibly done inside collection class or collection page class instead
    // }

    /*
     * Delete collection
     */
    // public boolean deleteCollection(String id) {
    //    // boolean found = false;
    //    // int index = 0;
    //
    //    // loop through collections to find the relevant one, set found and index
    //
    //    // if (found) {
    //          // this.collections.remove(index)
    //          // deletion in xml will be handled by a different class
    //    // }
    //
    //    // return found;
    // }
}