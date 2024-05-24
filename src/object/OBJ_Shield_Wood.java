package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = "Wood Shield";
        i_down[0] = setup("shield_wood");
        defenseValue = 3;
        price = 30;
        sellPrice=10;
        description = "[" + name + "]\nMade by wood.";
    }
}
