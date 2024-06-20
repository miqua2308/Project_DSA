package monster;

import Main.GamePanel;
import entity.Entity;

import java.util.Random;

public class MON_Jelly extends Entity {
    public MON_Jelly(GamePanel gp) {
        super(gp);
        name = "Jelly";
        speed = 1;
        HP = 30;
        currentHP = HP;

        hitBox.x = 9;
        hitBox.y = 27;
        hitBox.width = 30;
        hitBox.height = 24;
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

    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100)+1; // pick up a number between 1 and 100
            if (i <= 25) { direction = "up";}
            if (i > 25 && i <= 50) { direction = "down";}
            if (i > 50 && i <= 75) { direction = "left";}
            if (i > 75 && i <= 100) { direction = "right";}
            actionLockCounter = 0;
        }
    }
}

