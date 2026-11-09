package spawner;

import java.util.ArrayList;
import java.util.List;

import main.GamePanel;

public class WaveManager {

	GamePanel gp;

    private List<EnemyWave> waves = new ArrayList<>();
    private List<EnemyWave> activeWaves = new ArrayList<>();

    private int waveIndex = 0;
    public int waveNumber = 0;

    public WaveManager(GamePanel gp) {
    	
        this.gp = gp;
        setUpWaves();
    }
    
    // ========== PUBLIC METHODS ========== //
    public void update () {
    	
    	for (int i = 0; i < activeWaves.size(); i++) {
    		EnemyWave wave = activeWaves.get(i);
    		wave.update();
    		
    		if (wave.isComplete()) activeWaves.remove(i);
    	}
    }
	public void startNextWave () {
		
		// START THE VERY FIRST WAVE IF NONE HAS STARTED YET
		if (waveIndex == 0 && waveNumber == 0) {
			waveNumber++;
			EnemyWave startWave = waves.get(waveIndex);
			startWave.start();
			activeWaves.add(startWave);
		
		// START NEXT WAVE IF THERE ARE REMANING WAVES
		} else if (waveIndex < waves.size()) {
			waveIndex++;
			waveNumber++;
			EnemyWave nextWave = waves.get(waveIndex);
			nextWave.start();
			activeWaves.add(nextWave);
			System.out.println("Current Wave: " + waveNumber);	
		
		// STOP THE GAME IF ALL WAVES ARE CLEARED
		} else {
			System.out.println("Final Wave Reached!");
			gp.gameState = gp.pauseState;
		
		}
	}
	public EnemyWave getCurrentWave () {
		
		return waves.get(waveIndex);
	}
	public boolean canStartNextWave () {
		
		return (waveNumber == 0 || getCurrentWave().canSkip()) && waveNumber < waves.size();
	}
	
	// ========== PRIVATE METHODS ========== //
	
	// ========== SETUPS ========== //
	private void setUpWaves () {
		
		this.waves.add(new EnemyWave(this, 1, 5, 60));
		this.waves.add(new EnemyWave(this, 2, 5, 45));
		this.waves.add(new EnemyWave(this, 3, 5, 30));
		this.waves.add(new EnemyWave(this, 4, 5, 30));
	}
}
