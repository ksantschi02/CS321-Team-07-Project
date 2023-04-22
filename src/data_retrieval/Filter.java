package data_retrieval;

import java.util.ArrayList;
import java.util.Collections;
import game_info.*;

/**
 * The Filter class is wholly used by the Collection class for the purpose
 * of storing sort type and filter type and the relevant methods.
 * @author Jacob Warren
 */
public class Filter {
    public int sortType; // the sorting method index
    public int filterType; // the filtering method index

    /**
     * Constructor called for individual collection
     * @param sortType the sorting method index
     * @param filterType the filtering method index
     */
    public Filter(int sortType, int filterType) {
        this.sortType = sortType;
        this.filterType = filterType;
    }

    /**
     * sort a collection by initialized type
     * @param collection the games in a Collection to be sorted
     */
    public void sortCollection(ArrayList<Game> collection) {
        //Collections.sort(collection, new SortCollection(sortType));
    }

    /**
     * filter a collection by initialized type
     * @param collection the games in a Collection to be filtered
     */
     public void filterCollection(ArrayList<Game> collection) {
         // min players
         if (filterType == 1) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMinPlayers() > 1) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 2) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMinPlayers() > 2) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 3) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMinPlayers() > 3) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 4) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMinPlayers() > 4) {
                     collection.remove(i);
                 }
             }
         }
         // max players
         else if (filterType == 5) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMaxPlayers() < 1) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 6) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMaxPlayers() < 2) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 7) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMaxPlayers() < 3) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 8) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMaxPlayers() < 4) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 9) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMaxPlayers() < 5) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 10) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMaxPlayers() < 6) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 11) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMaxPlayers() < 7) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 12) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMaxPlayers() < 8) {
                     collection.remove(i);
                 }
             }
         }
         // min age
         else if (filterType == 13) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMinAge() > 3) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 14) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMinAge() > 7) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 15) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMinAge() > 12) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 16) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (collection.get(i).getMinAge() > 16) {
                     collection.remove(i);
                 }
             }
         }
         // genre
         else if (filterType == 17) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Card Game")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 18) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Science Fiction")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 19) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Economic")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 20) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Adventure")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 21) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Educational")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 22) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Deduction")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 23) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Exploration")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 24) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Animals")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 25) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Movies / TV / Radio Theme")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 26) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Fantasy")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 27) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Fighting")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 28) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Civilization")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 29) {
             for (int i = collection.size() - 1; i >= 0; i--) {
                 if (!collection.get(i).getGenre().contains("Puzzle")) {
                     collection.remove(i);
                 }
             }
         }
     }

    /**
     * set the sort type
     * @param sortType the new sort type
     * @return whether the sort type was edited
     */
    public boolean editSort(int sortType) {
        boolean valid = sortType >= 0 && sortType <= 6;

        if (valid) {
            this.sortType = sortType;
        }

        return valid;
    }

    /**
     * set the filter type
     * @param filterType the new filter type
     * @return whether the filter type was edited
     */
    public boolean editFilter(int filterType) {
        boolean valid = filterType >= 0 && filterType <= 29;

        if (valid) {
            this.filterType = filterType;
        }

        return valid;
    }

    /**
     * Get the sort type
     * @return the current sort type
     */
    public int getSortType () {
        return this.sortType;
    }

    /**
     * Get the filter type
     * @return the current filter type
     */
    public int getFilterType () {
        return this.filterType;
    }
}
