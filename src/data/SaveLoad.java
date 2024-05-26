package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.io.*;

public class SaveLoad {
    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public Entity getObject(String itemName) {
        Entity obj = null;

        switch (itemName) {
            case "Coin":
                obj = new OBJ_Coin(gp);
                break;
            case "Stone":
                obj = new OBJ_Stone(gp);
                break;
            case "Wood":
                obj = new OBJ_Wood(gp);
                break;
            case "Iron":
                obj = new OBJ_Iron(gp);
                break;
            case "Woodcutter's Axe":
                obj = new OBJ_Axe(gp);
                break;
            case "Wood Shield":
                obj = new OBJ_Shield_Wood(gp);
                break;
            case "Blue Shield":
                obj = new OBJ_Shield_Blue(gp);
                break;
            case "Red Potion":
                obj = new OBJ_Potion_Red(gp);
                break;
            case "Pistol":
                obj = new OBJ_Pistol(gp);
                break;
            case "Stick":
                obj = new OBJ_Stick(gp);
                break;
            case "Patura":
                obj = new OBJ_Patura(gp);
                break;
            case "Heart":
                obj = new OBJ_Heart(gp);
                break;
            case "Mana":
                obj = new OBJ_ManaCrystal(gp);
                break;
            case "Swood Normal":
                obj = new OBJ_Sword_Normal(gp);
                break;
            case "Lantern":
                obj = new OBJ_Lantern(gp);
                break;
            case "Tent":
                obj = new OBJ_Tent(gp);
                break;
            case "Default":
                System.out.println("Nu exista obiectul " + itemName);
        }
        return obj;
    }

    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

            DataStorage ds = new DataStorage();

            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coin = gp.player.coin;
            ds.worldX = gp.player.worldX;
            ds.worldY = gp.player.worldY;

            // PLAYER INVENTORY
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemAmounts.add(gp.player.inventory.get(i).amount);
            }
            // Player Equipment
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.player.getCurrentShieldlot();

            // Objects on map
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (gp.obj[mapNum][i] == null) {
                        ds.mapObjectNames[mapNum][i] = "NA";
                    } else {
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                    }
                }
            }
            // Write the DataStorage object
            oos.writeObject(ds);
        } catch (Exception e) {
            System.out.println("Save error!");
        }
    }

    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            // Read the DataStorage object
            DataStorage ds = (DataStorage) ois.readObject();

            // Player Stats
            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;
            gp.player.worldX = ds.worldX;
            gp.player.worldY = ds.worldY;

            // PLAYER INVENTORY
            gp.player.inventory.clear();
            for (int i = 0; i < ds.itemNames.size(); i++) {
                gp.player.inventory.add(getObject(ds.itemNames.get(i)));
                gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
            }
            // Player equipment
            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getPlayerAttackImage();

            // Objects on map
            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (ds.mapObjectNames[mapNum][i].equals("NA")) {
                        gp.obj[mapNum][i] = null;
                    } else {
                        gp.obj[mapNum][i] = getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Load error!");
        }
    }
}
