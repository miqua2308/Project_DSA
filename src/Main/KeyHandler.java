/* Name: Nguyen Phuc Minh Quan ITDSIU22163
 Purpose:This class manages keyboard input for the game.
 It detects and processes key presses to control the player and other game actions.
*/
package Main;

import tile.TileManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    private static KeyHandler instance;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    boolean checkDrawTime = false;
    public boolean enterPressed = false;

    //GAME STATE
    private KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    public static KeyHandler createKeyHandler(GamePanel gp) {
        if (instance == null) {
            instance = new KeyHandler(gp);
        }
        return instance;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //TITLE STATE
        if (gp.gameState == gp.titleState){
            tileState(code);
        }

        //PLAY STATE
        else if (gp.gameState == gp.playState){
            playState(code);
        }

        //PAUSE STATE
        else if (gp.gameState == gp.pauseState){
            pauseState(code);

        }
        //DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }

        //characterState
        else if (gp.gameState == gp.characterState){
            characterState(code);
        }
        
        //option state
        else if (gp.gameState == gp.optionState) {
            optionState(code);
        }

        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }

        else if (gp.gameState == gp.gameWinState){
            gameWinState(code);
        }


    }

    public void tileState(int code){

        //cheking subState
        if (gp.ui.titleScreenState == 0){

            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if (gp.ui.commandNum <0){
                    gp.ui.commandNum =2;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if (gp.ui.commandNum >2){
                    gp.ui.commandNum =0;
                }
            }


            if(code == KeyEvent.VK_ENTER){

                if(gp.ui.commandNum == 0){
                    gp.ui.titleScreenState =1;
                }

                if(gp.ui.commandNum == 1){
                    //add later
                }

                if(gp.ui.commandNum == 2){
                    System.exit(0);
                }
            }
        }

        else if (gp.ui.titleScreenState == 1){

            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if (gp.ui.commandNum <0){
                    gp.ui.commandNum =2;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if (gp.ui.commandNum >2){
                    gp.ui.commandNum =0;
                }
            }


            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                    gp.player.setWarriorDefault();
                    gp.player.jobType = 1; // 1 is warrior
                    gp.ui.commandNum = 0;
                }

                if(gp.ui.commandNum == 1){
                    gp.gameState = gp.playState;
                    gp.player.setArcherDefault();
                    gp.player.jobType = 2; //2 is Archer;
                    gp.ui.commandNum = 0;
                }

                if(gp.ui.commandNum == 2){
                    gp.ui.titleScreenState = 0;
                    gp.ui.commandNum = 0;
                }
            }
        }


    }
    public void playState(int code){

        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.pauseState;
        }

        if (code == KeyEvent.VK_E){
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.optionState;
        }


        if(code == KeyEvent.VK_T){
            if (gp.tileM.drawPath == false){
                gp.tileM.drawPath = true;
            }
            else if (gp.tileM.drawPath == true){
                gp.tileM.drawPath = false;
            }
        }
        else if (code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }


    }
    public void pauseState(int code){
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            gp.gameState = gp.playState;
        }
    }
    public void characterState(int code){
        if (code == KeyEvent.VK_E){
            gp.gameState = gp.playState;
        }
    }

    public void optionState(int code){
        if (code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
            gp.ui.subState=0;
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        int maxCommandNum = 0;
        switch (gp.ui.subState){
            case 0: maxCommandNum = 2; break;
            case 2: maxCommandNum = 1; break;
        }
        if (code == KeyEvent.VK_W){
            gp.ui.commandNum --;
            if (gp.ui.commandNum <0){
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum > maxCommandNum){
                gp.ui.commandNum =0;
            }
        }



    }

    public void gameWinState(int code){

        if (code == KeyEvent.VK_ENTER){
            System.exit(0);
        }
    }

    public void gameOverState(int code){
        if (code == KeyEvent.VK_W){
            gp.ui.commandNum --;
            if (gp.ui.commandNum <0){
                gp.ui.commandNum = 1;
            }
        }

        if (code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if (gp.ui.commandNum >1){
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_ENTER){
            if (gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
            }
            else if (gp.ui.commandNum == 1){
                gp.ui.titleScreenState =0;
                gp.ui.commandNum = 0;
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
    }


}
