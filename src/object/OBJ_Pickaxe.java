package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {
    public OBJ_Pickaxe(GamePanel gp) {
        super(gp);

        type = type_pickaxe;
        name = "Pickaxe";
        i_down[0] = setup("pickaxe");
        attackValue = 5;
        attackArea.width = 30;
        attackArea.height = 30;
        price = 1;
        sellPrice = 20;
        description = "[" + name + "]\nYou will dig with it!";
        knockBackPower = 10;
        motion1_duration = 4;
        motion2_duration = 7;
        motion3_duration = 8;
        motion4_duration = 10;
    }
}
