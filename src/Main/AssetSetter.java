/* Name: Nguyen Phuc Minh Quan ITDSIU22163
 Purpose:This class sets up and initializes various game assets such as objects, NPCs, and monsters within the game world.
*/
package Main;

import entity.NPC;
import monster.MON_Jelly;
import monster.MON_racer;
import objects.*;
import objects.Weapon.OBJ_sword;

public class AssetSetter {

    GamePanel gp;
    private static AssetSetter instance;
    private AssetSetter(GamePanel gp){
        this.gp = gp;

    }

    public static AssetSetter createAssetSetter(GamePanel gp) {
        if (instance == null) {
            instance = new AssetSetter(gp);
        }
        return instance;
    }


    public void setObject(){
        gp.obj[0] = new OBJ_door(gp);
        gp.obj[0].worldX = 29*gp.tileSize;
        gp.obj[0].worldY = 45*gp.tileSize;

        gp.obj[1] = new OBJ_key(gp);
        gp.obj[1].worldX = 48*gp.tileSize;
        gp.obj[1].worldY = 48*gp.tileSize;

        gp.obj[4] = new OBJ_boat(gp);
        gp.obj[4].worldX = 34*gp.tileSize;
        gp.obj[4].worldY = 6*gp.tileSize;

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

    public void setRacer(int col, int row){
        gp.monster[1] = new MON_racer(gp);
        gp.monster[1].worldX = gp.tileSize*col;
        gp.monster[1].worldY = gp.tileSize*row;
    }

    public void setTree(int col,int row,int i){
        gp.obj[i] = new OBJ_Tree(gp);
        gp.obj[i].worldX = gp.tileSize*col;
        gp.obj[i].worldY = gp.tileSize*row;
    }
}
