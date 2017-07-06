package game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class Main {
	static final int tileSize = 75;
	static public JPanel gameBoard;
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				buildGUI();
			}
		});
	}
	
	static void buildGUI() {
		System.out.println("Running buildGUI...");
		JFrame frame = new JFrame("Chess");
		frame.setSize(new Dimension(tileSize * 8, tileSize * 8 + 22));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		gameBoard = new GameBoard(tileSize);
		frame.add(gameBoard, BorderLayout.CENTER);
	}

}