/* CollisionCheck vechi
package main;

import entity.Entity;

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
                int tileNumTopLeft = gp.tileM.map[entityLeftCol][entityTopRow];
                // Obținem numărul țiglei de la colțul drept al entității după mișcare
                int tileNumTopRight = gp.tileM.map[entityRightCol][entityTopRow];
                // Verificăm dacă țiglele în care se deplasează entitatea sunt coliziuni
                if (gp.tileM.tiles[tileNumTopLeft].collision || gp.tileM.tiles[tileNumTopRight].collision) {
                    // Dacă da, setăm indicatorul de coliziune al entității pe true
                    entity.collisionOn = true;
                }
                break;
            case "down":
                // Similar pentru celelalte direcții de mișcare
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                int tileNumBottomLeft = gp.tileM.map[entityLeftCol][entityBottomRow];
                int tileNumBottomRight = gp.tileM.map[entityRightCol][entityBottomRow];
                if (gp.tileM.tiles[tileNumBottomLeft].collision || gp.tileM.tiles[tileNumBottomRight].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                int tileNumLeftTop = gp.tileM.map[entityLeftCol][entityTopRow];
                int tileNumLeftBottom = gp.tileM.map[entityLeftCol][entityBottomRow];
                if (gp.tileM.tiles[tileNumLeftTop].collision || gp.tileM.tiles[tileNumLeftBottom].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                int tileNumRightTop = gp.tileM.map[entityRightCol][entityTopRow];
                int tileNumRightBottom = gp.tileM.map[entityRightCol][entityBottomRow];
                if (gp.tileM.tiles[tileNumRightTop].collision || gp.tileM.tiles[tileNumRightBottom].collision) {
                    entity.collisionOn = true;
                }
                break;
            default:
                throw new IllegalStateException("Direcție neașteptată: " + entity.direction);
        }
    }
}
 */

package main;

import entity.Entity;

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

        // Variabile pentru coordonatele și numărul tile-ului de coliziune
        int collisionTileX = -1;
        int collisionTileY = -1;
        int collisionTileNum = -1;

        // Verificăm coliziunile pentru fiecare direcție de mișcare
        switch (entity.direction) {
            case "up":
                // Calculăm rândul superior al entității după mișcare
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                // Obținem numărul țiglei de la colțul stâng al entității după mișcare
                int tileNumTopLeft = gp.tileM.map[entityLeftCol][entityTopRow];
                // Obținem numărul țiglei de la colțul drept al entității după mișcare
                int tileNumTopRight = gp.tileM.map[entityRightCol][entityTopRow];
                // Verificăm dacă țiglele în care se deplasează entitatea sunt coliziuni
                if (gp.tileM.tiles[tileNumTopLeft].collision || gp.tileM.tiles[tileNumTopRight].collision) {
                    // Dacă da, setăm indicatorul de coliziune al entității pe true
                    entity.collisionOn = true;
                    // Salvăm coordonatele și numărul țiglei de coliziune
                    collisionTileX = entityLeftCol;
                    collisionTileY = entityTopRow;
                    collisionTileNum = tileNumTopLeft;
                }
                break;
            case "down":
                // Similar pentru celelalte direcții de mișcare
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                int tileNumBottomLeft = gp.tileM.map[entityLeftCol][entityBottomRow];
                int tileNumBottomRight = gp.tileM.map[entityRightCol][entityBottomRow];
                if (gp.tileM.tiles[tileNumBottomLeft].collision || gp.tileM.tiles[tileNumBottomRight].collision) {
                    entity.collisionOn = true;
                    collisionTileX = entityLeftCol;
                    collisionTileY = entityBottomRow;
                    collisionTileNum = tileNumBottomLeft;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                int tileNumLeftTop = gp.tileM.map[entityLeftCol][entityTopRow];
                int tileNumLeftBottom = gp.tileM.map[entityLeftCol][entityBottomRow];
                if (gp.tileM.tiles[tileNumLeftTop].collision || gp.tileM.tiles[tileNumLeftBottom].collision) {
                    entity.collisionOn = true;
                    collisionTileX = entityLeftCol;
                    collisionTileY = entityTopRow;
                    collisionTileNum = tileNumLeftTop;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                int tileNumRightTop = gp.tileM.map[entityRightCol][entityTopRow];
                int tileNumRightBottom = gp.tileM.map[entityRightCol][entityBottomRow];
                if (gp.tileM.tiles[tileNumRightTop].collision || gp.tileM.tiles[tileNumRightBottom].collision) {
                    entity.collisionOn = true;
                    collisionTileX = entityRightCol;
                    collisionTileY = entityTopRow;
                    collisionTileNum = tileNumRightTop;
                }
                break;
            default:
                throw new IllegalStateException("Direcție neașteptată: " + entity.direction);
        }

        // Dacă am detectat o coliziune, afișăm mesajul de debug
        if (entity.collisionOn) {
            if (gp.tileM.tiles[collisionTileNum].collision) {
                System.out.println("Coliziune detectată. Oprire mișcare. Coordonatele tile-ului: (" + collisionTileX + ", " + collisionTileY + "). Numărul tile-ului: " + collisionTileNum + ". Coordonatele caracterului: (" + entity.worldX/gp.tileSize + ", " + entity.worldY/gp.tileSize + ")");
            }
        }
    }
}

