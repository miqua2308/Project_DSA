package entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    //coordinate of screen
    public final int screenX;
    public final int screenY;

    public int hasKey = 0;


    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        this.screenX = gp.screenWidth/2 -(gp.tileSize/2);
        this.screenY = gp.screenHeight/2 - (gp.tileSize/2);

        hitBox = new Rectangle();
        hitBox.x = 5*3; //3 is the scale
        hitBox.y = 4*3;

        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = gp.tileSize/2; // i has calculate it outside
        hitBox.height = gp.tileSize/2;

        getPlayerImage();
        setDefaultValues();
    }

    public void setDefaultValues(){
        this.worldX = gp.tileSize * 41; //coordiate of x in world map
        this.worldY = gp.tileSize * 48;
        this.speed = 4;
        this.HP = 100;
        direction = "right";
    }

    public void getPlayerImage(){
            //right
            right1 = setup("Slime_right_1");
            right2 = setup("Slime_right_2");
            right3 = setup("Slime_right_3");
            //left
            left1 = setup("Slime_left_1");
            left2 = setup("Slime_left_2");
            left3 = setup("Slime_left_3");

            //up
            up1 = setup("Slime_up_1");
            up2 = setup("Slime_up_2");
            up3 = setup("Slime_up_3");

            //down
            down1 = setup("Slime_down_1");
            down2 = setup("Slime_down_2");
            down3 = setup("Slime_down_3");

    }

    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;
        try{
            scaledImage = ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
            scaledImage = uTool.scaleImage(scaledImage,gp.tileSize,gp.tileSize);
        }catch (IOException e){e.printStackTrace();}

        return scaledImage;
    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            }
            if (keyH.downPressed) {
                direction = "down";
            }
            if (keyH.rightPressed) {
                direction = "right";
            }
            if (keyH.leftPressed) {
                direction = "left";
            }
            //check collision
            collision = false;
            gp.collisionChecker.checkTile(this);


            //object collison
            int onjIndex = gp.collisionChecker.checkObject(this,true);
            pickUpObject(onjIndex);

            //if collision is false => player can move
            if (!collision) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                }
            }
        }

        // I have 3 image of slime that change the Y direction, so in order to make it move
        // I just need to loop it
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2 && spritNum2 == 0) {
                    spriteNum = 3;
                } else if (spriteNum == 3 && spritNum2 == 0) {
                    spriteNum = 2;
                    spritNum2 = 1;
                } else if (spriteNum == 2 && spritNum2 == 1) {
                    spriteNum = 1;
                    spritNum2 = 0;
                }
                spriteCounter = 0;
            }
    }


    public void pickUpObject(int i){
        if(i != 999){
            String ObjName = gp.obj[i].name;

            switch (ObjName){
                case "Door":
                    if (hasKey >0){
                        hasKey--;
                        gp.obj[i] = null;
                        gp.ui.showMessage("Door opened");
                    } else {
                        gp.ui.showMessage("You need a key");
                    }
                    break;
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key! you can use this to open door");
                    break;
                case "Boot":
                    speed+=2;
                    gp.obj[i] = null;
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
        g2.drawImage(image ,screenX,screenY,null);
    }
}
