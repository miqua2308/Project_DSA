package objects;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_door extends Entity {

    GamePanel gp;
    public  OBJ_door(GamePanel gp){
        super(gp);
        name = "Door" ;
        down1 = setup("/object/door");
        collision = true;
    }
}



