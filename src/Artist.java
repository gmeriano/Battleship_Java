import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

//import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Artist extends JPanel {

	//does all drawing to screen
	private void doDrawing(Graphics g, Grid myGrid, Grid opponentGrid, Ship[][] shipArray) throws IOException {	
		Graphics2D g2d = (Graphics2D) g;
		drawBoard(g2d);
		
		//places player ships on lower board. getRow()+12 translates it down 12 tile spaces
		for (int i = 0; i < 5; i++) {
			placeShips(g2d, shipArray[1][i].getRow()+12, shipArray[1][i].getCol()+1, shipArray[1][i]);			
		}
		

		//if enemy ship has been sunk, put a red rectangle at its coords to show player that it is sunk
		for (int i = 0; i < 5; i++) {
			if (shipArray[0][i].getHasBeenSunk()) {
				if (shipArray[0][i].getIsVertical()) {
					g2d.setColor(Color.RED);
					g2d.fillRect(30*(1+shipArray[0][i].getCol())+5, 30*(1+shipArray[0][i].getRow()),20,30*shipArray[0][i].getSize());
				}
				else {
					g2d.setColor(Color.RED);
					g2d.fillRect(30*(1+shipArray[0][i].getCol()), 30*(1+shipArray[0][i].getRow())+5,30*shipArray[0][i].getSize(),20);
				}
			}
		}
		
		//places red or black dots on your grid to show hits and misses
		for (int i =0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				if (myGrid.peek(i, j) == Response.HIT)
					placeHits(g2d,j+1,i+1);
				else if (myGrid.peek(i, j) == Response.MISS)
					placeMisses(g2d,j+1,i+1);
			}
		
		//places red or black dots on opponent grid to show hits and misses
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				if (opponentGrid.peek(i, j) == Response.HIT)
					placeHits(g2d,j+1,i+12);
				else if (opponentGrid.peek(i, j) == Response.MISS)
					placeMisses(g2d,j+1,i+12);
			}
	}
	
	//draws empty board
	private void drawBoard(Graphics2D g2d) {
		
		int cellWidth = 30;
		//Vertical Lines
		for (int i = 1; i < 12; i++) {
			g2d.drawLine(i*cellWidth, cellWidth, i*cellWidth, MainWindow.HEIGHT);
		}
		
		//Horizontal Lines
		int cellHeight = cellWidth;		
		for (int i = 1; i < 23; i++) {
			g2d.drawLine(cellWidth, i*cellHeight, MainWindow.WIDTH, i*cellHeight);
		}
		
		//Divider rectangle
		g2d.setColor(Color.BLUE);
		g2d.fillRect(cellWidth, 11*cellHeight, MainWindow.WIDTH-cellWidth, cellHeight);
	}
	
	//places hits at given coords
	private void placeHits(Graphics2D g2d, int xPos, int yPos) {
		g2d.setColor(Color.RED);
		g2d.fillOval(30*xPos+7, 30*yPos+7,15,15);
		repaint();
		revalidate();
	}
	
	//places misses at given coords
	private void placeMisses(Graphics2D g2d, int xPos, int yPos) {
		g2d.setColor(Color.BLACK);
		g2d.fillOval(30*xPos+7, 30*yPos+7,15,15);
		repaint();
		revalidate();
	}
	
	//places ship at given coords
	private void placeShips(Graphics2D g2d, int xPos, int yPos, Ship ship) {
		g2d.setColor(Color.GRAY);
		
		if (ship.getIsVertical()) {
			if (ship.getHasBeenSunk()) 
				g2d.setColor(Color.RED);
			g2d.fillRect(30*yPos+5, 30*xPos,20,30*ship.getSize());
		}
		if (!ship.getIsVertical()) {
			if (ship.getHasBeenSunk()) 
				g2d.setColor(Color.RED);
			g2d.fillRect(30*yPos, 30*xPos+5,30*ship.getSize(),20);
		}
		repaint();
		revalidate();
	}
	
	//paintComponent method for doDrawing
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try { //try-catch for doDrawing(g)
			doDrawing(g, TextUI.ui.getGrid(1), TextUI.ui.getGrid(0), TextUI.ui.getPlayerShips());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}