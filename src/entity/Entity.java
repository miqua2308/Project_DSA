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
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBaron = false;
    public int currentHP;
    public String name;
    public int actionLockCounter;
    public int dyingCounter = 0;
    GamePanel gp;
    //Image of an entity
    public BufferedImage up1,up2,up3,down1,down2,down3,left1,left2,left3,right1,right2,right3;
    public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackRight1,attackRight2,attackLeft1,attackLeft2;
    public String direction = "down";
    public boolean invicible = false;
    public int invicibleCount =0;
    public int spriteCounter = 0;
    public int hpBarCounter = 0;
    public int spriteNum = 1;
    public int spritNum2 = 0;
    boolean attacking = false;

    public Rectangle hitBox = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int hitBoxDefaultX, hitBoxDefaultY;
    public boolean collision = false;
    public int type; //0 is player, 1 is npc. 2 is monster


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


            //MONSTER HP
            if (type ==2 && hpBaron == true){

                double oneScale = (double)gp.tileSize/HP;
                double hpBarValue = oneScale*currentHP;
                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1,screenY -16,gp.tileSize+2, 12);


                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY -15,(int)hpBarValue , 10);


                hpBarCounter++;
                if(hpBarCounter > 600){
                    hpBarCounter = 0;
                    hpBaron = false;
                }
            }

            if (invicible){
                hpBaron = true;
                hpBarCounter =0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3F));
            }
            if (dying){
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize , gp.tileSize ,null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));
        }
    }

    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        int i =5; // the time we want the monster to die
        if (dyingCounter <=i){
            changeAlpha(g2,0F);
        }
        if (dyingCounter >i && dyingCounter<=i*2){
            changeAlpha(g2,1F);
        }
        if (dyingCounter >i*2 && dyingCounter<=i*3){
            changeAlpha(g2,0F);
        }
        if (dyingCounter >i*3 && dyingCounter<=i*4){
            changeAlpha(g2,1F);
        }
        if (dyingCounter >i*4 && dyingCounter<=i*5){
            changeAlpha(g2,0F);
        }
        if (dyingCounter >i*5 && dyingCounter<=i*6){
            changeAlpha(g2,1F);
        }

        if (dyingCounter >i*6 && dyingCounter<=i*7){
         changeAlpha(g2,0F);
        }
        if (dyingCounter >i*7 && dyingCounter<=i*8){
            changeAlpha(g2,1F);
        }
        if(dyingCounter >40){
            dying = false;
            alive = false;
        }

    }
    public void changeAlpha(Graphics2D g2,float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));
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

    public void setAction(){}

    public void damageReaction(){

    }
    public void update(){
        setAction();
        collision = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this,false);
//        gp.collisionChecker.checkEntity(this,gp.npc);
        gp.collisionChecker.checkEntity(this,gp.monster);
        boolean contactPlayer = gp.collisionChecker.checkPlayer(this);
        if (this.type == 2 && contactPlayer == true){
            if(gp.player.invicible == false){
                gp.player.currentHP -= this.ATK;
                gp.player.invicible = true;
            }
        }
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

        this.spriteCounter++;
        if (this.spriteCounter > 10) {
            if (this.spriteNum == 1) {
                this.spriteNum = 2;
            } else if (this.spriteNum == 2 && this.spritNum2 == 0) {
                this.spriteNum = 3;
            } else if (this.spriteNum == 3 && this.spritNum2 == 0) {
                this.spriteNum = 2;
                this.spritNum2 = 1;
            } else if (this.spriteNum == 2 && this.spritNum2 == 1) {
                this.spriteNum = 1;
                this.spritNum2 = 0;
            }
            this.spriteCounter = 0;
        }

        if (invicible == true){
            invicibleCount++;
            if (invicibleCount == 30){
                invicible = false;
                invicibleCount =0;
            }
        }

    }

}
