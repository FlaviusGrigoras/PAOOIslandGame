package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {
    // Setări fereastră
    final int originalTileSize = 16; // 16px16p tile
    public final int scale = 3; // Scalez la 48px48x pentru o vizibilitate mai bună

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 800 / tileSize;
    public final int maxScreenRow = 600 / tileSize;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // Setări lume
    public final int maxWorldCol = 25;
    public final int maxWorldRow = 25;

    double FPS = 60;
    int Real_FPS;
    Font serif = new Font("Serif", Font.BOLD, 14);

    public TileManager tileM = new TileManager(this);

    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler=new EventHandler(this);

    //Entity and object
    public Player player;
    public SuperObject[] obj = new SuperObject[10];
    public Entity[] npc = new Entity[10];

    //Game state

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogState = 3;

    // Variabile pentru afișarea coordonatelor jucătorului
    int playerX;
    int playerY;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(0x689cfc));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void initializeCharacter(int characterNumber) {
        player = new Player(this, keyH, characterNumber);
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                Real_FPS = drawCount;
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {
        if (gameState == playState && player != null) {
            //Player
            player.update();
            //NPC
            for (Entity entity : npc)
                if (entity != null) {
                    entity.update();
                }
            // Actualizează coordonatele jucătorului
            playerX = player.worldX;
            playerY = player.worldY;
        }
        if (gameState == pauseState) {
            //Nothing
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Debug
        double drawStart = 0;
        if (keyH.DebugMode) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // Others
        else {
            // TILE
            if (tileM != null) { // Verifică dacă tileM nu este null
                tileM.draw(g2);
            } else {
                System.out.println("TileManager-ul nu a fost inițializat corespunzător.");
            }

            // OBJECT
            Arrays.stream(obj).filter(Objects::nonNull).forEach(superObject -> superObject.draw(g2, this));

            // NPC
            for (Entity entity : npc)
                if (entity != null)
                    entity.draw(g2);

            // PLAYER
            if (player != null) { // Verifică dacă player nu este null
                player.draw(g2);
            } else {
                System.out.println("Jucătorul nu a fost inițializat corespunzător.");
            }
        }

        // Debug
        if (keyH.DebugMode) {
            double drawEnd = System.nanoTime();
            double passed = (drawEnd - drawStart) / 1_000_000.0; // Convert to milliseconds
            String passedFormatted = String.format("%.2f", passed); // Format to 2 decimal places
            g2.setColor(Color.white);
            g2.setFont(serif);
            g2.drawString("Draw Time: " + passedFormatted + "ms", 10, 80);
            System.out.println("Draw Time: " + passedFormatted + "ms");

            g2.setFont(serif);
            g2.setColor(Color.white);
            int tileNum = tileM.map[playerX / tileSize][playerY / tileSize];
            g2.drawString("Tile: " + tileNum, 10, 60); // Afișează numărul de tile
        }

        // UI
        ui.draw(g2);
    }
}
