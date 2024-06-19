package entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NPC extends Entity{
    public NPC(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 0;
        getNPCImage();
    }


    public void getNPCImage(){
        //right
        right1 = setup("/npc/npc");
        //left
        left1 = setup("/npc/npc");
        //up
        up1 = setup("/npc/npc");
        //down
        down1 = setup("/npc/npc");

    }

}
