/*
 * Garrison Hollis, Gabriel Meriano
 * 
 * As you write the methods below, replace the instruction comments with reasonable code comments.
 * 
 */

class Ship {
    private String shipName; 
    private int shipSize; 
    private boolean hasBeenHit[]; 
    private boolean isVertical; 
    private int row; 
    private int column; 
    private boolean hasBeenSunk;

    /*
     * Declare intance variables.  For each of these, figure out what their type should be, and ask if you
     * get stuck or have no idea.  You should declare all instance variables private and use accessors and
     * mutators to view or change them.
     * 
     * The instance variables you'll need:
     * 
     * name - holds the name of the ship type, "PT" "SUBMARINE", etc.
     * size - holds the size (length) of the ship, between 2 and 5.
     * hasBeenHit[] - an array whose length is equal to "size".  Holds true for every part of the
     *   ship that has been hit, and false otherwise.  Indexes should start with 0.
     * isVertical - true if the ship is placed vertically on the board; false if horizontal.
     * row - 0-9 corresponding to row A-J.  For vertical ships, holds the topmost row occupied.
     * column - 0-9 corresponding to the ship's column.  For horizontal ships, the leftmost row occupied.
     */

    // You declare the rest, using the variable names above.  You don't need to initialize their values here.

    /*
     * Now write accessors and mutators for the instance variables, as appropriate.  Instance variables
     * don't need mutators if they can't change after an object is constructed.
     * 
     * Here's the first accessor for free.
     */

    /*
     * Accessor for instance variable name
     */
    public String getName() {
        return shipName;
    }

    public void setName(String a){
        shipName = a; 
    }
    
    public void setHasBeenSunk(boolean b) {
    	hasBeenSunk = b;
    }

    public int getRow(){
        return row; 
    }

    public int getCol(){
        return column; 
    }    

    public int getSize(){
        return shipSize; 
    }

    public boolean getHasBeenHit(int a){
        return hasBeenHit[a];
    }

    public void setHasBeenHit(int a){
        hasBeenHit[a] = true; 
    }

    public boolean getIsVertical(){
        return isVertical; 
    }
    
    public boolean getHasBeenSunk(){
    	return hasBeenSunk;
    }

    /* 
     * You write the rest: getSize, getHasBeenHit, etc.
     * 
     * A hint: getHasBeenHit shouldn't return an array.  Instead it should take an index as a parameter,
     * and return true or false depending on whether the specified part of the ship has been hit, which is
     * what should be stored in the array at that index.
     * 
     * Another hint: Size and location should not change during the life of a Ship object, so the only
     * mutator needed will be called setHasBeenHit.  Similar to getHasBeenHit, it will take an index number
     * as a parameter, and will set the corresponding value at that index only.
     */

    /*
     * Now create a constructor.  A Ship can be created if you know its location, size, and name.  The
     * location requires the row and column coordinates of its first grid location, and whether it is
     * vertical or horizontal.
     */

    public Ship(int r, int c, boolean vert, int sz, String nm) {
        row = r; 
        column = c; 
        isVertical = vert; 
        shipSize = sz; 
        shipName = nm; 
        hasBeenHit = new boolean[shipSize]; 
        for(int i = 0; i < shipSize; i++){
            hasBeenHit[i] = false; 
        }        
        hasBeenSunk = false;
        /*
         * This constructor has two basic jobs.
         * (1) Create anything needed with "new".  In this case, there is one array to be created.
         * (2) Initialize every instance variable correctly from the constructor's parameters.
         */
    }

    /*  
     * Create a method to check if this ship is sunk by checking if all squares are hit.
     * 
     * If ANY value in the instance variable array hasBeenHit[] is false, then this should return false.
     * Conversely, if ALL values in that array are true, then this should return true.
     * 
     * If you've played Battleship, this should make sense... a ship is sunk only once all of its parts
     * have been hit.
     */  

    public boolean checkSunk() {
        /* This is a "stub".  It's incorrect placeholder code, which is here so the class will compile.
         * Replace it with correct code.
         */
    	//Checks if a ship is sunk based on its shipSize
        for(int i = 0; i < shipSize-1; i++){
            if (shipSize == 2)
            	if (hasBeenHit[0] == false || hasBeenHit[1] == false) return false;
            if (shipSize == 3)
            	if (hasBeenHit[0] == false || hasBeenHit[1] == false || hasBeenHit[2] == false) return false;
            if (shipSize == 4)
            	if (hasBeenHit[0] == false || hasBeenHit[1] == false || hasBeenHit[2] == false || hasBeenHit[3] == false) return false;
            if (shipSize == 5)
            	if (hasBeenHit[0] == false || hasBeenHit[1] == false || hasBeenHit[2] == false || hasBeenHit[3] == false || hasBeenHit[4] == false) return false;
        }
        return true; 
    }

    /*  
     * Create a method that returns true if the coordinates hit this ship.
     * 
     * First look at whether the ship is vertical or horizontal.  This object's instance variables
     * have that information, as well as the row and column coordinates of the upper-left-most part
     * of this ship.  With that you should be able to determine whether the row and column coordinates
     * in the method arguments will collide with the ship.
     */  

    public boolean checkHit(int r, int c) {   

        /* This is a "stub".  It's incorrect placeholder code, which is here so the class will compile.
         * Replace it with correct code.
         */
        if(isVertical){
            if(c == column){ //If it is vertical then the attack column will always have to equal the ships column
                for(int i = 0; i < shipSize; i++){
                    if(r == row+i){ //goes through each point on ship until it finds a hit or ends
                        hasBeenHit[i] = true; 
                        return true; 
                    }
                }
            }
        }else if(!isVertical){
        	System.out.println("r = " + r + " and row = " + row);
            if(r == row){ //If it is horizontal then the attack row will always have to equal the ships row
                for(int i = 0; i < shipSize; i++){
                    if(c == column+i){ //goes through each point on ship until it finds a hit or ends
                        hasBeenHit[i] = true; 
                        return true; 
                    }
                }
            }
        }
       /* for (int i = 0; i < shipSize; i++) {
        	if (hasBeenHit[i] == false) {
           		break;
        	}
        	if (i == shipSize-1)
        		setHasBeenSunk(true);
        }*/
        return false;
    }

    /*
     * Create a method that determines which part of this ship occupies the given row and column location.
     * 
     * This method should first call checkHit to see if the given coordinates actually hit this ship.  If they
     * don't, you can just return -1.
     * 
     * Assuming the coordinate-left-most part of the ship would be zero, and the lower-right-most part of the
     * ship would be numbered (size-1).  There's a simple way to calculate this, but you need to handle
     * horizontal and vertical positions separately.
     */
    public int getHitIndex(int r, int c) {
        /* This is a "stub".  It's incorrect placeholder code, which is here so the class will compile.
         * Replace it with correct code.
         */
        if(isVertical){
            if(checkHit(r, c))return r-row;
        }
        else if(!isVertical){
            if(checkHit(r, c))return c-column; 
        }            
        return -1;
    }     

    /*
     * Create a method that returns a String containing all the object state that you might want when debugging.
     * Don't do the I/O here... just return a String that you can use elsewhere.
     * Concise but thorough reprsentation of the object's state might look like this:
     * "<<Ship: Row 2, Col 3, Size 4, Ver, Hits: X-X-, Name DESTROYER>>"
     * 
     * Later in the project, if you run into problems and aren't sure what's happening, dumping this kind of
     * output can be very helpful in tracking down the problem.
     */
    public String getDebugState() {
        /* This is a "stub".  It's incorrect placeholder code, which is here so the class will compile.
         * Replace it with correct code.
         */
        String vertical; 
        String hit[] = new String[shipSize]; 
        String hits = ""; 
        for(int i = 0; i < shipSize-1; i++){
            hit[i] = "-"; 
        }
        if(isVertical)vertical = "Ver"; 
        else vertical= "Not Ver";
        for(int i = 0; i < shipSize-1; i++){
            if(hasBeenHit[i]){
                hit[i]= "X";
            }
            hits += hit[i]; 
        }
        
        return "Ship: " + "Row " + row + " Col " + column + " Size " + shipSize + " " + vertical + " Hits: " + hits + ", Name " + shipName + " Has Been Sunk: " + checkSunk();
    }

}