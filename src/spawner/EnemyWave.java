package spawner;

import enemy.Basic;
import main.GamePanel;

public class EnemyWave {
	
	WaveManager wManager;
	GamePanel gp;
	
	int waveId;       		// WAVE NUMBER
	int spawnCount;       	// NUMBER OF ENEMIES TO SPAWN
	int spawnRate;		    // INTERVALS BETWEEN SPAWNS
	
	private int spawnCounter = 0;
	private int spawned = 0;
	private boolean isStarted = false;  	// FLAG WEATHER THE WAVE HAS STARTED
	private int waveElapsedTicks = 0;
	private int waveDurationTicks = 3600;   // WAVE DURATION (60 FPS -> 3600/MIN)
//	int enemyType;
	
	public EnemyWave(WaveManager wManager, int waveId, int spawnCount, int spawnRate) {

		this.wManager = wManager;
		this.gp = wManager.gp;
		this.waveId = waveId;
		this.spawnCount = spawnCount;
		this.spawnRate = spawnRate;
	}
	
	// ========== PUBLIC METHODS ========== //
	public void update () {
		
		if (!isStarted || isComplete()) return;
		
		spawnCounter++;
		waveElapsedTicks++;
		
		if (spawnCounter >= spawnRate) {
			spawnEnemy();
			spawnCounter = 0;
			spawned++;
		}
		
//		if (waveElapsedTicks >= waveDurationTicks) wManager.startNextWave();
		
	}	
	public void start () {
		
		this.isStarted = true;
	}		
	public boolean isComplete () {
		
		return spawned >= spawnCount;
	}	
	public boolean canSkip () {
		
//		return waveElapsedTicks >= 1200;
		return (waveElapsedTicks >= 300 && isStarted) || isComplete();
	}

	// ========== PRIVATE METHODS ========== //
	private void spawnEnemy () {
		System.out.println("Spawned");
		
		this.gp.enemyList.add(new Basic(gp, 0 * gp.tileSize, 5 * gp.tileSize - (gp.tileSize / 2) / 2));
	}
}
