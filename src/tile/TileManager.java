package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileManager {
    public static int[] coordinates;
    private final GamePanel gp;
    private BufferedImage tileSheet;
    public final Tile[] tiles;
    public int[][] map;

    private final int tileWidth = 16;
    private final int numTilesX = 14;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[140]; // Numărul total de țigle din spritesheet, inclusiv noile tipuri de țigle

        try {
            tileSheet = ImageIO.read(new File("res/tiles/TinyIslands.png"));
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

    tiles[5] = Copac
    tiles[6] = Stanca

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

        tiles[31].collision=true;
        tiles[32].collision=true;
        tiles[33].collision=true;
        tiles[34].collision=true;
        tiles[35].collision=true;
        tiles[36].collision=true;
        tiles[5].collision=true;
        tiles[6].collision=true;

        tiles[0].collision=true;
        tiles[1].collision=true;
        tiles[2].collision=true;
        tiles[14].collision=true;
        tiles[16].collision=true;
        tiles[28].collision=true;
        tiles[29].collision=true;
        tiles[30].collision=true;

        tiles[25].collision=true;
        tiles[26].collision=true;
        tiles[27].collision=true;
        tiles[39].collision=true;
        tiles[41].collision=true;
        tiles[53].collision=true;
        tiles[54].collision=true;
        tiles[55].collision=true;

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
            while (worldRow < gp.maxWorldRow) {
                worldCol = 0; // Resetează coloana la fiecare rând nou

                while (worldCol < gp.maxWorldCol) {
                    int tileNum = map[worldCol][worldRow];
                    int worldX = worldCol * gp.tileSize;
                    int worldY = worldRow * gp.tileSize;
                    int screenX = worldX - gp.player.worldX + gp.player.screenX;
                    int screenY = worldY - gp.player.worldY + gp.player.screenY;

                    if (screenX + tileSize > 0 && screenX < gp.getWidth() &&
                            screenY + tileSize > 0 && screenY < gp.getHeight()) {

                        if (tileNum >= 0 && tileNum < tiles.length) {
                            if (tiles[tileNum] != null && tiles[tileNum].image != null) {
                                g2.drawImage(tiles[tileNum].image, screenX, screenY, tileSize, tileSize, null);
                            } else {
                                System.out.println("Imaginea pentru țigla " + tileNum + " lipsește.");
                            }
                        } else {
                            System.out.println("Tipul țiglei " + tileNum + " nu este valid.");
                        }
                    }
                    worldCol++;
                }
                worldRow++;
            }
        } else {
            System.out.println("Tiles sau map nu au fost inițializate corespunzător.");
        }
    }





}