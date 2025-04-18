package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class OBJ_Iron extends Entity {

    public OBJ_Iron(GamePanel gp) {
        super(gp);
        name = "Iron";
        i_down[0] = setup(name);
        sellPrice = 35;

        collision = true;
        solidArea.x = 8 * 3;
        solidArea.y = 5 * 3;
        solidArea.width = 8 * 3;
        solidArea.height = 11 * 3;

        stackable=true;
        description = "[" + name + "]\nYou can craft better \ntools.";
    }
}
