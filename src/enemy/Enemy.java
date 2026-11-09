package enemy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Enemy {
	
	// POSITION
	public int x, y;
	
	// ENEMY STATS AND PROPERTIES
	public String name;
	public int speed;
	public int totalHealth;
	public int health;
	public boolean isAlive = true;
	public String direction;
    public boolean isDamaged = false;
	public int rewardMoney;
    
	// RESROUCES
	GamePanel gp;
	UtilityTool uTool = new UtilityTool();
	public BufferedImage eImage, eNormalImage, eDamagedImage1, eDamagedImage2;
	public int spriteCounter = 0; 
	public int spriteNum = 1;
	
	public Enemy(GamePanel gp, int x, int y) {
		
		this.gp = gp;
		this.x = x;
		this.y = y;
	}
	
	public void setAction() {
		
	}
	
	public void update() {
		
		setAction();
		switch(direction) {
			case "up":
				y -= speed;
				break;
			case "down":
				y += speed;
				break;
			case "left":
				x -= speed;
				break;
			case "right":
				x += speed;
				break;
		}
		
		// IF THE ENEMY RECEIVED DAMAGE, 
		// HANDLE THE RECEIVED DAMAGE ANIMATION
		if (isDamaged) {
			spriteCounter++;
			
			if(spriteCounter > 1) { 
						
				if (spriteNum == 1 || spriteNum == 2) spriteNum ++;
				
				else if (spriteNum == 3) {
					spriteNum = 1;
					isDamaged = false;
				}
						
				spriteCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		if (spriteNum == 1) eImage = eNormalImage;
		
		else if (spriteNum == 2) eImage = eDamagedImage1;
		
		else if (spriteNum == 3) eImage = eDamagedImage2;
		
		g2.drawImage(eImage, x, y, gp.tileSize, gp.tileSize, null);
	}
	
	public BufferedImage setup(String imagePath) {
		
		BufferedImage scaledImage = null;
		
		try {
			
			scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			scaledImage = uTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return scaledImage;
	}
}
