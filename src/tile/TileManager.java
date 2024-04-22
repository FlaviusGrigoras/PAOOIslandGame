package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

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

        getTileImage();
        generateMap();
    }

    public void generateMap() {
        map = MapGenerator.generateMap(gp.maxWorldCol, gp.maxWorldRow);
        coordinates = MapGenerator.chooseRandomIslandTile(map);
        System.out.println("Coordonatele tile-ului de tip insulă aleator selectat sunt: (" + coordinates[0] + ", " + coordinates[1] + "). Tile-ul are numarul: " + map[coordinates[0]][coordinates[1]]);
    }

    private void getTileImage() {
        setup(0, "CORNER_TOP_LEFT", "island", true);
        setup(1, "EDGE_TOP", "island", true);
        setup(2, "CORNER_TOP_RIGHT", "island", true);

        setup(3, "EDGE_LEFT", "island", true);
        setup(4, "ISLAND", "island", false);
        setup(5, "EDGE_RIGHT", "island", true);

        setup(6, "CORNER_BOTTOM_LEFT", "island", true);
        setup(7, "EDGE_BOTTOM", "island", true);
        setup(8, "CORNER_BOTTOM_RIGHT", "island", true);

        setup(9, "OCEAN", "ocean", true);
        setup(10, "OCEAN_1", "ocean", true);
        setup(11, "OCEAN_2", "ocean", true);
        setup(12, "OCEAN_3", "ocean", true);
        setup(13, "OCEAN_4", "ocean", true);
        setup(14, "OCEAN_5", "ocean", true);

        setup(15, "TOP_LEFT_RAMA", "rama", true);
        setup(16, "TOP_RAMA", "rama", true);
        setup(17, "TOP_RIGHT_RAMA", "rama", true);

        setup(18, "LEFT_RAMA", "rama", true);
        setup(19, "RIGHT_RAMA", "rama", true);

        setup(20, "BOTTOM_LEFT_RAMA", "rama", true);
        setup(21, "BOTTOM_RAMA", "rama", true);
        setup(22, "BOTTOM_RIGHT_RAMA", "rama", true);

        setup(23, "TREE", "interactive", true);
        setup(24, "TREE_1", "interactive", true);

        setup(25, "ROCK", "interactive", true);
        setup(26, "ROCK_1", "interactive", true);

        setup(27, "TOP_LEFT_LAKE", "lake", true);
        setup(28, "TOP_RIGHT_LAKE", "lake", true);

        setup(29, "BOTTOM_LEFT_LAKE", "lake", true);
        setup(30, "BOTTOM_RIGHT_LAKE", "lake", true);
    }
    public void setup(int index, String imageName, String tileType, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(new File("res/tiles/" + tileType + "/" + imageName + ".png"));
            tiles[index].image = uTool.scaleImage(tiles[index].image, gp.tileSize, gp.tileSize);
            tiles[index].collision = collision;
        } catch (IOException e) {

            e.printStackTrace();
        }
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