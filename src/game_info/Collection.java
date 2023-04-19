/**
 *  Collection class
 *  author/programmer: Hugh Vessels
 */
package game_info;

import java.util.ArrayList;
import data_retrieval.Filter;

public class Collection {
    private Filter f1;
    private String title;  //title attribute relates to the title of the object
    private ArrayList<Game> games = new ArrayList<Game>();  //games arraylist is a collection/list of the games specified by the user.

    /**
     * Collection constructor
     * @param some_sortType
     * @param some_title
     */
    public Collection(int some_sortType, int some_filter_type, String some_title, ArrayList<Game> some_games)
    {
        //this.sortType = some_sortType;
        f1 = new Filter(some_sortType,some_filter_type);
        this.title = some_title;
        this.games = some_games;
    }

    public Collection(Collection some_collec)
    {
        this.title = some_collec.title;
        this.f1 = some_collec.f1;

        this.games = new ArrayList<>(some_collec.games.size());

        for (Game g : some_collec.games)
        {
            games.add((Game)g.clone());
        }

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
            games.add(to, temp);
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
        if (some_game.isEmpty()) {
            return this.games;
        }

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
        f1.filterCollection(temp);
        f1.sortCollection(temp);
        return temp;
    }

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
        this.f1.sortType = sortType;
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


    public int getCollectionSortType()
    {
        return f1.getSortType();
    }


    public int getCollectionFilterType()
    {
        return f1.getFilterType();
    }
}
