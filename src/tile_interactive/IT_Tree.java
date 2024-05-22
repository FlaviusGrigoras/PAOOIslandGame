package tile_interactive;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_ManaCrystal;
import object.OBJ_Wood;

import java.awt.*;

public class IT_Tree extends InteractiveTile {
    GamePanel gp;

    public IT_Tree(GamePanel gp) {
        super(gp);
        this.gp = gp;

        i_down[0] = setup("tiles", "interactive", "TREE_1");
        destructible = true;
        life = 3;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.currentWeapon.type == type_axe) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }

    public void playSE() {
        gp.playSE(10);
    }

    @Override
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gp, worldX / gp.tileSize, worldY / gp.tileSize);
        dropItem(new OBJ_Wood(gp));
        return tile;
    }

    public Color getParticleColor() {
        Color color = new Color(65, 50, 30);
        return color;
    }

    public int getParticleSize() {
        int size = 6;
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
