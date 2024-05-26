package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    //Debug
    boolean DebugMode = false;
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //TitleState
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }
        //PlayState
        else if (gp.gameState == gp.playState) {
            playState(code);
        }
        //Pause State
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        //Dialogue State
        else if (gp.gameState == gp.dialogState) {
            dialogueState(code);
        }
        //Character State
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }
        // OPTIONS STATE
        else if (gp.gameState == gp.optionState) {
            optionState(code);
        }
        // GAME OVER STATE
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }
        else if (gp.gameState == gp.winnerState) {
            winnerState(code);
        }
        // TRADE STATE
        else if (gp.gameState == gp.tradeState) {
            tradeState(code);
        }
        // MAP STATE
        else if (gp.gameState == gp.mapState) {
            mapState(code);
        }

    }

    private void winnerState(int code) {
        if (code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
            gp.playSE(7);
        }
        if (code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(7);
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.resetGame(false);
                gp.stopMusic();
            } else if (gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.resetGame(true);
            }
        }
    }

    private void mapState(int code) {
        if (code == KeyEvent.VK_M) {
            gp.gameState = gp.playState;
        }
    }

    private void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
                gp.playSE(7);
            }
            if (code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
                gp.playSE(7);
            }
        }
        if (gp.ui.subState == 1) {
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
        if (gp.ui.subState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }

    public void playerInventory(int code) {
        if (code == KeyEvent.VK_UP) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(7);
            }
        }
        if (code == KeyEvent.VK_LEFT) {
            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(7);
            }
        }
        if (code == KeyEvent.VK_DOWN) {
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(7);
            }
        }
        if (code == KeyEvent.VK_RIGHT) {
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(7);
            }
        }
    }

    public void npcInventory(int code) {
        if (code == KeyEvent.VK_UP) {
            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(7);
            }
        }
        if (code == KeyEvent.VK_LEFT) {
            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(7);
            }
        }
        if (code == KeyEvent.VK_DOWN) {
            if (gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(7);
            }
        }
        if (code == KeyEvent.VK_RIGHT) {
            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(7);
            }
        }

    }

    private void gameOverState(int code) {
        if (code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
            gp.playSE(7);
        }
        if (code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(7);
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.resetGame(false);
                gp.stopMusic();
            } else if (gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.resetGame(true);
            }
        }
    }

    public void optionState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0:
                maxCommandNum = 5;
                break;
            case 3:
                maxCommandNum = 5;
                break;

        }

        if (code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            gp.playSE(7);
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            gp.playSE(7);
            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_LEFT) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.sound.volumeScale > 0) {
                    gp.sound.volumeScale--;
                    stopAndResume();

                    gp.sound.checkVolume();
                    gp.playSE(7);
                }
                if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(7);
                }
            }
        }
        if (code == KeyEvent.VK_RIGHT) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.sound.volumeScale < 5) {
                    gp.sound.volumeScale++;
                    stopAndResume();

                    gp.sound.checkVolume();
                    gp.playSE(7);
                }
                if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(7);
                }
            }
        }
    }

    private void stopAndResume() {
        gp.stopMusic();
        if (gp.currentArea == gp.outside) {
            gp.playSE(0, true);
        }
        if (gp.currentArea == gp.indoor) {
            gp.playSE(14, true);
        }
        if (gp.currentArea == gp.dungeon) {
            gp.playSE(15, true);
        }
    }

    public void titleState(int code) {
        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0)
                    gp.ui.commandNum = 2;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2)
                    gp.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                }
                if (gp.ui.commandNum == 1) {
                    gp.saveLoad.load();
                    gp.gameState = gp.playState;

                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
        /* titleScreenState 2 pentru alegere personaje
        else if (gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0)
                    gp.ui.commandNum = 2;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2)
                    gp.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    //De adaugat lucruri specifice
                    gp.initializeCharacter(1);
                    gp.gameState = gp.playState;
                }
                //Character Selection
                if (gp.ui.commandNum == 1) {
                    //De adaugat lucruri specifice
                    gp.initializeCharacter(2);
                    gp.gameState = gp.playState;
                }
                if (gp.ui.commandNum == 2) {
                    gp.ui.titleScreenState = 0;
                }
            }
         */
    }

    public void playState(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }

        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionState;
        }
        if (code == KeyEvent.VK_M) {
            gp.gameState = gp.mapState;
        }
        if (code == KeyEvent.VK_X) {
            if (!gp.map.miniMapOn) {
                gp.map.miniMapOn = true;
            } else {
                gp.map.miniMapOn = false;
            }
        }

        //Debug
        if (code == KeyEvent.VK_T) {
            DebugMode = !DebugMode;
        }
        if (code == KeyEvent.VK_R) {
            switch (gp.currentMap) {
                case 0:
                    gp.tileM.loadMap("/maps/map01.txt", 0);
                    break;
                case 1:
                    gp.tileM.loadMap("/maps/interior01.txt", 1);
                    break;
            }
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
        playerInventory(code);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
    }
}