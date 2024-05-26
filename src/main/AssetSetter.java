package main;

import entity.NPC_Merchant;
import entity.NPC_Villager;
import monster.MON_GreenSlime;
import monster.MON_Orc;
import monster.MON_RedSlime;
import object.*;
import tile_interactive.IT_Rock;
import tile_interactive.IT_Tree;

import java.awt.desktop.ScreenSleepEvent;
import java.util.Random;

public class AssetSetter {
    GamePanel gp;
    public int objectCounter = 0;
    public int monsterCounter = 0;
    public int npcCounter = 0;
    public int itCounter = 0;
    public int[][][] alreadyExisting = null;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
        this.alreadyExisting = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
    }

    public void setObject() {

    }

    public void setNPC() {
        createNPC(npcCounter, "Villager", 15, 15, 0);
        createNPC(npcCounter, "Merchant", 12, 7, 1);
    }

    public void setMonster() {

        createMonster(monsterCounter, "redslime", 15, 25, 0);
        createMonster(monsterCounter, "redslime", 14, 28, 0);
        createMonster(monsterCounter, "redslime", 10, 34, 0);
        createMonster(monsterCounter, "redslime", 13, 37, 0);
        createMonster(monsterCounter, "redslime", 23, 38, 0);
        createMonster(monsterCounter, "redslime", 26, 39, 0);
        createMonster(monsterCounter, "redslime", 30, 37, 0);
        createMonster(monsterCounter, "redslime", 32, 39, 0);

        Random rand = new Random();
        for (int x = 0; x < gp.maxWorldCol; x++) {
            for (int y = 0; y < gp.maxWorldRow; y++) {
                if (getAlreadyExisting(0, x, y)) {
                    if (gp.tileM.map[0][x][y] == 217) {
                        int chance = rand.nextInt(100);
                        if (chance < 0.5) {
                            createMonster(monsterCounter, "greenslime", x, y, 0);
                        } else if (chance < 20) {
                        }
                    }
                }
            }
        }
        if (getAlreadyExisting(0, 42, 31)) {
            //createMonster(monsterCounter, "orc", 42, 31, 0);
        }

    }

    public void setInteractiveTile() {

        for (int i = 13; i <= 17; i++)
            createInteractiveTile(itCounter, "Rock", i, 26, 0);
        for (int i = 37; i <= 39; i++) {
            createInteractiveTile(itCounter, "Rock", 20, i, 0);
            createInteractiveTile(itCounter, "Rock", 34, i, 0);
        }


        Random rand = new Random();
        for (int x = 0; x < gp.maxWorldCol; x++) {
            for (int y = 0; y < gp.maxWorldRow; y++) {
                if (getAlreadyExisting(0, x, y)) {
                    if (gp.tileM.map[0][x][y] == 217) {
                        int chance = rand.nextInt(100);
                        if (chance < 10) {
                            createInteractiveTile(itCounter, "Tree", x, y, 0);
                        } else if (chance < 20) {
                            createInteractiveTile(itCounter, "Rock", x, y, 0);
                        }

                    }
                }
            }
        }
    }

    public boolean excludedX(int x) {
        int[] no = {23, 41, 29, 42};
        boolean ok = true;
        for (int i = 0; i < no.length; i++) {
            if (x == no[i]) {
                ok = false;
            }
        }
        return ok;
    }

    public boolean excludedY(int y) {
        int[] no = {21, 10, 24, 31};
        boolean ok = true;
        for (int i = 0; i < no.length; i++) {
            if (y == no[i]) {
                ok = false;
            }
        }
        return ok;
    }

    public void createObject(int index, String Type, int x, int y, int mapNum) {
        switch (Type) {
            case "Coin":
                gp.obj[mapNum][index] = new OBJ_Coin(gp);
                break;
            case "Stone":
                gp.obj[mapNum][index] = new OBJ_Stone(gp);
                break;
            case "Wood":
                gp.obj[mapNum][index] = new OBJ_Wood(gp);
                break;
            case "Iron":
                gp.obj[mapNum][index] = new OBJ_Iron(gp);
                break;
            case "Axe":
                gp.obj[mapNum][index] = new OBJ_Axe(gp);
                break;
            case "Wood_Shield":
                gp.obj[mapNum][index] = new OBJ_Shield_Wood(gp);
                break;
            case "Blue_Shield":
                gp.obj[mapNum][index] = new OBJ_Shield_Blue(gp);
                break;
            case "Red_Potion":
                gp.obj[mapNum][index] = new OBJ_Potion_Red(gp);
                break;
            case "Pistol":
                gp.obj[mapNum][index] = new OBJ_Pistol(gp);
                break;
            case "Stick":
                gp.obj[mapNum][index] = new OBJ_Stick(gp);
                break;
            case "Patura":
                gp.obj[mapNum][index] = new OBJ_Patura(gp);
                break;
            case "Inima":
                gp.obj[mapNum][index] = new OBJ_Heart(gp);
                break;
            case "Mana":
                gp.obj[mapNum][index] = new OBJ_ManaCrystal(gp);
                break;
            case "Swood_Normal":
                gp.obj[mapNum][index] = new OBJ_Sword_Normal(gp);
                break;
            case "Lantern":
                gp.obj[mapNum][index] = new OBJ_Lantern(gp);
                break;
            case "Tent":
                gp.obj[mapNum][index] = new OBJ_Tent(gp);
                break;

            case "Default":
                System.out.println("Not an object!");
                break;
        }
        gp.obj[mapNum][index].worldX = gp.tileSize * x;
        gp.obj[mapNum][index].worldY = gp.tileSize * y;
        objectCounter++;
    }

    public void createNPC(int index, String Type, int x, int y, int mapNum) {
        switch (Type) {
            case "Villager":
                gp.npc[mapNum][index] = new NPC_Villager(gp);
                break;
            case "Merchant":
                gp.npc[mapNum][index] = new NPC_Merchant(gp);
                break;
            case "Default":
                System.out.println("Not an NPC!");
                break;
        }
        gp.npc[mapNum][index].worldX = x * gp.tileSize;
        gp.npc[mapNum][index].worldY = y * gp.tileSize;
        alreadyExisting[mapNum][x][y] = 1;
        npcCounter++;
    }

    public boolean getAlreadyExisting(int mapNum, int x, int y) {
        if (alreadyExisting[mapNum][x][y] != 0)
            return false;
        else return true;
    }

    public void createMonster(int index, String Type, int x, int y, int mapNum) {
        switch (Type) {
            case "greenslime":
                gp.monster[mapNum][index] = new MON_GreenSlime(gp);
                break;
            case "redslime":
                gp.monster[mapNum][index] = new MON_RedSlime(gp);
                break;
            case "orc":
                gp.monster[mapNum][index] = new MON_Orc(gp);
                break;
            case "Default":
                System.out.println("Not an Monster!");
                break;
        }
        gp.monster[mapNum][index].worldX = x * gp.tileSize;
        gp.monster[mapNum][index].worldY = y * gp.tileSize;
        alreadyExisting[mapNum][x][y] = 1;
        monsterCounter++;
    }

    public void createInteractiveTile(int index, String Type, int x, int y, int mapNum) {

        switch (Type) {
            case "Tree":
                gp.iTile[mapNum][index] = new IT_Tree(gp);
                break;
            case "Rock":
                gp.iTile[mapNum][index] = new IT_Rock(gp);
                break;
            case "Default":
                System.out.println("Not an interactive Tile!");
                break;
        }
        gp.iTile[mapNum][index].worldX = x * gp.tileSize;
        gp.iTile[mapNum][index].worldY = y * gp.tileSize;
        alreadyExisting[mapNum][x][y] = 1;
        itCounter++;
    }
}
