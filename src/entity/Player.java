package entity;

import main.GamePanel;
import main.KeyHandler;
import tile.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public BufferedImage idleSprite, walkSprite;
    public boolean isWalking = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(9, 18, 30, 30);

        int[] coordinates = TileManager.coordinates;
        setDefaultValues(coordinates);
        getPlayerImage();
    }

    public void setDefaultValues(int[] coordinates) {
        worldX = gp.tileSize * coordinates[0];
        worldY = gp.tileSize * coordinates[1];

        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            idleSprite = ImageIO.read(new File("res/player/Kid01_idle.png"));
            walkSprite = ImageIO.read(new File("res/player/Kid01_walk.png"));
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

        // Check for collisions
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // If no collision, move the player based on the direction
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    if(isWalking)
                        worldY -= speed;
                    break;
                case "down":
                    if(isWalking)
                        worldY += speed;
                    break;
                case "left":
                    if(isWalking)
                        worldX -= speed;
                    break;
                case "right":
                    if(isWalking)
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
    }




    public void draw(Graphics2D g2) {
        //Cod optimizat de la 145 de linii de cod la doar 8
        BufferedImage spriteSheet = isWalking ? walkSprite : idleSprite;
        int yOffset = direction.equals("up") ? 48 : (direction.equals("down") ? 0 : (direction.equals("left") ? 16 : 32));

        if (isWalking || spriteNum == 1 || spriteNum == 2 || spriteNum == 3 || spriteNum == 4) {
            int xOffset = (spriteNum - 1) * 16;
            g2.drawImage(spriteSheet.getSubimage(xOffset, yOffset, 16, 16), screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
