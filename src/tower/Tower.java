package tower;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import enemy.Enemy;
import main.AssetSetter;
import main.GamePanel;
import main.UtilityTool;
import projectile.Projectile;

public class Tower {
	
	// POSITION
	public int x, y;
	
	// TOWER STATS AND PROPERTIES
	public String name;             // NAME OF THE TOWER
	public int range;               // ATTACK RANGE OF THE TOWER
	public int damage;              // DAMAGER DEALT PER ATTACK
	protected int attackCd;         // ATTACK COOLDOWN DURATION (IN FRAMES)
	public int attackSpd;        	// ATTACK SPEED (ATTACK PER SEC)
	private int cooldownTimer = 0;  // TIMER TO TRACK WHEN THE TOWER CAN ATTACK AGAIN
	public int upgrades = 0;
	
    // TOWER STATE
    double angle = -1.50;				// CURRENT ANGLE OF THE TOWER (FOR ROTATION AND AIMING)
    public boolean isSelected = false;  // WHETHER THE TOWER IS SELECTED BY THE PLAYER 
    
    // GAME RESOURCES AND TOOLS
	GamePanel gp;
	BufferedImage tImage, tImageDamaged1, tImageDamaged2;
	UtilityTool uTool = new UtilityTool();
	
	// DETECTED ENEMY
	Enemy target;
	
	AssetSetter aSetter;

	public Tower(GamePanel gp, int x, int y) {
		
		this.gp = gp;
		this.x = x;
		this.y = y;
		
		aSetter = new AssetSetter(gp);
	}
	
	public boolean canAttack() {
		
		return cooldownTimer <= 0;
	}
	
	public boolean isInRange(Enemy e) {
		
		target = e;
		
		if (target.health == 0) {
			target.isAlive = false;
		}
		
		double angle = Math.atan2(e.y - y + (gp.tileSize / 2), e.x - x + (gp.tileSize / 2));
		int dx = (e.x + gp.tileSize / 2) - (this.x + gp.tileSize / 2);
        int dy = (e.y + gp.tileSize / 2) - (this.y + gp.tileSize / 2);
        
        boolean isInRange = (dx * dx + dy * dy) <= (range * range);
        if (isInRange) this.angle = angle;
        
        return isInRange;
    }

    public void attack(Enemy e) {
    	
    	// CREATE A PROJECTILE ENTITY
    	Projectile p = new Projectile(gp, this.x, this.y, e, this.damage);
    	gp.projectileList.add(p);
    	
    	System.out.println("Attacking " + e);
    	
    	if (e.health <= 0) {
    		e.isAlive = false;
    		System.out.println(e + " defeated!");
//    		aSetter.setEnemy();
    	}
    	
    	// SET COOLDOWN AFTER ATTACKED
    	cooldownTimer = attackCd;
    }
    
    public void findNewTarget() {
        for (Enemy enemy : gp.enemyList) {
            if (enemy.isAlive && isInRange(enemy)) {
                target = enemy;
                break;
            }
        }
    }
    
    public void update() {
    	
    	if (target != null && isInRange(target)) angle = Math.atan2(target.y - y + (gp.tileSize / 2), target.x - x + (gp.tileSize / 2));
    	if (target != null && !target.isAlive) target = null;
    	if (cooldownTimer > 0) cooldownTimer --;
    }
	
	public void draw(Graphics2D g2) {
		
		AffineTransform old = g2.getTransform();

		// IF THE TOWER IS SELECTED, DRAW THE TOWER'S 
		// ATTACK RANGE INDICATOR
	    if (isSelected) {
			
			g2.setColor(Color.WHITE);
	    	g2.drawOval(
		        x + gp.tileSize / 2 - range,
		        y + gp.tileSize / 2 - range,
		        range * 2,
		        range * 2
		    );
	    	
	    	g2.setColor(new Color(0, 0, 0, 90));
	    	g2.fillOval(
	    	    x + gp.tileSize / 2 - range,
	    		y + gp.tileSize / 2 - range,
	    		range * 2,
	    		range * 2
	    	);
	    
	    }

	    // MOVE TO CENTER, ROTATE AND DRAW THE IMAGE
	    g2.translate(x + gp.tileSize / 2, y + gp.tileSize / 2);
	    g2.rotate(angle);
	    g2.drawImage(tImage, -gp.tileSize/2, -gp.tileSize/2, gp.tileSize, gp.tileSize, null);

	    // RESET TRANSFORM
	    g2.setTransform(old);

	    // FOR DEBUG
	    g2.setColor(Color.RED);
	    g2.fillOval(x + gp.tileSize / 2 - 4, y + gp.tileSize / 2 - 4, 8, 8);
//	    g2.drawRect(x, y, gp.tileSize, gp.tileSize);
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
	
	public void upgrade() {
		
	}
	
}
