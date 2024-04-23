package main;

import object.OBJ_Coin;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B, serif, cambria;

    public String currentDialogue = "";

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        serif = new Font("Serif", Font.BOLD, 14);
        cambria=new Font("Cambria", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(cambria);
        g2.setColor(Color.WHITE);

        //Playstate
        if (gp.gameState == gp.playState) {
            //Playstate stuff later
        }
        //PauseState
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        //Dialogue state
        if (gp.gameState == gp.dialogState) {
            drawDialogScreen();
        }

        drawCoordinates(serif, Color.WHITE);
        drawFPS(serif, Color.WHITE);

        g2.dispose();
    }

    private void drawDialogScreen() {
        //Window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);


        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }


    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
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
