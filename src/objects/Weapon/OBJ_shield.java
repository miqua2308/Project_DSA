package objects.Weapon;

import objects.SuperObject;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_shield extends SuperObject {
    public OBJ_shield(){
        name = "Shield";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/shield.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

        collision = true;
    }
}
