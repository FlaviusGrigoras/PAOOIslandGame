package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin extends Entity {
    GamePanel gp;

    public OBJ_Coin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = "Coin";
        value = 1;
        i_down[0] = setup(name);
    }

    public boolean use(Entity entity) {
        gp.playSE(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coin += value;
        return false;
    }
}
