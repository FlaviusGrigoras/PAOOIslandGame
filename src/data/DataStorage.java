package data;

import entity.Entity;
import main.GamePanel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataStorage {

    public static void savePlayer(GamePanel gp) {
        try (Connection connection = SQLiteDB.getConnection()) {
            String deletePlayer = "DELETE FROM player";
            String insertPlayer = "INSERT INTO player (level, maxLife, life, maxMana, mana, strength, dexterity, exp, nextLevelExp, coin, worldX, worldY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement deleteStmt = connection.prepareStatement(deletePlayer);
            deleteStmt.executeUpdate();

            PreparedStatement insertStmt = connection.prepareStatement(insertPlayer);
            insertStmt.setInt(1, gp.player.level);
            insertStmt.setInt(2, gp.player.maxLife);
            insertStmt.setInt(3, gp.player.life);
            insertStmt.setInt(4, gp.player.maxMana);
            insertStmt.setInt(5, gp.player.mana);
            insertStmt.setInt(6, gp.player.strength);
            insertStmt.setInt(7, gp.player.dexterity);
            insertStmt.setInt(8, gp.player.exp);
            insertStmt.setInt(9, gp.player.nextLevelExp);
            insertStmt.setInt(10, gp.player.coin);
            insertStmt.setInt(11, gp.player.worldX);
            insertStmt.setInt(12, gp.player.worldY);
            insertStmt.executeUpdate();

            System.out.println("Datele jucătorului au fost salvate cu succes!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadPlayer(GamePanel gp) {
        try (Connection connection = SQLiteDB.getConnection()) {
            String selectPlayer = "SELECT * FROM player";

            PreparedStatement selectStmt = connection.prepareStatement(selectPlayer);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                gp.player.level = rs.getInt("level");
                gp.player.maxLife = rs.getInt("maxLife");
                gp.player.life = rs.getInt("life");
                gp.player.maxMana = rs.getInt("maxMana");
                gp.player.mana = rs.getInt("mana");
                gp.player.strength = rs.getInt("strength");
                gp.player.dexterity = rs.getInt("dexterity");
                gp.player.exp = rs.getInt("exp");
                gp.player.nextLevelExp = rs.getInt("nextLevelExp");
                gp.player.coin = rs.getInt("coin");
                gp.player.worldX = rs.getInt("worldX");
                gp.player.worldY = rs.getInt("worldY");

                System.out.println("Datele jucătorului au fost încărcate cu succes!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveInventory(GamePanel gp) {
        try (Connection connection = SQLiteDB.getConnection()) {
            String deleteInventory = "DELETE FROM inventory";
            String insertInventory = "INSERT INTO inventory (name, amount) VALUES (?, ?)";

            PreparedStatement deleteStmt = connection.prepareStatement(deleteInventory);
            deleteStmt.executeUpdate();

            PreparedStatement insertStmt = connection.prepareStatement(insertInventory);
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                insertStmt.setString(1, gp.player.inventory.get(i).name);
                insertStmt.setInt(2, gp.player.inventory.get(i).amount);
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();

            System.out.println("Inventarul a fost salvat cu succes!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadInventory(GamePanel gp, SaveLoad saveLoad) {
        try (Connection connection = SQLiteDB.getConnection()) {
            String selectInventory = "SELECT * FROM player_inventory";

            PreparedStatement selectStmt = connection.prepareStatement(selectInventory);
            ResultSet rs = selectStmt.executeQuery();

            gp.player.inventory.clear();

            while (rs.next()) {
                String itemName = rs.getString("name");
                int amount = rs.getInt("amount");

                Entity obj = saveLoad.getObject(itemName);
                if (obj == null) {
                    System.out.println("Obiectul " + itemName + " nu a fost găsit în catalogul de obiecte!");
                    continue;
                }

                obj.amount = amount;
                gp.player.inventory.add(obj);
            }

            System.out.println("Inventarul a fost încărcat cu succes!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Eroare la încărcarea inventarului!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Eroare necunoscută la încărcarea inventarului!");
        }
    }



    public static void saveMapObjects(GamePanel gp) {
        try (Connection connection = SQLiteDB.getConnection()) {
            String deleteMapObjects = "DELETE FROM map_objects";
            String insertMapObjects = "INSERT INTO map_objects (mapNum, objIndex, name, worldX, worldY) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement deleteStmt = connection.prepareStatement(deleteMapObjects);
            deleteStmt.executeUpdate();

            PreparedStatement insertStmt = connection.prepareStatement(insertMapObjects);
            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (gp.obj[mapNum][i] != null) {
                        insertStmt.setInt(1, mapNum);
                        insertStmt.setInt(2, i);
                        insertStmt.setString(3, gp.obj[mapNum][i].name);
                        insertStmt.setInt(4, gp.obj[mapNum][i].worldX);
                        insertStmt.setInt(5, gp.obj[mapNum][i].worldY);
                        insertStmt.addBatch();
                    }
                }
            }
            insertStmt.executeBatch();

            System.out.println("Obiectele de pe hartă au fost salvate cu succes!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void loadMapObjects(GamePanel gp, SaveLoad saveLoad) {
        try (Connection connection = SQLiteDB.getConnection()) {
            String selectMapObjects = "SELECT * FROM map_objects";

            PreparedStatement selectStmt = connection.prepareStatement(selectMapObjects);
            ResultSet rs = selectStmt.executeQuery();

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    gp.obj[mapNum][i] = null;
                }
            }

            while (rs.next()) {
                int mapNum = rs.getInt("mapNum");
                int objIndex = rs.getInt("objIndex");
                String name = rs.getString("name");
                int worldX = rs.getInt("worldX");
                int worldY = rs.getInt("worldY");

                Entity obj = saveLoad.getObject(name);
                if (obj == null) {
                    System.out.println("Obiectul " + name + " nu a fost găsit!");
                    continue;
                }

                gp.obj[mapNum][objIndex] = obj;
                gp.obj[mapNum][objIndex].worldX = worldX;
                gp.obj[mapNum][objIndex].worldY = worldY;
            }

            System.out.println("Obiectele de pe hartă au fost încărcate cu succes!");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Eroare la încărcarea obiectelor de pe hartă!");
        }
    }

}
