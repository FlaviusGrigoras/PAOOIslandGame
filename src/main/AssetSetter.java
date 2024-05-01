package main;

import entity.NPC_Villager;
import object.OBJ_Coin;
import object.OBJ_Iron;
import object.OBJ_Stone;
import object.OBJ_Wood;
import tile.MapGenerator;
import tile.TileManager;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        createObject(0, "Coin", 10, 11);
        createObject(1, "Stone", 10, 12);
        createObject(2, "Wood", 10, 13);
        createObject(3, "Iron", 11, 14);
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
            case "Default":
                System.out.println("Not an object!");
                break;
        }
        gp.obj[index].worldX = gp.tileSize * x;
        gp.obj[index].worldY = gp.tileSize * y;
    }

    public int[] getRandomCoordinates() {
        TileManager tileM = new TileManager(gp);
        int[] coordinates = MapGenerator.chooseRandomIslandTile(gp.tileM.map);

        int x = coordinates[0];
        int y = coordinates[1];
        while (tileM.map[x + 1][y + 1] != 4) {
            coordinates = MapGenerator.chooseRandomIslandTile(tileM.map);
            x = coordinates[0];
            y = coordinates[1];
        }
        return coordinates;
    }

    public void setNPC() {
        if (gp != null && gp.npc != null) {
            int[] coordinates = getRandomCoordinates();
            createNPC(0, "Villager", coordinates[0], coordinates[1]);

            coordinates = getRandomCoordinates();
            createNPC(1, "Villager", coordinates[0], coordinates[1]);

            coordinates = getRandomCoordinates();
            createNPC(2, "Villager", coordinates[0], coordinates[1]);

            coordinates = getRandomCoordinates();
            createNPC(3, "Villager", coordinates[0], coordinates[1]);
        }
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
    }
}
