package entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    KeyHandler keyH;

    //coordinate of screen
    public final int screenX;
    public final int screenY;

    public boolean attackCanceled = false;
    public int jobType = 0;
    public int hasKey = 0;



    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
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


        //ATTACK AREA

        attackArea.width =36;
        attackArea.height = 36;

        getPlayerImage();
        setDefaultValues();
        getPlayerAttackImage();
    }

    public void setDefaultValues(){
        this.worldX = gp.tileSize * 41; //coordiate of x in world map
        this.worldY = gp.tileSize * 48;
        this.name = "player";
        direction = "right";
    }
    public void setDefaultPosition(){
        this.worldX = gp.tileSize * 41; //coordiate of x in world map
        this.worldY = gp.tileSize * 48;
        direction = "right";
    }

    public void restoreHP(){
        currentHP = HP;
        invicible = false;
    }
    public void setWarriorDefault(){
        this.level = 1;
        this.exp =0;
        this.nextLevelExp = this.level*5;
        this.speed =4;
        this.HP=100;
        this.currentHP = this.HP;
        this.ATK = 15;
    }

    public void setArcherDefault(){
        this.level = 1;
        this.exp = 0;
        this.nextLevelExp = this.level*4;
        this.speed =6;
        this.HP=75;
        this.currentHP = this.HP;
        this.ATK = 25;
    }

    public void getPlayerImage(){
            //right
            right1 = setup("/player/Slime_right_1");
            right2 = setup("/player/Slime_right_2");
            right3 = setup("/player/Slime_right_3");
            //left
            left1 = setup("/player/Slime_left_1");
            left2 = setup("/player/Slime_left_2");
            left3 = setup("/player/Slime_left_3");

            //up
            up1 = setup("/player/Slime_up_1");
            up2 = setup("/player/Slime_up_2");
            up3 = setup("/player/Slime_up_3");

            //down
            down1 = setup("/player/Slime_down_1");
            down2 = setup("/player/Slime_down_2");
            down3 = setup("/player/Slime_down_3");

    }

    public void getPlayerAttackImage(){
        attackUp1 = setup("/player/Slime_up_1");
        attackUp2 = setup("/player/Slime_attack_up_2");

        attackDown1 = setup("/player/Slime_attack_down_1");
        attackDown2 = setup("/player/Slime_attack_down_2");

        attackLeft1 = setup("/player/Slime_attack_left_1");
        attackLeft2 = setup("/player/Slime_attack_left_2");

        attackRight1 = setup("/player/Slime_attack_right_1");
        attackRight2 = setup("/player/Slime_attack_right_2");
    }

    public void update() {

        if (attacking == true){
            attacking();
        }

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
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
            int npcindex = gp.collisionChecker.checkEntity(this,gp.npc);
            interactNPC(npcindex);
            int monsterIndex = gp.collisionChecker.checkEntity(this,gp.monster);
            contactMonster(monsterIndex);

            //check event
            gp.eHandler.checkEvent();

            //if collision is false => player can move
            if (!collision && !keyH.enterPressed) {
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

            if (keyH.enterPressed == true && attackCanceled == false){
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;
            // I have 3 image of slime that change the Y direction, so in order to make it move
            // I just need to loop it
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
        }

        if (invicible == true){
            invicibleCount++;
            if (invicibleCount == 60){
                invicible = false;
                invicibleCount =0;
            }
        }


        if(currentHP <=0 ){
            gp.gameState = gp.gameOverState;
        }

    }


    public void attacking(){
        spriteCounter++;
        if(spriteCounter<=5){
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <=25){
            spriteNum =2;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int hitboxWidth = hitBox.width;
            int hitboxHeight = hitBox.height;


            //ATACKKK
            switch (direction){
                case "up":worldY-=attackArea.height;break;
                case "down": worldY += attackArea.height;break;
                case "left": worldX-= attackArea.width;break;
                case "right": worldX+= attackArea.width;break;
            }

            hitBox.width = attackArea.width;
            hitBox.height = attackArea.height;

            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);
            //return to original
            worldX = currentWorldX;
            worldY = currentWorldY;
            hitBox.width = hitboxWidth;
            hitBox.height = hitboxHeight;

        }

        if(spriteCounter >25){
            spriteNum =1 ;
            spriteCounter =0;
            attacking = false;
        }
    }
    public void pickUpObject(int i){
        if(i != 999) {
            if (gp.obj[i] != null) {
                if (gp.obj[i].name == "Key") {
                    hasKey++;
                    gp.obj[i] = null;
                    gp.gameState = gp.dialogueState;
                    gp.ui.currentDialouge = "YOU PICKUP A KEY";
                }
            }
            if (gp.obj[i] != null) {
                if (gp.obj[i].name == "Door" && hasKey < 1) {
                    gp.gameState = gp.dialogueState;
                    gp.ui.currentDialouge = "YOU NEED A KEY";
                }
                else if (gp.obj[i].name == "Door" && hasKey >= 1) {
                    gp.obj[i] = null;
                }
            }
        }

    }
    public void interactNPC(int i){
        if(i != 999){
            attackCanceled = true;
        }
    }

    public void contactMonster(int i){
        if (i != 999){
            if (invicible == false) {
                currentHP -= gp.monster[i].ATK;
                invicible = true;
            }
        }
    }

    public void damageMonster(int i){
        if(i != 999) {
            if (gp.monster[i].invicible == false) {
                gp.monster[i].currentHP -= gp.player.ATK;
                gp.monster[i].invicible = true;
                gp.monster[i].damageReaction();

                if (gp.monster[i].currentHP <= 0) {
                    gp.monster[i].dying = true;
                }
            }
        }
    }


    public void draw(Graphics2D g2){
        BufferedImage image = null;
        switch (direction){
            case "up":
                if (attacking == false) {
                    if (this.spriteNum == 1) {
                        image = up1;
                    }
                    if (this.spriteNum == 2) {
                        image = up2;
                    }
                    if (this.spriteNum == 3) {
                        image = up3;
                    }
                }
                if (attacking == true){
                    if (this.spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (this.spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if (attacking == false) {
                    if (this.spriteNum == 1) {
                        image = down1;
                    }
                    if (this.spriteNum == 2) {
                        image = down2;
                    }
                    if (this.spriteNum == 3) {
                        image = down3;
                    }
                }
                if (attacking){
                    if (this.spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (this.spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    if (this.spriteNum == 1) {
                        image = right1;
                    }
                    if (this.spriteNum == 2) {
                        image = right2;
                    }
                    if (this.spriteNum == 3) {
                        image = right3;
                    }
                }
                if (attacking){
                    if (this.spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (this.spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    if (this.spriteNum == 1) {
                        image = left1;
                    }
                    if (this.spriteNum == 2) {
                        image = left2;
                    }
                    if (this.spriteNum == 3) {
                        image = left3;
                    }
                }
                if (attacking == true){
                    if (this.spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (this.spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
        }
        g2.drawImage(image ,screenX,screenY,null);

    }




}
