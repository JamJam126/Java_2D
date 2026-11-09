package enemy;

import main.GamePanel;

public class Basic extends Enemy {
	
	public Basic(GamePanel gp, int x, int y) {
		
		super(gp, x, y);
		
		// TODO Auto-generated constructor stub
		direction = "right";
		name = "Basic";
		speed = 2;
		totalHealth = 10;
		health = totalHealth;
		rewardMoney = 23;
		eNormalImage = setup("/enemy/basic");
		eDamagedImage1 = setup("/enemy/basic_damage_1");
		eDamagedImage2 = setup("/enemy/basic_damage_2");
		
		eImage = eNormalImage;
	}
	
	
	public void setAction() {
		
		if (x == ( gp.maxScreenCol - 10 ) * gp.tileSize) {
			direction = "down";
		}

	}

}
