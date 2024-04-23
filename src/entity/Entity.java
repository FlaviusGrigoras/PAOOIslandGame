package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Entity {
    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    // Imaginile pentru mers
    public BufferedImage[] w_up = new BufferedImage[4];
    public BufferedImage[] w_down = new BufferedImage[4];
    public BufferedImage[] w_left = new BufferedImage[4];
    public BufferedImage[] w_right = new BufferedImage[4];

    // Imaginile pentru stat in repaus
    public BufferedImage[] i_up = new BufferedImage[4];
    public BufferedImage[] i_down = new BufferedImage[4];
    public BufferedImage[] i_left = new BufferedImage[4];
    public BufferedImage[] i_right = new BufferedImage[4];

    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 0; // Am început de la 0 pentru a corespunde indexului în vectori
    public Rectangle solidArea = new Rectangle(9, 18, 30, 30);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean isWalking = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {

    }

    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

        if (!collisionOn) {
            if (isWalking) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
        }

        // Increment spriteCounter
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteCounter = 0; // Reset spriteCounter here
            spriteNum = (spriteNum + 1) % 4; // Schimbarea imaginii conform spriteNum
        }

    }

    public BufferedImage setup(String CharacterType, String CharacterType2, String StatusPath, String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/" + CharacterType + '/' + CharacterType2 + '/' + StatusPath + "/" + imageName + ".png"));
            //image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (isWalking) {
                        image = w_up[spriteNum];
                    } else {
                        image = i_up[spriteNum];
                    }
                    break;
                case "down":
                    if (isWalking) {
                        image = w_down[spriteNum];
                    } else {
                        image = i_down[spriteNum];
                    }
                    break;
                case "left":
                    if (isWalking) {
                        image = w_left[spriteNum];
                    } else {
                        image = i_left[spriteNum];
                    }
                    break;
                case "right":
                    if (isWalking) {
                        image = w_right[spriteNum];
                    } else {
                        image = i_right[spriteNum];
                    }
                    break;
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
