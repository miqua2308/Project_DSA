package Main;

import entity.NPC;
import monster.MON_Jelly;
import objects.OBJ_boot;
import objects.OBJ_door;
import objects.OBJ_key;
import objects.Weapon.OBJ_sword;

public class AssetSetter {

    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }


    public void setObject(){
        gp.obj[0] = new OBJ_door(gp);
        gp.obj[0].worldX = 29*gp.tileSize;
        gp.obj[0].worldY = 45*gp.tileSize;

        gp.obj[1] = new OBJ_key(gp);
        gp.obj[1].worldX = 48*gp.tileSize;
        gp.obj[1].worldY = 48*gp.tileSize;

    }


    public void setNPC(){
        gp.npc[0] = new NPC(gp);
        gp.npc[0].worldX = gp.tileSize*42;
        gp.npc[0].worldY = gp.tileSize*46;
    }


    public void setMonster(){
        gp.monster[0] = new MON_Jelly(gp);
        gp.monster[0].worldX = gp.tileSize*41;
        gp.monster[0].worldY = gp.tileSize*45;
    }
}
