import java.util.Random;
import java.util.Scanner;

/**
 */
public class TextUI {

    private final Random rand;
    private final Scanner scan;
    
    public static TextUI ui;
    
    /*
     * isAI[0] is true if player 0 is an AI, false otherwise
     * isAI[1] is true if player 1 is an AI, false otherwise
     */
    private boolean[] isAI = new boolean[2];

    /*
     * players[p] is null if p is a human, and an AI object otherwise
     */
    private AI[] players = new AI[2];

    /*
     * player index (0 or 1) of the player whose turn it is now
     */
    private int currentPlayer;  // 0 if player 0 goes next; 1 if player 1 goes next

    /*
     * Grids for players 0 and 1, for use by this UI in tracking and displaying attacks and results
     */
    private Grid[] grids = new Grid[2];

    /*
     * 2D array for the UI to hold its copy of both players' ships
     * playerShips[0][0] through [0][4] for player 0's five ships, and
     * [1][0] through [1][4] for player 1's ships
     */
    Ship[][] playerShips = new Ship[2][];

    /*
     * Main method; simple program entry point to start a game
     */
    
    public Ship[][] getPlayerShips() {
    	return playerShips;
    }

    
    public Grid getGrid(int index) {
    	return grids[index];
    }
    
    public static void main(String args[]) {
        ui = new TextUI();
        // ui.playCvC();
        ui.playPvC();
    }

    /*
     * Zero argument constructor sets up Random and Scanner objects
     */
    public TextUI() {
        rand = new Random(System.nanoTime());
        scan = new Scanner(System.in);
    }

    /*
     * Play one human-vs-computer game, with first player chosen randomly
     */
    public void playPvC() {
        boolean computerFirst = true;
        playPvC(computerFirst);
    }

    /*
     * Play one human-vs-computer game, specifying who goes first
     */
    public void playPvC(boolean computerPlaysFirst) {
        if (computerPlaysFirst) {
            isAI[0] = true;
            isAI[1] = false;
            players[0] = new AI();
            players[1] = null;
            playerShips[0] = players[0].getShipList();
            //System.out.println(players[0].getShipList().)
            playerShips[1] = consoleGetShips(1);
        } else {
            players[0] = null;
            players[1] = new AI();
            playerShips[0] = consoleGetShips(0);
            playerShips[1] = players[1].getShipList();
        }
        grids[0] = new Grid();
        grids[1] = new Grid();
        currentPlayer = 0;
        playGame();
    }

    /*
     * Play one computer-vs-computer game
     */
    public void playCvC() {
        isAI[0] = true;
        isAI[1] = true;
        players[0] = new AI();
        players[1] = new AI();
        playerShips[0] = players[0].getShipList();
        playerShips[1] = players[1].getShipList();
        grids[0] = new Grid();
        grids[1] = new Grid();
        currentPlayer = 0;
        playGame();
    }

    /*
     * Alternate currentPlayer. (Called at the end of a player's turn.)
     */
    private void changeCurrentPlayer() {
        switch (currentPlayer) {
            case 0: currentPlayer = 1;
            return;
            default: currentPlayer = 0;
            return;
        }
    }

    /*
     * Top-level loop for a single game, involving any mix of humans or computers.
     * The while loop in this method continues until the game is over, one (half) turn per iteration.
     * The game should be set up (players defined, Ship lists gathered) before this method is called.
     */
    public void playGame() {
    	
    	//Starts up window for graphical display of game
        MainWindow window = new MainWindow();
        window.launchWindow();
        
        boolean playing = true;
        int attacker, defender;
        Attack attack;
        Response response;
        /*
         * Each iteration of this loop is one player's turn.
         */
        while (playing) {
            /*
             * For now we do debug output.  Before going ot production we would want to change this,
             * and at least shop showing all secret Ship placement at every turn.
             */
            //printDebugState(0);
           // printDebugState(1);

            attacker = currentPlayer;
            defender = otherPlayer(currentPlayer);

            
            /*
             * First gather an Attack
             */
            if (isAI[attacker]) { // It is an AI's turn
                attack = players[attacker].makeAttack(playerShips[1]); //makeAttack takes the players Ship array to check and see if it gets a hit
            } else { // It is a human's turnids[1
                attack = consoleGetAttack(attacker);
            }

            System.out.println("Player " + currentPlayer +
                " attacks at (" + attack.getRow() + "," + attack.getColumn() + ")");
            
            /*
             * Deliver the attack and collect the Response
             */
            if (isAI[defender]) { // The defender is an AI
                response = players[defender].receiveAttack(attack);
            } else { // It is a human's turn
                response = consoleGetResponse(attack, defender);
            }

            switch (response.getResult()) {
                case Response.HIT:
                System.out.println("It's a HIT!");
                break;
                case Response.MISS:
                System.out.println("It's a MISS!");
                break;
                case Response.SUNK:
                System.out.println("Ship sunk: " + response.getShipName());
                
                break;
                case Response.ALLSUNK:
                System.out.println("Ship sunk: " + response.getShipName());
                System.out.println("Game over; player " + currentPlayer + " wins!");
                playing = false;
                break;
            }
            
            
            /*
             * Return the Response to the Attacker
             */
            if (isAI[currentPlayer]) { // It is an AI's turn
                players[currentPlayer].receiveResponse(response);
            } else { // It is a human's turn
            }

            /*
             * Finally update the UI's instance variables
             */
            if (response.getResult() == Response.MISS) {
                grids[currentPlayer].set(attack.getRow(), attack.getColumn(), Grid.MISS);
            } else {
                grids[currentPlayer].set(attack.getRow(), attack.getColumn(), Grid.HIT);
            }
            changeCurrentPlayer();
        } // while (true) -- Continue playing turns until the game ends
    }

    /*
     * For a human player on the console, input the location of a single ship
     */
    private Ship consoleGetAShip(Ship[] ships, String name, int size) {
        // boolean goodLocation = false;
        boolean vertical;
        int range = 10 - size;
        int row, col;
        String line;
        String in;
        Ship ship;
        
        //This code sometimes places ships outside of graphical grid
        //   ¯\_("/)_/¯
        while (true) {
            System.out.println("Place your " + name + ": Will it be vertical? (Y/N)  ");
            line = scan.nextLine();
            while (line.length() == 0) {
                line = scan.nextLine();
            }
            in = line.substring(0,1);
            System.out.println("input string: " + in);
            
            if (in.equalsIgnoreCase("Y")) {
                vertical = true;
                break;
            } else if (in.equalsIgnoreCase("N")) {
                vertical = false;
                break;
            }
            // Invalid input; once more through the while loop to try again
        }
        while (true) {
            if (vertical) {
                System.out.print("Row    (0 through " + range + "):  ");
                row = scan.nextInt();
                scan.nextLine();
                System.out.print("Column (0 through 9):  ");
                col = scan.nextInt();
                if (row < 0 || row > range || col < 0 || col > 9) continue;
            } else { // horizontal
                System.out.print("Row    (0 through 9):  ");
                row = scan.nextInt();
                System.out.print("Column (0 through " + range + "):  ");
                col = scan.nextInt();
                if (row < 0 || row > 9 || col < 0 || col > range) continue;
            }            
            ship = new Ship(row, col, vertical, size, name);
            if (collide(ship, ships)) {
                System.out.println("There's already a ship there!  Please try again.");
                continue;
            }
            break;
        }
        return ship;
    }
    
    /*
     * For a human player on the console, input the location of all their ships, using the above method.
     */
    private Ship[] consoleGetShips(int player) {
        //Grid grid = new Grid();
        Ship[] ships = new Ship[5];
        System.out.println();
        System.out.println("You are player " + player);
        System.out.println("Please place your ships.");
        ships[0] = consoleGetAShip(ships, "CARRIER", 5);
        ships[1] = consoleGetAShip(ships, "DESTROYER", 4);
        ships[2] = consoleGetAShip(ships, "BATTLESHIP", 3);
        ships[3] = consoleGetAShip(ships, "SUBMARINE", 3);
        ships[4] = consoleGetAShip(ships, "PT", 2);
        return ships;
    }
    
    /*
     * For a human player on the console, input coordinates for an attack
     */
    private Attack consoleGetAttack(int player) {
        int row, col;
        System.out.println("Player " + player + ", it is your turn to attack.");
        while (true) {
	        while (true) {
	            System.out.print("Row    (0 through 9): ");
	            row = scan.nextInt();
	            if (row < 0) continue;
	            if (row > 9) continue;
	            break;
	        }
	        while (true) {
	            System.out.print("Column (0 through 9): ");
	            col = scan.nextInt();
	            if (col < 0) continue;
	            if (col > 9) continue;
	            break;
	        }
	        if (grids[1].peek(row, col) != Grid.UNKNOWN) continue;
	        break;
        }
        return new Attack(row, col);
    }

    /*
     * Does not change currentPlayer, but returns the index of the opposite player.
     * Useful for code that needs to reference both attacker (current) and defender (other) ships
     */
    int otherPlayer(int player) {
        switch (player) {
            case 0: return 1;
            case 1: return 0;
            default: return 0;
        }
    }

    /*
     * For a human player on the console, determine the Response from an incoming Attack.
     * 
     * We are preventing cheating by storing the player's Ship placement and calculating HIT and
     * MISS results on his behalf, so this method does that without console IO.
     * 
     * The defender argument is the index of the (human console) player who is defending the attack
     */
    private Response consoleGetResponse(Attack attack, int defender) {
        int row = attack.getRow();
        int col = attack.getColumn();
        int idx; // once we know the location index on a ship that's hit
        Ship[] ships = playerShips[defender];
        Ship ship = null; // once we know which Ship is hit
        if (!collide(row, col, ships)) {
            return new Response(Response.MISS);
        }
        // From here we know it's a hit at least
        for (Ship s : ships) {
            if (collide(row, col, s)) {
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
        for (int i = 0; i < ships.length; i++) {
        	if (ships[i].checkSunk()) {
        		ships[i].setHasBeenSunk(true); }
        }
        for (Ship s : ships) {
            if (! s.checkSunk()) {
                // At least one ship remains
            	return new Response(Response.SUNK, ship.getName(), ship.getSize());
                
            }
        }
        // From here we know all ships are sunk
        return new Response(Response.ALLSUNK, ship.getName(), ship.getSize());
    }
    
    
    /*
     * True if the given (row,col) coordinates collide (overlap) with any of the given list of ships.
     */
    private boolean collide(int row, int col, Ship[] ships) {
        for (Ship ship : ships) {
            if (collide(row, col, ship)) return true;
        }
        return false;
    }

    /*
     * True if the given (row,col) coordinates collide (overlap) with the given ship
     */
    private boolean collide(int row, int col, Ship ship) {
        if (ship.getIsVertical()) {
            if (col != ship.getCol()) return false;
            if (row < ship.getRow()) return false;
            if (row >= ship.getRow() + ship.getSize()) return false;
            return true;
        } else {
            if (row != ship.getRow()) return false;
            if (col < ship.getCol()) return false;
            if (col >= ship.getCol() + ship.getSize()) return false;
            return true;
        }
    }

    /*
     * True if the given Ship collides with any of the given list of ships
     */
    private boolean collide(Ship ship, Ship[] ships) {
        for (Ship s : ships) {
            if (collide(s, ship)) return true;
        }
        return false;
    }

    /*
     * True if the two given ships collide.
     */
    private boolean collide(Ship s1, Ship s2) {
        if (s1 == null || s2 == null) return false; // no collision if either ship is null
        boolean vert1 = s1.getIsVertical();
        boolean vert2 = s2.getIsVertical();
        if (vert1 && vert2) {
            if (s1.getCol() != s2.getCol()) return false;  // 2 vertical ships in different columns
            if (s1.getRow() >= s2.getRow() + s2.getSize()) return false; // s1 is below s2
            if (s2.getRow() >= s1.getRow() + s1.getSize()) return false; // s2 is below s1
            return true; // otherwise they collide
        } else if (!vert1 && !vert2) {
            if (s1.getRow() != s2.getRow()) return false; // 2 holizontal ships in different rows
            if (s1.getCol() >= s2.getCol() + s2.getSize()) return false; // s1 to the right of s2
            if (s2.getCol() >= s1.getCol() + s1.getSize()) return false; // s2 to the right of s1
            return true; // otherwise they collide
        } else {
            if (s1.getRow() >= s2.getRow() + s2.getSize()) return false; // s1 is below s2
            if (s2.getRow() >= s1.getRow() + s1.getSize()) return false; // s2 is below s1
            if (s1.getCol() >= s2.getCol() + s2.getSize()) return false; // s1 to the right of s2
            if (s2.getCol() >= s1.getCol() + s1.getSize()) return false; // s2 to the right of s1
            return true; // otherwise they collide
        }
    }

    /*
     * For debugging purposes, output the full state of both players
     * When playing "for real" this shouldn't be called
     */
    private void printDebugState(int player) {
        System.out.println("Player " + player + " ships and grid:");
        for (Ship s : playerShips[player]) {
            System.out.println(s.getDebugState());
        }
        System.out.println(grids[player].toString());
    }
    
}
