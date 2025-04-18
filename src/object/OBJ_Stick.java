package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Stick extends Entity {
    public OBJ_Stick(GamePanel gp) {
        super(gp);

        type = type_fist;
        name = "Stick";
        i_down[0] = setup("stick");
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        price = 10;
        sellPrice=5;
        description = "[" + name + "]\nBetter than nothing \nahh weapon.";

        motion1_duration = 5;
        motion2_duration = 12;
        motion3_duration = 19;
        motion4_duration = 25;
    }
}
