//Gabriel Meriano, Garrison Hollis
/*
 * This very simple class represents an Attack in a Battleship game.  An Attack is just a board location,
 * like "E5".  Since we represent both rows and columns as integers starting from 0, "E5" would be
 * an Attack object with row and column both set to 4.  Don't worry about that offset yet... we should be
 * consistent about numbering from 0-9 everywhere except possibly the user interface.
 */
public class Attack {
/*
     * Declare two instance variables "row" and "column".  Both should be private ints.\]
     * 
     * Then write accessors getRow and getColumn.  As usual, these are simple public methods.  Accessors should
     * never change any state (ie. never modify instance variables).
     */
    
    
    // Declare instance variables
    private int row; 
    private int column; 
    
    // Write accessor methods
    public int getRow(){
        return row; 
    }
    
    public int getColumn(){
        return column; 
    }
    
    /*
     * Now write a constructor for Attack that takes two arguments "r" and "c" and initializes the
     * instance variables accordingly.
     */
    // Write the constructor
    
    public Attack(int r, int c){
        row = r; 
        column = c; 
    }
    
    /*
     * For testing and debugging, we'll also want to be able to represent Attacks as Strings.  Write
     * a method that returns a concise String representing the Attack.  This isn't intended for the user
     * interface, but for debugging.  So it should contain all the relevant info, but we won't worry
     * about it being pretty.
     * 
     * Suggested output:
     * 
     * <<<Attack: (0,5)>>>
     */
    public String toString() {        
        return "<<<Attack: (" + getRow() + "," + getColumn() + ")>>>";
    }

}
