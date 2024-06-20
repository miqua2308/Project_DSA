package entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC extends Entity{
    public NPC(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;
        getNPCImage();
    }


    public void getNPCImage(){
        //right
        right1 = setup("/npc/npc");
        right2 = setup("/npc/npc");
        right3 = setup("/npc/npc");
        //left
        left1 = setup("/npc/npc");
        left2 = setup("/npc/npc");
        left3 = setup("/npc/npc");
        //up
        up1 = setup("/npc/npc");
        up2 = setup("/npc/npc");
        up3 = setup("/npc/npc");
        //down
        down1 = setup("/npc/npc");
        down2 = setup("/npc/npc");
        down3 = setup("/npc/npc");
    }
    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100)+1; // pick up a number between 1 and 100
            System.out.println("I: " +i);
            if (i <= 25) { direction = "up";}
            if (i > 25 && i <= 50) { direction = "down";}
            if (i > 50 && i <= 75) { direction = "left";}
            if (i > 75 && i <= 100) { direction = "right";}
            actionLockCounter = 0;
        }
    }

}
