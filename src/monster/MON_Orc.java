package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class MON_Orc extends Entity {
    GamePanel gp;

    public MON_Orc(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;

        maxLife = 10;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 10;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;

        motion1_duration = 20;
        motion2_duration = 40;
        motion3_duration = 60;
        motion4_duration = 85;

        getImage();
        getAttackImage();
    }

    public void getImage() {

        String name = "orc";
        //UP
        w_up[0] = setup("monster", name, "normal", "up_1");
        w_up[1] = setup("monster", name, "normal", "up_2");
        w_up[2] = setup("monster", name, "normal", "up_1");
        w_up[3] = setup("monster", name, "normal", "up_2");

        i_up[0] = setup("monster", name, "normal", "up_1");
        i_up[1] = setup("monster", name, "normal", "up_2");
        i_up[2] = setup("monster", name, "normal", "up_1");
        i_up[3] = setup("monster", name, "normal", "up_2");

        //DOWN
        w_down[0] = setup("monster", name, "normal", "down_1");
        w_down[1] = setup("monster", name, "normal", "down_2");
        w_down[2] = setup("monster", name, "normal", "down_1");
        w_down[3] = setup("monster", name, "normal", "down_2");

        i_down[0] = setup("monster", name, "normal", "down_1");
        i_down[1] = setup("monster", name, "normal", "down_2");
        i_down[2] = setup("monster", name, "normal", "down_1");
        i_down[3] = setup("monster", name, "normal", "down_2");

        //LEFT
        w_left[0] = setup("monster", name, "normal", "left_1");
        w_left[1] = setup("monster", name, "normal", "left_2");
        w_left[2] = setup("monster", name, "normal", "left_1");
        w_left[3] = setup("monster", name, "normal", "left_2");

        i_left[0] = setup("monster", name, "normal", "left_1");
        i_left[1] = setup("monster", name, "normal", "left_2");
        i_left[2] = setup("monster", name, "normal", "left_1");
        i_left[3] = setup("monster", name, "normal", "left_2");

        //RIGHT
        w_right[0] = setup("monster", name, "normal", "right_1");
        w_right[1] = setup("monster", name, "normal", "right_2");
        w_right[2] = setup("monster", name, "normal", "right_1");
        w_right[3] = setup("monster", name, "normal", "right_2");

        i_right[0] = setup("monster", name, "normal", "right_1");
        i_right[1] = setup("monster", name, "normal", "right_2");
        i_right[2] = setup("monster", name, "normal", "right_1");
        i_right[3] = setup("monster", name, "normal", "right_2");
    }

    public void getAttackImage() {
        String name = "orc";
        // Attack UP
        a_up[0] = setup("monster", name, "attack", "up_1", gp.tileSize, gp.tileSize * 2);
        a_up[1] = setup("monster", name, "attack", "up_2", gp.tileSize, gp.tileSize * 2);
        a_up[2] = setup("monster", name, "attack", "up_1", gp.tileSize, gp.tileSize * 2);
        a_up[3] = setup("monster", name, "attack", "up_2", gp.tileSize, gp.tileSize * 2);

        // Attack DOWN
        a_down[0] = setup("monster", name, "attack", "down_1", gp.tileSize, gp.tileSize * 2);
        a_down[1] = setup("monster", name, "attack", "down_2", gp.tileSize, gp.tileSize * 2);
        a_down[2] = setup("monster", name, "attack", "down_1", gp.tileSize, gp.tileSize * 2);
        a_down[3] = setup("monster", name, "attack", "down_2", gp.tileSize, gp.tileSize * 2);

        // Attack RIGHT
        a_right[0] = setup("monster", name, "attack", "right_1", gp.tileSize * 2, gp.tileSize);
        a_right[1] = setup("monster", name, "attack", "right_2", gp.tileSize * 2, gp.tileSize);
        a_right[2] = setup("monster", name, "attack", "right_1", gp.tileSize * 2, gp.tileSize);
        a_right[3] = setup("monster", name, "attack", "right_2", gp.tileSize * 2, gp.tileSize);

        // Attack LEFT
        a_left[0] = setup("monster", name, "attack", "left_1", gp.tileSize * 2, gp.tileSize);
        a_left[1] = setup("monster", name, "attack", "left_2", gp.tileSize * 2, gp.tileSize);
        a_left[2] = setup("monster", name, "attack", "left_1", gp.tileSize * 2, gp.tileSize);
        a_left[3] = setup("monster", name, "attack", "left_2", gp.tileSize * 2, gp.tileSize);

    }

    public void setAction() {
        if (onPath) {
            // Check if it stops chasing
            checkStopChasingOrNot(gp.player, 15, 100);

            // Search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        } else {
            // Check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);
            // Get a random direction if not on path
            getRandomDirection(120);
        }

        // Check if it attacks
        if (!attacking)
            checkAttackOrNot(30, gp.tileSize * 4, gp.tileSize);
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
    }

    public void checkDrop() {
        // Cast a die
        int i = new Random().nextInt(100) + 1;

        // Set the monster drop
        if (i < 50) {
            dropItem(new OBJ_Coin(gp));
        }
        if (i >= 50 && i < 70) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
        if (i >= 70 && i < 90) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >= 90) {
            dropItem(new OBJ_Axe(gp));
        }
    }
}