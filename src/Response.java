/*
 * The Response class represents all the information in the response to a Battleship Attack:
 * 
 * - The result of the attack using the constants defined below: HIT, MISS, SUNK, ALLSUNK
 * - If a ship was sunk, the name and size of the ship that was sunk
 */
public class Response {

    /*
     * These constants reprsent the possible responses to an attack.  If a ships is sunk, there
     * will be additional information about which ship.
     */
    public static final int HIT  = 1;     // A ship was hit but not sunk
    public static final int MISS = 2;     // Nothing was hit
    public static final int SUNK = 3;     // A ship was sunk, but other ships remain
    public static final int ALLSUNK = 4;  // All ships were sunk; the game is over
    public static final int INVALID = -1; // (optional) May be used to respond to an invalid attack
    
    /*
     * Declare instance variables "result", "shipName" and "shipSize".  You shouldn't initialize them
     * until the constructor, since we don't have reasonable default values.
     */
    // Declare instance variables
    private int result;
    private String shipName;
    private int shipSize;
    /*
     * Write accessors for the three instance variables.  Using the conventional accessor naming scheme,
     * call them getResult, getShipName, and getShipSize.
     */
    // Write accessors
    public int getResult() {
        return result;
    }
    
    public String getShipName() {
        return shipName;
    }
    
    public int getShipSize() {
        return shipSize;
    }
    /*
     * Now write two constructors for Response.
     * 
     * For Responses when a ship has *not* been sunk, use a single-argument constructor, which just takes
     * a result code (one of the constants defined above).  Then set result from this argument.  You can set
     * the other instance variables to zero and null; they shouldn't be used.
     * 
     * For Responses when a ship *has* been sunk, we need a three-argument constructor, with arguments
     * corresponding to all three instance variables.
     * 
     * By the way, it's simplest to use constructor argument names that are shortened from the instance
     * variable names.  I suggest "res", "name", and "size" for constructor arguments, corresponding to
     * "result", "shipName" and "shipSize" above.  This avoids name collisions.  It's possible to use the
     * same name, and refer to the instance variable as eg. "this.result".
     */
    // Write one-argument constructor
    public Response(int res) {
        result = res;
        shipName = null;
        shipSize = 0;
    }
    // Write three-argument constructor
    public Response(int res, String name, int size) {
        result = res;
        shipName = name;
        shipSize = size;
    }
    /*
     * For testing and debugging, we'll also want to be able to represent Responses as Strings.  Write
     * a method that returns a concise String representing the Response.  This isn't intended for the user
     * interface, but for debugging.  So it should contain all the relevant info, but we won't worry
     * about it being pretty.
     * 
     * Suggested output:
     * 
     * <<<Response: MISS>>>
     * <<<Response: HIT>>>
     * <<<Response: SUNK CARRIER size 5>>>
     * <<<Response: ALLSUNK>>>
     */
    public String toString() {
        if (getResult() == HIT)
            return "<<<Response: HIT>>>";
        else if (getResult() == MISS)
            return "<<<Response: MISS>>>";
        else if (getResult() == SUNK)
            return "<<<Response: SUNK>>>";
        else if (getResult() == ALLSUNK)
            return "<<<Response: ALLSUNK>>>";
        else
            return "<<<Response: INVALID>>>";
    }
   
}
