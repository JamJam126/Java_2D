package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum [] [];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];
		
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		getTileImage();
		loadMap("/maps/map.txt");
	}
	
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}
	
	void getTileImage() {
		
		setup(0, "blue");
		setup(1, "green");
	}
	
	public void setup(int index, String imageName) {
		
		try {
			
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			
			
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

//		System.out.println(gp.maxScreenCol);
//		System.out.println(gp.maxScreenRow);
		int col = 0;
		int row = 0;
//		boolean a = true;
//		for (int i = 0; i < gp.maxScreenCol; i++) {
//			for (int j = 0; j < gp.maxScreenRow; j++) {
//				System.out.println(i);
//				System.out.println(j);
//				screenX = i * gp.tileSize;
//				screenY = j * gp.tileSize;
//				if (a) {
//					g2.setColor(Color.green);
//					g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
//				}
//				else {
//					g2.setColor(Color.blue);
//					g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
//				}
//				a = !a;
//
//			}
//			
//		}
		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
			
			int screenX = col * gp.tileSize;
			int screenY = row * gp.tileSize;
//			if (a) {
//				
//				g2.drawImage(tile[0].image, screenX, screenY, null);
//			}
//			else {
//				g2.drawImage(tile[1].image, screenX, screenY, null);
//			}
//			a = !a;
			g2.drawImage(tile[mapTileNum[col][row]].image, screenX, screenY, null);
			col ++;
			
			if (col == gp.maxScreenCol) {
				
				col = 0;
				row ++;
			}
		}
	}
	
}
