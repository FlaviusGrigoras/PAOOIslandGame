package tile_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile {
    GamePanel gp;

    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        i_down[0] = setup("tiles", "interactive", "trunk");
    }
}
