package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {

    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = type_light;
        name = "Lantern";
        i_down[0] = setup("lantern");
        description = "[" + name + "]\nIlluminates your \nsurroundings.";
        price = 30;
        sellPrice = 10;
        lightRadius = 250;
    }
}
