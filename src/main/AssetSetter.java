package main;

import enemy.Basic;
import tower.Cannon;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void setObject() {
		
	}
	
	public void setTower() {
		
		gp.towerList.add(new Cannon(gp, 7 * gp.tileSize, 6 * gp.tileSize));
		gp.towerList.add(new Cannon(gp, 9 * gp.tileSize, 6 * gp.tileSize));
	}
	
	public void setEnemy() {
		
//		Basic basic1 = new Basic(gp, 0 * gp.tileSize, 1 * gp.tileSize + (gp.tileSize / 2) / 2 );
//		Basic basic2 = new Basic(gp, 0 * gp.tileSize, 1 * gp.tileSize - (gp.tileSize / 2) / 2);
//		
//		gp.enemyList.add(basic1);
//		gp.enemyList.add(basic2);
//		gp.enemy[0] = new Basic(gp, 0 * gp.tileSize, 1 * gp.tileSize + (gp.tileSize / 2) / 2 );
//		gp.enemy[1] = new Basic(gp, 0 * gp.tileSize, 1 * gp.tileSize - (gp.tileSize / 2) / 2);
		
//		gp.npc[0] = new NPC_OldMan(gp);
//		gp.npc[0].worldX = 21 * gp.tileSize;
//		gp.npc[0].worldY = 21 * gp.tileSize;
	}
}
