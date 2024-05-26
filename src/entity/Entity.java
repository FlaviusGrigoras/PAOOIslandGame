package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Entity {
    GamePanel gp;
    public int worldX, worldY;
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

    // Imaginile pentru atac
    public BufferedImage[] a_up = new BufferedImage[4];
    public BufferedImage[] a_down = new BufferedImage[4];
    public BufferedImage[] a_left = new BufferedImage[4];
    public BufferedImage[] a_right = new BufferedImage[4];

    public BufferedImage image, image2, image3;
    public boolean collision = false;
    boolean hpBarOn = false;
    public boolean invincible = false;

    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    //COUNTER
    public int actionLockCounter = 0;
    public int spriteCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    public int shotAvailableCounter = 0;
    int knockBackCounter = 0;

    //Character Attributes
    public String name;
    public int defaultSpeed;
    public int speed;
    public int maxLife;
    public int life;
    public int level;
    public int ammo;
    public int strength;
    public int dexterity;
    public int attack, defense, exp, nextLevelExp, coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public int maxMana;
    public int mana;
    public Projectile projectile;
    public boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;
    public Entity attacker;
    public Entity linkedEntity;
    public int motion1_duration;
    public int motion2_duration;
    public int motion3_duration;
    public int motion4_duration;


    //ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int value;
    public int price;
    public int sellPrice;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;

    public String direction = "down";

    public int spriteNum = 0; // Am început de la 0 pentru a corespunde indexului în vectori
    public Rectangle solidArea = new Rectangle(9, 18, 30, 30);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean isWalking = false;
    public String[] dialogues = new String[20];
    int dialogueIndex = 0;
    public int knockBackPower = 0;

    //TYPE
    public int type; // 0=player, 1= npc, 2=monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pistol = 7;
    public final int type_fist = 8;
    public final int type_pickupOnly = 9;
    public final int type_obstacle = 10;
    public final int type_light = 11;
    public final int type_pickaxe = 12;

    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {

    }

    public void damageReaction() {

    }

    public boolean use(Entity entity) {
        return false;
    }

    public void checkDrop() {
    }

    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX; // Dead monster worldX
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public int getXdistance(Entity target) {
        return Math.abs(worldX - target.worldX);
    }

    public int getYdistance(Entity target) {
        return Math.abs(worldY - target.worldY);
    }

    public int getTileDistance(Entity target) {
        int tileDistance = (getXdistance(target) + getYdistance(target)) / gp.tileSize;
        return tileDistance;
    }

    public int getGoalCol(Entity target) {
        return (target.worldX + target.solidArea.x) / gp.tileSize;
    }

    public int getGoalRow(Entity target) {
        return (target.worldY + target.solidArea.y) / gp.tileSize;
    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = false;
            }
        }
    }

    public void checkStartChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = true;
            }
        }
    }

    public void setKnockBack(Entity target, Entity attacker, int knockBackPower) {
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;
    }


    public void getRandomDirection() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // 1-100
            int j = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }

            isWalking = j <= 75;

            actionLockCounter = 0;
        }
    }

    public void checkShootOrNot(int rate, int shotInterval) {
        int i = new Random().nextInt(rate);
        if (i == 0 && !projectile.alive && shotAvailableCounter == shotInterval) {
            projectile.set(worldX, worldY, direction, true, this);

            // CHECK VACANCY
            for (int ii = 0; ii < gp.projectile[1].length; ii++) {
                if (gp.projectile[gp.currentMap][ii] == null) {
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;
        }
    }

    public void checkAttackOrNot(int rate, int straight, int horizontal) {
        boolean targetInRange = false;
        int xDis = getXdistance(gp.player);
        int yDis = getYdistance(gp.player);

        switch (direction) {
            case "up":
                if (gp.player.worldY < worldY && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "down":
                if (gp.player.worldY > worldY && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "left":
                if (gp.player.worldX < worldX && xDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "right":
                if (gp.player.worldX > worldX && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
        }
        if (targetInRange) {
            // Check if it initiates an attack
            int i = new Random().nextInt(rate);
            if (i == 0) {
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }

    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == type_monster && contactPlayer) {
            damagePlayer(attack);
        }
    }

    public void update() {
        if (knockBack) {
            checkCollision();
            if (collisionOn) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else if (!collisionOn) {
                switch (knockBackDirection) {
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
            knockBackCounter++;
            if (knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else if (attacking) {
                attacking();
            }
        } else {
            setAction();
            checkCollision();

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

        // Verificarea stării de invincibilitate și resetarea acesteia după un interval de timp
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) { // Aproximativ 1 secundă la 40 de cadre pe secundă
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

    }

    public void damagePlayer(int attack) {
        if (!gp.player.invincible) {
            //Damage
            gp.playSE(3);

            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }

            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public BufferedImage setup(String CharacterType, String CharacterType2, String StatusPath, String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/" + CharacterType + '/' + CharacterType2 + '/' + StatusPath + "/" + imageName + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setup(String CharacterType, String CharacterType2, String StatusPath, String Weapon, String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/" + CharacterType + '/' + CharacterType2 + '/' + StatusPath + "/" + Weapon + "/" + imageName + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setup(String CharacterType, String CharacterType2, String StatusPath, String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/" + CharacterType + '/' + CharacterType2 + '/' + StatusPath + "/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setup(String CharacterType, String CharacterType2, String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/" + CharacterType + '/' + CharacterType2 + "/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public BufferedImage setup(String ObjectName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/objects/" + ObjectName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
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

            int tempScreenX = screenX;
            int tempScreenY = screenY;

            switch (direction) {
                case "up":
                    image = getUpImage();
                    break;
                case "down":
                    image = getDownImage();
                    break;
                case "left":
                    image = getLeftImage();
                    break;
                case "right":
                    image = getRightImage();
                    break;
                default:
                    break;
            }

            if (image != null) {
                // MONSTER HP Bar
                if (type == 2 && hpBarOn) {
                    double oneScale = (double) gp.tileSize / maxLife;
                    double hpBarValue = oneScale * life;

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                    hpBarCounter++;
                    if (hpBarCounter > 600) {
                        hpBarCounter = 0;
                        hpBarOn = false;
                    }
                }

                if (invincible) {
                    hpBarOn = true;
                    hpBarCounter = 0;
                    changeAlpha(g2, 0.4F);
                }
                if (dying)
                    dyingAnimation(g2);

                g2.drawImage(image, tempScreenX, tempScreenY, null);
                changeAlpha(g2, 1F);
            } else {
                System.out.println("Image is null for direction: " + direction + ", spriteNum: " + spriteNum);
            }
        }
    }

    public void move(String direction) {

    }

    private BufferedImage getUpImage() {
        if (attacking) {
            if (spriteNum >= 0 && spriteNum < a_up.length) {
                return a_up[spriteNum];
            }
        } else if (isWalking) {
            if (spriteNum >= 0 && spriteNum < w_up.length) {
                return w_up[spriteNum];
            }
        } else {
            if (spriteNum >= 0 && spriteNum < i_up.length) {
                return i_up[spriteNum];
            }
        }
        return null;
    }

    private BufferedImage getDownImage() {
        if (attacking) {
            if (spriteNum >= 0 && spriteNum < a_down.length) {
                return a_down[spriteNum];
            }
        } else if (isWalking) {
            if (spriteNum >= 0 && spriteNum < w_down.length) {
                return w_down[spriteNum];
            }
        } else {
            if (spriteNum >= 0 && spriteNum < i_down.length) {
                return i_down[spriteNum];
            }
        }
        return null;
    }

    private BufferedImage getLeftImage() {
        if (attacking) {
            if (spriteNum >= 0 && spriteNum < a_left.length) {
                return a_left[spriteNum];
            }
        } else if (isWalking) {
            if (spriteNum >= 0 && spriteNum < w_left.length) {
                return w_left[spriteNum];
            }
        } else {
            if (spriteNum >= 0 && spriteNum < i_left.length) {
                return i_left[spriteNum];
            }
        }
        return null;
    }

    private BufferedImage getRightImage() {
        if (attacking) {
            if (spriteNum >= 0 && spriteNum < a_right.length) {
                return a_right[spriteNum];
            }
        } else if (isWalking) {
            if (spriteNum >= 0 && spriteNum < w_right.length) {
                return w_right[spriteNum];
            }
        } else {
            if (spriteNum >= 0 && spriteNum < i_right.length) {
                return i_right[spriteNum];
            }
        }
        return null;
    }


    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= motion1_duration) {
            spriteNum = 1;
        }
        if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {
            spriteNum = 2;
        }
        if (spriteCounter > motion2_duration && spriteCounter <= motion3_duration) {
            spriteNum = 3;
        }
        if (spriteCounter > motion3_duration && spriteCounter <= motion4_duration) {
            spriteNum = 4;

            //Save current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player worldX/Y for the attackArea
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }
            //attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if (type == type_monster) {
                if (gp.cChecker.checkPlayer(this)) {
                    damagePlayer(attack);
                }
            } else {
                // Player
                // Check monster collision with the updated worldX, worldY, solidArea
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);

                // Check interactive tile collision
                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);

                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }


            //Dupa verificarea coliziunii, resetare la valorile originale
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > motion4_duration) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 10;

        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 8) {
            //dying=false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public Color getParticleColor() {
        Color color = null;
        return color;
    }

    public int getParticleSize() {
        int size = 0;
        return size;
    }

    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }

    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search()) {
            // Next worldX & worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                // left or right
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                // down or left
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // down or right
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }

            }
            /*// If he reaches the final, he stops
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;
            if (nextCol == goalCol && nextRow == goalRow) {
                onPath = false;
            }*/
        }
    }
}