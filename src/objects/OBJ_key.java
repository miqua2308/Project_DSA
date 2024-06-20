package objects;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_key extends Entity {

    GamePanel gp;
    public  OBJ_key(GamePanel gp){
        super(gp);
        name = "Key";
        down1 = setup("/object/door-key");
        collision = false;
    }

}
