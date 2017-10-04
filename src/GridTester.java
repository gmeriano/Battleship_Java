/**
 * Tester is a class to test other code in the Battleship project.
 */
public class GridTester
{
    static Ship shipDES; // A DESTROYER Ship object, for simple testing
    static Grid grid = new Grid();
    
    /*
     * Main method will run other tests as needed
     */
    public static void main(String [] args) {
        System.out.println();
        testShip();
        testGrid();
    }

    /*
     * A few simple tests of the Ship class, using a single test object
     */
    public static void testShip() {
        int rowDES = 2;
        int colDES = 3;
        int sizeDES = 4;
        boolean verticalDES = true;
        String nameDES = "DESTROYER";
        shipDES = new Ship(rowDES, colDES, verticalDES, sizeDES, nameDES);
        System.out.println("Should be a hit (true): " + shipDES.checkHit(2, 3));
        System.out.println("Should be a hit (true): " + shipDES.checkHit(5, 3));
        System.out.println("Should be a miss (false): " + shipDES.checkHit(6, 3));
        
        System.out.println("Destroyer state: " + shipDES.getDebugState());
        System.out.println("Sunk? " + shipDES.checkSunk());
        shipDES.setHasBeenHit(0);
        shipDES.setHasBeenHit(2);
        System.out.println("Destroyer state: " + shipDES.getDebugState());
        System.out.println("Sunk? " + shipDES.checkSunk());
        shipDES.setHasBeenHit(1);
        shipDES.setHasBeenHit(3);
        System.out.println("Destroyer state: " + shipDES.getDebugState());
        System.out.println("Sunk? " + shipDES.checkSunk());
    }

    /*
     * A few simple tests of the Ship class, using a single test object
     */
    public static void testGrid() {
        grid = new Grid();
        
        //This is for testing purposes only and should be removed when your code is finished.
        grid.set(0,0,Grid.HIT);   
        grid.set(1,1,Grid.MISS);   
        System.out.println(grid.toString());
    }

}
