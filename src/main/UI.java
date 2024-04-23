package main;

import object.OBJ_Coin;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B, serif;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        serif = new Font("Serif", Font.BOLD, 14);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);


        if (gp.gameState == gp.playState) {
            //Playstate stuff later
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        drawCoordinates(serif, Color.WHITE);
        drawFPS(serif, Color.WHITE);

        g2.dispose();
    }


    public void drawCoordinates(Font font, Color color) {
        g2.setFont(font);
        g2.setColor(color);
        g2.drawString("X: " + gp.playerX / gp.tileSize + ", Y: " + gp.playerY / gp.tileSize, 10, 20);
    }

    public void drawFPS(Font font, Color color) {
        g2.setFont(font);
        g2.setColor(color);
        g2.drawString("FPS: " + gp.Real_FPS, 10, 40);
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
