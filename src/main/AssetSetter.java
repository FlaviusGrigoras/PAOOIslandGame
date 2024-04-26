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
