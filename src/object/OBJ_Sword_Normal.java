package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {
    public OBJ_Sword_Normal(GamePanel gp){
        super(gp);

        name="Normal Sword";
        i_down[0]=setup("sword_normal");
        attackValue=1;
    }
}
