/* Name: Nguyen Phuc Minh Quan ITDSIU22163
 Purpose:This class represents a boat object in the game. It includes properties and methods specific to the boat.
*/
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



