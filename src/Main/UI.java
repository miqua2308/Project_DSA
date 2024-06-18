package Main;

import objects.OBJ_key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font aial_40;
    Graphics2D g2;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0 ;
    public boolean gameFinished = false;



    public UI(GamePanel gp){
        this.gp = gp;
        aial_40 = new Font("Arial",Font.PLAIN,40);


    }


    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(aial_40);
        g2.setColor(Color.white);


        if(gp.gameState == gp.playState){
            //at the moment, no
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
    }

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSE";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text,x,y);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 -length/2;
        return x;
    }

}
