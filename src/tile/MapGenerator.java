package tile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator {
    public static final int CORNER_TOP_LEFT = 0;
    public static final int EDGE_TOP = 3;
    public static final int CORNER_TOP_RIGHT = 6;

    public static final int EDGE_LEFT = 1;
    public static final int ISLAND = 4;
    public static final int EDGE_RIGHT = 7;

    public static final int CORNER_BOTTOM_LEFT = 2;
    public static final int EDGE_BOTTOM = 5;
    public static final int CORNER_BOTTOM_RIGHT = 8;

    public static final int OCEAN = 9;

    public static final int TOP_LEFT_RAMA = 15;
    public static final int TOP_RAMA = 18;
    public static final int TOP_RIGHT_RAMA = 20;

    public static final int LEFT_RAMA = 16;
    public static final int RIGHT_RAMA = 21;

    public static final int BOTTOM_LEFT_RAMA = 17;
    public static final int BOTTOM_RAMA = 19;
    public static final int BOTTOM_RIGHT_RAMA = 22;

    public static final int TREE = 23;
    public static final int ROCK = 25;

    public static int[][] generateMap(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive.");
        }

        int[][] map = new int[width][height];
        Random random = new Random();

        // Generate ocean
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (random.nextDouble() < 0.02) {
                    int randomValue = random.nextInt(5);
                    if (randomValue == 0) {
                        randomValue = 1; // or any other positive integer
                    }
                    map[i][j] = randomValue + OCEAN + 1; // Random other water tiles
                } else {
                    map[i][j] = OCEAN;
                }
            }
        }

        // Generate island
        int islandWidth = Math.max(20, random.nextInt(width - 10) + 10);
        int islandHeight = Math.max(15, random.nextInt(height - 10) + 10);
        if (width < islandWidth || height < islandHeight) {
            throw new IllegalArgumentException("Width and height must be large enough to generate island.");
        }
        int islandStartX = random.nextInt(Math.max(1, width - islandWidth - 2)) + 1;
        int islandStartY = random.nextInt(Math.max(1, height - islandHeight - 2)) + 1;

        for (int i = islandStartX; i < islandStartX + islandWidth; i++) {
            for (int j = islandStartY; j < islandStartY + islandHeight; j++) {
                if (i < width && j < height) {
                    map[i][j] = ISLAND;
                }
            }
        }

        // Replace island tiles according to surrounding ocean
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] == ISLAND) {
                    int tilePosition = checkTilePosition(map, i, j);
                    switch (tilePosition) {
                        case CORNER_TOP_LEFT:
                            map[i][j] = CORNER_TOP_LEFT; // Colt. Ocean in stanga si sus
                            break;
                        case EDGE_TOP:
                            map[i][j] = EDGE_TOP; // Margine. Ocean doar sus
                            break;
                        case CORNER_TOP_RIGHT:
                            map[i][j] = CORNER_TOP_RIGHT; // Colt. Ocean in dreapta si sus
                            break;
                        case EDGE_LEFT:
                            map[i][j] = EDGE_LEFT; // Margine. Ocean doar in stanga
                            break;
                        case EDGE_RIGHT:
                            map[i][j] = EDGE_RIGHT; // Margine. Ocean doar in dreapta
                            break;
                        case CORNER_BOTTOM_LEFT:
                            map[i][j] = CORNER_BOTTOM_LEFT; // Colt. Ocean in stanga si jos
                            break;
                        case EDGE_BOTTOM:
                            map[i][j] = EDGE_BOTTOM; // Margine. Ocean doar in jos
                            break;
                        case CORNER_BOTTOM_RIGHT:
                            map[i][j] = CORNER_BOTTOM_RIGHT; // Colt. Ocean in dreapta si jos
                            break;
                        default:
                            break;
                    }
                    // Check if the tile is surrounded by 3 or 4 ocean tiles
                    int oceanCount = 0;
                    if (i > 0 && map[i - 1][j] == OCEAN) oceanCount++;
                    if (i < width - 1 && map[i + 1][j] == OCEAN) oceanCount++;
                    if (j > 0 && map[i][j - 1] == OCEAN) oceanCount++;
                    if (j < height - 1 && map[i][j + 1] == OCEAN) oceanCount++;
                    if (oceanCount >= 3) {
                        map[i][j] = OCEAN;
                    }
                }
            }
        }
        for (int i = islandStartX; i < islandStartX + islandWidth; i++) {
            for (int j = islandStartY; j < islandStartY + islandHeight; j++) {
                if (i < width && j < height && map[i][j] == ISLAND) {
                    double randomValue = random.nextDouble();
                    if (randomValue < 0.1) {
                        map[i][j] = TREE; // Tree tile with 20% chance
                    } else if (randomValue < 0.13) {
                        map[i][j] = ROCK; // Rock tile with 3% chance
                    }
                }
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0) {
                    if (j == 0) {
                        map[i][j] = TOP_LEFT_RAMA; // Coltul stânga sus
                    } else if (j == height - 1) {
                        map[i][j] = TOP_RIGHT_RAMA; // Coltul dreapta sus
                    } else {
                        map[i][j] = TOP_RAMA; // Marginea sus
                    }
                } else if (i == width - 1) {
                    if (j == 0) {
                        map[i][j] = BOTTOM_LEFT_RAMA; // Coltul stânga jos
                    } else if (j == height - 1) {
                        map[i][j] = BOTTOM_RIGHT_RAMA; // Coltul dreapta jos
                    } else {
                        map[i][j] = BOTTOM_RAMA; // Marginea jos
                    }
                } else if (j == 0) {
                    map[i][j] = LEFT_RAMA; // Marginea stânga
                } else if (j == height - 1) {
                    map[i][j] = RIGHT_RAMA; // Marginea dreapta
                }
            }
        }

        // Write map to file after generation
        writeMapToFile(map);

        return map;
    }

    public static int checkTilePosition(int[][] map, int x, int y) {
        int width = map.length;
        int height = map[0].length;

        boolean oceanLeft = (y > 0 && map[x][y - 1] == OCEAN);
        boolean oceanRight = (y < height - 1 && map[x][y + 1] == OCEAN);
        boolean oceanTop = (x > 0 && map[x - 1][y] == OCEAN);
        boolean oceanBottom = (x < width - 1 && map[x + 1][y] == OCEAN);

        if (oceanTop && oceanLeft) {
            return CORNER_TOP_LEFT;
        } else if (oceanTop && oceanRight) {
            return CORNER_TOP_RIGHT;
        } else if (oceanBottom && oceanLeft) {
            return CORNER_BOTTOM_LEFT;
        } else if (oceanBottom && oceanRight) {
            return CORNER_BOTTOM_RIGHT;
        } else if (oceanTop) {
            return EDGE_TOP;
        } else if (oceanBottom) {
            return EDGE_BOTTOM;
        } else if (oceanLeft) {
            return EDGE_LEFT;
        } else if (oceanRight) {
            return EDGE_RIGHT;
        } else {
            return -1;
        }
    }

    private static void writeMapToFile(int[][] map) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter("Mapa.txt"))) {
            // Write map to file
            for (int[] ints : map) {
                for (int j = 0; j < map[0].length; j++) {
                    fileWriter.write(ints[j] + " ");
                }
                fileWriter.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static int[] chooseRandomIslandTile(int[][] map) {
        Random random = new Random();
        int[] coordinates = new int[2];

        // Lista pentru a ține coordonatele tuturor tile-urilor de tip 4 (ISLAND)
        List<int[]> islandTiles = new ArrayList<>();

        // Căutăm toate tile-urile de tip 15 (ISLAND) și adăugăm coordonatele lor în listă
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == ISLAND) { // Verificare dacă tile-ul este de tip insulă (15)
                    islandTiles.add(new int[]{
                            i,
                            j
                    });
                }
            }
        }

        // Alegem aleatoriu un tile de tip 15 (ISLAND)
        if (!islandTiles.isEmpty()) {
            int randomIndex = random.nextInt(islandTiles.size());
            int[] randomIslandTile = islandTiles.get(randomIndex);
            coordinates[0] = randomIslandTile[0]; // X
            coordinates[1] = randomIslandTile[1]; // Y
        } else {
            // Dacă nu există tile-uri de tip 15 (ISLAND), apelăm recursiv funcția
            return chooseRandomIslandTile(map);
        }

        return coordinates;
    }
}