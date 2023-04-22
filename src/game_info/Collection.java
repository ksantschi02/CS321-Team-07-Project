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
     * @param some_sortType passes in the sort type of the collection
     * @param some_title passes in the title for the collection
     * @param some_filter_type passes in the filter type for the colleciton
     * @param some_games passes in the games for the collection
     */
    public Collection(int some_sortType, int some_filter_type, String some_title, ArrayList<Game> some_games)
    {
        f1 = new Filter(some_sortType,some_filter_type);  //create a new filter

        //initialize collection class attributes
        this.title = some_title;
        this.games = some_games;
    }

    /**
     *Copy constructor
     * @param some_collec passes in another collection to perform deepy copying on
     */
    public Collection(Collection some_collec)
    {
        //set current object attributes to collection object that is pases in
        this.title = some_collec.title;  //set title to new title
        this.f1 = some_collec.f1; //set filter to new filter

        this.games = new ArrayList<>(some_collec.games.size());  //creat a list for games

        //load/set new games
        for (Game g : some_collec.games)
        {
            games.add((Game)g.clone());  //deep copy
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
     * Method that adds unique feature to our code, to where games can be moved or ranked in collection
     * @param from  passes in the location of where a games is currently
     * @param to passes in the location of where said game wants to go
     */
    public void moveGame(int from, int to)
    {
        Game temp;        //temp game
        temp = games.get(from);   //get location for temp game

        //if and if else statement determines if the game is supposed to go up or down the game list
        //for the collection
        if(from < to)
        {
            games.remove(games.get(from));     //create space for game to move to
            games.add(to, temp);               //move game into spot in collection
        }
        else if (to<from)
        {

            games.remove(games.get(from));   //create space for game to move to
            games.add(to, temp);      //move game into spot in collection
        }
    }

    /**
     * method used as an additional feature, allowing the user to search for games in the collection
     * @param some_game pass the substring that will be used to compare to the title of the other games
     * @return temp, a list of games that matches the search entry
     */
    public ArrayList<Game> search(String some_game)
    {
        //make sure string is not empty
        if (some_game.isEmpty()) {
            return this.games; //is so return all games in the collection
        }

        ArrayList <Game> temp = new ArrayList<>();  //create a temp array list of game that
        String start_char = "!";  //start or append character used in searching
        String search_temp; // declare a temp search string

        some_game = start_char.concat(some_game.toLowerCase()); //make sure passed in string is lower case
                                                                //to make searching easier
        //for loop performs searching
        for (Game g: games)
        {
            search_temp = start_char.concat(g.getTitle().toLowerCase()); //load in lower case title of game in collection

            //check if game title contains the passed in string
            if(search_temp.contains(some_game))
            {
                temp.add(g);    //if so, add to our temporary list of game matches
            }
        }
        f1.filterCollection(temp); //use filter method to filter out games
        f1.sortCollection(temp);  //use filter method to sort the result
        return temp;  //return the list of games
    }

    /**
     * getTitle returns the title of the user's collection
     * @return the title current collection object
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * method used in determining the number of games in the collection
     * @return the size of collection games
     */
    public int getSize()
    {
        return games.size();
    }

    /**
     * Set method used to edit the sort type for the collection of games
     * @param sortType passes in the new sort type to be used on the collection
     */
    public void editSortType(int sortType)
    {
        this.f1.sortType = sortType;
    }

    /**
     *Set method used to edit the filter type for the collection of games
     * @param filterType passes in the new filter type to be used on the collection
     */
    public void editFilterType(int filterType)
    {
        this.f1.filterType = filterType;
    }

    /**
     * Set method used to edit the title of the collection
     * @param title passes in the new title used for the current collection
     */
    public void editTitle(String title) {
        this.title = title;
    }


    /**
     * method used to determine what games are inside of the collection
     * @return the ArrayList of object Game filled with board games called belonging to this collection object
     */
    public ArrayList<Game> getGames() {return this.games;}

    /**
     * method to use to determine the sort type of the filter being used on the collection
     * @return the sort type that is being used to sort the games in the collection
     */
    public int getCollectionSortType() {return f1.getSortType();}

    /**
     *  method to use to determine what type of filter the collection has
     * @return the collection filter type used in filter the collection's games
     */
    public int getCollectionFilterType() {return f1.getFilterType();}

    /**
     *  method to use to determine what filter the collection has
     * @return the filter being use by the current collection object
     */
    public Filter getFilter() {return f1;}
}
