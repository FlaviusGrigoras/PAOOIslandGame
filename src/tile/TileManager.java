package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TileManager {
    public static int[] coordinates;
    private final GamePanel gp;
    private BufferedImage tileSheet;
    private final Tile[] tiles;
    private int[][] map;

    private final int tileWidth = 16;
    private final int numTilesX = 14;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[140]; // Numărul total de țigle din spritesheet, inclusiv noile tipuri de țigle

        try {
            tileSheet = ImageIO.read(new File("res/tiles/TinyIslands2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        getTilesFromSheet();
        generateMap();
    }

    private void getTilesFromSheet() {
        int tileIndex = 0;

        // Tăiem țiglele din spritesheet și le atribuim în array-ul de țigle
    /*
    Insula inconjurata de ocean
    tiles[0] = Colt. Ocean in stanga si sus
    tiles[1] = Margine. Ocean doar sus
    tiles[2] = Colt. Ocean in dreapta si sus

    tiles[14] = Margine. Ocean doar in stanga
    tiles[15] = Pamant fara ocean
    tiles[16] = Margine. Ocean doar in dreapta

    tiles[28] = Colt. Ocean in stanga si jos
    tiles[29] = Margine. Ocean doar in sus
    tiles[30] = Colt. Ocean in dreapta si jos

    Lac inconjurat de pamant
    tiles[3] = Lac cu pamant stanga si sus complet
    tiles[4] = Lac cu pamant dreapta si sus complet

    tiles[17] = Lac cu pamant stanga si jos complet
    tiles[18] = Lac cu pamant dreapta si jos complet

    Golf in partea de sus a insulei:
    tiles[17] + tiles[18]
    Gold in partea de jos a insulei:
    tiles[3] + tiles[4]
    Golf in partea stanga a insulei:
    tiles[4] + tiles[18]
    Golf in partea dreapta a insulei:
    tiles[3] + tiles[17]



    Variatii apa ocean
    tiles[31] = Ocean simplu
    tiles[32] = Ocean simplu variatie 2
    tiles[33] = Ocean simplu variatie 3
    tiles[34] = Ocean simplu variatie 4
    tiles[35] = Ocean simplu variatie 5
    tiles[36] = Ocean simplu variatie 6
    */
        int tileHeight = 16;
        for (int y = 0; y < tileSheet.getHeight() / tileHeight; y++) {
            // Numărul de țigle pe axa X în spritesheet
            for (int x = 0; x < numTilesX; x++) {
                BufferedImage subImage = tileSheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                tiles[tileIndex] = new Tile();
                tiles[tileIndex].image = subImage;
                tileIndex++;
            }
        }
    }

    public void generateMap() {
        map = MapGenerator.generateMap(gp.maxWorldCol, gp.maxWorldRow);
        coordinates = MapGenerator.chooseRandomIslandTile(map);
        System.out.println("Coordonatele tile-ului de tip insulă aleator selectat sunt: (" + coordinates[0] + ", " + coordinates[1] + "). Tile-ul are numarul: "+ map[coordinates[0]][coordinates[1]]);
    }
    public void draw(Graphics2D g2) {
        int tileSize = gp.tileSize;
        int worldCol = 0;
        int worldRow = 0;

        if (tiles != null && map != null) { // Verificăm dacă tiles și map nu sunt null
            // Iterăm prin harta și desenăm fiecare țiglă în funcție de valorile din hartă
            for (int[] ints : map) { // Utilizăm map.length pentru a obține numărul de rânduri
                for (int tileType : ints) { // Utilizăm map[i].length pentru a obține numărul de coloane din fiecare rând
                    if (tileType >= 0 && tileType < tiles.length) { // Verificăm dacă tileType este în limitele array-ului tiles
                        int worldX = worldCol * gp.tileSize;
                        int worldY = worldRow * gp.tileSize;
                        int screenX = worldX - gp.player.worldX + gp.player.screenX;
                        int screenY = worldY - gp.player.worldY + gp.player.screenY;
                        //if (worldX > gp.player.worldX - gp.player.screenX && worldX < gp.player.worldX + gp.player.screenX && worldY > gp.player.worldY - gp.player.screenY && worldY < gp.player.worldY + gp.player.screenY)
                        if (tiles[tileType] != null && tiles[tileType].image != null) { // Verificăm dacă tiles[tileType] și tiles[tileType].image nu sunt null
                            g2.drawImage(tiles[tileType].image, screenX, screenY, tileSize, tileSize, null);
                            worldCol++;
                            if (worldCol == gp.maxWorldCol) {
                                worldCol = 0;
                                worldRow++;
                            }
                        } else {
                            System.out.println("Imaginea pentru țigla " + tileType + " lipsește.");
                        }
                    } else {
                        System.out.println("Tipul țiglei " + tileType + " nu este valid.");
                    }
                }
            }
        } else {
            System.out.println("Tiles sau map nu au fost inițializate corespunzător.");
        }
    }


}