package entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {
    public int worldX, worldY;
    public int speed;
    public int HP;
    public int ATK;
    public int currentHP;
    GamePanel gp;
    //Image of an entity
    public BufferedImage up1,up2,up3,down1,down2,down3,left1,left2,left3,right1,right2,right3;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int spritNum2 = 0;

    public Rectangle hitBox = new Rectangle(0,0,48,48);
    public int hitBoxDefaultX, hitBoxDefaultY;
    public boolean collision = false;


    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if (worldX + gp.tileSize> (gp.player.worldX - gp.player.screenX)
                && worldX - gp.tileSize < (gp.player.worldX + gp.player.screenX)
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

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
            g2.drawImage(image, screenX, screenY, gp.tileSize , gp.tileSize ,null);
        }
    }
    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;
        try{
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imageName+".png"));
            scaledImage = uTool.scaleImage(scaledImage,gp.tileSize,gp.tileSize);
        }catch (IOException e){e.printStackTrace();}

        return scaledImage;
    }

}
