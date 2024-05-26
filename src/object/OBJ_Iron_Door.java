package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Iron_Door extends Entity {
    GamePanel gp;
    public static final String objName = "Door";

    public OBJ_Iron_Door(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = objName;
        i_down[0] = setup("door_iron");
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialogue();
    }

    public void setDialogue() {
        dialogues[0] = "It won't budge";
    }
}
