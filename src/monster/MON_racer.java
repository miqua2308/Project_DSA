/* Name: Nguyen Phuc Minh Quan ITDSIU22163
 Purpose:This class represents a racer monster in the game. It includes properties and methods specific to the racer monster.
*/
package monster;

import Main.GamePanel;
import entity.Entity;

import java.util.Random;

public class MON_racer extends Entity {
    GamePanel gp;
    public MON_racer(GamePanel gp) {
        super(gp);

        this.gp = gp;
        name = "Racer";
        speed = 5;
        HP = 1000;
        currentHP = HP;
        ATK = 50;
        type = 2;
        onPath = true;

        hitBox.x = 9;
        hitBox.y = 27;
        hitBox.width = 40;
        hitBox.height = 40;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;

        getImage();
    }


    public void getImage(){

        up1 = setup("/monster/OchreJellyIdleSide_2");
        up2 = setup("/monster/OchreJellyIdleSide_3");
        up3 = setup("/monster/OchreJellyIdleSide_4");

        down1 = setup("/monster/OchreJellyIdleSide_2");
        down2 = setup("/monster/OchreJellyIdleSide_3");
        down3 = setup("/monster/OchreJellyIdleSide_4");

        right1 = setup("/monster/OchreJellyIdleSide_2");
        right2 = setup("/monster/OchreJellyIdleSide_3");
        right3 = setup("/monster/OchreJellyIdleSide_4");

        left1 = setup("/monster/OchreJellyIdleSide_2");
        left2 = setup("/monster/OchreJellyIdleSide_3");
        left3 = setup("/monster/OchreJellyIdleSide_4");
    }

    public void setAction() {
        if (onPath) {
            int goalCol = (gp.player.worldX + gp.player.hitBox.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.hitBox.y) / gp.tileSize;
            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;
            if (actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick up a number between 1 and 100
                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75 && i <= 100) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }

//    public void damageReaction() {
//        actionLockCounter =0;
//        direction = gp.player.direction;
//    }
}

