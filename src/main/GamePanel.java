package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import enemy.Enemy;
import projectile.Projectile;
import spawner.WaveManager;
import tile.TileManager;
import tower.Tower;

public class GamePanel extends JPanel implements Runnable, MouseListener {
	

	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;

	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 24; // 24 Tiles
	public final int maxScreenRow = 16; // 16 Tiles
	public final int screenWidth = tileSize * maxScreenCol; //  pixels
	public final int screenHeight = tileSize * maxScreenRow; //  pixels

	// TOWER INFO PANEL
	int tPanelWidth = 7 * tileSize;   // PANEL WIDTH
	int tPanelHeight = 6 * tileSize;  // PANEL HEIGHT
	int tPanelX = 16; 
	int tPanelY = screenHeight / 2 - tPanelHeight / 2; // VERTICALLY CENTERED
	
	// PANEL BUTTONS
	
	// SYSTEM
	Thread gameThread;
	TileManager tileM = new TileManager(this);
	AssetSetter aSetter = new AssetSetter(this);
	WaveManager wManager = new WaveManager(this);
	UI ui = new UI(this, wManager);
	
	// ENTITIES
	public Player player = new Player();
	public Tower selectedTower = null;
	public ArrayList<Enemy> enemyList = new ArrayList<>();
	public ArrayList<Tower> towerList = new ArrayList<>();
	public ArrayList<Projectile> projectileList = new ArrayList<>();
	
	// FPS
	int FPS = 60;
	
	int counters = 0;
	
	// GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	
	GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		addMouseListener(this);
	}
	
	// INITIALIZE GAME
	public void setupGame() {
		
		gameState = playState;
		aSetter.setTower();
		aSetter.setEnemy();
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

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
			
			if (timer >= 1000000000) { 
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}	
		}
	}
	
	void update() {
		
		// UPDATE THE GAME IF IT'S IN PLAYSTATE
		if (gameState == playState) {
			
//			eSpawner.update();
			wManager.update();
			
			// ENEMY
			for (int i = enemyList.size() - 1; i >= 0; i--) {
				
				Enemy enemy = enemyList.get(i);
				if (enemy != null && enemy.isAlive) enemy.update();
				if (!enemy.isAlive || enemy == null) {		
					player.balance += enemy.rewardMoney;
					enemyList.remove(i);
				}
			}
			
			// TOWER
			for (int i = 0; i < towerList.size(); i++) {
				
				Tower tower = towerList.get(i);
			    if (tower != null) {
			    	tower.update();
			    	
			    	if (tower.canAttack()) {
				        for (int j = 0; j < enemyList.size(); j++) {
				        	Enemy enemy = enemyList.get(j);
				        	
				            if (enemy != null && enemy.isAlive && tower.isInRange(enemy)) {

				                tower.attack(enemy);
				                break;
				            }
				        }
			        }
			    }
			}
			
			// PROJECTILE
			for (int i = 0; i < projectileList.size(); i++) {
				Projectile p = projectileList.get(i);
				p.update();
			
				if (!p.active) {
					projectileList.remove(i);
					i--;
				}
			}
		} 
		
		else if (gameState == pauseState) {
			
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
        
		Graphics2D g2 = (Graphics2D) g;
		
		// DRAW TILES
		tileM.draw(g2);
		
		// DRAW UI
		ui.draw(g2);
		
		// DRAW ENEMIES
		for (int i = 0; i < enemyList.size(); i++) {
			
			Enemy enemy = enemyList.get(i);
			if (enemy != null && enemy.isAlive) enemy.draw(g2);
		}
		
		// DRAW TOWERS
		for (int i = 0; i < towerList.size(); i++) {
			
			Tower tower = towerList.get(i);
			if (!tower.isSelected) tower.draw(g2);
		}
		
		// DRAW PROJECTILES
		for (Projectile p : projectileList) {
		    p.draw(g2);
		}
		
		// IF SELECTED A TOWER, DRAW A TOWER'S DETAIL PANEL
		if (selectedTower != null) {
			
			selectedTower.draw(g2);
			ui.drawTowerPanel(g2, selectedTower);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		// CLICKED POSTION
		int mx = e.getX();
		int my = e.getY();
		
		// CHECK IF PLAYER CLICKED START WAVE BUTTON
		if (ui.handleStartWave(mx, my)) return;
		
		// CHECK IF CLICKED ON TOWER'S UPGRADE BUTTON
		if (ui.handleTowerUpgrade(mx, my, selectedTower)) return;
		
		// CLEAR THE SELECTION IF NO TOWER OR BUTTON IS CLICKED
		for (Tower tower : towerList) tower.isSelected = false;
		selectedTower = null;
		
		// CHECK FOR TOWER CLICK
		for (int i = 0; i < towerList.size(); i++) {
			
			Tower tower = towerList.get(i);
			int dx = mx - (tower.x + tileSize / 2);
	        int dy = my - (tower.y + tileSize / 2);

	        if ((dx * dx + dy * dy) <= 16 * 16) {
	            selectedTower = tower;
	            tower.isSelected = true;
	            System.out.println("Clicked on " + tower);
	            return;
	        }
		}
		
		System.out.println("Clicked on x: " + e.getX() + ", y: " + e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
