package game_info;

import java.util.Comparator;

public class SortCollection implements Comparator<Game>{
    private int index;

    /**
     * Default Constructor
     */
    public SortCollection(int index) {
        this.index = index;
    }

    /*
     * compare override for sorting collections of games by different metrics
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
