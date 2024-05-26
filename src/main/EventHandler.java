package main;


import entity.Entity;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    UI ui;

    EventRect[][][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        this.ui = new UI(gp);

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {
        //Check if the character is 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(38, 13, "up", 0) || hit(39, 13, "up", 0)) {
                healingPool(gp.dialogState);
            } else if (hit(40, 11, "left", 0) || hit(40, 12, "left", 0)) {
                healingPool(gp.dialogState);
            } else if (hit(37, 11, "right", 0) || hit(37, 12, "right", 0)) {
                healingPool(gp.dialogState);
            } else if (hit(38, 10, "down", 0) || hit(39, 10, "down", 0)) {
                healingPool(gp.dialogState);
            } else if (hit(23, 12, "up", 0)) {
                damagePit(gp.dialogState);
            } else if (hit(29, 25, "any", 0)) {
                teleport(12, 12, 1, gp.indoor); // To mercahnt
            } else if (hit(12, 13, "any", 1)) {
                teleport(29, 25, 0, gp.outside); // To outside
            } else if (hit(12, 9, "up", 1)) {
                speak(gp.npc[1][1]);
            } else if (hit(42, 32, "any", 0)) {
                teleport(9, 41, 2, gp.dungeon); // To the dungeon
            } else if (hit(9, 41, "any", 2)) {
                teleport(42, 32, 0, gp.outside); // To outside
            } else if (hit(8, 7, "any", 2)) {
                teleport(26, 41, 3, gp.dungeon); // To B2
            } else if (hit(26, 41, "any", 3)) {
                teleport(8, 7, 2, gp.dungeon); // To B1
            } else if (hit(25, 8, "any", 3)) {
                teleport(18, 11, 4, gp.outside); // To Desert
            } else if (hit(17, 38, "down", 4)) {
                win();
            }
        }
    }

    private void win() {
        if (gp.keyH.enterPressed) {
            gp.gameState = gp.winnerState;
            gp.player.attackCanceled = true;
            gp.playSE(19);
            gp.ui.currentDialogue = "Ai reușit să scapi de pe insula bântuită!";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.saveLoad.save();
        }
        gp.keyH.enterPressed = false;
    }

    public void healingPool(int gameState) {
        if (gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.ui.currentDialogue = "Ai sorbit din apele cristaline ale lacului fermecat." +
                    "\nViața ți-a fost restabilită în totalitate." + "\n(The progress has been saved)";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.saveLoad.save();
        }
        gp.keyH.enterPressed = false;
    }

    private void teleport(int col, int row, int map, int area) {
        gp.gameState = gp.transitionState;
        gp.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;

       /* gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
       */
        canTouchEvent = false;
        gp.playSE(12);
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -= 1;
        canTouchEvent = false;
    }


    public void speak(Entity entity) {
        if (gp.keyH.enterPressed) {
            gp.gameState = gp.dialogState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }


    public boolean hit(int col, int row, String reqDirection, int map) {
        boolean hit = false;

        if (map == gp.currentMap) {
            Rectangle playerSolidArea = new Rectangle(gp.player.worldX + gp.player.solidArea.x, gp.player.worldY + gp.player.solidArea.y, gp.player.solidArea.width, gp.player.solidArea.height);
            Rectangle eventRectangle = new Rectangle(col * gp.tileSize + eventRect[map][col][row].x, row * gp.tileSize + eventRect[map][col][row].y, eventRect[map][col][row].width, eventRect[map][col][row].height);

            if (playerSolidArea.intersects(eventRectangle) && !eventRect[map][col][row].eventDone) {
                if (gp.player.direction.equals(reqDirection) || reqDirection.equals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
        }

        return hit;
    }
}
