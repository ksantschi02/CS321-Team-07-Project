import java.util.ArrayList;
import java.util.Collections;

public class Filter {
    protected int sortType;
    protected int filterType;

    /*
     * Constructor called for individual collection
     */
    public Filter(int sortType, int filterType) {
        this.sortType = sortType;
        this.filterType = filterType;
    }

    /*
     * sort a collection by initialized type
     */
    public void sortCollection(ArrayList<Game> collection) {
        Collections.sort(collection, new SortCollection(sortType));
    }

    /*
     * filter a collection by initialized type
     */
     public void filterCollection(ArrayList<Game> collection) {
         // default
         if (filterType == 0) {}
         // min players
         else if (filterType == 1) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMinPlayers() > 1) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 2) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMinPlayers() > 2) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 3) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMinPlayers() > 3) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 4) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMinPlayers() > 4) {
                     collection.remove(i);
                 }
             }
         }
         // max players
         else if (filterType == 5) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMaxPlayers() != 1) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 6) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMaxPlayers() < 2) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 7) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMaxPlayers() < 3) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 8) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMaxPlayers() < 4) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 9) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMaxPlayers() < 5) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 10) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMaxPlayers() < 6) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 11) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMaxPlayers() < 7) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 12) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMaxPlayers() < 8) {
                     collection.remove(i);
                 }
             }
         }
         // min age
         else if (filterType == 13) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMaxPlayers() > 3) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 14) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMaxPlayers() > 7) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 15) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMaxPlayers() > 12) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 16) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (collection.get(i).getMaxPlayers() > 16) {
                     collection.remove(i);
                 }
             }
         }
         // genre
         else if (filterType == 17) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (!collection.get(i).getGenre().equals("family")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 18) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (!collection.get(i).getGenre().equals("dexterity")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 19) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (!collection.get(i).getGenre().equals("party")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 20) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (!collection.get(i).getGenre().equals("abstract")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 21) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (!collection.get(i).getGenre().equals("thematic")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 22) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (!collection.get(i).getGenre().equals("euro")) {
                     collection.remove(i);
                 }
             }
         }
         else if (filterType == 23) {
             for (int i = 0; i < collection.size(); i++) {
                 if (i >= collection.size()) {
                     break;
                 }
                 if (!collection.get(i).getGenre().equals("war")) {
                     collection.remove(i);
                 }
             }
         }
     }

    /*
     * set the sort type
     */
    public boolean editSort(int sortType) {
        boolean valid = false;

        if (sortType >= 0 && sortType <= 6) {
            valid = true;
        }

        if (valid) {
            this.sortType = sortType;
        }

        return valid;
    }

    /*
     * set the filter type
     */
    public boolean editFilter(int filterType) {
        boolean valid = false;

        if (filterType >= 0 && filterType <= 24) {
            valid = true;
        }

        if (valid) {
            this.filterType = filterType;
        }

        return valid;
    }

    /*
     * Get the sort type
     */
    public int getSortType () {
        return this.sortType;
    }

    /*
     * Get the filter type
     */
    public int getFilterType () {
        return this.filterType;
    }
}
