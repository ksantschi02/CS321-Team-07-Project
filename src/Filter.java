import java.util.ArrayList;
public class Filter {
    private int sortType;
    private int filterType;

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
        // if chain to determine method, loop through collection and sort games by specifications
    }

    /*
     * filter a collection by initialized type
     */
     public void filterCollection(ArrayList<Game> collection) {
        // if chain to determine method, loop through collection and filter out games by specification
     }

    /*
     * set the sort type
     */
    public boolean editSort(int sortType) {
        boolean valid = false;

        // check if passed value is a valid sort type index, set valid

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

        // check if passed value is a valid filter type index, set valid

        if (valid) {
            this.filterType = filterType;
        }

        return valid;
    }
}
