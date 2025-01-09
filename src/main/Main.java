package main;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
		window.setTitle("2D Adventure");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack(); // Cause this window to fit the preffered size of Gamepanel
		window.setLocationRelativeTo(null);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
}
