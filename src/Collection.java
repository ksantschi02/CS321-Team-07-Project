/**
 *  Collection class
 *  author/programmer: Hugh Vessels
 */
import java.util.ArrayList;
public class Collection {
    private int sortType;   //sortType attribute will specify how a collection should be sorted, ????
    private String title;  //title attribute relates to the title of the object
    private ArrayList<Game> games = new ArrayList<Game>();  //games arraylist is a collection/list of the games specified by the user.

    /**
     * Collection constructor
     * @param some_sortType
     * @param some_title
     */
    public Collection(int some_sortType, String some_title, ArrayList<Game> some_games)
    {
        this.sortType = some_sortType;
        this.title = some_title;
        this.games = some_games;
    }

    /**
     * addGame method adds the specified game to the collection or arraylist of games
     * @param some_game specified game from user
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
        Game temp;
        temp = games.get(from);
        if(from < to)
        {

            games.remove(games.get(from));
            games.add(to-1, temp);
        }
        else if (to<from)
        {

            games.remove(games.get(from));
            games.add(to, temp);
        }
    }

    /**
     *
     * @param some_game
     */
    public ArrayList<Game> search(String some_game)
    {
        ArrayList <Game> temp = new ArrayList<Game>();
        //games.contains(some_game);  //case sensitivity?
        String start_char = "!";
        String search_temp;
        some_game = start_char.concat(some_game.toLowerCase());
        for (Game g: games)
        {
            search_temp = start_char.concat(g.getTitle().toLowerCase());

            if(search_temp.contains(some_game))
            {
                temp.add(g);
            }
        }
        return temp;
    }


    /**
     *
     */




    /**
     * getTitle returns the title of the user's collection
     * @return
     */
    public String getTitle()
    {
        return title;
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

    public ArrayList<Game> getGames()
    {
        return this.games;
    }
}
