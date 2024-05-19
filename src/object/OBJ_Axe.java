package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gp){
        super(gp);

        type=type_axe;
        name="Woodcutter's Axe";
        i_down[0]=setup("axe");
        attackValue=5;
        attackArea.width=30;
        attackArea.height=30;
        description = "[" + name + "]\nA bit rusty but still can \ncut out some trees.";
    }
}
