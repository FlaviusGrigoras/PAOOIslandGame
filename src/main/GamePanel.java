package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Setări fereastră
    final int originalTileSize = 16; // 16px16p tile
    public final int scale = 3; // Scalez la 48px48x pentru o vizibilitate mai bună

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 600/tileSize;
    public final int maxScreenRow = 400/tileSize;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // Setări lume
    public final int maxWorldCol = 255;
    public final int maxWorldRow = 255;
    public final int worldWidth=tileSize*maxWorldCol;
    public final int worldHeight=tileSize*maxWorldRow;

    double FPS = 60;

    public TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);

    // Variabile pentru afișarea coordonatelor jucătorului
    private int playerX;
    private int playerY;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(0x689cfc));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        // Inițializează coordonatele jucătorului
        playerX = player.screenX;
        playerY = player.screenY;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
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
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {
        player.update();

        // Actualizează coordonatele jucătorului
        playerX = player.worldX;
        playerY = player.worldY;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (tileM != null) { // Verifică dacă tileM nu este null
            tileM.draw(g2);
        } else {
            System.out.println("TileManager-ul nu a fost inițializat corespunzător.");
        }

        if (player != null) { // Verifică dacă player nu este null
            player.draw(g2);
        } else {
            System.out.println("Jucătorul nu a fost inițializat corespunzător.");
        }

        // Afișează coordonatele jucătorului și numărul de tile în colțul ecranului
        g2.setColor(Color.WHITE);
        g2.drawString("X: " + playerX/tileSize + ", Y: " + playerY/tileSize, 10, 20);

        // Obține numărul de tile din harta jocului folosind coordonatele jucătorului
        int tileNum = tileM.map[playerX / tileSize][playerY / tileSize];
        g2.drawString("Tile: " + tileNum, 10, 40); // Afișează numărul de tile
        g2.dispose();
    }

}
