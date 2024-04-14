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
    private Tile[] tiles;
    private int[][] map;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        setTiles(new Tile[140]); // Numărul total de țigle din spritesheet, inclusiv noile tipuri de țigle

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
        int tileHeight = 16;
        // Tăiem țiglele din spritesheet și le atribuim în array-ul de țigle
        for (int y = 0; y < tileSheet.getHeight() / tileHeight; y++) {
            // Numărul de țigle pe axa X în spritesheet
            int numTilesX = 14;
            for (int x = 0; x < numTilesX; x++) {
                int tileWidth = 16;
                BufferedImage subImage = tileSheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                tiles[tileIndex] = new Tile();
                tiles[tileIndex].image = subImage;

                if(tileIndex>30 && tileIndex<37)
                {
                    tiles[tileIndex].collision=true;
                }

                tileIndex++;
            }
        }
    }

    public void generateMap() {
        setMap(MapGenerator.generateMap(gp.maxWorldCol, gp.maxWorldRow));
        coordinates = MapGenerator.chooseRandomIslandTile(getMap());
    }
    public void draw(Graphics2D g2) {
        int tileSize = gp.tileSize;
        int worldCol = 0;
        int worldRow = 0;

        if (tiles != null && getMap() != null) { // Verificăm dacă tiles și map nu sunt null
            // Iterăm prin harta și desenăm fiecare țiglă în funcție de valorile din hartă
            for (int i = 0; i < getMap().length; i++) { // Utilizăm map.length pentru a obține numărul de rânduri
                for (int j = 0; j < getMap()[i].length; j++) { // Utilizăm map[i].length pentru a obține numărul de coloane din fiecare rând
                    int tileType = getMap()[i][j];
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


    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }
}