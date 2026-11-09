package projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import enemy.Enemy;
import main.GamePanel;
import main.UtilityTool;

public class Projectile {
	
	public int x, y; // START POSITION
	public Enemy target; // TARGET TO ATTACK
	public double dx, dy; // MOVEMENT PER FRAME
	
	// PROJECTILE PROPERTIES
	public int damage; 
	public boolean active = true;	
	public double speed = 10;
	
	// LOAD PROJECTILE IMAGE
	public BufferedImage pImage;
	
	// RESOURCES
	GamePanel gp;
	UtilityTool uTool = new UtilityTool();
	
	public Projectile(GamePanel gp, int x, int y, Enemy target, int damage) {
		
		this.gp = gp;
		this.x = x;
		this.y = y;
		this.target = target;
		this.damage = damage;
		
		pImage = setup("/projectiles/bullet");
	}
	
	public boolean isActive() {
		
		return active;
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
	
	public void update() {
		
		if (target == null || !target.isAlive) {
            active = false;
            return;
        }

		// CENTER POINT OF THE TARGET
		int targetX = target.x + gp.tileSize / 2;
		int targetY = target.y + gp.tileSize / 2;
		
		// CENTER POINT OF THE PORJECTILE
		int centerX = x + gp.tileSize / 2;
		int centerY = y + gp.tileSize / 2;
		
		double angle = Math.atan2(targetY - centerY, targetX - centerX); 
		dx = speed * Math.cos(angle);
		dy = speed * Math.sin(angle);

		// CURRENT BULLET POSITION
		x += dx;
		y += dy;
		
		// CHECK IF THE PROJECT IS CLOSE ENOUGH TO THE TARGET
		if (Math.hypot(targetX - centerX , targetY - centerY) <= gp.tileSize / 2) {
			active = false;
			target.health -= damage;
			target.isDamaged = true;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		// DRAW FROM THE CENTER
		int drawX = x;
		int drawY = y;
		
		g2.drawImage(pImage, drawX, drawY, gp.tileSize, gp.tileSize, null);
		
		// FOR DEBUG
		g2.setColor(new Color(255, 0, 0));
		g2.fillOval(target.x + gp.tileSize / 2 - 3, target.y + gp.tileSize / 2 - 3, 6, 6);
		
	}
}
