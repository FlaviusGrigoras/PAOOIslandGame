package main;

import entity.Entity;

import java.awt.*;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // Calculăm coordonatele și dimensiunile dreptunghiului solid al entității
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Calculăm coloanele și rândurile din harta tile-urilor pentru entitate
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        // Verificăm coliziunile pentru fiecare direcție de mișcare
        switch (entity.direction) {
            case "up":
                // Calculăm rândul superior al entității după mișcare
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                // Obținem numărul țiglei de la colțul stâng al entității după mișcare
                int tileNumTopLeft = gp.tileM.map[gp.currentMap][entityLeftCol][entityTopRow];
                // Obținem numărul țiglei de la colțul drept al entității după mișcare
                int tileNumTopRight = gp.tileM.map[gp.currentMap][entityRightCol][entityTopRow];
                // Verificăm dacă țiglele în care se deplasează entitatea sunt coliziuni
                if (gp.tileM.tiles[tileNumTopLeft].collision || gp.tileM.tiles[tileNumTopRight].collision) {
                    // Dacă da, setăm indicatorul de coliziune al entității pe true
                    entity.collisionOn = true;
                    // Salvăm coordonatele și numărul țiglei de coliziune
                }
                break;
            case "down":
                // Similar pentru celelalte direcții de mișcare
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                int tileNumBottomLeft = gp.tileM.map[gp.currentMap][entityLeftCol][entityBottomRow];
                int tileNumBottomRight = gp.tileM.map[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tiles[tileNumBottomLeft].collision || gp.tileM.tiles[tileNumBottomRight].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                int tileNumLeftTop = gp.tileM.map[gp.currentMap][entityLeftCol][entityTopRow];
                int tileNumLeftBottom = gp.tileM.map[gp.currentMap][entityLeftCol][entityBottomRow];
                if (gp.tileM.tiles[tileNumLeftTop].collision || gp.tileM.tiles[tileNumLeftBottom].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                int tileNumRightTop = gp.tileM.map[gp.currentMap][entityRightCol][entityTopRow];
                int tileNumRightBottom = gp.tileM.map[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tiles[tileNumRightTop].collision || gp.tileM.tiles[tileNumRightBottom].collision) {
                    entity.collisionOn = true;
                }
                break;
            default:
                throw new IllegalStateException("Direcție neașteptată: " + entity.direction);
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        Rectangle entitySolidAreaCopy = new Rectangle(entity.solidArea);
        Rectangle objectSolidAreaCopy;

        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null) {
                objectSolidAreaCopy = new Rectangle(gp.obj[gp.currentMap][i].solidArea);

                // Get entity's solid area position
                entitySolidAreaCopy.x = entity.worldX + entity.solidArea.x;
                entitySolidAreaCopy.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                objectSolidAreaCopy.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                objectSolidAreaCopy.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entitySolidAreaCopy.y -= entity.speed;
                        if (entitySolidAreaCopy.intersects(objectSolidAreaCopy)) {
                            if (gp.obj[gp.currentMap][i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entitySolidAreaCopy.y += entity.speed;
                        if (entitySolidAreaCopy.intersects(objectSolidAreaCopy)) {
                            if (gp.obj[gp.currentMap][i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entitySolidAreaCopy.x -= entity.speed;
                        if (entitySolidAreaCopy.intersects(objectSolidAreaCopy)) {
                            if (gp.obj[gp.currentMap][i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entitySolidAreaCopy.x += entity.speed;
                        if (entitySolidAreaCopy.intersects(objectSolidAreaCopy)) {
                            if (gp.obj[gp.currentMap][i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
            }
        }
        return index;
    }

    //NPC - Monster Collision
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;
        Rectangle entitySolidAreaCopy = new Rectangle(entity.solidArea);
        Rectangle objectSolidAreaCopy;

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currentMap][i] != null) {
                objectSolidAreaCopy = new Rectangle(target[gp.currentMap][i].solidArea);

                // Get entity's solid area position
                entitySolidAreaCopy.x = entity.worldX + entity.solidArea.x;
                entitySolidAreaCopy.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                objectSolidAreaCopy.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                objectSolidAreaCopy.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entitySolidAreaCopy.y -= entity.speed;
                        if (entitySolidAreaCopy.intersects(objectSolidAreaCopy)) {
                            if (target[gp.currentMap][i] != entity) {
                                entity.collisionOn = true;
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entitySolidAreaCopy.y += entity.speed;
                        if (entitySolidAreaCopy.intersects(objectSolidAreaCopy)) {
                            if (target[gp.currentMap][i] != entity) {
                                entity.collisionOn = true;
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entitySolidAreaCopy.x -= entity.speed;
                        if (entitySolidAreaCopy.intersects(objectSolidAreaCopy)) {
                            if (target[gp.currentMap][i] != entity) {
                                entity.collisionOn = true;
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entitySolidAreaCopy.x += entity.speed;
                        if (entitySolidAreaCopy.intersects(objectSolidAreaCopy)) {
                            if (target[gp.currentMap][i] != entity) {
                                entity.collisionOn = true;
                                index = i;
                            }
                        }
                        break;
                }
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;
        Rectangle entitySolidAreaCopy = new Rectangle(entity.solidArea);
        Rectangle objectSolidAreaCopy;

        objectSolidAreaCopy = new Rectangle(gp.player.solidArea);

        // Get entity's solid area position
        entitySolidAreaCopy.x = entity.worldX + entity.solidArea.x;
        entitySolidAreaCopy.y = entity.worldY + entity.solidArea.y;

        // Get the object's solid area position
        objectSolidAreaCopy.x = gp.player.worldX + gp.player.solidArea.x;
        objectSolidAreaCopy.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction) {
            case "up":
                entitySolidAreaCopy.y -= entity.speed;
                break;
            case "down":
                entitySolidAreaCopy.y += entity.speed;
                break;
            case "left":
                entitySolidAreaCopy.x -= entity.speed;
                break;
            case "right":
                entitySolidAreaCopy.x += entity.speed;
                break;
        }
        if (entitySolidAreaCopy.intersects(objectSolidAreaCopy)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        return contactPlayer;
    }
}