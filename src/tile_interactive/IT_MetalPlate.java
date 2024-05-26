package tile_interactive;

import main.GamePanel;

public class IT_MetalPlate extends InteractiveTile {
    GamePanel gp;
    public static final String itName = "Metal Plate";

    public IT_MetalPlate(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = itName;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;

        i_down[0] = setup("tiles", "interactive", "metalplate");
    }
}
