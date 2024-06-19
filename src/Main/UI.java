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
    public String currentDialouge = "";
    public int commandNum = 0;

    public int titleScreenState = 0; // 0: the first, 1: the second


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

        if (gp.gameState == gp.titleState){
            drawTitleScreeen();
        }
        if(gp.gameState == gp.playState){
            drawPlayerHealth();
        }
        if(gp.gameState == gp.pauseState){
            drawPlayerHealth();
            drawPauseScreen();
        }
        if (gp.gameState == gp.dialogueState){
            drawPlayerHealth();
            drawDialogueScreen();
        }
    }

    public void drawPlayerHealth(){
        int x = gp.tileSize/2 ;
        int y = gp.tileSize*10 + gp.tileSize/2;
        int barWidth = gp.screenWidth - gp.tileSize;
        int barHeight = gp.tileSize;
        int maxHealth = gp.player.HP;
        int currentHealth = gp.player.currentHP;

        g2.setColor(Color.GRAY);
        g2.fillRect(x, y, barWidth, barHeight);

        // Calculate the width of the current health bar
        int currentBarWidth = (int) ((double) currentHealth / maxHealth * barWidth);

        // Draw the current health bar (green)
        g2.setColor(Color.RED);
        g2.fillRect(x, y, currentBarWidth, barHeight);

        // Draw the border (black)
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, barWidth, barHeight);
        // Draw the health text
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(Color.WHITE);
        String healthText = currentHealth + " / " + maxHealth;
        int textWidth = g2.getFontMetrics().stringWidth(healthText);
        int textX = x + (barWidth - textWidth) / 2;
        int textY = y + (barHeight + g2.getFontMetrics().getHeight()) / 2 - g2.getFontMetrics().getDescent();
        g2.drawString(healthText, textX, textY);
    }

    public void drawDialogueScreen(){
        //subwindow
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize *5;
        drawSubWindow(x,y,width,height);
        x += gp.tileSize;
        y += gp.tileSize;
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(Color.WHITE);
        g2.drawString(currentDialouge,x,y);
    }

    public void drawSubWindow(int x,int y,int width,int height){
        int arcWidth = 35;
        int arcHeight = 35;
        Color c = new Color(0,0,0,210); //black
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,arcWidth,arcHeight);
    }
    public void drawTitleScreeen(){

        //checking the subState of the screen
        if (titleScreenState ==0){

            //BG color
            g2.setColor(new Color(70,120,80));
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
            //title name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,70F));
            String text = "SLIME ADVENTURE";
            int x= getXforCenteredText(text);
            int y=gp.tileSize*3;

            g2.setColor(Color.white);
            g2.drawString(text,x,y);


            //add image
            x = gp.screenWidth/2 -(gp.tileSize*2)/2;
            y += gp.tileSize;
            g2.drawImage(gp.player.down1,x,y,gp.tileSize*2,gp.tileSize*2,null);


            //menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,35F));
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize*4;
            g2.drawString(text,x,y);
            if (commandNum == 0){
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if (commandNum == 1){
                g2.drawString(">",x-gp.tileSize,y);
            }
            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if (commandNum == 2){
                g2.drawString(">",x-gp.tileSize,y);
            }
        }
        else if(titleScreenState == 1){

            //class selection screen
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));
            String text = "SELECT YOUR CLASS";
            int x =getXforCenteredText(text);
            int y =gp.tileSize*3;
            g2.drawString(text,x,y);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD,35F));
            text = "Warrior";
            x = getXforCenteredText(text);
            y += gp.tileSize*4;
            g2.drawString(text,x,y);
            if (commandNum == 0){
                g2.drawString(">",x-gp.tileSize,y);
            }
            text = "Archer";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if (commandNum == 1){
                g2.drawString(">",x-gp.tileSize,y);
            }
            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text,x,y);
            if (commandNum == 2){
                g2.drawString(">",x-gp.tileSize,y);
            }
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
