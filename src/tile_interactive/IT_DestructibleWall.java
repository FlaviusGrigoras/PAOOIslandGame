package tile_interactive;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.awt.*;
import java.util.Random;

public class IT_DestructibleWall extends InteractiveTile {
    GamePanel gp;

    public IT_DestructibleWall(GamePanel gp) {
        super(gp);
        this.gp = gp;

        i_down[0] = setup("tiles", "interactive", "destructiblewall");
        destructible = true;
        life = 3;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.currentWeapon.type == type_pickaxe) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }

    public void playSE() {
        gp.playSE(16);
    }


    public Color getParticleColor() {
        Color color = new Color(65, 65, 65);
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

    @Override
    public InteractiveTile getDestroyedForm() {
        Random rand = new Random();
        int n = rand.nextInt(10);
        if (n < 9) { // 90% din cazuri
            dropItem(new OBJ_Iron(gp));
        } else { // 10% din cazuri
            dropItem(new OBJ_Stone(gp));
        }
        return null;
    }
}
