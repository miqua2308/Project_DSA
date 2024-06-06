package objects.Weapon;

import objects.SuperObject;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_sword extends SuperObject {
    public OBJ_sword(){
        name = "Sword";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/sword.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

        collision = true;
    }
}
