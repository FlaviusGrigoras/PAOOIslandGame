package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.*;

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
    public EventHandler eHandler = new EventHandler(this);

    //SOUND
    Sound sound = new Sound();

    //Entity and object
    public Player player;
    public Entity[] obj = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    //Game state

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogState = 3;
    public final int characterState = 4;

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
        aSetter.setMonster();
        gameState = titleState;


        playMusic(0, -20.0f);
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
            //MONSTER
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].alive) {
                        monster[i].update();
                    } else {
                        monster[i] = null;
                    }
                }
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

            // ADD ENTITIES TO THE LIST
            entityList.add(player);
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);

                }
            }
            for (Entity value : obj) {
                if (value != null) {
                    entityList.add(value);
                }
            }
            for (Entity entity : monster) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            //EMPTY ENTITY LIST
            entityList.clear();
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

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void playMusic(int i, float volume) {
        sound.setFile(i);
        sound.setVolume(volume);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}
