package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class OBJ_Stone extends Entity {
    public OBJ_Stone(GamePanel gp) {
        super(gp);
        name = "Stone";
        sellPrice=15;
        i_down[0] = setup(name);

        collision = true;
        solidArea.x = 8 * 3;
        solidArea.y = 5 * 3;
        solidArea.width = 8 * 3;
        solidArea.height = 11 * 3;
        description = "[" + name + "]\nBetter than wood.";
    }
}
