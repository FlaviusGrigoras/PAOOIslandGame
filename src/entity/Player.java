package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public boolean isWalking = false;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;

    public Player(GamePanel gp, KeyHandler keyH, int characterNumber) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        attacking = false;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(9, 18, 30, 30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        /*attackArea.width = 36;
        attackArea.height = 36;*/

        setDefaultPositions();
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        //Player status
        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1; // More strength -> More Damage
        dexterity = 1;// More Dexterity -> Less Damage
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        maxMana = 4;
        ammo = 10;
        mana = maxMana;
        currentWeapon = new OBJ_Stick(gp);
        currentShield = new OBJ_Patura(gp);

        currentLight = null;

        projectile = new OBJ_Fireball(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public void setDefaultPositions() {
        worldX = gp.tileSize * 43;
        worldY = gp.tileSize * 9;
        /*worldX = gp.tileSize * 18;
        worldY = gp.tileSize * 11;*/
        direction = "down";
    }

    public void restoreStatus() {
        life = maxLife;
        mana = maxMana;
        invincible = false;
        attacking = false;
        knockBack = false;
        lightUpdated = true;
    }

    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentWeapon) {
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldlot() {
        int currentShieldSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentShield) {
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }

    public void setItems() {
        inventory.clear();
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        motion3_duration = currentWeapon.motion3_duration;
        motion4_duration = currentWeapon.motion4_duration;
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

        // Coliziunea cu Tile Interactiv
        gp.cChecker.checkEntity(this, gp.iTile);

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
        if (gp.keyH.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.haveResource(this)) {
            // Set default Coordinates, direction and user
            projectile.set(worldX, worldY, direction, true, this);

            // SUBTRACT THE COST
            projectile.subtractResource(this);

            //CHECK VACANTY
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
            gp.playSE(9);
        }
        if (life > maxLife) {
            life = maxLife;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(11);
        }
    }


    public void damageProjectile(int i) {
        if (i != 999) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].isCorrectItem(this) && !gp.iTile[gp.currentMap][i].invincible) {
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;

            // Generate particle
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

            if (gp.iTile[gp.currentMap][i].life == 0) {
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            // PICKUP ONLY
            if (gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            } else {
                // INVENTORY ITEMS
                String text;
                if (canObtainItem(gp.obj[gp.currentMap][i])) {
                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                } else {
                    text = "You cannot carry any more!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }

    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {
        if (i != 999) {
            if (!gp.monster[gp.currentMap][i].invincible) {
                gp.playSE(2);

                if (knockBackPower > 0) {
                    setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if (damage < 0) {
                    damage = 0;
                }

                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
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
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

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
            if (selectedItem.type == type_light) {
                if (currentLight == selectedItem) {
                    currentLight = null;
                } else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            if (selectedItem.type == type_consumable) {
                if (selectedItem.use(this)) {
                    if (selectedItem.amount > 1) {
                        selectedItem.amount--;
                    } else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible && !gp.monster[gp.currentMap][i].dying) {
                gp.playSE(3);

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }

                life -= damage;
                invincible = true;
            }
        }
    }

    public void getSleepingImage(BufferedImage image) {
        for (int i = 0; i < 4; i++) {
            i_down[i] = image;
            i_up[i] = image;
            i_left[i] = image;
            i_right[i] = image;
        }

    }

    private void interactNPC(int i) {
        if (gp.keyH.enterPressed) {
            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogState;
                gp.npc[gp.currentMap][i].speak();
            }
        }
    }

    public int searchItemInInventory(String itemName) {
        int itemIndex = 999;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public boolean canObtainItem(Entity item) {
        boolean canObtain = false;

        // Check if stackable
        if (item.stackable == true) {
            int index = searchItemInInventory(item.name);

            if (index != 999) {
                inventory.get(index).amount++;
                canObtain = true;
            } else {
                // New Item
                if (inventory.size() != maxInventorySize) {
                    inventory.add(item);
                    canObtain = true;
                }
            }
        } else {
            // Not stackable
            if (inventory.size() != maxInventorySize) {
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
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