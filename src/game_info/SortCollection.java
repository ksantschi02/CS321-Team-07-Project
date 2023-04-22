package game_info;

import java.util.Comparator;
/**
 * The SortCollection class implements the compare method from Comparator<Game>
 * for use in the Collection sorting method in the Filter class.
 * @author Jacob Warren
 */
public class SortCollection implements Comparator<Game>{
    private int index; // used for determining the method of sorting used in the compare method

    /**
     * Constructor
     * @param index the index of the sorting method
     */
    public SortCollection(int index) {
        this.index = index;
    }

    /**
     * compare override for sorting collections of games by different metrics
     * @param gameA Game to be compared
     * @param gameB Game to be compared
     */
    public int compare(Game gameA, Game gameB) {
        // default
        if (index == 0) {return 0;}
        // average rating
        else if (index == 1) {return -Double.compare(gameA.getAvgRating(), gameB.getAvgRating());}
        else if (index == 2) {return Double.compare(gameA.getAvgRating(), gameB.getAvgRating());}
        // title
        else if (index == 3) {return gameA.getTitle().compareToIgnoreCase(gameB.getTitle());}
        else if (index == 4) {return -gameA.getTitle().compareToIgnoreCase(gameB.getTitle());}
        // average of min and max playtime
        else if (index == 5) {return Double.compare(((double)gameA.getMinPlaytime() + (double)gameA.getMaxPlaytime()) / 2.0, ((double)gameB.getMinPlaytime() + (double)gameB.getMaxPlaytime()) / 2.0);}
        else if (index == 6) {return -Double.compare(((double)gameA.getMinPlaytime() + (double)gameA.getMaxPlaytime()) / 2.0, ((double)gameB.getMinPlaytime() + (double)gameB.getMaxPlaytime()) / 2.0);}
        else {return 0;}
    }
}
