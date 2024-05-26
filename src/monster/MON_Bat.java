package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class MON_Bat extends Entity {
    GamePanel gp;

    public MON_Bat(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Bat";
        defaultSpeed = 4;
        speed = defaultSpeed;

        maxLife = 7;
        life = maxLife;
        attack = 7;
        defense = 0;
        exp = 7;

        solidArea.x = 3;
        solidArea.y = 15;
        solidArea.width = 42;
        solidArea.height = 21;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    public void getImage() {

        //UP
        String name = "bat";
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
        if (onPath) {
            /*// Check if it stops chasing
            checkStopChasingOrNot(gp.player, 15, 100);

            // Search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

            // Check if it shoots a projectile
            checkShootOrNot(200, 30);*/
        } else {
            // Check if it starts chasing
            //checkStartChasingOrNot(gp.player, 5, 100);
            // Get a random direction if not on path
            getRandomDirection(10);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
    }

    public void checkDrop() {
        // Cast a die
        int i = new Random().nextInt(100) + 1;

        // Set the monster drop
        if (i < 75) {
            dropItem(new OBJ_Coin(gp));
            dropItem(new OBJ_Coin(gp));
            dropItem(new OBJ_Coin(gp));
            dropItem(new OBJ_Coin(gp));
            dropItem(new OBJ_Coin(gp));
        }
        if (i > 74) {
            dropItem(new OBJ_Heart(gp));
        }
    }
}
