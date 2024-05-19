package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {

    GamePanel gp;

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Fireball";
        speed = 7;
        maxLife = 40;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        i_down[0] = setup("projectile", "fireball", "down_1");
        i_down[1] = setup("projectile", "fireball", "down_2");

        i_left[0] = setup("projectile", "fireball", "left_1");
        i_left[1] = setup("projectile", "fireball", "left_2");

        i_up[0] = setup("projectile", "fireball", "up_1");
        i_up[1] = setup("projectile", "fireball", "up_2");

        i_right[0] = setup("projectile", "fireball", "right_1");
        i_right[1] = setup("projectile", "fireball", "right_2");
    }
}
