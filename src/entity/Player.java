package entity;

import main.GamePanel;
import main.KeyHandler;
import tile.MapGenerator;
import tile.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public BufferedImage idleSprite, walkSprite;
    public boolean isWalking = false;
    public int hasCoin = 0;
    int hasIron = 0;
    int hasStone = 0;
    int hasWood = 0;

    public Player(GamePanel gp, KeyHandler keyH, int characterNumber) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        int[] coordinates = new int[2];

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(9, 18, 30, 30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        TileManager TileM = new TileManager(gp);
        coordinates = MapGenerator.chooseRandomIslandTile(TileM.map);
        int x = coordinates[0];
        int y = coordinates[1];

        while (TileM.map[x + 1][y + 1] != 4) {
            coordinates = MapGenerator.chooseRandomIslandTile(TileM.map);
            x = coordinates[0];
            y = coordinates[1];
            System.out.println("x= " + x + "y= " + y + "Tile= " + TileM.map[x][x]);
        }

        System.out.println("Coordonatele tile-ului de tip insulă aleator selectat pentru Player sunt: (" + (coordinates[0] + 1) + ", " + (coordinates[1] + 1) + "). Tile-ul are numarul: " + TileM.map[coordinates[0]][coordinates[1]]);

        setDefaultValues(coordinates);

        getPlayerImage(characterNumber);
    }

    public void setDefaultValues(int[] coordinates) {
        worldX = gp.tileSize * coordinates[0];
        worldY = gp.tileSize * coordinates[1];

        speed = 4;
        direction = "down";

        //Player status
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage(int characterNumber) {
        try {
            idleSprite = ImageIO.read(new File("res/player/Kid" + characterNumber + "_idle.png"));
            walkSprite = ImageIO.read(new File("res/player/Kid" + characterNumber + "_walk.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // Determine if the player is walking and the direction
        if (keyH.upPressed) {
            isWalking = true;
            direction = "up";
        } else if (keyH.downPressed) {
            isWalking = true;
            direction = "down";
        } else if (keyH.leftPressed) {
            isWalking = true;
            direction = "left";
        } else if (keyH.rightPressed) {
            isWalking = true;
            direction = "right";
        } else {
            isWalking = false;
        }

        // Check for collision
        collisionOn = false;
        gp.cChecker.checkTile(this);


        // Check for object collision
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        // Check npc collision

        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        //Check monster Collision
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        contactMonster(monsterIndex);

        // Check event
        gp.eHandler.checkEvent();
        gp.keyH.enterPressed = false;


        // If no collision, move the player based on the direction
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    if (isWalking)
                        worldY -= speed;
                    break;
                case "down":
                    if (isWalking)
                        worldY += speed;
                    break;
                case "left":
                    if (isWalking)
                        worldX -= speed;
                    break;
                case "right":
                    if (isWalking)
                        worldX += speed;
                    break;
            }
        }
        // Increment spriteCounter
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteCounter = 0; // Reset spriteCounter here
            if (spriteNum < 4)
                spriteNum++;
            else
                spriteNum = 1;
        }
        //Outside key if statement
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                life -= 1;
                invincible = true;
            }
        }
    }

    private void interactNPC(int i) {
        if (i != 999) {
            if (gp.keyH.enterPressed) {
                gp.gameState = gp.dialogState;
                gp.npc[i].speak();
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Coin":
                    hasCoin++;
                    gp.obj[i] = null;
                    break;
                case "Iron":
                    hasIron++;
                    gp.obj[i] = null;
                    break;
                case "Stone":
                    hasStone++;
                    gp.obj[i] = null;
                    break;
                case "Wood":
                    hasWood++;
                    gp.obj[i] = null;
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        //Cod optimizat de la 145 de linii de cod la doar 8
        BufferedImage spriteSheet = isWalking ? walkSprite : idleSprite;
        int yOffset = direction.equals("up") ? 48 : (direction.equals("down") ? 0 : (direction.equals("left") ? 16 : 32));

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        if (isWalking || spriteNum == 1 || spriteNum == 2 || spriteNum == 3 || spriteNum == 4) {
            int xOffset = (spriteNum - 1) * 16;
            g2.drawImage(spriteSheet.getSubimage(xOffset, yOffset, 16, 16), screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //DEBUG
        //g2.setFont(new Font("Arial", Font.PLAIN, 26));
        //g2.setColor(Color.white);
        //g2.drawString("Invicible:" + invincibleCounter, 10, 400);
    }
}