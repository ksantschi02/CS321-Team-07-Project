/**
 *
 */
import java.util.ArrayList;
public class Game {



    private int id;  // game ID from XML database
    private int minPlayers;  //game minimum suggested number of players
    private int maxPlayers;  //game maximum suggested number of players
    private int minPlaytime;  //game minimum suggested playing time
    private int maxPlaytime;  //game maximum suggested playing time
    private int minAge;   //game minimum age of the players
    private double avgRating;  //game average rating provided from the XML database
    private String title;  //game title from XML database
    private String genre;   //game genre from XML database
    private String description;  //game description from XML database
    private String image;   //game image from XML database, really the path to the image
    ArrayList<Review> reviews = new ArrayList<Review>();  //review list for the game

    /**
     * Game constructor that constructs a game with the given attributes
     * @param some_id game id parameter that comes from Game data in the database
     * @param some_minPlayers suggested number of minimum players that was read in from the XML database
     * @param some_maxPlayers suggested number of maximum players that was read in from the XML database
     * @param some_minPlaytime suggested number of minimum playing time that was read in from the XML database
     * @param some_maxPlaytime suggested number of maximum playing time that was read in from the XML database
     * @param some_minAge suggested number of minimum age of the players that was read in from the XML database
     * @param some_avgRating game's average rating that was read in from the XML database
     * @param some_title game's title/name that was read in from the XML database
     * @param some_description suggested number of minimum players that was read in from the XML database
     * @param some_image suggested number of minimum players that was read in from the XML database
     */
    public Game(int some_id, int some_minPlayers, int some_maxPlayers, int some_minPlaytime,
                int some_maxPlaytime, int some_minAge, double some_avgRating, String some_title, String some_genre,
                String some_description, String some_image)   //????? genre
    {
        //the following initializes the game object to that of the game that was read from the database
        this.id = some_id;
        this.minPlayers = some_minPlayers;
        this.maxPlayers = some_maxPlayers;
        this.minPlaytime = some_minPlaytime;
        this.maxPlaytime = some_maxPlaytime;
        this.minAge = some_minAge;
        this.avgRating = some_avgRating;
        this.title = some_title;
        this.genre = some_genre;
        this.description =some_description;
        this.image = some_image;


    }

    /**
     * addReview method that adds a review for the currently pointed to game
     * @param review holds the content of the user's review for the current game object
     */
    public void addReview(Review review)
    {
        reviews.add(review);
    }  //add review to review arraylist

    /**
     * removeReview method that removes a user's review from the current game object
     * @param index is the index of the review that will be removed
     */
    public void removeReview(int index)
    {
        reviews.remove(index);
    }  //remove review of game at the specified index in the arraylist

    /**
     * getId method returns the game Id of the current game object
     * @return the id for the specified game
     */
    public int getId() {
        return id;
    } //observer method for a game's id


    /**
     *getMinPlayers method returns the game minimum suggested number of players of the current game object
     * @return the minPlayers attribute for the specified game
     */
    public int getMinPlayers() {
        return minPlayers;
    }  //observer method for the minimum number of players

    /**
     * getMaxPlayers method returns the game maximum suggested number of players of the current game object
     * @return the maxPlayers attribute for the specified game
     */
    public int getMaxPlayers() {
        return maxPlayers;
    } //observer method for the maximum number of players

    /**
     * getMinAge method provides information on the suggested minimum age of a player for the current game object
     * @return the suggested minimum age of a player
     */
    public int getMinAge() {
        return minAge;
    }  //observer method for the minimum age of a player

    /**
     *
     * @return
     */
    public double getAvgRating() {
        return avgRating;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return
     */
    public String getGenre()
    {
        return genre;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public String getImage() {
        return image;
    }

}

