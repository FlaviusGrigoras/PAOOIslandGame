package entity;

import main.GamePanel;
import object.*;

import java.util.Random;

public class NPC_Merchant extends Entity {
    public NPC_Merchant(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {
        String Type = "Merchant";
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
        dialogues[0] = "He he, so you found me.\nI have some good stuff.\nDo you want to trade?";
    }

    public void setItems() {
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Stick(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
        inventory.add(new OBJ_Pistol(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
        inventory.add(new OBJ_Lantern(gp));
        inventory.add(new OBJ_Tent(gp));
        inventory.add(new OBJ_Pickaxe(gp));
    }

    public void speak() {
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
