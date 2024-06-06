package objects.Chest;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_chest_dia extends OBJ_chest{
    public OBJ_chest_dia(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/dia-chest.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}