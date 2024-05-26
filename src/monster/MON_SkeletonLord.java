package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_SkeletonLord extends Entity {
    GamePanel gp;
    public static final String monName = "Skeleton Lord";

    public MON_SkeletonLord(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;

        maxLife = 50;
        life = maxLife;
        attack = 10;
        defense = 2;
        exp = 50;
        knockBackPower = 5;

        int size = gp.tileSize * 5;

        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;

        motion1_duration = 10;
        motion2_duration = 20;
        motion3_duration = 30;
        motion4_duration = 50;

        getImage();
        getAttackImage();
    }

    public void getImage() {

        String name = "skeletonlord";
        int i = 5;

        // UP
        w_up[0] = setup("monster", name, "normal", "up_1", gp.tileSize * i, gp.tileSize * i);
        w_up[1] = setup("monster", name, "normal", "up_2", gp.tileSize * i, gp.tileSize * i);
        w_up[2] = setup("monster", name, "normal", "up_1", gp.tileSize * i, gp.tileSize * i);
        w_up[3] = setup("monster", name, "normal", "up_2", gp.tileSize * i, gp.tileSize * i);

        i_up[0] = setup("monster", name, "normal", "up_1", gp.tileSize * i, gp.tileSize * i);
        i_up[1] = setup("monster", name, "normal", "up_2", gp.tileSize * i, gp.tileSize * i);
        i_up[2] = setup("monster", name, "normal", "up_1", gp.tileSize * i, gp.tileSize * i);
        i_up[3] = setup("monster", name, "normal", "up_2", gp.tileSize * i, gp.tileSize * i);

        // DOWN
        w_down[0] = setup("monster", name, "normal", "down_1", gp.tileSize * i, gp.tileSize * i);
        w_down[1] = setup("monster", name, "normal", "down_2", gp.tileSize * i, gp.tileSize * i);
        w_down[2] = setup("monster", name, "normal", "down_1", gp.tileSize * i, gp.tileSize * i);
        w_down[3] = setup("monster", name, "normal", "down_2", gp.tileSize * i, gp.tileSize * i);

        i_down[0] = setup("monster", name, "normal", "down_1", gp.tileSize * i, gp.tileSize * i);
        i_down[1] = setup("monster", name, "normal", "down_2", gp.tileSize * i, gp.tileSize * i);
        i_down[2] = setup("monster", name, "normal", "down_1", gp.tileSize * i, gp.tileSize * i);
        i_down[3] = setup("monster", name, "normal", "down_2", gp.tileSize * i, gp.tileSize * i);

        // LEFT
        w_left[0] = setup("monster", name, "normal", "left_1", gp.tileSize * i, gp.tileSize * i);
        w_left[1] = setup("monster", name, "normal", "left_2", gp.tileSize * i, gp.tileSize * i);
        w_left[2] = setup("monster", name, "normal", "left_1", gp.tileSize * i, gp.tileSize * i);
        w_left[3] = setup("monster", name, "normal", "left_2", gp.tileSize * i, gp.tileSize * i);

        i_left[0] = setup("monster", name, "normal", "left_1", gp.tileSize * i, gp.tileSize * i);
        i_left[1] = setup("monster", name, "normal", "left_2", gp.tileSize * i, gp.tileSize * i);
        i_left[2] = setup("monster", name, "normal", "left_1", gp.tileSize * i, gp.tileSize * i);
        i_left[3] = setup("monster", name, "normal", "left_2", gp.tileSize * i, gp.tileSize * i);

        // RIGHT
        w_right[0] = setup("monster", name, "normal", "right_1", gp.tileSize * i, gp.tileSize * i);
        w_right[1] = setup("monster", name, "normal", "right_2", gp.tileSize * i, gp.tileSize * i);
        w_right[2] = setup("monster", name, "normal", "right_1", gp.tileSize * i, gp.tileSize * i);
        w_right[3] = setup("monster", name, "normal", "right_2", gp.tileSize * i, gp.tileSize * i);

        i_right[0] = setup("monster", name, "normal", "right_1", gp.tileSize * i, gp.tileSize * i);
        i_right[1] = setup("monster", name, "normal", "right_2", gp.tileSize * i, gp.tileSize * i);
        i_right[2] = setup("monster", name, "normal", "right_1", gp.tileSize * i, gp.tileSize * i);
        i_right[3] = setup("monster", name, "normal", "right_2", gp.tileSize * i, gp.tileSize * i);
    }

    public void getAttackImage() {
        String name = "skeletonlord";
        int i = 5;
        // Attack UP
        a_up[0] = setup("monster", name, "attack", "up_1", gp.tileSize * i, gp.tileSize * 2 * i);
        a_up[1] = setup("monster", name, "attack", "up_2", gp.tileSize * i, gp.tileSize * 2 * i);
        a_up[2] = setup("monster", name, "attack", "up_1", gp.tileSize * i, gp.tileSize * 2 * i);
        a_up[3] = setup("monster", name, "attack", "up_2", gp.tileSize * i, gp.tileSize * 2 * i);

        // Attack DOWN
        a_down[0] = setup("monster", name, "attack", "down_1", gp.tileSize * i, gp.tileSize * 2 * i);
        a_down[1] = setup("monster", name, "attack", "down_2", gp.tileSize * i, gp.tileSize * 2 * i);
        a_down[2] = setup("monster", name, "attack", "down_1", gp.tileSize * i, gp.tileSize * 2 * i);
        a_down[3] = setup("monster", name, "attack", "down_2", gp.tileSize * i, gp.tileSize * 2 * i);

        // Attack RIGHT
        a_right[0] = setup("monster", name, "attack", "right_1", gp.tileSize * 2 * i, gp.tileSize * i);
        a_right[1] = setup("monster", name, "attack", "right_2", gp.tileSize * 2 * i, gp.tileSize * i);
        a_right[2] = setup("monster", name, "attack", "right_1", gp.tileSize * 2 * i, gp.tileSize * i);
        a_right[3] = setup("monster", name, "attack", "right_2", gp.tileSize * 2 * i, gp.tileSize * i);

        // Attack LEFT
        a_left[0] = setup("monster", name, "attack", "left_1", gp.tileSize * 2 * i, gp.tileSize * i);
        a_left[1] = setup("monster", name, "attack", "left_2", gp.tileSize * 2 * i, gp.tileSize * i);
        a_left[2] = setup("monster", name, "attack", "left_1", gp.tileSize * 2 * i, gp.tileSize * i);
        a_left[3] = setup("monster", name, "attack", "left_2", gp.tileSize * 2 * i, gp.tileSize * i);

    }

    public void setAction() {
        if (getTileDistance(gp.player) < 10) {
            moveTowardPlayer(60);

        } else {
            // Get a random direction if not on path
            getRandomDirection(120);
        }

        // Check if it attacks
        if (!attacking)
            checkAttackOrNot(60, gp.tileSize * 10, gp.tileSize * 5);
    }


    public void damageReaction() {
        actionLockCounter = 0;
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