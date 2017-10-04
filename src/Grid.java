/* Gabriel Meriano, Garrison Hollis
 * (Don't neglect to replace these comments with your own as you work through the Grid class.)
 * 
 * The next class to write is Grid.  This is a utility class that the game AI will use to track
 * its opponent's game board.
 */
public class Grid {  
    /*
     * Constants used to formalize the status of each square in the grid
     */  
    public static final int UNKNOWN  = 0;  // The status at coordinates we have not yet attacked
    public static final int HIT      = 1;  // The status at coordinates where our attack has HIT
    public static final int MISS     = 2;  // The status at coordinates where our attack has MISSED
    public final int GRIDSIZE = 10; // Constant for now, but we could play on a larger grid than 10x10...

    /*  array holding status of each grid location   */  

    /*
     * You'll need to create an instance variable to hold the current status at each (row,col) coordinate.
     * Call the instance variable "grid".  Since it is indexed by row and column (two integers), it will be
     * a two-dimensional array.  The array holds status identifiers, which are defined by the constants
     * above: UNKNOWN, HIT and MISS.  That should tell you what type the array elements hold.
     * 
     * You don't need to initialize the array here; you'll do that in the constructor below.
     */

    // Declare the instance variable "grid" here
    public int[][] grid = new int[GRIDSIZE][GRIDSIZE];
    /*
     * Now write a constructor for Grid objects.
    */
    public Grid() {
        for (int row = 0; row < GRIDSIZE; row++)
            for (int col = 0; col < GRIDSIZE; col++)
                grid[row][col] = UNKNOWN;
    }
    /*
     * You don't need any information to create a Grid.  (They are all the same fixed size, and we'll always
     * initialize them so that all coordinates start out UNKNOWN.)  So the constructor doesn't need any arguments.
     * 
     * The constructor does need to create the array held in the "grid" instance variable.  Also, if necessary,
     * this would be the place to initialize array values.
     */

    // Write the zero-argument constructor for Grid here.
    
    /*
     * This method generates and returns a String representing the current state of a Grid object.
     * As before, don't do any I/O here... just return the String.  The caller can choose how to use that
     * String, which may include printing output to the console.
     * 
     * To be readable, this String should be multi-line, with one line for each row in the grid.
     * Here is an example of reasonable output, using "HIT", "---" and "???" to represent HIT, MISS,
     * and UNKNOWN values in the grid.
     * 
     *    HIT    ---    ???    ???    ???    ???    ???    ???    ???    ???
     *    ???    ???    ???    ???    ???    ???    ???    ???    ???    ???
     *    ???    ???    ???    ???    ???    ???    ???    ???    ???    ???
     *    ???    ???    ???    ???    ???    ???    ???    ???    ???    ???
     *    ???    ???    ???    ???    ???    ???    ???    ???    ???    ???
     *    ???    ???    ???    ???    ???    ???    ???    ???    ???    ???
     *    ???    ???    ???    ???    ???    ???    ???    ???    ???    ???
     *    ???    ???    ???    ???    ???    ???    ???    ???    ???    ???
     *    ???    ???    ???    ???    ???    ???    ???    ???    ???    ???
     *    ???    ???    ???    ???    ???    ???    ???    ???    ???    ???
     */

    public String toString() {   
        String result = "";
        for (int row = 0; row < GRIDSIZE; row++) {
            for (int col = 0; col < GRIDSIZE; col++) {
                String status = "";
                if (grid[row][col] == 0)
                    status = " ??? ";
                else if (grid[row][col] == 1)
                    status = " HIT ";
                else if (grid[row][col] == 2)
                    status = " --- ";
                result += status;
            }
            result += "\n";
        }
        // Generate a reasonable String representing Grid contents.
        return result;
    }

    /*
     *  Write a method thats sets a hit or miss in the grid.  This is done by modifying this object's
     *  array instance variable.  Specifically, modifying the corresponding array cell.
     *  
     *  The caller of this method should check to make sure it's passing valid row and column values.
     */
    public void set(int row, int col, int status) {
        if (row < GRIDSIZE && row >= 0 && col < GRIDSIZE && col >= 0)
            grid[row][col] = status;
    }

    /*
     *  Write a method that allows you to see the value of a particular point on the grid.
     *  
     *  This is similar to the method above, which changes a value.  Here we read and return a
     *  value instead.
     *  
     *  The caller of this method should check to make sure it's passing valid row and column values.
     *  Optionally, you can check here and return an invalid status if they are not valid.
     */

    public int peek(int row, int col) {
        if (row <= GRIDSIZE && row >= 0 && col <= GRIDSIZE && col >= 0)
            return grid[row][col];
        return -1;
    }

}

