package objects.Chest;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_nchest extends OBJ_chest{
    public OBJ_nchest(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/chest.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
