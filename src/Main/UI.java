package Main;

import objects.OBJ_key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font aial_40;
    BufferedImage keyimage;
    public boolean messageOn = false;
    public String message = "";



    public UI(GamePanel gp){
        this.gp = gp;
        aial_40 = new Font("Arial",Font.PLAIN,40);
        OBJ_key key = new OBJ_key();
        keyimage = key.image;
    }


    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){
        g2.setFont(aial_40);
        g2.setColor(Color.white);
        g2.drawImage(keyimage,gp.tileSize/2,gp.tileSize/2,gp.tileSize,gp.tileSize,null);
        g2.drawString("x "+ gp.player.hasKey,75,65);



        //message
        if (messageOn){
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message,gp.tileSize/2,gp.tileSize*5);
        }
    }
}
