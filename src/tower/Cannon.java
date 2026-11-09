package tower;

import main.GamePanel;

public class Cannon extends Tower {

	public Cannon(GamePanel gp, int x, int y) {
		
		super(gp, x, y);
		
		// TOWER ATTRIBUTES						
		name = "Cannon";						// NAME OF THE TOWER
		range = 4 * gp.tileSize;				// ATTACK OF THE TOWER (RANGE IN PIXELS, BASED ON TILE SIZE)
		damage = 5;								// DAMAGE DEALT PER ATTACK
		attackSpd = 2;							// ATTACKS SPEED (ATTACKS PER SECOND)
		attackCd = 60 / attackSpd;				// COOLDOWN IN FRAMES
		
		// LOAD TOWER IMAGE
		tImage = setup("/towers/cannon");	// LOAD CANNON'S IMAGE FROM RESOURCES
	}
	
	public void upgrade() {
		upgrades++;
		tImage = setup("/towers/cannon-2");
	}
}
