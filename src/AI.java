//Gabriel Meriano and Garrison Hollis
//“As a man of St. Thomas, I uphold our honor through goodness, discipline, and knowledge, and I
//lead my brothers to do the same.”
//I promise to uphold the St. Thomas Honor Code on this project.
import java.util.*;
/*
 * A class representing an AI (Artifical Intelligence) player for Battleship.
 * 
 * The job of an AI is in some ways complicated... it replaces a human player in the game, and must
 * have memory of the game, and make decisions.  But we'll break down those actions into simple steps.
 * 
 * This AI must be capable of remembering all the game state that it should know.  (It doesn't have
 * access to the opponent's secret Ship placement, for example.)
 * 
 * It must be able to place its own ships on the board, and return an array of those Ships to the UI.
 * 
 * It must be able to generate Attacks on its own turn.  It will receive Responses to those Attacks,
 * and may use that information to improve its game play.
 * 
 * It must be able to receive Attacks from its opponent, and generate Responses.  It must not cheat,
 * although this will not be enforced, at least initially.  So it must correctly report hits and misses,
 * and when Ships are sunk.  It must detect and report when its last Ship is sunk.
 */
public class AI {
    /*
     * Instance variables for AI.  Make these private, and we'll use accessors if necessary.
     */

    // Declare shipList, an array containing this player's ships
    private Ship[] shipList;
    // Declare myGrid, a Grid object used while placing our ships to ensure they don't overlap
    private Grid myGrid;
    private boolean[][] board;
    // Declare opponentGrid, a Grid object used to track what locations we have attacked, and what
    // the results are.  A very primitive AI may not use this at all, but a really strong AI will make
    // clever use of it.
    private Grid opponentGrid;
    private boolean[][] boardHits; //double array that shows if you have attacked at a location or not
    public int shipCounter = 0;
    public int foundAttack = 0;
    public int foundAttackCounter = 0;
    
    //saves previous attack each time AI attacks
    public Attack lastAttack;
    //saves the attack coord for when the AI first hits a ship so that it can go back to that point
    public Attack firstAttackCoord;
    //saves previous response to lastAttack
    public Response lastResponse;
    //ship that AI is currently attacking
    public Ship currentShip;

    //temporary player shiplist
    public Ship[] playerShipList;
    // We'll need to randomize ship placement and guesses, so make an instance of a Random object
    Random rndm = new Random();
    /* 
     * Write accessors.
     * Following the usual convention, call them getShipList, getMyGrid, getOpponentGrid.
     * 
     * getShipList should return the full array.  This will be useful for testing, and for writing a UI later.
     */
    public Ship[] getShipList() {
        return shipList;
    }

    public Grid getMyGrid() {
        return myGrid;
    }

    public Grid getOpponentGrid() {
        return opponentGrid;
    }

    /*
     * Write the constructor for AI.  This needs to allocate (create) any arrays, and set initial
     * values for the instance variables.  Finally it should call placeShips() to initialize Ship placement.
     * 
     * We'll revisit this as we add instance variables.
     */
    public AI() {
        lastAttack = new Attack(-1,-1); //temp 
        firstAttackCoord = new Attack(-1,-1); //temp
        shipList = new Ship[5];
        playerShipList = new Ship[5];
        myGrid = new Grid();
        boardHits = new boolean[10][10]; 
        for (int r = 0; r < 10; r++) 
            for (int c = 0; c < 10; c++) {
                myGrid.set(r,c,Grid.MISS);
                boardHits[r][c] = false;
            }
        opponentGrid = new Grid();
        for (int r = 0; r < 10; r++)
        	for (int c = 0; c < 10; c++) {
        		opponentGrid.set(r, c, Grid.MISS);
        	}
        placeShips();
    }

    /*
     * Before writing placeShips(), let's break down the work to make it simpler.  Be sure to read
     * the notes on the next few methods to get a handle on how they fit together before writing code.
     * 
     * This first method attempts to place just a single ship, whose location is already chosen.  Here
     * we have to do either of two things:
     * (1) We determine it can't fit here, and return false; or,
     * (2) We add the ship and return true.
     */
    private boolean tryToPlaceOneShip(String name, int size, boolean vertical, int row, int col) {
        // First check to see if the ship fits on our own board.  If it doesn't, return false so
        // placeOneShip knows we failed
    	//if vertical
        if (vertical) {
            if (row < 0 || row + size > myGrid.GRIDSIZE || col < 0 || col > myGrid.GRIDSIZE) //checks if ship will fit on board
                return false;
            for (int i = row; i < row + size; i++)
                if (myGrid.peek(row,col) == 1) //checks to see if another ship is already in that location
                    return false;
        }
        //if horizontal
        if (vertical == false) {
            if (col < 0 || col + size > myGrid.GRIDSIZE || row < 0 || row > myGrid.GRIDSIZE) //checks if ship will fit on board
                return false;
            for (int i = col; i < col + size; i++) 
                if (myGrid.peek(row,col) == 1) //checks to see if another ship is already in that location
                    return false;
        }
        // Now we know it will fit, so we go ahead and add it:
        // - mark our own board to show this new ship's locations are occupied
        // - Create a Ship object an place it in the shipList array in the first empty place
        // - return true so that placeOneShip knows we succeeded
        return true;
    }

    /*
     * To place one ship, we'll randomly generate a location for it.  But that space may not be free,
     * since we may have placed other ships there already.  So we'll need to do that in a loop, and try
     * repeatedly until we succeed.
     * 
     * To keep this loop from getting too complicated, we should generate a random location in this method,
     * and leave the work of trying to fit it on our board to another method, which is tryToPlaceOneShip.
     */
    private void placeOneShip(String name, int size) {
        boolean notPlaced = true;
        while (notPlaced) {
            int col = rndm.nextInt(10);
            int row = rndm.nextInt(10);
            boolean isVertical = rndm.nextBoolean();
            if (tryToPlaceOneShip(name, size, isVertical, row, col)) { //randomly selects a location to try and place ship
                for (int i = row; i < row + size; i++)
                    myGrid.set(i,col,1); //adds ship to grid if it fits there
                shipList[shipCounter] = new Ship(row,col,isVertical,size,name); //adds ship to shipList array
                notPlaced = false;
            }
        }
        shipCounter++;
    }

    /*
     * placeShips will be simple, since it will just call placeOneShip once for each size of ship that we want.
     * The constructor will call this method to take care of the whole job.
     */
    private void placeShips() {
        placeOneShip("PT", 2);
        placeOneShip("Submarine", 3);
        placeOneShip("Battleship", 3);
        placeOneShip("Destroyer", 4);
        placeOneShip("Carrier", 5);

    }

    /*
     * Determine whether a given (row,column) pair "collides with" a given Ship.  Returns true
     * if the Ship occupies the given location, false otherwise.
     * 
     * When an attack is received, this method might be used to determine, for each Ship, whether
     * it has been hit.
     */
    private boolean checkCollision(int row, int col, Ship ship) { //Based on method from TextUI
        if (ship.getIsVertical()) {
            return (col == ship.getCol()
                && row >= ship.getRow()
                && row < ship.getRow() + ship.getSize());
        } else {
            return (row == ship.getRow()
                && col >= ship.getCol()
                && col < ship.getCol() + ship.getSize());
        }                       
    }
    
    //Checks all ships to see if there are collisions
    private boolean collide(int row, int col, Ship[] ships) { //based on method from TextUI
        for (Ship ship : ships) {
            if (checkCollision(row, col, ship)) return true;
        }
        return false;
    }

    /*
     * Check to see if all the ships have been sunk. 
     *
     */
    private boolean checkAllSunk() {
        for (int i = 0; i < shipList.length; i++)
            if (shipList[i].checkSunk() == false)
                return false;
        return true;
    }

    /*
     * This is what happens when the opponent attacks you.
     *         Handle the simplest case first -- a MISS
     *   Then what happens if they HIT on my Grid, mark the ship as HIT, check to see if the ship has *  been sunk, see if all of the ships have been sunk.
     */
    public Response receiveAttack(Attack attack) {
    	//Based on TextUI
            int row = attack.getRow();
            int col = attack.getColumn();
            int idx; // once we know the location index on a ship that's hit
            Ship[] ships = shipList;
            Ship ship = null; // once we know which Ship is hit
            if (!collide(row, col, ships)) {
                return new Response(Response.MISS);
            }
            // From here we know it's a hit at least
            for (Ship s : ships) {
                if (checkCollision(row, col, s)) {
                    ship = s;
                }
            }
            if (ship == null) {
                // Should not be reached; we've already verified that (row,col) collides with at least one ship
                throw new Error("Error in TextUI.consoleGetResponse()");
            }
            idx = ship.getHitIndex(row, col);
            ship.setHasBeenHit(idx);
            if (! ship.checkSunk()) {
                return new Response(Response.HIT);
            }
            // From here we know a ship is sunk
            for (Ship s : ships) {
                if (! s.checkSunk()) {
                    // At least one ship remains
                	ship.setHasBeenSunk(true);
                	return new Response(Response.SUNK, ship.getName(), ship.getSize());
                    
                }
            }
            // From here we know all ships are sunk
            return new Response(Response.ALLSUNK, ship.getName(), ship.getSize());
        }
        
        
        
    

    /* Attack their ships.  The simiplest version is to randomly attack anywhere on the board.
     * You can make this more complicated for additional points.
     */

    //Complex AI code
    public Attack makeAttack(Ship[] enemyShips) {
    	
    	boolean startOver = false; //becomes true after you sink a ship. This causes the AI to go back to random coords
        for (int i = 0; i < enemyShips.length; i++)
	        if (enemyShips[i].checkHit(lastAttack.getRow(), lastAttack.getColumn())) { //if lastAttack was a hit
	        	lastResponse = new Response(Response.HIT);
	        	boardHits[lastAttack.getRow()][lastAttack.getColumn()] = true;
	        	if (foundAttack == 0) { //if this is the first hit on the ship
	        		firstAttackCoord = new Attack(lastAttack.getRow(), lastAttack.getColumn()); 
	        		currentShip = enemyShips[i];
	        	}
	        	break;
	        }
	        else {
	        	lastResponse = new Response(Response.MISS);
	        	if (lastAttack.getRow() != -1) //Doesn't run the first time
	        		boardHits[lastAttack.getRow()][lastAttack.getColumn()] = true;;
	        }
        
        //checks to see if currentShip was sunk during lastAttack. If it was, the AI starts over
        if (foundAttack != 0) { 
            if (currentShip.checkSunk()) {
            	foundAttack = 0;
            	startOver = true;
            }
        }
        
        for (int i = 0; i < enemyShips.length; i++)
            //if the AI has already found a ship and the lastAttack was a miss
	        if (foundAttack != 0 && lastResponse.getResult() == Response.MISS) {// && enemyShips[i].checkHit(lastAttack.getRow(), lastAttack.getColumn()) == false) {  //aalways returns 0 because opponentGrid is full of 0s
	            foundAttack++;
	            foundAttackCounter = 0;
	            break;
	        }
        	//if AI just found a hit then foundAttack becomes 1 which allows the smart AI to begin
	        else if(foundAttack == 0 && startOver == false && enemyShips[i].checkHit(lastAttack.getRow(), lastAttack.getColumn())) {
	        	foundAttack = 1;
	        }
        
        //hasn't found attack yet. Picks random coords
        if (foundAttack == 0) {
        	boolean hit = true;
        	while (hit) {
		    	int row = rndm.nextInt(10);
		        int col = rndm.nextInt(10);
		        if (boardHits[row][col] == false) {
			        lastAttack = new Attack(row,col);
			        return lastAttack;
		        }
        	}
    	}
    	
        //Found attack. Goes up until it finds a miss
        if (foundAttack == 1) {
    		if (boardHits[lastAttack.getRow()-1][lastAttack.getColumn()] == false) {
    			lastAttack = new Attack(lastAttack.getRow()-1, lastAttack.getColumn());
    			return lastAttack;
    		}
    		else 
    			foundAttack++;
    	}
        //After finding a miss going up, it goes down from firstAttackCoord until it finds a miss
        if (foundAttack == 2) {
        	if (foundAttackCounter == 0) {
        		foundAttackCounter++;
	            lastAttack = new Attack(firstAttackCoord.getRow()+1, firstAttackCoord.getColumn());
	            return lastAttack;
        	}
        	else {
	            lastAttack = new Attack(lastAttack.getRow()+1, lastAttack.getColumn());
	            return lastAttack;
        	}
    	}
        //After finding a miss going down, it goes left from firstAttackCoord until it finds a miss
        if (foundAttack == 3) {
        	if (foundAttackCounter == 0) {
        		foundAttackCounter++;
    				lastAttack = new Attack(firstAttackCoord.getRow(), firstAttackCoord.getColumn()-1);
    				return lastAttack;
    		}
        	else {
	            lastAttack = new Attack(lastAttack.getRow(), lastAttack.getColumn()-1);
	            return lastAttack;
        	}
    	}
        //After finding a miss going left, it goes right from firstAttackCoord until it finds a miss
        if (foundAttack == 4) {
        	if (foundAttackCounter == 0) {
        		foundAttackCounter++;
    				lastAttack = new Attack(firstAttackCoord.getRow(), firstAttackCoord.getColumn()+1);
    				return lastAttack;
    		}
        	else {
				lastAttack = new Attack(lastAttack.getRow(), lastAttack.getColumn()+1);
				return lastAttack;
    		}
        }       
        boardHits[lastAttack.getRow()][lastAttack.getColumn()] = false;
        return lastAttack;
    }


    /*
     * Once you have attacked they will tell you if it is a hit, miss, sunk or all sunk
     */
    public void receiveResponse(Response response) {
        System.out.println(response.getResult());
    }   
    
    
    //creates a random row and col coord and if the AI has not already attacked there, it returns an attack at those coords
    public Attack randomAttack() {
    	foundAttack = 0;
     	boolean hit = true;
     	while (hit) {
	    	int row = rndm.nextInt(10);
	        int col = rndm.nextInt(10);
	        if (boardHits[row][col] == false) {
		        lastAttack = new Attack(row,col);
		        return lastAttack;
	        }
     	}
     	return new Attack(-1,-1);
    }
}

