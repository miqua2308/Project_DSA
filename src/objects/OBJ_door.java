/* Name: Nguyen Phuc Minh Quan ITDSIU22163
 Purpose:This class represents a door object in the game. It includes properties and methods specific to the door.
*/
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



