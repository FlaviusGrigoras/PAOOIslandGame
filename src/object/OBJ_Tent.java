package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {
    GamePanel gp;

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Tent";
        i_down[0] = setup("tent");
        description = "[Tent]\nYou can sleep until\nnext morning!";
        price = 350;
        stackable = true;
    }

    public boolean use(Entity entity) {
        gp.gameState = gp.sleepState;
        gp.playSE(13);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(i_down[0]);
        return false;
    }
}
