package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class OBJ_Coin extends Entity {

    public OBJ_Coin(GamePanel gp) {
        super(gp);
        name = "Coin";
        i_down[0] = setup(name);

        collision = true;
        solidArea.x = 8 * 3;
        solidArea.y = 5 * 3;
        solidArea.width = 8 * 3;
        solidArea.height = 11 * 3;
    }
}
