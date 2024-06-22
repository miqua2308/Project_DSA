package Main;

import objects.OBJ_key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    private static UI instance;
    Font aial_40;
    Graphics2D g2;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0 ;
    public boolean gameFinished = false;
    public String currentDialouge = "";
    public int commandNum = 0;
    public int subState = 0;

    public int titleScreenState = 0; // 0: the first, 1: the second


    private UI(GamePanel gp){
        this.gp = gp;
        aial_40 = new Font("Arial",Font.PLAIN,40);
    }

    public static UI createUI(GamePanel gp) {
        if (instance == null) {
            instance = new UI(gp);
        }
        return instance;
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
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
        }
        if (gp.gameState == gp.optionState){
            drawOptionScreen();
        }
        if (gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        if (gp.gameState == gp.gameWinState){
            drawWinScreen();
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


    public void drawCharacterScreen(){
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth= gp.tileSize*5;
        final int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20F));

        int textX = frameX + 20;
        int textY = frameY +gp.tileSize;
        final int lineHeight = 32;

        //NAME
        g2.drawString("Level",textX,textY);
        textY += lineHeight;
        g2.drawString("HP",textX,textY);
        textY+=lineHeight;
        g2.drawString("ATK",textX,textY);
        textY+=lineHeight;
        g2.drawString("Speed",textX,textY);
        textY+=lineHeight;
        g2.drawString("EXP",textX,textY);
        textY+=lineHeight;
        g2.drawString("Next Level EXP",textX,textY);
        textY+=lineHeight;


        //value
        int tailX = (frameX + frameWidth) -30;
        //reset Y
        textY = frameY + gp.tileSize;
        String value;
        //level
        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;
        //HP
        value = String.valueOf(gp.player.HP);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;
        //ATK
        value = String.valueOf(gp.player.ATK);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;
        //speed
        value = String.valueOf(gp.player.speed);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;
        //EXP
        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;
        //Next exp level
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

    }

    public void drawOptionScreen(){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        //subwind
        int frameX = gp.tileSize*4;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);
        g2.setColor(Color.white);

        switch (subState){
            case 0: option_top(frameX,frameY); break;
            case 1: option_control(frameX,frameY); break;
            case 2: option_endGameConfirm(frameX,frameY); break;
        }
    }

    public void option_top(int frameX , int frameY){
        int textX;
        int textY;

        //title
        String text = "Option";
        textX = getXforCenteredText(text);
        textY = frameY += gp.tileSize;
        g2.drawString(text,textX,textY);
        //end game option
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("END GAME",textX,textY);
        if (commandNum == 0){
            g2.drawString(">",textX-25,textY);
            if (gp.keyH.enterPressed == true){
                subState = 2;
                commandNum = 0;
                gp.keyH.enterPressed = false;
            }
        }
        //control
        textY+= gp.tileSize;
        g2.drawString("CONTROL",textX,textY);
        if (commandNum == 1){
            g2.drawString(">",textX-25,textY);
            if (gp.keyH.enterPressed == true){
                subState = 1;
                commandNum = 0;
                gp.keyH.enterPressed = false;
            }
        }
        //back
        textY+= gp.tileSize;
        g2.drawString("BACK",textX,textY);
        if (commandNum == 2){
            g2.drawString(">",textX-25,textY);
            if (gp.keyH.enterPressed == true){
                commandNum = 0;
                gp.gameState = gp.playState;
                gp.keyH.enterPressed = false;
            }
        }
    }


    public void option_control(int frameX , int frameY){
        int textX;
        int textY;

        //TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.setColor(Color.white);
        g2.drawString(text,textX,textY);

        textX= frameX + gp.tileSize;
        textY += gp.tileSize;

        g2.drawString("Move",textX,textY); textY+=gp.tileSize;
        g2.drawString("Attack",textX,textY); textY+=gp.tileSize;
        g2.drawString("Statue",textX,textY); textY+=gp.tileSize;
        g2.drawString("Pause",textX,textY); textY+=gp.tileSize;
        g2.drawString("Option",textX,textY); textY+=gp.tileSize;


        textX = frameX + gp.tileSize*4;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD", textX,textY); textY += gp.tileSize;
        g2.drawString("Enter", textX,textY); textY += gp.tileSize;
        g2.drawString("E", textX,textY); textY += gp.tileSize;
        g2.drawString("P", textX,textY); textY += gp.tileSize;
        g2.drawString("ESC", textX,textY); textY += gp.tileSize;

        //BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("BACK",textX,textY);
        if (commandNum == 0){
            g2.drawString(">",textX - 25,textY);
            if (gp.keyH.enterPressed){
                subState = 0;
                gp.keyH.enterPressed = false;
            }
        }
    }

    public void option_endGameConfirm(int frameX,int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialouge = "Quit the game and \nreturn to the title????";

        for (String line: currentDialouge.split("\n")){
            g2.drawString(line,textX,textY);
            textY += 40;
        }

        //yes
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text,textX,textY);

        if (commandNum == 0){
            g2.drawString(">",textX-25,textY);
            if (gp.keyH.enterPressed == true){
                subState = 0;
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.titleState;

            }
        }
        //no
        String textNo = "No";
        textX = getXforCenteredText(textNo);
        textY += gp.tileSize;
        g2.drawString(textNo,textX,textY);

        if (commandNum == 1){
            g2.drawString(">",textX-25,textY);
            if (gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 0;
                gp.keyH.enterPressed = false;
            }
        }
    }
    public void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110F) );

        text = "Game Over";
        g2.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text,x,y);
        //main
        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);

        //retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text,x,y);
        if (commandNum == 0){
            g2.drawString(">", x -40,y);
        }

        //back to title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text,x,y);
        if (commandNum == 1){
            g2.drawString(">", x -40,y);
        }



    }

    public void drawWinScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,100f) );

        text = "YOU WIN";
        g2.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text,x,y);
        //main
        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);

        g2.setFont(g2.getFont().deriveFont(50f));
        //back to title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text,x,y);
        if (commandNum == 0){
            g2.drawString(">", x -50,y);
        }



    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 -length/2;
        return x;
    }

    public int getXforAlignToRightText(String text,int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX -length;
        return x;
    }

}
