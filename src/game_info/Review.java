/**
 * Review class
 * author/programmer: Hugh Vessels
 */
package game_info;

public class Review{

private double rating;  // numerical rating of game, 10
private String content;  // content of review that the user will be specifying
private String author;   //username that is providing the review

    /**
     * Review constructor
     * @param some_rating  pass in rating for game
     * @param some_content  passes in content of review for game
     * @param some_author  passes in author of the review for game
     */
    public Review(double some_rating, String some_content, String some_author)
{
    //the following sets all class attributes to the one passed in the constructor
    this.rating = some_rating;
    this.content = some_content;
    this.author = some_author;
}

    /**
     * Copy constructor
     * @param r1 passes in other review object to be copied
     */
    public Review (Review r1)
{
    //initialize all current object attributes to the ones passed in from the other review object
    this.rating = r1.rating;
    this.content = r1.content;
    this.author = r1.author;
}

    /**
     * getRating method allows for other to be able to see the rating/score for each review
     * @return returns the rating for the current review object
     */
    public double getRating() {
        return rating;
    }

    /**
     * getAuthor method allows for others to see which user left certain reviews
     * @return returns the name of the author for the specified game
     */
    public String getAuthor() {
        return author;  //observor method
    }

    /**
     * editaRating method allows the user to edit their score they gave to a specific game
     * @param rating contains the rating for the game that was given by the user
     */
    public void editRating(double rating)
    {
        this.rating = rating;   //accessor method
    }

    /**
     * method used to get the content of the review
     * @return content of the review for the current review object
     */
    public String getContent()
    {
        return content;
    }

    /**
     * editContent method allows the user to edit the content of their review for the current game object
     * @param content contains the text for the review, written by user
     */
    public void editContent(String content) {
        this.content = content;  //accessor method
    }
}
