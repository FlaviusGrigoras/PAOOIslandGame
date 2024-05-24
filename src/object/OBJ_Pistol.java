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
        price = 250;
        sellPrice = 100;
        projectile = new OBJ_Rock(gp);
        description = "[" + name + "]\nDon't get caught by the \npolice.";
        knockBackPower = 15;
    }
}