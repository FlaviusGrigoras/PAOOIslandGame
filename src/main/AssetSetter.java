package main;

import entity.NPC_Villager;
import object.OBJ_Coin;
import object.OBJ_Iron;
import object.OBJ_Stone;
import object.OBJ_Wood;
import tile.TileManager;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Coin(gp);
        gp.obj[0].worldX = gp.tileSize * 10;
        gp.obj[0].worldY = gp.tileSize * 11;

        gp.obj[1] = new OBJ_Stone(gp);
        gp.obj[1].worldX = gp.tileSize * 10;
        gp.obj[1].worldY = gp.tileSize * 12;

        gp.obj[2] = new OBJ_Wood(gp);
        gp.obj[2].worldX = gp.tileSize * 10;
        gp.obj[2].worldY = gp.tileSize * 13;

        gp.obj[3] = new OBJ_Iron(gp);
        gp.obj[3].worldX = gp.tileSize * 11;
        gp.obj[3].worldY = gp.tileSize * 11;
    }

    public void setNPC() {
        if (gp != null && gp.npc != null) {
            int[] coordinates = TileManager.NPC1Coordinates;
            gp.npc[0] = new NPC_Villager(gp);
            gp.npc[0].worldX = coordinates[0] * gp.tileSize;
            gp.npc[0].worldY = coordinates[1] * gp.tileSize;
        }
    }


}
