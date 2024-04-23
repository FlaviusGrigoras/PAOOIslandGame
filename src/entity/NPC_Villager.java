package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_Villager extends Entity {
    public NPC_Villager(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
    }

    public void getImage() {
        // Pentru directia 'up'
        w_up[0] = setup("npc", "Villager", "Walk", "up_1");
        w_up[1] = setup("npc", "Villager", "Walk", "up_2");
        w_up[2] = setup("npc", "Villager", "Walk", "up_3");
        w_up[3] = setup("npc", "Villager", "Walk", "up_4");

        i_up[0] = setup("npc", "Villager", "Idle", "up_1");
        i_up[1] = setup("npc", "Villager", "Idle", "up_2");
        i_up[2] = setup("npc", "Villager", "Idle", "up_3");
        i_up[3] = setup("npc", "Villager", "Idle", "up_4");

        // Pentru directia 'down'
        w_down[0] = setup("npc", "Villager", "Walk", "down_1");
        w_down[1] = setup("npc", "Villager", "Walk", "down_2");
        w_down[2] = setup("npc", "Villager", "Walk", "down_3");
        w_down[3] = setup("npc", "Villager", "Walk", "down_4");

        i_down[0] = setup("npc", "Villager", "Idle", "down_1");
        i_down[1] = setup("npc", "Villager", "Idle", "down_2");
        i_down[2] = setup("npc", "Villager", "Idle", "down_3");
        i_down[3] = setup("npc", "Villager", "Idle", "down_4");

        // Pentru directia 'left'
        w_left[0] = setup("npc", "Villager", "Walk", "left_1");
        w_left[1] = setup("npc", "Villager", "Walk", "left_2");
        w_left[2] = setup("npc", "Villager", "Walk", "left_3");
        w_left[3] = setup("npc", "Villager", "Walk", "left_4");

        i_left[0] = setup("npc", "Villager", "Idle", "left_1");
        i_left[1] = setup("npc", "Villager", "Idle", "left_2");
        i_left[2] = setup("npc", "Villager", "Idle", "left_3");
        i_left[3] = setup("npc", "Villager", "Idle", "left_4");

        // Pentru directia 'right'
        w_right[0] = setup("npc", "Villager", "Walk", "right_1");
        w_right[1] = setup("npc", "Villager", "Walk", "right_2");
        w_right[2] = setup("npc", "Villager", "Walk", "right_3");
        w_right[3] = setup("npc", "Villager", "Walk", "right_4");

        i_right[0] = setup("npc", "Villager", "Idle", "right_1");
        i_right[1] = setup("npc", "Villager", "Idle", "right_2");
        i_right[2] = setup("npc", "Villager", "Idle", "right_3");
        i_right[3] = setup("npc", "Villager", "Idle", "right_4");
    }

    @Override
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
    }
}
