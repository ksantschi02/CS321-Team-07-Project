import java.util.ArrayList;

/**
 *
 */
public class Collection {

    private int sortType;   //sortType attribute will specify how a collection should be sorted, ????
    private String title;  //title attribute relates to the title of the object
    private ArrayList<Game> games = new ArrayList<Game>();  //games arraylist is a collection/list of the games specified by the user.

    /**
     * Collection constructor
     * @param some_sortType
     * @param some_title
     */
    public Collection(int some_sortType, String some_title)
    {
        this.sortType = some_sortType;
        this.title = some_title;
    }

    /**
     * addGame method adds the specified game to the collection or arraylist of games
     * @param some_game specfied game from user
     */
    public void addGame(Game some_game)
    {
        games.add(some_game);
    }

    /**
     * removeGame method allows the user to edit their collection by removing any unwanted game in their collection
     * @param index parameter gives the location of the game in the collection to be removed
     */
    public void removeGame(int index)
    {
        games.remove(index);
    }

    /**
     *
     * @param from
     * @param to
     */
    public void moveGame(int from, int to)
    {
        //games.set(to, games.get(from));
        /*
        Game temp;
        temp = games.get(from);
        games.
        games.set(from, games.get(to));
        games.set(to, temp);
        */
    } //????

    /**
     *
     * @param some_game
     */
    public void search(String some_game)//?????
    {
        games.contains(some_game);
        //for (Game g : )
    }

    /**
     *
     */
    public void save()  //????????????
    {
        /*
        Write Collection information to file




         */
    }

    /**
     * getTitle returns the title of the user's collection
     * @return
     */
    public String getTitle()
    {
        return this.title;
    }

    /**
     *
     * @return
     */
    public int getSize()
    {
        return games.size();
    }

    /**
     *
     * @param sortType
     */
    public void editSortType(int sortType)
    {
        this.sortType = sortType;
    }

    /**
     *
     * @param title
     */
    public void editTitle(String title) {
        this.title = title;
    }
}
