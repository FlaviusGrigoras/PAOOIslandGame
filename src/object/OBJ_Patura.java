package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Patura extends Entity {
    public OBJ_Patura(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = "Broken cape";
        i_down[0] = setup("patura");
        defenseValue = 1;
        price=10;
        sellPrice=5;
        description = "[" + name + "]\nFrom my grandma.";
    }
}
