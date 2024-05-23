package main;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB, serif;

    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: first screen, 1: second screen
    public int slotCol = 0, slotRow = 0;
    int subState = 0;


    public UI(GamePanel gp) {
        this.gp = gp;
        serif = new Font("Serif", Font.BOLD, 14);


        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            assert is != null;
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            assert is != null;
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(purisaB);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        //Title State
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        //Playstate
        if (gp.gameState == gp.playState) {
            drawUsefullInformation();
            drawPlayerLife();
            drawMessage();
        }
        //PauseState
        if (gp.gameState == gp.pauseState) {
            drawUsefullInformation();
            drawPauseScreen();
            drawPlayerLife();
        }
        //Dialogue state
        if (gp.gameState == gp.dialogState) {
            drawUsefullInformation();
            drawDialogScreen();
            drawPlayerLife();
        }

        //Character State
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory();
        }

        // OPTION STATE
        if (gp.gameState == gp.optionState) {
            drawOptionsScreen();
        }
    }

    private void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(maruMonica.deriveFont(32F));

        // SUB WINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                options_fullScreenNotification(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                options_endGameConfirmation(frameX, frameY);
                break;
        }
        gp.keyH.enterPressed = false;
    }

    private void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Quit the game and \nreturn to the title screen?";
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        // Yes
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed)
                subState = 0;
            gp.gameState = gp.titleState;
        }
        // NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 4;
            }
        }

    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                if (!gp.fullScreenOn) {
                    gp.fullScreenOn = true;
                } else if (gp.fullScreenOn) {
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        // MUSIC
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        // SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        //CONTROL
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 2;
                commandNum = 0;
            }
        }

        // END GAME
        textY += gp.tileSize;
        g2.drawString("Quit", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 3;
                commandNum = 0;
            }
        }


        // Back
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
        }

        // FULL SCREEN CHECK BOX
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.fullScreenOn) {
            g2.fillRect(textX, textY, 24, 24);
        }

        // MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24); // 120/5=24
        int volumeWidth = 24 * gp.sound.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // SOUND EFFECT
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;

        g2.drawString("Character Screen", textX, textY);
        textY += gp.tileSize;

        g2.drawString("Confirm/Attack", textX, textY);
        textY += gp.tileSize;

        g2.drawString("Shoot/Cast", textX, textY);
        textY += gp.tileSize;

        g2.drawString("Character Screen", textX, textY);
        textY += gp.tileSize;

        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize;

        g2.drawString("Options", textX, textY);

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;

        g2.drawString("Arrows", textX, textY);
        textY += gp.tileSize;

        g2.drawString("ENTER", textX, textY);
        textY += gp.tileSize;

        g2.drawString("F", textX, textY);
        textY += gp.tileSize;

        g2.drawString("C", textX, textY);
        textY += gp.tileSize;

        g2.drawString("P", textX, textY);
        textY += gp.tileSize;

        g2.drawString("ESC", textX, textY);

        // BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.screenHeight - gp.tileSize * 2;
        int i = 0;

        //DRAW BLANK HEART
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        //RESET
        x = gp.tileSize / 2;
        y = gp.screenHeight - gp.tileSize * 2;
        i = 0;

        //DRAW CURRENT LIFE
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
        //DRAW MAX MANA
        x = (gp.tileSize / 2) - 5;
        y = gp.screenHeight - gp.tileSize * 3;
        i = 0;
        while (i < gp.player.maxMana) {
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }
        // DRAW MANA
        x = (gp.tileSize / 2) - 5;
        y = gp.screenHeight - gp.tileSize * 3;
        i = 0;
        while (i < gp.player.mana) {
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }

    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // messageCounter++
                messageCounter.set(i, counter); // set the counter to the array

                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen() {

        if (titleScreenState == 0) {
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //TITLE NAME

            g2.setFont(maruMonica.deriveFont(Font.PLAIN, 80F));
            String text = "Island escape";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            //SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + 5);


            //MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleScreenState == 1) {
            g2.setFont(maruMonica.deriveFont(Font.PLAIN, 80F));
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            //Class selection screen
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Boy";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", gp.tileSize, y);
            }

            text = "Girl";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", gp.tileSize, y);
            }

            text = "Cancel";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", gp.tileSize, y);
            }
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
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

    public void drawCharacterScreen() {
        //Create a frame
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //Text
        g2.setColor(Color.white);
        g2.setFont(maruMonica.deriveFont(Font.PLAIN, 32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;
        //Names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;

        g2.drawString("Life", textX, textY);
        textY += lineHeight;

        g2.drawString("Mana", textX, textY);
        textY += lineHeight;

        g2.drawString("Strength", textX, textY);
        textY += lineHeight;

        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;

        g2.drawString("Attack", textX, textY);
        textY += lineHeight;

        g2.drawString("Defense", textX, textY);
        textY += lineHeight;

        g2.drawString("Experience", textX, textY);
        textY += lineHeight;

        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;

        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;

        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;

        g2.drawString("Shield", textX, textY);

        //Values
        int tailX = (frameX + frameWidth) - 30;
        //Reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.i_down[0], tailX - gp.tileSize, textY - 24, null);
        textY += gp.tileSize;

        g2.drawImage(gp.player.currentShield.i_down[0], tailX - gp.tileSize, textY - 24, null);

    }

    public void drawInventory() {
        //Frame
        int frameX = gp.tileSize * 12;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;

        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // Draw Player's items
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            if (gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }


            g2.drawImage(gp.player.inventory.get(i).i_down[0], slotX, slotY, null);

            slotX += gp.tileSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // Cursor
        int cursorX = slotXstart + (gp.tileSize * slotCol);
        int cursorY = slotYstart + (gp.tileSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //Draw Cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //Description Frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize * 3;


        //Draw description text
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));
        int itemIndex = getItemIndexOnSlot();

        if (itemIndex < gp.player.inventory.size()) {
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "The change will take \neffect after restarting \nthe game.";
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // BACK
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
            }
        }
    }

    public int getItemIndexOnSlot() {
        return slotCol + (slotRow * 5);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

    private void drawUsefullInformation() {
        drawCoordinates(serif, Color.WHITE);
        drawFPS(serif, Color.WHITE);
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
        g2.drawString("X: " + (gp.playerX / gp.tileSize + 1) + ", Y: " + (gp.playerY / gp.tileSize + 1), 10, 20);
    }

    public void drawFPS(Font font, Color color) {
        g2.setFont(font);
        g2.setColor(color);
        g2.drawString("FPS: " + gp.Real_FPS, 10, 40);
    }
}
