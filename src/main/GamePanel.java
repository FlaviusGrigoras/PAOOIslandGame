package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class GamePanel extends JPanel implements Runnable {
    // Setări fereastră
    final int originalTileSize = 16; // 16px16p tile
    public final int scale = 3; // Scalez la 48px48x pentru o vizibilitate mai bună

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // Setări lume
    public final int maxWorldCol = 25;
    public final int maxWorldRow = 25;

    // FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

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
    Config config = new Config(this);

    //SOUND
    Sound sound = new Sound();
    Sound se = new Sound();

    //Entity and object
    public Player player;
    public Entity[] obj = new Entity[50];
    public Entity[] npc = new Entity[50];
    public Entity[] monster = new Entity[50];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public InteractiveTile[] iTile = new InteractiveTile[500];
    public ArrayList<Entity> particleList = new ArrayList<>();

    //Game state

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogState = 3;
    public final int characterState = 4;
    public final int optionState = 5;

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
        aSetter.setInteractiveTile();
        gameState = titleState;
        playMusic(0);

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn) {
            setFullScreen();
        }
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
                drawToTempScreen();
                drawToScreen();
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
                    if (monster[i].alive && !monster[i].dying) {
                        monster[i].update();
                    }
                    if (!monster[i].alive) {
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }

            //Projectile
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive) {
                        projectileList.get(i).update();
                    }
                    if (!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }

            for (int i = 0; i < iTile.length; i++) {
                if (iTile[i] != null) {
                    iTile[i].update();
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

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }


    public void setFullScreen() {
        // get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void drawToTempScreen() {
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
            if (tileM != null) {
                tileM.draw(g2);
            } else {
                System.out.println("TileManager-ul nu a fost inițializat corespunzător.");
            }

            // INTERACTIVE TILE
            for (int i = 0; i < iTile.length; i++) {
                if (iTile[i] != null) {
                    iTile[i].draw(g2);
                }
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

            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
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

    public void stopMusic() {
        sound.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}