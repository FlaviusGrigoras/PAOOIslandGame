package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Rock;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    GamePanel gp;

    public MON_GreenSlime(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Green Slime";
        speed = 1;

        maxLife = 4;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    public void getImage() {

        //UP
        String name = "greenslime";
        w_up[0] = setup("monster", name, "down_1");
        w_up[1] = setup("monster", name, "down_2");
        w_up[2] = setup("monster", name, "down_1");
        w_up[3] = setup("monster", name, "down_2");

        i_up[0] = setup("monster", name, "down_1");
        i_up[1] = setup("monster", name, "down_2");
        i_up[2] = setup("monster", name, "down_1");
        i_up[3] = setup("monster", name, "down_2");

        //DOWN
        w_down[0] = setup("monster", name, "down_1");
        w_down[1] = setup("monster", name, "down_2");
        w_down[2] = setup("monster", name, "down_1");
        w_down[3] = setup("monster", name, "down_2");

        i_down[0] = setup("monster", name, "down_1");
        i_down[1] = setup("monster", name, "down_2");
        i_down[2] = setup("monster", name, "down_1");
        i_down[3] = setup("monster", name, "down_2");

        //LEFT
        w_left[0] = setup("monster", name, "down_1");
        w_left[1] = setup("monster", name, "down_2");
        w_left[2] = setup("monster", name, "down_1");
        w_left[3] = setup("monster", name, "down_2");

        i_left[0] = setup("monster", name, "down_1");
        i_left[1] = setup("monster", name, "down_2");
        i_left[2] = setup("monster", name, "down_1");
        i_left[3] = setup("monster", name, "down_2");

        //RIGHT
        w_right[0] = setup("monster", name, "down_1");
        w_right[1] = setup("monster", name, "down_2");
        w_right[2] = setup("monster", name, "down_1");
        w_right[3] = setup("monster", name, "down_2");

        i_right[0] = setup("monster", name, "down_1");
        i_right[1] = setup("monster", name, "down_2");
        i_right[2] = setup("monster", name, "down_1");
        i_right[3] = setup("monster", name, "down_2");
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // 1-100
            int j = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }
            isWalking = j <= 75;
            actionLockCounter = 0;
        }
        int i = new Random().nextInt(100) + 1;
        if (i > 99 && !projectile.alive && shotAvailableCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
}
