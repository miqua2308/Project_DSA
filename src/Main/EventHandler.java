package Main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EvenRect eRect[][];
    public EventHandler(GamePanel gp){
        this.gp = gp;
        eRect = new EvenRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col <gp.maxWorldCol && row < gp.maxWorldRow){
            eRect[col][row] = new EvenRect();
            eRect[col][row].x = 23;
            eRect[col][row].y = 23;
            eRect[col][row].width = 2;
            eRect[col][row].height = 2;
            eRect[col][row].eventRectDefaultX = eRect[col][row].x;
            eRect[col][row].eventRectDefaultY = eRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol){
                col =0;
                row++;
            }
        }
    }


    public void checkEvent(){

        if(hit(39, 48, "any")){
            //event happen
            damagePit(39,48,gp.dialogueState);
        }
        if (hit(41,45,"up")){
            healingPool(gp.dialogueState);
        }
    }

    public boolean hit(int col,int row,String reqDirection){
        boolean hit = false;

        gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
        gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;

        eRect[col][row].x =col*gp.tileSize + eRect[col][row].x;
        eRect[col][row].y =row*gp.tileSize + eRect[col][row].y;

        if(gp.player.hitBox.intersects(eRect[col][row]) && eRect[col][row].eventDone == false){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
            }
        }
        gp.player.hitBox.x = gp.player.hitBoxDefaultX;
        gp.player.hitBox.y = gp.player.hitBoxDefaultY;
        eRect[col][row].x = eRect[col][row].eventRectDefaultX;
        eRect[col][row].y = eRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int col , int row ,int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialouge = "YOU FALL";
        gp.player.currentHP -= 10;
        eRect[col][row].eventDone = true;
    }

    public void healingPool(int gameState){
        if (gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.ui.currentDialouge = "HEAL water";
            gp.player.currentHP = gp.player.HP;
        }
        gp.keyH.enterPressed = false;
    }
}
