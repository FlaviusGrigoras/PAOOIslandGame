package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {

    GamePanel gp;

    public OBJ_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
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
    public void substractResource(Entity user){
        user.ammo-=useCost;
    }
}
