package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {
    public static int[] PlayerCoordinates;
    public static int[] NPC1Coordinates;
    private final GamePanel gp;
    public final Tile[] tiles;
    public int[][][] map;
    boolean drawPath = true;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[140]; // Numărul total de țigle din spritesheet, inclusiv noile tipuri de țigle
        map = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("maps/map00.txt", 0);
        loadMap("maps/interior01.txt", 1);
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

        setup(31, "hut", "hut", false);
        setup(32, "floor", "hut", false);
        setup(33, "table", "hut", true);
        setup(34, "wall", "hut", true);

        setup(35, "EDGE_RIGHT", "island", false);
        setup(36, "OCEAN", "ocean", false);
        setup(37, "EDGE_TOP", "island", false);

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

    public void loadMap(String filePath, int mapi) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }

                String[] numbers = line.split(" ");

                while (col < gp.maxWorldCol && col < numbers.length) {
                    int num = Integer.parseInt(numbers[col]);

                    map[mapi][col][row] = num;
                    col++;
                }

                col = 0; // Reset column index after processing a row
                row++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2) {
        int tileSize = gp.tileSize;
        int worldCol;
        int worldRow = 0;

        if (tiles != null && map != null) { // Verificăm dacă tiles și map nu sunt null
            while (worldRow < gp.maxWorldRow) {
                worldCol = 0; // Resetează coloana la fiecare rând nou

                while (worldCol < gp.maxWorldCol) {
                    int tileNum = map[gp.currentMap][worldCol][worldRow];
                    int worldX = worldCol * gp.tileSize;
                    int worldY = worldRow * gp.tileSize;
                    int screenX = worldX - gp.player.worldX + gp.player.screenX;
                    int screenY = worldY - gp.player.worldY + gp.player.screenY;

                    if (screenX + tileSize > 0 && screenX < gp.getWidth() &&
                            screenY + tileSize > 0 && screenY < gp.getHeight()) {

                        if (tileNum >= 0 && tileNum < tiles.length) {
                            if (tiles[tileNum] != null && tiles[tileNum].image != null) {
                                g2.drawImage(tiles[tileNum].image, screenX, screenY, null);
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
        if (drawPath = true) {
            g2.setColor(new Color(255, 0, 0, 70));

            for (int i = 0; i < gp.pFinder.pathList.size(); i++) {
                int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }
    }
        /*
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = map[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            g2.drawImage(tiles[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
                 */
}