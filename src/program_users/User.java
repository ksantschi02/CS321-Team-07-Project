package program_users;

import java.util.ArrayList;
import game_info.*;

/**
 * The User class is populated by the Login Page with the username, password,
 * and collections of the current User, and is used by other pages to populate themselves.
 * @author Jacob Warren
 */
public class User {
    private String username; // username of the current user
    private String password; // password of the current user
    private ArrayList<Collection> collections; // all collections created and saved by the user

    /**
     * Copy constructor for User
     * @param source the source User to be copied
     */
    public User(User source) {
        this.username = source.getUser();
        this.password = source.getPassword();

        this.collections = new ArrayList<>();
        for (Collection C : source.getCollections()) {
            this.collections.add(new Collection(C));
        }
    }

    /**
     * Creates User at login
     * @param username the username of the logging in user
     * @param password the password of the logging in user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        collections = new ArrayList<>();
    }

    /**
     * get the username
     * @return the username
     */
    public String getUser() {
        return this.username;
    }

    /**
     * get the password
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Add a collection to user
     * @param collection the Collection being added to users collections
     */
    public void addCollection(Collection collection) {
        this.collections.add(collection);
    }

    /**
     * get a collection from this user
     * @param id title of the sought after collection
     * @return the collection that was found or a null collection
     */
    public Collection getCollection(String id) {
        Collection collection = new Collection(0, 0, null, new ArrayList<>());

        if(this.collections != null) {
            for (Collection c : this.collections) {
                if (c.getTitle().equals(id)) {
                    collection = c;
                    break;
                }
            }
        }
        else {
            collection = new Collection(0, 0, null, new ArrayList<>());
        }

        return collection;
    }

    /**
     * get all collections from this user
     * @return the users collections
     */
    public ArrayList<Collection> getCollections() {
        return this.collections;
    }
}
