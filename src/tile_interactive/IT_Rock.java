package tile_interactive;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Iron;
import object.OBJ_Rock;
import object.OBJ_Stone;
import object.OBJ_Wood;

import java.awt.*;
import java.util.Random;

public class IT_Rock extends InteractiveTile {
    GamePanel gp;

    public IT_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;

        i_down[0] = setup("tiles", "interactive", "ROCK_1");
        destructible = true;
        life = 3;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.currentWeapon.type == type_axe) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }

    public void playSE() {
        gp.playSE(10);
    }

    @Override
    public InteractiveTile getDestroyedForm() {
        Random rand = new Random();
        int n = rand.nextInt(10);
        if (n < 9) { // 90% din cazuri
            dropItem(new OBJ_Stone(gp));
        } else { // 10% din cazuri
            dropItem(new OBJ_Iron(gp));
        }
        return null;
    }


    public Color getParticleColor() {
        Color color = new Color(73, 57, 57);
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
