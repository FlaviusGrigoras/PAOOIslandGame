package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDB {

    private static Connection connection = null;

    public static void initializeDatabase() {
        try (Connection connection = SQLiteDB.getConnection()) {
            String createPlayerTable = "CREATE TABLE IF NOT EXISTS player ("
                    + "level INTEGER, "
                    + "maxLife INTEGER, "
                    + "life INTEGER, "
                    + "maxMana INTEGER, "
                    + "mana INTEGER, "
                    + "strength INTEGER, "
                    + "dexterity INTEGER, "
                    + "exp INTEGER, "
                    + "nextLevelExp INTEGER, "
                    + "coin INTEGER, "
                    + "worldX INTEGER, "
                    + "worldY INTEGER"
                    + ")";
            String createInventoryTable = "CREATE TABLE IF NOT EXISTS player_inventory ("
                    + "name TEXT, "
                    + "amount INTEGER"
                    + ")";
            String createMapObjectsTable = "CREATE TABLE IF NOT EXISTS map_objects ("
                    + "mapNum INTEGER, "
                    + "objIndex INTEGER, "
                    + "name TEXT, "
                    + "worldX INTEGER, "
                    + "worldY INTEGER"
                    + ")";

            connection.createStatement().execute(createPlayerTable);
            connection.createStatement().execute(createInventoryTable);
            connection.createStatement().execute(createMapObjectsTable);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Eroare la inițializarea bazei de date!");
        }
    }


    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String dbPath = "jdbc:sqlite:game_database.sqlite";
            connection = DriverManager.getConnection(dbPath);
        }
        return connection;
    }
}
