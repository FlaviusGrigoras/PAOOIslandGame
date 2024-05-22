package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Red Potion";
        value = 5;
        i_down[0] = setup("potion_red");
        description = "[" + name + "]" + "\nHeals your life by " + value + ".";
    }

    public void use(Entity entity) {
        gp.gameState = gp.dialogState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
        entity.life += value;
        gp.playSE(8);
    }
}
