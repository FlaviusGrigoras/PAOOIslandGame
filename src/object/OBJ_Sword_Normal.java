package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {
    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = "Normal Sword";
        i_down[0] = setup("sword_normal");
        attackValue = 3;
        attackArea.width = 36;
        attackArea.height = 36;
        price = 40;
        sellPrice=15;
        description = "[" + name + "]\nAn old sword.";
    }
}
