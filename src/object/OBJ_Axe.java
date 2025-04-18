package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gp) {
        super(gp);

        type = type_axe;
        name = "Woodcutter's Axe";
        i_down[0] = setup("axe");
        attackValue = 5;
        attackArea.width = 30;
        attackArea.height = 30;
        price = 75;
        sellPrice = 20;
        description = "[" + name + "]\nA bit rusty but still can \ncut out some trees.";
        knockBackPower = 10;
        motion1_duration = 5;
        motion2_duration = 12;
        motion3_duration = 19;
        motion4_duration = 25;
    }
}
