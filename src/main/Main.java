package main;

import javax.swing.JFrame;

/***
 * 
javac -d bin src\main\*.java src\tile\*.java src\Enemy\*.java src\Tower\*.java src\Projectile\*.java src\Spawner\*.java
xcopy /s /e /i /y res\* bin\
java -cp bin main.Main
*
***/

public class Main {
	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
		window.setTitle("2D Tower Defense");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack(); // Cause this window to fit the preffered size of Gamepanel
		window.setLocationRelativeTo(null);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
}
