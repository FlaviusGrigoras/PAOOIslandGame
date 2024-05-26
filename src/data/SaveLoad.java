package data;

import entity.Entity;
import main.GamePanel;
import object.*;

public class SaveLoad {
    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
        SQLiteDB.initializeDatabase();
    }

    public Entity getObject(String itemName) {
        Entity obj = null;

        switch (itemName) {
            case "Coin":
                obj = new OBJ_Coin(gp);
                break;
            case "Stone":
                obj = new OBJ_Stone(gp);
                break;
            case "Wood":
                obj = new OBJ_Wood(gp);
                break;
            case "Iron":
                obj = new OBJ_Iron(gp);
                break;
            case "Axe":
                obj = new OBJ_Axe(gp);
                break;
            case "Pickaxe":
                obj = new OBJ_Pickaxe(gp);
                break;
            case "Wood Shield":
                obj = new OBJ_Shield_Wood(gp);
                break;
            case "Blue Shield":
                obj = new OBJ_Shield_Blue(gp);
                break;
            case "Red Potion":
                obj = new OBJ_Potion_Red(gp);
                break;
            case "Pistol":
                obj = new OBJ_Pistol(gp);
                break;
            case "Stick":
                obj = new OBJ_Stick(gp);
                break;
            case "Patura":
                obj = new OBJ_Patura(gp);
                break;
            case "Heart":
                obj = new OBJ_Heart(gp);
                break;
            case "Mana":
                obj = new OBJ_ManaCrystal(gp);
                break;
            case "Swood Normal":
                obj = new OBJ_Sword_Normal(gp);
                break;
            case "Lantern":
                obj = new OBJ_Lantern(gp);
                break;
            case "Tent":
                obj = new OBJ_Tent(gp);
                break;
            case "Door":  // Asigură-te că ai o clasă pentru obiectul Door
                obj = new OBJ_Iron_Door(gp);
                break;
            default:
                System.out.println("Obiectul " + itemName + " nu a fost găsit în catalogul de obiecte!");
                obj = new Entity(gp); // Fallback pentru obiecte necunoscute
                obj.name = itemName;
        }

        return obj;
    }


    public void save() {
        DataStorage.savePlayer(gp);
        DataStorage.saveInventory(gp);
        DataStorage.saveMapObjects(gp);
    }

    public void load() {
        try {
            DataStorage.loadPlayer(gp);
            System.out.println("Datele jucătorului au fost încărcate cu succes!");

            DataStorage.loadInventory(gp, this);
            System.out.println("Inventarul a fost încărcat cu succes!");

            DataStorage.loadMapObjects(gp, this);
            System.out.println("Obiectele de pe hartă au fost încărcate cu succes!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Eroare la încărcarea datelor!");
        }
    }

}
