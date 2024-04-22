package main;

import object.OBJ_Coin;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font arial_40;
    BufferedImage coinImage;
    public UI(GamePanel gp)
    {
        this.gp=gp;

        arial_40=new Font("Arial", Font.PLAIN, 40);
        OBJ_Coin coin=new OBJ_Coin();
        coinImage=coin.image;
    }

    public void draw(Graphics2D g2){
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawImage(coinImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString(" x "+gp.player.hasCoin, 79, 70);
    }
}
