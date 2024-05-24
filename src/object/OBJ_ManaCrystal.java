package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    GamePanel gp;

    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = "Mana Crystal";
        value = 1;
        price = 50;
        i_down[0] = setup("manacrystal_full");
        image = setup("manacrystal_full");
        image2 = setup("manacrystal_blank");
    }

    public boolean use(Entity entity) {
        gp.playSE(1);
        gp.ui.addMessage("Mana +" + value);
        entity.mana += value;
        return false;
    }
}
