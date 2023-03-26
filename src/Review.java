/**
 *
 */
public class Review {


private double rating;  // numerical rating of game, 10
private String content;  // content of review that the user will be specifying
private User author;   //username that is providing the review

    /**
     *
     * @param some_rating
     * @param some_content
     * @param some_author
     */
    public Review(double some_rating, String some_content, tempUser some_author)
{
    this.rating = some_rating;
    this.content = some_content;
    this.author = some_author;
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
    public tempUser getAuthor() {
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
     * editContent method allows the user to edit the content of their review for the current game object
     * @param content contains the text for the review, written by user
     */
    public void editContent(String content) {
        this.content = content;  //accessor method
    }
}
