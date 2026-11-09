package main;

import java.awt.*;

import spawner.WaveManager;
import tower.Tower;

public class UI {

	// CONSTANTS
	private static final Font FONT_ARIAL_18P = new Font("Arial", Font.PLAIN, 18);
	private static final Font FONT_ARIAL_12B = new Font("Arial", Font.BOLD, 12);
    private static final Font FONT_ARIAL_14B = new Font("Arial", Font.BOLD, 14);
    private static final Font FONT_ARIAL_18B = new Font("Arial", Font.BOLD, 18);
    private static final Font FONT_ARIAL_24B = new Font("Arial", Font.BOLD, 24);

    // REFERENCES
    private final GamePanel gp;
    private WaveManager wManager;
    public TowerPanel tPanel;
    
    private Rectangle startWaveButton;

    public UI(GamePanel gp, WaveManager wManager) {
    	
        this.gp = gp;
        this.wManager = wManager;
        tPanel = new TowerPanel(gp);
    }

    // ========== PUBLIC METHODS ========== //
    public void draw(Graphics2D g2) {
    	
        // DRAW THE CURRENT WAVE TEXT
    	drawWaveLabel(g2);
    	
    	// DRAW THE START NEXT WAVR BUTTON
    	if (this.wManager.canStartNextWave()) 
    		drawStartWaveButton(g2);
    	
    	// DRAW THE PLAYER'S HP BAR
    	drawPlayerHPBar(g2);
    	
    	// DRAW TOWER SLOTS CONTAINER
    	drawTowerSlots(g2);
    	
    	// DRAW PLAYER'S BALANCE
    	drawPlayerBalance(g2);
    }    
    public void drawTowerPanel(Graphics2D g2, Tower tower) {
    	
    	tPanel.setTower(tower);
    	tPanel.draw(g2);
    }
    
    // ========== PRIVATE METHODS ========== //
    private void drawWaveLabel(Graphics2D g2) {
        
    	// TEXT, FONT AND TEXT METRICS 
    	String waveLabel = "Wave: " + this.wManager.waveNumber;
    	g2.setFont(FONT_ARIAL_18P);
        FontMetrics metrics = g2.getFontMetrics();
        
        // TEXT POSITION
        int textX = (gp.screenWidth - metrics.stringWidth(waveLabel)) / 2;  // center horizontally
        int textY = 24;

        // TEXT SHADOW
        g2.setColor(new Color(0, 0, 0, 150)); 
        g2.drawString(waveLabel, textX + 2, textY + 2);

        // TEXT
        g2.setColor(Color.WHITE);
        g2.drawString(waveLabel, textX, textY);
    }
    private void drawStartWaveButton(Graphics2D g2) {

        // BUTTON TEXT AND SIZE
        String buttonText = "Start Wave";
        int buttonWidth = 160;
        int buttonHeight = 36;
        
    	// TEXT FONT AND METRICS
    	g2.setFont(FONT_ARIAL_24B);
        FontMetrics metrics = g2.getFontMetrics();
        
        // BUTTON POSITION
        int buttonX = gp.screenWidth - 32 - buttonWidth; 
        int buttonY = 48 - buttonHeight + (metrics.getHeight() / 2); 

        // SET UP THE BUTTON RECT
        this.startWaveButton = setUpButtonRects(buttonX, buttonY, buttonWidth, buttonHeight);

        // BUTTON COLOR
        Color buttonFill = new Color(0, 128, 0); 
        g2.setColor(buttonFill);
        g2.fill(this.startWaveButton);

        // BUTTON BORDER
        g2.setColor(new Color(0, 100, 0));
        g2.draw(this.startWaveButton);

        // TEXT SHADOW
        g2.setColor(new Color(0, 0, 0, 150));
        g2.drawString(
            buttonText,
            startWaveButton.x + (startWaveButton.width - metrics.stringWidth(buttonText)) / 2 + 1,
            startWaveButton.y + (startWaveButton.height - metrics.getHeight()) / 2 + metrics.getAscent() + 1
        );

        // TEXT
        g2.setColor(Color.WHITE);
        g2.drawString(
            buttonText,
            startWaveButton.x + (startWaveButton.width - metrics.stringWidth(buttonText)) / 2,
            startWaveButton.y + (startWaveButton.height - metrics.getHeight()) / 2 + metrics.getAscent()
        );
    }
    private void drawPlayerHPBar(Graphics2D g2) {
    	
        // BAR SIZE
        int barWidth = 480;
        int barHeight = 16;

        // BAR POSITION
        int barX = (gp.screenWidth - barWidth) / 2;
        int barY = 32;

        // CURRENT HP (EXAMPLE)
        int currentHP = gp.player.currentHP;
        int maxHP = gp.player.totalHP;
        float hpPercent = (float) currentHP / maxHP;

        // FULL BAR BACKGROUND
        g2.setColor(new Color(60, 60, 60));
        g2.fillRect(barX, barY, barWidth, barHeight);

        // FILLED HP BAR
        int filledWidth = (int) (barWidth * hpPercent);
        g2.setColor(new Color(0, 180, 0)); 
        g2.fillRect(barX, barY, filledWidth, barHeight);

        // BAR BORDER
        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY, barWidth, barHeight);

        // TEXT AND FONT METRICS
        String hpText = currentHP + "/" + maxHP;
        g2.setFont(FONT_ARIAL_12B);
        FontMetrics metrics = g2.getFontMetrics();

        // TEXT POSITION (CENTER OF THE BAR)
        int textX = barX + (barWidth - metrics.stringWidth(hpText)) / 2;
        int textY = barY + (barHeight - metrics.getHeight()) / 2 + metrics.getAscent();

        // TEXT SHADOW
        g2.setColor(new Color(0, 0, 0, 150));
        g2.drawString(hpText, textX + 1, textY + 1);

        // TEXT
        g2.setColor(Color.WHITE);
        g2.drawString(hpText, textX, textY);
    }
    private void drawTowerSlots(Graphics2D g2) {
        
    	// TOWER SLOTS 
    	int slotSize = gp.tileSize;
        int slotSpacing = 12;
        int numSlots = 6;
        
        // SLOTS CONTAINER SIZE
        int paddingX = 24;
        int paddingY = 12;
        int totalSlotWidth = numSlots * slotSize + (numSlots - 1) * slotSpacing;
        int containerWidth = totalSlotWidth + paddingX * 2;
        int containerHeight = slotSize + paddingY * 2;

        // SLOTS CONTAINER POSITION
        int containerX = (gp.screenWidth - containerWidth) / 2;
        int containerY = gp.screenHeight - containerHeight - 16; 

        // DRAW CONTAINER
        g2.setColor(new Color(0, 0, 0, 200)); 
        g2.fillRoundRect(containerX, containerY, containerWidth, containerHeight, 6, 6);

        // DRAW CONTAINER BORDER
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(containerX, containerY, containerWidth, containerHeight, 6, 6);

        // DRAW THE 6 TOWER SLOTS INSIDE THE CONTAINER
        int startX = containerX + paddingX;
        int slotY = containerY + paddingY;

        for (int i = 0; i < numSlots; i++) {
        	
            int x = startX + i * (slotSize + slotSpacing);
            g2.setColor(new Color(100, 100, 100, 80));
            g2.fillRect(x, slotY, slotSize, slotSize);

            g2.setColor(Color.WHITE);
            g2.drawRect(x, slotY, slotSize, slotSize);
        }
    }
    private void drawPlayerBalance(Graphics2D g2) {
    	
    	String balance = gp.player.balance + "$";
    	g2.setFont(FONT_ARIAL_18B);
    	g2.setColor(new Color(255, 234, 0));
    	FontMetrics metrics = g2.getFontMetrics();
    	
    	g2.drawString(
    		balance,
    		(gp.screenWidth - metrics.stringWidth(balance)) / 2,
    		gp.screenHeight - 86 - 12 // 86 is the y point of the tower slot container and 12 is the spacing between them
    	);
    }
    
    // ========== INPUT HELPERS ========== //
    public boolean handleTowerUpgrade(int mx, int my, Tower selectedTower) {
    	
    	if (selectedTower != null) {
    		return tPanel.handleUpgrade(mx, my);
    	}
    	
    	return false;
    }
    public boolean handleStartWave(int mx, int my) {
    	
    	if (this.startWaveButton.contains(mx, my) && this.wManager.canStartNextWave()) {
    		
    		this.wManager.startNextWave();
    		return true;
    	} 	
    	return false;
    }
    
    // ========== SETUP ========== //
    private Rectangle setUpButtonRects(int x, int y, int width, int height) {
        
    	return new Rectangle(x, y, width, height);
    }
    
    private static class TowerPanel {
    	
    	private GamePanel gp;
    	private Tower tower;
    	
    	// CONSTANTS
        private static final Font FONT_ARIAL_14B = new Font("Arial", Font.BOLD, 14);
        private static final Font FONT_ARIAL_24B = new Font("Arial", Font.BOLD, 24);
        
        private static final int BUTTON_WIDTH = 64;
        private static final int BUTTON_HEIGHT = 24;
        private static final int BUTTON_SPACING = 16;
        private static final int PANEL_MARGIN = 16;
        private static final int LABEL_HEIGHT = 32;
    	
        // LAYOUT
        private int tPanelWidth, tPanelHeight, tPanelX, tPanelY;

        // UI ELEMENTS
        public Rectangle sellButtonRect, targetButtonRect, upgradeButtonRect;
        private Rectangle damageLabelRect, killLabelRect;
        private Rectangle portraitRect;
        private Rectangle attackStatRect, rangeStatRect, speedStatRect;
    	
    	public TowerPanel(GamePanel gp) {
    		
    		this.gp = gp;
    		setupLayout();
    	}
    	
    	private void setupLayout() {
        	
            tPanelX = PANEL_MARGIN;
            tPanelWidth = 7 * gp.tileSize;
            tPanelHeight = 5 * gp.tileSize;
            tPanelY = gp.screenHeight / 2 - tPanelHeight / 2;

            int buttonY = tPanelY + tPanelHeight - (20 + PANEL_MARGIN);      
            int labelWidth = BUTTON_WIDTH * 2 + BUTTON_SPACING;

            int portraitWidth = gp.tileSize * 3;
            int portraitHeight = gp.tileSize * 3;
            
            sellButtonRect = new Rectangle(30, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);
            
            targetButtonRect = new Rectangle(
            	sellButtonRect.x + BUTTON_WIDTH + BUTTON_SPACING,
            	buttonY,
            	BUTTON_WIDTH,
            	BUTTON_HEIGHT
            );
            
            upgradeButtonRect = new Rectangle(
            	targetButtonRect.x + BUTTON_WIDTH + BUTTON_SPACING,
            	buttonY - 32,
            	(tPanelWidth / 2) - 18,
            	gp.tileSize + 8
            );
            
            damageLabelRect = new Rectangle(30, buttonY - BUTTON_HEIGHT - BUTTON_SPACING / 2, labelWidth, BUTTON_HEIGHT);

            portraitRect = new Rectangle(30, tPanelY + 14, portraitWidth, portraitHeight);
            
            // STAT LABELS
            int statLabelWidth = (tPanelWidth - (4 * 16) + 20) / 3;
            int statLabelY = tPanelY - (LABEL_HEIGHT + 14);
            
            attackStatRect = new Rectangle(tPanelX, statLabelY, statLabelWidth + 5, LABEL_HEIGHT);
            rangeStatRect = new Rectangle(attackStatRect.x + statLabelWidth + 22, statLabelY, statLabelWidth, LABEL_HEIGHT);
            speedStatRect = new Rectangle(rangeStatRect.x + statLabelWidth + 22, statLabelY, statLabelWidth, LABEL_HEIGHT);
        }
    	
        public void draw(Graphics2D g2) {
        	
            drawPanelBackground(g2);
            drawPortrait(g2);
            drawButtons(g2);
            drawLabels(g2);
            
            // DEBUG
        //    System.out.println(selectedTower.damage);
        }
        
        public void setTower(Tower tower) {
        	this.tower = tower;
        }
        
        public boolean handleUpgrade (int mx, int my) {
        	
        	if (upgradeButtonRect.contains(mx, my)) {
        		this.tower.upgrade();
        		return true;
        	}
        	return false;
        }

        // DRAW PANEL BACKGROUND
        private void drawPanelBackground(Graphics2D g2) {
        	
            g2.setColor(new Color(32, 32, 32));
            g2.fillRect(tPanelX, tPanelY, tPanelWidth, tPanelHeight);
            
            g2.setColor(Color.WHITE);
            g2.drawRect(tPanelX, tPanelY, tPanelWidth, tPanelHeight);
        }

        // DRAW POTRAIT
        private void drawPortrait(Graphics2D g2) {
        	
        	g2.setColor(Color.WHITE);
        	g2.draw(portraitRect);
        }
        
        // DRAW BUTTONS
        private void drawButtons(Graphics2D g2) {
            
        	g2.setFont(FONT_ARIAL_14B);
            FontMetrics metrics = g2.getFontMetrics();

            drawButton(g2, sellButtonRect, "Sell", Color.RED, metrics);
            drawButton(g2, targetButtonRect, "First", Color.GRAY, metrics);

        	g2.setFont(new Font("Arial", Font.BOLD, 24));
        	metrics = g2.getFontMetrics();
            drawButton(g2, upgradeButtonRect, "Upgrade", new Color(0x09bd0e), metrics);
            
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            g2.drawString("Upgrade [" + tower.upgrades +"]", portraitRect.x + portraitRect.width + 12, tPanelY + 32);
        }
        
        // DRAW LABELS
        private void drawLabels(Graphics2D g2) {
            
            g2.setColor(Color.WHITE);
            g2.draw(damageLabelRect);
            
            // FILL AND DRAW STAT LABELS
        	g2.setColor(new Color(32, 32, 32));
            g2.fill(attackStatRect);
            g2.fill(rangeStatRect);
            g2.fill(speedStatRect);
            
            g2.setColor(Color.WHITE);
            g2.draw(attackStatRect);
            g2.draw(rangeStatRect);
            g2.draw(speedStatRect);
            
            // DRAW STAT VALUES
            drawStatLabel(g2, attackStatRect, "Atk: " + tower.damage);
            drawStatLabel(g2, rangeStatRect, "Rng: " + tower.range);
            drawStatLabel(g2, speedStatRect, "Spd: " + tower.attackSpd);
        }
        
        // FUNCTION TO DRAW EACH BUTTON
        private void drawButton(Graphics2D g2, Rectangle rect, String text, Color color, FontMetrics metrics) {

        	g2.setColor(color);
            g2.fill(rect);
            g2.setColor(Color.WHITE);
            g2.draw(rect);

            int textX = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
            int textY = rect.y + (rect.height - metrics.getHeight()) / 2 + metrics.getAscent() + 2;
            
            g2.drawString(text, textX, textY);
        }
        
        // FUNCTION TO DRAW EACH LABEL
        private void drawStatLabel(Graphics2D g2, Rectangle labelRect, String text) {
        	
            g2.setFont(FONT_ARIAL_14B);
            FontMetrics metrics = g2.getFontMetrics();
            
            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getHeight();
            
            int textX = labelRect.x + (labelRect.width - textWidth) / 2;
            int textY = labelRect.y + (labelRect.height - textHeight) / 2 + metrics.getAscent();
            
            g2.drawString(text, textX, textY);
        }
    }
}
