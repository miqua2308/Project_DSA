package objects;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_boat extends Entity {

    GamePanel gp;
    public  OBJ_boat(GamePanel gp){
        super(gp);
        name = "Boat" ;
        down1 = setup("/object/boat");
        collision = true;
    }
}



