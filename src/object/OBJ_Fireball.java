package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Fireball extends Projectile {

    GamePanel gp;

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Fireball";
        speed = 5;
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

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.mana >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }



    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    public Color getParticleColor() {
        Color color = new Color(240, 50, 0);
        return color;
    }

    public int getParticleSize() {
        int size = 10;
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
