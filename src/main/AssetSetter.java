package main;

import entity.NPC_Villager;
import monster.MON_GreenSlime;
import object.*;
import tile.MapGenerator;
import tile.TileManager;
import tile_interactive.IT_Tree;

public class AssetSetter {
    GamePanel gp;
    public int objectCounter = 0;
    public int monsterCounter = 0;
    public int npcCounter = 0;
    public int itCounter = 0;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        createObject(objectCounter, "Coin", 10, 10);
        createObject(objectCounter, "Stone", 11, 10);
        createObject(objectCounter, "Wood", 12, 10);
        createObject(objectCounter, "Iron", 13, 10);
        createObject(objectCounter, "Axe", 14, 10);
        createObject(objectCounter, "Blue_Shield", 15, 10);
        createObject(objectCounter, "Red_Potion", 16, 10);
        createObject(objectCounter, "Pistol", 17, 10);
        createObject(objectCounter, "Swood_Normal", 18, 10);
        createObject(objectCounter, "Wood_Shield", 19, 10);
        createObject(objectCounter, "Mana", 20, 10);
        createObject(objectCounter, "Inima", 21, 10);

    }

    public void createObject(int index, String Type, int x, int y) {
        switch (Type) {
            case "Coin":
                gp.obj[index] = new OBJ_Coin(gp);
                break;
            case "Stone":
                gp.obj[index] = new OBJ_Stone(gp);
                break;
            case "Wood":
                gp.obj[index] = new OBJ_Wood(gp);
                break;
            case "Iron":
                gp.obj[index] = new OBJ_Iron(gp);
                break;
            case "Axe":
                gp.obj[index] = new OBJ_Axe(gp);
                break;
            case "Wood_Shield":
                gp.obj[index] = new OBJ_Shield_Wood(gp);
                break;
            case "Blue_Shield":
                gp.obj[index] = new OBJ_Shield_Blue(gp);
                break;
            case "Red_Potion":
                gp.obj[index] = new OBJ_Potion_Red(gp);
                break;
            case "Pistol":
                gp.obj[index] = new OBJ_Pistol(gp);
                break;
            case "Stick":
                gp.obj[index] = new OBJ_Stick(gp);
                break;
            case "Patura":
                gp.obj[index] = new OBJ_Patura(gp);
                break;
            case "Inima":
                gp.obj[index] = new OBJ_Heart(gp);
                break;
            case "Mana":
                gp.obj[index] = new OBJ_ManaCrystal(gp);
                break;
            case "Swood_Normal":
                gp.obj[index] = new OBJ_Sword_Normal(gp);
                break;

            case "Default":
                System.out.println("Not an object!");
                break;
        }
        gp.obj[index].worldX = gp.tileSize * x;
        gp.obj[index].worldY = gp.tileSize * y;
        objectCounter++;
    }


    public void setNPC() {
        if (gp != null && gp.npc != null) {
            createNPC(npcCounter, "Villager", 15, 15);
        }
    }

    public void setMonster() {
        int i = 0;

        createMonster(monsterCounter, "greenslime", 15, 22);
        createMonster(monsterCounter, "greenslime", 17, 13);
        createMonster(monsterCounter, "greenslime", 20, 10);
    }

    public void createMonster(int index, String Type, int x, int y) {
        switch (Type) {
            case "greenslime":
                gp.monster[index] = new MON_GreenSlime(gp);
                break;
            case "Default":
                System.out.println("Not an Monster!");
                break;
        }
        gp.monster[index].worldX = x * gp.tileSize;
        gp.monster[index].worldY = y * gp.tileSize;
        monsterCounter++;
    }


    public void createNPC(int index, String Type, int x, int y) {
        switch (Type) {
            case "Villager":
                gp.npc[index] = new NPC_Villager(gp);
                break;
            case "Default":
                System.out.println("Not an NPC!");
                break;
        }
        gp.npc[index].worldX = x * gp.tileSize;
        gp.npc[index].worldY = y * gp.tileSize;
        npcCounter++;
    }

    public void setInteractiveTile() {
        createInteractiveTile(itCounter, "Tree", 11, 11);
        createInteractiveTile(itCounter, "Tree", 11, 12);
        createInteractiveTile(itCounter, "Tree", 11, 13);
    }

    public void createInteractiveTile(int index, String Type, int x, int y) {
        switch (Type) {
            case "Tree":
                gp.iTile[index] = new IT_Tree(gp);
                break;
            case "Rock":
                //gp.iTile[index]=new IT_Rock(gp);
                break;
            case "Default":
                System.out.println("Not an interactive Tile!");
                break;
        }
        gp.iTile[index].worldX = x * gp.tileSize;
        gp.iTile[index].worldY = y * gp.tileSize;
        itCounter++;
    }
}
