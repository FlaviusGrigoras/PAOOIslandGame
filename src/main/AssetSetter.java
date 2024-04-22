package main;

import object.OBJ_Coin;
import object.OBJ_Iron;
import object.OBJ_Stone;
import object.OBJ_Wood;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp)
    {
        this.gp=gp;
    }
    public void setObject()
    {
        gp.obj[0]=new OBJ_Coin();
        gp.obj[0].worldX=8*gp.tileSize;
        gp.obj[0].worldY=8*gp.tileSize;

        gp.obj[1]=new OBJ_Iron();
        gp.obj[1].worldX=9*gp.tileSize;
        gp.obj[1].worldY=8*gp.tileSize;

        gp.obj[2]=new OBJ_Wood();
        gp.obj[2].worldX=10*gp.tileSize;
        gp.obj[2].worldY=8*gp.tileSize;

        gp.obj[3]=new OBJ_Stone();
        gp.obj[3].worldX=11*gp.tileSize;
        gp.obj[3].worldY=8*gp.tileSize;

    }
}
