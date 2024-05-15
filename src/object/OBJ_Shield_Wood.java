package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);
        name = "Wood Shield";
        i_down[0] = setup("shield_wood");
        defenseValue = 1;
    }
}
