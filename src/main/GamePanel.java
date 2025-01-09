package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;

	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16; // 16 Tiles
	public final int maxScreenRow = 12; // 12 Tiles
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

	Thread gameThread;
	TileManager tileM = new TileManager(this);
	
	// FPS
	int FPS = 60;
	
	// GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	
	
	GamePanel(){
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
	}
	
	public void setupGame() {
		
		gameState = playState;
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		System.out.println("Run");
		// TODO Auto-generated method stub
		double drawInterval = 1000000000/FPS; // 16,666,666.66666667 or 0.01666 seconds 
		double delta = 0; // Delta accumulates time to determine if an update should occur.
		long lastTime = System.nanoTime();
		long currentTime; // Store current timestamp of the loop
		long timer = 0; // Track the elapsed time
		int drawCount = 0; // Store the number of drawn frames
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			
			// If the timer is higher than 1 second
			// Print out the number of frames that are drawn in 1 second
			// Reset the timer and drawCount back to 0
			
			if(timer >= 1000000000) { 
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}	
		}
	}
	
	void update() {
		if (gameState == playState) {
			
		} else if (gameState == pauseState) {
			
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
//        System.out.println("paintComponent called");
        
		Graphics2D g2 = (Graphics2D) g;
		
		tileM.draw(g2);
		
	}
}
