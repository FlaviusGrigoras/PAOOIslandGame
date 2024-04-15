package main;

import entity.Entity;

public class CollisionCheck {
    GamePanel gp;
    public CollisionCheck(GamePanel gp) {
        this.gp = gp;
    }
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol=entityLeftWorldX/gp.tileSize;
        int entityRightCol=entityRightWorldX/gp.tileSize;
        int entityTopRow=entityTopWorldY/gp.tileSize;
        int entityBottomRow=entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction)
        {
            case "up":
                entityTopRow=(entityTopWorldY-entity.speed)/gp.tileSize;
                tileNum1= gp.getTileM().getMap()[entityLeftCol][entityTopRow];
                tileNum2= gp.getTileM().getMap()[entityRightCol][entityTopRow];
                if(gp.getTileM().getTiles()[tileNum1].collision || gp.getTileM().getTiles()[tileNum2].collision)
                {
                    entity.collisionOn=true;
                }
                break;
            case "down":
                entityBottomRow=(entityBottomWorldY+entity.speed)/gp.tileSize;
                tileNum1= gp.getTileM().getMap()[entityLeftCol][entityBottomRow];
                tileNum2= gp.getTileM().getMap()[entityRightCol][entityBottomRow];
                if(gp.getTileM().getTiles()[tileNum1].collision || gp.getTileM().getTiles()[tileNum2].collision)
                {
                    entity.collisionOn=true;
                }
                break;
            case "left":
                entityLeftCol=(entityLeftWorldX-entity.speed)/gp.tileSize;
                tileNum1= gp.getTileM().getMap()[entityLeftCol][entityTopRow];
                tileNum2= gp.getTileM().getMap()[entityLeftCol][entityBottomRow];
                if(gp.getTileM().getTiles()[tileNum1].collision || gp.getTileM().getTiles()[tileNum2].collision)
                {
                    entity.collisionOn=true;
                }
                break;
            case "right":
                entityRightCol=(entityRightWorldX+entity.speed)/gp.tileSize;
                tileNum1= gp.getTileM().getMap()[entityRightCol][entityTopRow];
                tileNum2= gp.getTileM().getMap()[entityRightCol][entityBottomRow];
                if(gp.getTileM().getTiles()[tileNum1].collision || gp.getTileM().getTiles()[tileNum2].collision)
                {
                    entity.collisionOn=true;
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + entity.direction);
        }
    }
}