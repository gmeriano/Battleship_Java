import javax.swing.JFrame;

public class MainWindow extends JFrame {

	public static final int FULL_WIDTH = 380;
	public static final int FULL_HEIGHT = 820;
	public static final int WIDTH = 30*11;
	public static final int HEIGHT = 30*22;
	
	
	//sets up JFrame
	public void launchWindow() {		
		//setup JFrame
		JFrame window = new JFrame();
		window.setSize(FULL_WIDTH, FULL_HEIGHT);
		window.setTitle("Battleship!");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		
		Artist dc = new Artist();
		window.add(dc);	
	}
}