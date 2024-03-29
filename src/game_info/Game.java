/**
 * Game class
 * author/programmer: Hugh Vessels
 */
package game_info;

import java.text.DecimalFormat;
import java.util.ArrayList;
public class Game implements Cloneable{


    private int id;  // game ID from XML database
    private int minPlayers;  //game minimum suggested number of players
    private int maxPlayers;  //game maximum suggested number of players
    private int minPlaytime;  //game minimum suggested playing time
    private int maxPlaytime;  //game maximum suggested playing time
    private int minAge;   //game minimum age of the players
    private double avgRating;  //game average rating provided from the XML database
    private String title;  //game title from XML database
    private ArrayList<String> genre;   //game genre from XML database
    private String description;  //game description from XML database
    private String image;   //game image from XML database, really the path to the image
    private ArrayList<Review> reviews = new ArrayList<Review>();  //review list for the game

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
     * @param some_reviews list of reviews for the constructed game object
     * @param some_genre list of strings describing each game read in from the XML database
     */
    public Game(int some_id, int some_minPlayers, int some_maxPlayers, int some_minPlaytime,
                int some_maxPlaytime, int some_minAge, double some_avgRating, String some_title, ArrayList<String> some_genre,
                String some_description, String some_image, ArrayList<Review> some_reviews)
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
        this.description = some_description;
        this.image = some_image;
        this.reviews =  some_reviews;


    }

    /**
     * MyDateList explicitly defined clone function, second way to perform deep copies
     * @return the deep copy of MyDateList object, overwriting built-in clone function
     */
    public Game clone()
    {
        //use try-catch statement to see if the Cloneable interface is able to be implemented
        try{
            Game temp =  (Game) super.clone(); //created a temp variable of MyDateList type
            temp.reviews = new ArrayList<Review>();  //create an arraylist for temp.events

            //loop through all events in the current object and add them to the array list for the temp variable
            //for loop performs deep copies of the elements in the events arraylist
            for (Review r: this.reviews)
            {
                temp.reviews.add(new Review(r));  //deep copy the current event from the curren object
            }
            //temp.description = this.description;  //strings are immutable, copy paramaters description
            return temp;        //return temp as the deep copy of the current MyDateList object that clone was called on

        } catch (CloneNotSupportedException e) { //catch if clone is not supported
            return null;        //returns null when clone does not work
        }

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
     * method used to determine the average rating for a game
     * @return average rating that is returned is dependent on the number of reviews and the ratings for each review of this game
     */
    public double getAvgRating() {
        double rating = 0;   //temp value used to get rating

        //for loop reads in the reviews and sums up the ratings
        for(int i = 0; i < reviews.size(); i++)
        {
            rating =  rating + reviews.get(i).getRating() ;  //sum the ratings
        }
        rating = rating/reviews.size();       //divide by size or number of reviews to get average rating
        rating = Math.round((rating*100)/100);      //round average
        avgRating = rating;     //set current object average rating attribute to the one calculated
        return avgRating;       //return the average rating
    }

    /**
     * method used to get the title of a game
     * @return title is the title of the current game object
     */
    public String getTitle() {
        return title;
    }

    /**
     * method used to get the list of genres for a game
     * @return genre ArrayList holds strings, which are genres that is being returned
     */
    public ArrayList<String> getGenre()
    {
        return genre;
    }

    /**
     *method used to get the description of the game
     * @return the description of the current game object
     */
    public String getDescription() {
        return description;
    }

    /**
     * method used to get the image of the game
     * @return the image, which is a string/path to the image, for the current game object
     */
    public String getImage() {
        return image;
    }

    /**
     * method used to determine the minimum playing time for a game
     * @return minPlaytime is the minimum playing time suggested for the current game object
     */
    public int getMinPlaytime() {return minPlaytime;}

    /**
     * method used to determine the maximum playing time for a game
     * @return maxPlaytime is the maximum playing time suggested for the current game object
     */
    public int getMaxPlaytime() {return maxPlaytime;}

    /**
     * method used to access all the reviews for a game
     * @return reviews is an ArrayList of Review objects that holds reviews for the current game object
     */
    public ArrayList<Review> getReviews() {return reviews;}

}

