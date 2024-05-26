package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    // Player stats
    int level;
    int maxLife;
    int maxMana;
    int strength;
    int exp;
    int nextLevelExp;
    int coin;
    int mana;
    int dexterity;
    int life;
    int worldX, worldY;

    // PLAYER INVENTORY
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;

    // Object on map
    String[][] mapObjectNames;
    int[][] mapObjectWorldX;
    int mapObjectWorldY[][];

    String mapObjectLootNames[][];
    boolean mapObjectOpened[][];
}
