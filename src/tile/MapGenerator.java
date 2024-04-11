package tile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator {
    public static final int CORNER_TOP_LEFT = 0;
    public static final int EDGE_TOP = 1;
    public static final int CORNER_TOP_RIGHT = 2;
    public static final int EDGE_LEFT = 14;
    public static final int EDGE_RIGHT = 16;
    public static final int CORNER_BOTTOM_LEFT = 28;
    public static final int EDGE_BOTTOM = 29;
    public static final int CORNER_BOTTOM_RIGHT = 30;
    public static final int OCEAN = 31;
    public static final int ISLAND = 15;
    public static final int LAKE_TOP = 3;
    public static final int LAKE_BOTTOM = 17;
    public static final int MIN_DISTANCE_FROM_OCEAN = 3; // Changed to 3
    public static final double LAKE_CHANCE = 0.20;

    public static int[][] generateMap(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive.");
        }

        int[][] map = new int[width][height];
        Random random = new Random();

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter("Mapa.txt"))) {
            // Generate ocean
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (random.nextDouble() < 0.02) {
                        int randomValue = random.nextInt(5);
                        if (randomValue == 0) {
                            randomValue = 1; // or any other positive integer
                        }
                        map[i][j] = randomValue + 32; // Random other water tiles
                    } else {
                        map[i][j] = OCEAN;
                    }
                    fileWriter.write(map[i][j] + " ");
                }
                fileWriter.write("\n");
            }


            // Generate island
            int islandWidth = Math.max(20, random.nextInt(width - 10) + 10);
            int islandHeight = Math.max(15, random.nextInt(height - 10) + 10);
            if (width < islandWidth || height < islandHeight) {
                throw new IllegalArgumentException("Width and height must be large enough to generate island.");
            }
            int islandStartX = random.nextInt(Math.max(1,width - islandWidth - 2)) + 1;
            int islandStartY = random.nextInt(Math.max(1,height - islandHeight - 2)) + 1;

            for (int i = islandStartX; i < islandStartX + islandWidth; i++) {
                for (int j = islandStartY; j < islandStartY + islandHeight; j++) {
                    if (i < width && j < height) {
                        map[i][j] = ISLAND;
                    }
                }
            }

            // Restul codului pentru generarea lacurilor și așa mai departe...

            // Generate lakes
            for (int i = MIN_DISTANCE_FROM_OCEAN; i < width - MIN_DISTANCE_FROM_OCEAN; i++) {
                for (int j = MIN_DISTANCE_FROM_OCEAN; j < height - MIN_DISTANCE_FROM_OCEAN; j++) {
                    if (map[i][j] == ISLAND && random.nextDouble() < LAKE_CHANCE) {
                        // Check if the tile is at least MIN_DISTANCE_FROM_OCEAN tiles away from the ocean
                        boolean isNearOcean = true;
                        for (int x = i - MIN_DISTANCE_FROM_OCEAN; x <= i + MIN_DISTANCE_FROM_OCEAN && isNearOcean; x++) {
                            for (int y = j - MIN_DISTANCE_FROM_OCEAN; y <= j + MIN_DISTANCE_FROM_OCEAN; y++) {
                                if (x >= 0 && x < width && y >= 0 && y < height && map[x][y] != OCEAN) {
                                    isNearOcean = false;
                                    break;
                                }
                            }
                        }
                        if (isNearOcean) {
                            // Randomly choose between the top and bottom lake tiles
                            map[i][j] = (random.nextDouble() < 0.5) ? LAKE_TOP : LAKE_BOTTOM;
                        }
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

            // Write map to file
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    fileWriter.write(map[i][j] + " ");
                }
                fileWriter.write("\n");
            }

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

        return map;
    }

    public static int checkTilePosition(int[][] map, int x, int y) {
        int width = map.length;
        int height = map[0].length;

        boolean oceanTop = (y > 0 && map[x][y - 1] == OCEAN);
        boolean oceanBottom = (y < height - 1 && map[x][y + 1] == OCEAN);
        boolean oceanLeft = (x > 0 && map[x - 1][y] == OCEAN);
        boolean oceanRight = (x < width - 1 && map[x + 1][y] == OCEAN);

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

    public static int[] chooseRandomIslandTile(int[][] map) {
        Random random = new Random();
        int[] coordinates = new int[2];

        // Lista pentru a ține coordonatele tuturor tile-urilor de tip 15 (ISLAND)
        List<int[]> islandTiles = new ArrayList<>();

        // Căutăm toate tile-urile de tip 15 (ISLAND) și adăugăm coordonatele lor în listă
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == ISLAND) {
                    islandTiles.add(new int[]{i, j});
                }
            }
        }

        // Alegem aleatoriu un tile de tip 15 (ISLAND)
        if (!islandTiles.isEmpty()) {
            int[] randomIslandTile = islandTiles.get(random.nextInt(islandTiles.size()));
            coordinates[0] = randomIslandTile[0]; // X
            coordinates[1] = randomIslandTile[1]; // Y
        } else {
            // Dacă nu există tile-uri de tip 15 (ISLAND), returnăm {-1, -1}
            coordinates[0] = -1;
            coordinates[1] = -1;
        }

        return coordinates;
    }

}