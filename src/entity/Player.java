package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;
import tile.MapGenerator;
import tile.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public boolean isWalking = false;
    public int hasCoin = 0;
    int hasIron = 0;
    int hasStone = 0;
    int hasWood = 0;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH, int characterNumber) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        int[] coordinates = new int[2];
        attacking = false;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(9, 18, 30, 30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        /*attackArea.width = 36;
        attackArea.height = 36;*/

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
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues(int[] coordinates) {
        worldX = gp.tileSize * coordinates[0];
        worldY = gp.tileSize * coordinates[1];

        speed = 4;
        direction = "down";

        //Player status
        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1; // More strength -> More Damage
        dexterity = 1;// More Dexterity -> Less Damage
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Stick(gp);
        currentShield = new OBJ_Patura(gp);
        projectile = new OBJ_Fireball(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage() {
// Pentru directia 'up'
        w_up[0] = setup("player", "Kid1", "Walk", "up_1", gp.tileSize, gp.tileSize);
        w_up[1] = setup("player", "Kid1", "Walk", "up_2", gp.tileSize, gp.tileSize);
        w_up[2] = setup("player", "Kid1", "Walk", "up_3", gp.tileSize, gp.tileSize);
        w_up[3] = setup("player", "Kid1", "Walk", "up_4", gp.tileSize, gp.tileSize);

        i_up[0] = setup("player", "Kid1", "Idle", "up_1", gp.tileSize, gp.tileSize);
        i_up[1] = setup("player", "Kid1", "Idle", "up_2", gp.tileSize, gp.tileSize);
        i_up[2] = setup("player", "Kid1", "Idle", "up_3", gp.tileSize, gp.tileSize);
        i_up[3] = setup("player", "Kid1", "Idle", "up_4", gp.tileSize, gp.tileSize);

// Pentru directia 'down'
        w_down[0] = setup("player", "Kid1", "Walk", "down_1", gp.tileSize, gp.tileSize);
        w_down[1] = setup("player", "Kid1", "Walk", "down_2", gp.tileSize, gp.tileSize);
        w_down[2] = setup("player", "Kid1", "Walk", "down_3", gp.tileSize, gp.tileSize);
        w_down[3] = setup("player", "Kid1", "Walk", "down_4", gp.tileSize, gp.tileSize);

        i_down[0] = setup("player", "Kid1", "Idle", "down_1", gp.tileSize, gp.tileSize);
        i_down[1] = setup("player", "Kid1", "Idle", "down_2", gp.tileSize, gp.tileSize);
        i_down[2] = setup("player", "Kid1", "Idle", "down_3", gp.tileSize, gp.tileSize);
        i_down[3] = setup("player", "Kid1", "Idle", "down_4", gp.tileSize, gp.tileSize);

// Pentru directia 'left'
        w_left[0] = setup("player", "Kid1", "Walk", "left_1", gp.tileSize, gp.tileSize);
        w_left[1] = setup("player", "Kid1", "Walk", "left_2", gp.tileSize, gp.tileSize);
        w_left[2] = setup("player", "Kid1", "Walk", "left_3", gp.tileSize, gp.tileSize);
        w_left[3] = setup("player", "Kid1", "Walk", "left_4", gp.tileSize, gp.tileSize);

        i_left[0] = setup("player", "Kid1", "Idle", "left_1", gp.tileSize, gp.tileSize);
        i_left[1] = setup("player", "Kid1", "Idle", "left_2", gp.tileSize, gp.tileSize);
        i_left[2] = setup("player", "Kid1", "Idle", "left_3", gp.tileSize, gp.tileSize);
        i_left[3] = setup("player", "Kid1", "Idle", "left_4", gp.tileSize, gp.tileSize);

// Pentru directia 'right'
        w_right[0] = setup("player", "Kid1", "Walk", "right_1", gp.tileSize, gp.tileSize);
        w_right[1] = setup("player", "Kid1", "Walk", "right_2", gp.tileSize, gp.tileSize);
        w_right[2] = setup("player", "Kid1", "Walk", "right_3", gp.tileSize, gp.tileSize);
        w_right[3] = setup("player", "Kid1", "Walk", "right_4", gp.tileSize, gp.tileSize);

        i_right[0] = setup("player", "Kid1", "Idle", "right_1", gp.tileSize, gp.tileSize);
        i_right[1] = setup("player", "Kid1", "Idle", "right_2", gp.tileSize, gp.tileSize);
        i_right[2] = setup("player", "Kid1", "Idle", "right_3", gp.tileSize, gp.tileSize);
        i_right[3] = setup("player", "Kid1", "Idle", "right_4", gp.tileSize, gp.tileSize);


    }

    public void getPlayerAttackImage() {
        String Weapon = null;
        if (currentWeapon.type == type_sword) {
            Weapon = "Sword";
        }
        if (currentWeapon.type == type_axe) {
            Weapon = "Sword";
        }
        if (currentWeapon.type == type_pistol)
            Weapon = "Pistol";
        if (currentWeapon.type == type_fist)
            Weapon = "Fist";
        // Attack UP
        a_up[0] = setup("player", "Kid1", "Attack", Weapon, "up_1", gp.tileSize, gp.tileSize * 2);
        a_up[1] = setup("player", "Kid1", "Attack", Weapon, "up_2", gp.tileSize, gp.tileSize * 2);
        a_up[2] = setup("player", "Kid1", "Attack", Weapon, "up_3", gp.tileSize, gp.tileSize * 2);
        a_up[3] = setup("player", "Kid1", "Attack", Weapon, "up_4", gp.tileSize, gp.tileSize * 2);

// Attack DOWN
        a_down[0] = setup("player", "Kid1", "Attack", Weapon, "down_1", gp.tileSize, gp.tileSize * 2);
        a_down[1] = setup("player", "Kid1", "Attack", Weapon, "down_2", gp.tileSize, gp.tileSize * 2);
        a_down[2] = setup("player", "Kid1", "Attack", Weapon, "down_3", gp.tileSize, gp.tileSize * 2);
        a_down[3] = setup("player", "Kid1", "Attack", Weapon, "down_4", gp.tileSize, gp.tileSize * 2);

// Attack LEFT
        a_left[0] = setup("player", "Kid1", "Attack", Weapon, "left_1", gp.tileSize * 2, gp.tileSize);
        a_left[1] = setup("player", "Kid1", "Attack", Weapon, "left_2", gp.tileSize * 2, gp.tileSize);
        a_left[2] = setup("player", "Kid1", "Attack", Weapon, "left_3", gp.tileSize * 2, gp.tileSize);
        a_left[3] = setup("player", "Kid1", "Attack", Weapon, "left_4", gp.tileSize * 2, gp.tileSize);

// Attack RIGHT
        a_right[0] = setup("player", "Kid1", "Attack", Weapon, "right_1", gp.tileSize * 2, gp.tileSize);
        a_right[1] = setup("player", "Kid1", "Attack", Weapon, "right_2", gp.tileSize * 2, gp.tileSize);
        a_right[2] = setup("player", "Kid1", "Attack", Weapon, "right_3", gp.tileSize * 2, gp.tileSize);
        a_right[3] = setup("player", "Kid1", "Attack", Weapon, "right_4", gp.tileSize * 2, gp.tileSize);


    }


    public void update() {
        // Verifică dacă jucătorul atacă și execută animația corespunzătoare
        if (attacking) {
            attacking();
        }
        // Verifică dacă jucătorul se mișcă și determină direcția
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

        // Verifică coliziunile
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // Verifică coliziunile cu obiectele
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        // Verifică coliziunile cu NPC-urile
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        // Verifică coliziunile cu monștrii
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        contactMonster(monsterIndex);

        // Verifică evenimentele
        gp.eHandler.checkEvent();

        if (keyH.enterPressed && !attackCanceled) {
            gp.playSE(4);
            attacking = true;
            spriteCounter = 0;
        }
        attackCanceled = false;

        // Resetarea stării enterPressed pentru tastă
        gp.keyH.enterPressed = false;

        // Dacă nu există coliziuni, mișcă jucătorul în funcție de direcție și viteză
        if (!collisionOn && isWalking) {
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

        if (!attacking) {
            // Incrementarea spriteCounter pentru animație
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteCounter = 0;
                if (spriteNum < 4)
                    spriteNum++;
                else
                    spriteNum = 1;
            }
        }

        if (gp.keyH.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30) {
            // Set default Coordinates, direction and user
            projectile.set(worldX, worldY, direction, true, this);

            // Add it to the list
            gp.projectileList.add(projectile);
            shotAvailableCounter=0;
            gp.playSE(9);
        }

        // Verificarea stării de invincibilitate și resetarea acesteia după un interval de timp
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) { // Aproximativ 1 secundă la 60 de cadre pe secundă
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }

    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 10) {
            spriteNum = 1;
        }
        if (spriteCounter > 10 && spriteCounter <= 15) {
            spriteNum = 2;
        }
        if (spriteCounter > 15 && spriteCounter <= 18) {
            spriteNum = 3;
        }
        if (spriteCounter > 18 && spriteCounter <= 24) {
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

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            //Dupa verificarea coliziunii, resetare la valorile originale
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 24) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    public void pickUpObject(int i) {
        /*if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Coin":
                    hasCoin++;
                    gp.playSE(1);
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
        }*/
        if (i != 999) {
            String text;
            if (inventory.size() != maxInventorySize) {
                inventory.add(gp.obj[i]);
                gp.playSE(1);
                text = "Got a " + gp.obj[i].name + "!";
            } else {
                text = "You cannot carry any more!";
            }
            gp.ui.addMessage(text);
            gp.obj[i] = null;
        }
    }


    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if (!gp.monster[i].invincible) {
                gp.playSE(2);

                int damage = attack - gp.monster[i].defense;
                if (damage < 0) {
                    damage = 0;
                }

                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if (gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.monster[i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(6);
            gp.gameState = gp.dialogState;
            gp.ui.currentDialogue = "You are level " + level + " now!\n" + "You feel stronger!";
        }
    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot();

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if ((selectedItem.type == type_sword) || (selectedItem.type == type_axe) || (selectedItem.type == type_pistol) || selectedItem.type == type_fist) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if (selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == type_consumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible && !gp.monster[i].dying) {
                gp.playSE(3);

                int damage = gp.monster[i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }

                life -= damage;
                invincible = true;
            }
        }
    }

    private void interactNPC(int i) {
        if (gp.keyH.enterPressed) {
            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogState;
                gp.npc[i].speak();
            }
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (direction) {
            case "up":
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = a_up[0];
                    }
                    if (spriteNum == 2) {
                        image = a_up[1];
                    }
                    if (spriteNum == 3) {
                        image = a_up[2];
                    }
                    if (spriteNum == 4) {
                        image = a_up[3];
                    }
                } else if (isWalking) {
                    if (spriteNum == 1) {
                        image = w_up[0];
                    }
                    if (spriteNum == 2) {
                        image = w_up[1];
                    }
                    if (spriteNum == 3) {
                        image = w_up[2];
                    }
                    if (spriteNum == 4) {
                        image = w_up[3];
                    }
                } else {
                    if (spriteNum == 1) {
                        image = i_up[0];
                    }
                    if (spriteNum == 2) {
                        image = i_up[1];
                    }
                    if (spriteNum == 3) {
                        image = i_up[2];
                    }
                    if (spriteNum == 4) {
                        image = i_up[3];
                    }
                }
                break;
            case "down":
                if (attacking) {
                    if (spriteNum == 1) {
                        image = a_down[0];
                    }
                    if (spriteNum == 2) {
                        image = a_down[1];
                    }
                    if (spriteNum == 3) {
                        image = a_down[2];
                    }
                    if (spriteNum == 4) {
                        image = a_down[3];
                    }
                } else if (isWalking) {
                    if (spriteNum == 1) {
                        image = w_down[0];
                    }
                    if (spriteNum == 2) {
                        image = w_down[1];
                    }
                    if (spriteNum == 3) {
                        image = w_down[2];
                    }
                    if (spriteNum == 4) {
                        image = w_down[3];
                    }
                } else {
                    if (spriteNum == 1) {
                        image = i_down[0];
                    }
                    if (spriteNum == 2) {
                        image = i_down[1];
                    }
                    if (spriteNum == 3) {
                        image = i_down[2];
                    }
                    if (spriteNum == 4) {
                        image = i_down[3];
                    }
                }
                break;
            case "left":
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = a_left[0];
                    }
                    if (spriteNum == 2) {
                        image = a_left[1];
                    }
                    if (spriteNum == 3) {
                        image = a_left[2];
                    }
                    if (spriteNum == 4) {
                        image = a_left[3];
                    }
                } else if (isWalking) {
                    if (spriteNum == 1) {
                        image = w_left[0];
                    }
                    if (spriteNum == 2) {
                        image = w_left[1];
                    }
                    if (spriteNum == 3) {
                        image = w_left[2];
                    }
                    if (spriteNum == 4) {
                        image = w_left[3];
                    }
                } else {
                    if (spriteNum == 1) {
                        image = i_left[0];
                    }
                    if (spriteNum == 2) {
                        image = i_left[1];
                    }
                    if (spriteNum == 3) {
                        image = i_left[2];
                    }
                    if (spriteNum == 4) {
                        image = i_left[3];
                    }
                }
                break;
            case "right":
                if (attacking) {
                    if (spriteNum == 1) {
                        image = a_right[0];
                    }
                    if (spriteNum == 2) {
                        image = a_right[1];
                    }
                    if (spriteNum == 3) {
                        image = a_right[2];
                    }
                    if (spriteNum == 4) {
                        image = a_right[3];
                    }
                } else if (isWalking) {
                    if (spriteNum == 1) {
                        image = w_right[0];
                    }
                    if (spriteNum == 2) {
                        image = w_right[1];
                    }
                    if (spriteNum == 3) {
                        image = w_right[2];
                    }
                    if (spriteNum == 4) {
                        image = w_right[3];
                    }
                } else {
                    if (spriteNum == 1) {
                        image = i_right[0];
                    }
                    if (spriteNum == 2) {
                        image = i_right[1];
                    }
                    if (spriteNum == 3) {
                        image = i_right[2];
                    }
                    if (spriteNum == 4) {
                        image = i_right[3];
                    }
                }
                break;
            default:
                // Caz implicit pentru orice altă direcție necunoscută
                break;
        }
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}