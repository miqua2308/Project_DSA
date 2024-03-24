package entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;


    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        getPlayerImage();
        setDefaultValues();
    }

    public void setDefaultValues(){
        this.x = 100;
        this.y = 100;
        this.speed = 4;
        direction = "right";
    }

    public void getPlayerImage(){
        try {
            //right
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Slime_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Slime_right_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/Slime_right_3.png"));

            //left
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Slime_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Slime_left_2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/Slime_left_3.png"));

            //up
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/Slime_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/Slime_up_2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/Slime_up_3.png"));

            //down
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/Slime_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/Slime_down_2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/Slime_down_3.png"));
        }catch (IOException e){
            e.printStackTrace();

        }
    }

    public void update(){
        if(keyH.upPressed == true){
            direction = "up";
            y -= speed;
        }if(keyH.downPressed == true){
            direction = "down";
            y += speed;
        }if(keyH.rightPressed == true){
            direction = "right";
            x += speed;
        }if(keyH.leftPressed == true){
            direction = "left";
            x -= speed;
        }

        spriteCounter++;
        if (spriteCounter>10){
            if(spriteNum ==  1){
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 3;
            }
            else if (spriteNum == 3 ){
                spriteNum = 1;
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        switch (direction){
            case "up":
                if (spriteNum == 1){
                    image = up1;}
                if (spriteNum == 2){
                    image = up2;}
                if (spriteNum == 3){
                    image = up3;
                }
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;}
                if (spriteNum == 2){
                    image = down2;}
                if (spriteNum == 3){
                    image = down3;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;}
                if (spriteNum == 2){
                    image = right2;}
                if (spriteNum == 3){
                    image = right3;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;}
                if (spriteNum == 2){
                    image = left2;}
                if (spriteNum == 3){
                    image = left3;
                }
                break;
        }
        g2.drawImage(image ,x,y,gp.tileSize,gp.tileSize,null);
    }
}
