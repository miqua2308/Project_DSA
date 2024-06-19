package Main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    Rectangle eRect;
    int eRectDefaultX,eRectDefaultY;
    public EventHandler(GamePanel gp){
        this.gp = gp;
        eRect = new Rectangle();
        eRect.x = 23;
        eRect.y = 23;
        eRect.width = 2;
        eRect.height = 2;
        eRectDefaultX = eRect.x;
        eRectDefaultY = eRect.y;
    }


    public void checkEvent(){

        if(hit(39, 48, "any")){
            //event happen
            damagePit(gp.dialogueState);
        }
        if (hit(41,45,"up")){
            healingPool(gp.dialogueState);
        }
    }

    public boolean hit(int eventCol,int eventRow,String reqDirection){
        boolean hit = false;

        gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
        gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;

        eRect.x =eventCol*gp.tileSize + eRect.x;
        eRect.y =eventRow*gp.tileSize + eRect.y;

        if(gp.player.hitBox.intersects(eRect)){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
            }
        }
        gp.player.hitBox.x = gp.player.hitBoxDefaultX;
        gp.player.hitBox.y = gp.player.hitBoxDefaultY;
        eRect.x = eRectDefaultX;
        eRect.y = eRectDefaultY;

        return hit;
    }

    public void damagePit(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialouge = "YOU FALL";
        gp.player.currentHP -= 10;
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
