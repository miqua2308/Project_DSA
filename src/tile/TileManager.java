package tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];


    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap();
     //   checkMap();
    }

    public void getTileImage(){
        try{
            //grass
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass1.png"));
            //wall
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            //water
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
        }
        catch (IOException e){e.printStackTrace();}
    }

    public void loadMap(){
        try{
            //input map txt
            InputStream is = getClass().getResourceAsStream("/maps/map1.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //determine the coordinate of each tile
            int col = 0;
            int row = 0;

            while(col < gp.maxScreenCol && row < gp.maxScreenRow){
                String line = br.readLine();
                while (col < gp.maxScreenCol){
                    String number[] = line.split(" ");
                    int num = Integer.parseInt(number[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch (Exception e){}
    }

    public void checkMap(){
        for (int i = 0; i< mapTileNum.length ; i++){
            for(int j =0; j< mapTileNum[i].length; j++){
                System.out.print(mapTileNum[i][j]);
            }
        }
    }
    public void draw(Graphics2D g2){
        // setup a matrix for background
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;


        //draw each tile
        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize , gp.tileSize ,null);
            col++;
            x += gp.tileSize;
            if (col == gp.maxScreenCol){
                col =0;
                x = 0;
                row++;
                y += gp.tileSize;
            }

        }
    }

}
