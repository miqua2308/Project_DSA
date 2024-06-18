package objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_key extends SuperObject {

    public  OBJ_key(){
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/door-key.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

        collision = false;
    }

}
