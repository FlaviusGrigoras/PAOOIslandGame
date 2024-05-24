package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_Merchant extends Entity {
    public NPC_Merchant(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        String Type="Merchant";
        // Pentru directia 'up'
        w_up[0] = setup("npc", Type, "Walk", "up_1");
        w_up[1] = setup("npc", Type, "Walk", "up_2");
        w_up[2] = setup("npc", Type, "Walk", "up_3");
        w_up[3] = setup("npc", Type, "Walk", "up_4");

        i_up[0] = setup("npc", Type, "Idle", "up_1");
        i_up[1] = setup("npc", Type, "Idle", "up_2");
        i_up[2] = setup("npc", Type, "Idle", "up_3");
        i_up[3] = setup("npc", Type, "Idle", "up_4");

        // Pentru directia 'down'
        w_down[0] = setup("npc", Type, "Walk", "down_1");
        w_down[1] = setup("npc", Type, "Walk", "down_2");
        w_down[2] = setup("npc", Type, "Walk", "down_3");
        w_down[3] = setup("npc", Type, "Walk", "down_4");

        i_down[0] = setup("npc", Type, "Idle", "down_1");
        i_down[1] = setup("npc", Type, "Idle", "down_2");
        i_down[2] = setup("npc", Type, "Idle", "down_3");
        i_down[3] = setup("npc", Type, "Idle", "down_4");

        // Pentru directia 'left'
        w_left[0] = setup("npc", Type, "Walk", "left_1");
        w_left[1] = setup("npc", Type, "Walk", "left_2");
        w_left[2] = setup("npc", Type, "Walk", "left_3");
        w_left[3] = setup("npc", Type, "Walk", "left_4");

        i_left[0] = setup("npc", Type, "Idle", "left_1");
        i_left[1] = setup("npc", Type, "Idle", "left_2");
        i_left[2] = setup("npc", Type, "Idle", "left_3");
        i_left[3] = setup("npc", Type, "Idle", "left_4");

        // Pentru directia 'right'
        w_right[0] = setup("npc", Type, "Walk", "right_1");
        w_right[1] = setup("npc", Type, "Walk", "right_2");
        w_right[2] = setup("npc", Type, "Walk", "right_3");
        w_right[3] = setup("npc", Type, "Walk", "right_4");

        i_right[0] = setup("npc", Type, "Idle", "right_1");
        i_right[1] = setup("npc", Type, "Idle", "right_2");
        i_right[2] = setup("npc", Type, "Idle", "right_3");
        i_right[3] = setup("npc", Type, "Idle", "right_4");

    }

    public void setDialogue() {
        dialogues[0] = "Salut, călătorule!";
        dialogues[1] = "De unde ai răsărit? Nu îmi \namintesc să te fi văzut pe aici înainte!";
        dialogues[2] = "Fii precaut...când se lasă \nnoaptea, apar entități misterioase care \nnu sunt întotdeauna prietenoase.";
        dialogues[3] = "Construiește-ți o barcă cât\nmai repede \nși părăsește acest loc!";
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

    public void speak() {
        super.speak();
    }
}
