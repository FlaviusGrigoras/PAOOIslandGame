package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class OBJ_Wood extends Entity {
    public OBJ_Wood(GamePanel gp) {
        super(gp);
        name = "Wood";
        i_down[0] = setup(name);
        sellPrice=10;

        collision = true;
        solidArea.x = 5 * 3;
        solidArea.y = 8 * 3;
        solidArea.width = 11 * 3;
        solidArea.height = 8 * 3;
        description = "[" + name + "]\nGood for starter tools.";
    }
}
