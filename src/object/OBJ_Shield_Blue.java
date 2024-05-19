package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = "Blue Shield";
        i_down[0] = setup("shield_blue");
        defenseValue = 5;
        description = "[" + name + "]\nA shiny blue shield.";
    }
}
