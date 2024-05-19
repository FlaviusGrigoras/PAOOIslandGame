package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pistol extends Entity {
    public OBJ_Pistol(GamePanel gp) {
        super(gp);

        type = type_pistol;
        name = "Pistol";
        i_down[0] = setup("pistol");
        attackValue = 10;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nDon't get caught by the \npolice.";
    }
}
