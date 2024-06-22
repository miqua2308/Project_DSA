package objects;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Tree extends Entity {

    GamePanel gp;
    public  OBJ_Tree(GamePanel gp){
        super(gp);
        name = "Tree" ;
        down1 = setup("/tiles/tree-1");
        collision = true;
    }
}



