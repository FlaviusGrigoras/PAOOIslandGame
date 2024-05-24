package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Rock extends Projectile {

    GamePanel gp;

    public OBJ_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Rock";
        speed = 8;
        maxLife = 40;
        life = maxLife;
        attack = 2;
        useCost = 1;
        stackable=true;
        alive = false;
        getImage();
    }

    public void getImage() {
        i_down[0] = setup("projectile", "rock", "down_1");
        i_down[1] = setup("projectile", "rock", "down_1");

        i_left[0] = setup("projectile", "rock", "down_1");
        i_left[1] = setup("projectile", "rock", "down_1");

        i_up[0] = setup("projectile", "rock", "down_1");
        i_up[1] = setup("projectile", "rock", "down_1");

        i_right[0] = setup("projectile", "rock", "down_1");
        i_right[1] = setup("projectile", "rock", "down_1");
    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    public void substractResource(Entity user) {
        user.ammo -= useCost;
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
