package program_users;

import java.util.ArrayList;
import game_info.*;

public class User {
    private String username;
    private String password;
    private ArrayList<Collection> collections;

    /**
     * Copy constructor for User
     */
    public User(User source) {
        this.username = source.getUser();
        this.password = source.getPassword();

        this.collections = new ArrayList<>();
        for (Collection C : source.getCollections()) {
            this.collections.add(new Collection(C));
        }
    }

    /*
     * Creates User at login
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        collections = new ArrayList<>();
    }

    /*
     * get the username
     */
    public String getUser() {
        return this.username;
    }

    /*
     * set the username
     */
    public boolean setUser(String username) {
        boolean valid = false;

        if (valid) {
            this.username = username;
        }

        return valid;
    }

    /*
     * get the password
     */
    public String getPassword() {
        return this.password;
    }

    /*
     * set the password
     */
    public boolean setPassword(String password) {
        boolean valid = false;

        if (valid) {
            this.password = password;
        }

        return valid;
    }

    /*
     * Add a collection to user
     */
    public void addCollection(Collection collection) {
        this.collections.add(collection);
    }

    /*
     * get a collection from this user
     */
    public Collection getCollection(String id) {
        Collection collection = new Collection(0, 0, null, new ArrayList<Game>());

        if(this.collections != null) {
            for (Collection c : this.collections) {
                if (c.getTitle().equals(id)) {
                    collection = c;
                    break;
                }
            }
        }
        else if(this.collections == null)
        {
            collection = new Collection(0, 0, null, new ArrayList<Game>());
        }

        return collection;
    }

    /*
     * get all collections from this user
     */
    public ArrayList<Collection> getCollections() {
        return this.collections;
    }

    /*
     * Delete collection
     */
    public boolean deleteCollection(String id) {
        boolean found = false;

        if(this.collections != null) {
            for (Collection c : this.collections) {
                if (c.getTitle().equals(id)) {
                    collections.remove(c);
                    found = true;
                    break;
                }
            }
        }

        return found;
    }
}
