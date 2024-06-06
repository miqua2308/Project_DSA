package objects.Chest;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_chest_gold extends OBJ_chest{
    public OBJ_chest_gold(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/gold-chest.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
