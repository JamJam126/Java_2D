package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	
	public Tile[] tile;
	public int mapTileNum[][];
	
	// RESOURCES
	GamePanel gp;
	UtilityTool uTool = new UtilityTool();
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];
		
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		getTileImage();
		loadMap("/maps/map.txt");
	}
	
	void getTileImage() {
		
		setup(0, "grass");
		setup(1, "earth");
	}
	
	public void setup(int index, String imageName) {
		
		try {
			
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			
		} catch(IOException e)	{
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); // Open a specific file and prepare read it line by line
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
				
				String line = br.readLine();
				
				while(col < gp.maxScreenCol) {
					
					String numbers[] = line.split(" "); // Split the string
					
					int num = Integer.parseInt(numbers[col]); // Change string into int

					mapTileNum[col][row] = num;
					col ++;
					
//					System.out.println(num);
				}
				if (col == gp.maxScreenCol) {
					col = 0;
					row ++;
				}
				
			}
			br.close();
			
		} catch (Exception e) {
			
		}		
	}
	
	public void draw(Graphics2D g2) {

		int col = 0;
		int row = 0;

		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
			
			int screenX = col * gp.tileSize;
			int screenY = row * gp.tileSize;

			g2.drawImage(tile[mapTileNum[col][row]].image, screenX, screenY, null);
			col ++;
			
			if (col == gp.maxScreenCol) {
				
				col = 0;
				row ++;
			}
		}
	}
	
}
