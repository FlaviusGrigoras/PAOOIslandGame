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
        tiles = new Tile[300]; // Numărul total de țigle din spritesheet, inclusiv noile tipuri de țigle
        map = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("maps/map00.txt", 0);
        loadMap("maps/interior01.txt", 1);
        loadMap("maps/dungeon01.txt", 2);
        loadMap("maps/dungeon02.txt", 3);
        loadMap("maps/desert00.txt", 4);
    }


    private void getTileImage() {
        for (int i = 0; i < 213; i++) {
            String s = String.valueOf(i);
            if (i < 10) {
                setup(i, "00" + s, "desert", true);
            }
            if (i >= 10 && i < 100) {
                setup(i, "0" + s, "desert", true);
            }
            if (i > 99) {
                setup(i, s, "desert", true);
            }

        }

        for (int i = 0; i < 40; i++) {
            tiles[i].collision = false;
        }
        for (int i = 41; i < 45; i++) {
            tiles[i].collision = false;
        }
        for (int i = 55; i < 67; i++) {
            tiles[i].collision = false;
        }
        for (int i = 70; i < 81; i++) {
            tiles[i].collision = false;
        }
        for (int i = 175; i < 196; i++) {
            tiles[i].collision = false;
        }
        tiles[96].collision=false;
        tiles[109].collision=false;
        tiles[111].collision=false;
        tiles[124].collision=false;
        tiles[52].collision=false;


        setup(213, "CORNER_TOP_LEFT", "island", true);
        setup(214, "EDGE_TOP", "island", true);
        setup(215, "CORNER_TOP_RIGHT", "island", true);

        setup(216, "EDGE_LEFT", "island", true);
        setup(217, "ISLAND", "island", false);
        setup(218, "EDGE_RIGHT", "island", true);

        setup(219, "CORNER_BOTTOM_LEFT", "island", true);
        setup(220, "EDGE_BOTTOM", "island", true);
        setup(221, "CORNER_BOTTOM_RIGHT", "island", true);

        setup(222, "OCEAN", "ocean", true);
        setup(223, "OCEAN_1", "ocean", true);
        setup(224, "OCEAN_2", "ocean", true);
        setup(225, "OCEAN_3", "ocean", true);
        setup(226, "OCEAN_4", "ocean", true);
        setup(227, "OCEAN_5", "ocean", true);

        setup(228, "TOP_LEFT_RAMA", "rama", true);
        setup(229, "TOP_RAMA", "rama", true);
        setup(230, "TOP_RIGHT_RAMA", "rama", true);

        setup(231, "LEFT_RAMA", "rama", true);
        setup(232, "RIGHT_RAMA", "rama", true);

        setup(233, "BOTTOM_LEFT_RAMA", "rama", true);
        setup(234, "BOTTOM_RAMA", "rama", true);
        setup(235, "BOTTOM_RIGHT_RAMA", "rama", true);

        setup(236, "TREE", "interactive", true);
        setup(237, "TREE_1", "interactive", true);

        setup(238, "ROCK", "interactive", true);
        setup(239, "ROCK_1", "interactive", true);

        setup(240, "TOP_LEFT_LAKE", "lake", true);
        setup(241, "TOP_RIGHT_LAKE", "lake", true);

        setup(242, "BOTTOM_LEFT_LAKE", "lake", true);
        setup(243, "BOTTOM_RIGHT_LAKE", "lake", true);

        setup(244, "hut", "hut", false);
        setup(245, "floor", "hut", false);
        setup(246, "table", "hut", true);
        setup(247, "wall", "hut", true);

        setup(248, "EDGE_RIGHT", "island", false);
        setup(249, "OCEAN", "ocean", false);
        setup(250, "EDGE_TOP", "island", false);

        setup(251, "251", "dungeon", false);
        setup(252, "252", "dungeon", false);
        setup(253, "253", "dungeon", false);

        setup(254, "BLANK", "island", true);
        setup(255, "ISLAND", "island", false);
    }


    public void setup(int index, String imageName, String tileType, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(new File("res/tiles/" + tileType + "/" + imageName + ".png"));
            tiles[index].image = uTool.scaleImage(tiles[index].image, gp.tileSize, gp.tileSize);
            tiles[index].collision = collision;
        } catch (IOException e) {
            System.out.println("Nu se poate la index: " + index + " imageName: " + imageName);
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