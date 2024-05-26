package tile_interactive;

import main.GamePanel;

public class IT_Boat extends InteractiveTile {
    GamePanel gp;

    public IT_Boat(GamePanel gp) {
        super(gp);
        this.gp = gp;

        i_down[0] = setup("tiles", "interactive", "boat");
    }
}
