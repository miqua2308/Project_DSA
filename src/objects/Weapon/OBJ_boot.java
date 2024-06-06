package objects.Weapon;

import objects.SuperObject;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_boot extends SuperObject {
    public OBJ_boot(){
        name = "Boot" ;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/boot.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

        collision = false;
    }
}
