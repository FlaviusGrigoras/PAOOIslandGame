package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = "Heart";
        value = 2;
        i_down[0] = setup("heart_full");
        image = setup("heart_full");
        image2 = setup("heart_half");
        image3 = setup("heart_blank");
    }

    public boolean use(Entity entity) {
        gp.playSE(1);
        gp.ui.addMessage("Life +" + value);
        entity.life += value;
        return false;
    }
}
