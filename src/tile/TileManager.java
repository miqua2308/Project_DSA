package tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    private static TileManager instance;
    public Tile[] tile;
    public int mapTileNum[][];
    public boolean drawPath = false;


    private TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap();
     //   checkMap();
    }
    public static TileManager createTileManager(GamePanel gp) {
        if (instance == null) {
            instance = new TileManager(gp);
        }
        return instance;
    }

    public void getTileImage(){
            //grass
            setup(0,"grass1",false);
            //wall
            setup(1,"wall",true);
            //water
            setup(2,"water",true);
            //tree
            setup(3,"tree-1",true);
            //dirt
            setup(4,"dirt",false);
            //sand
            setup(5,"sand",false);
            //A black void
            setup(6,"void",true);
    }

    public void setup(int index,String imagePath,boolean collision){
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imagePath+".png"));
            tile[index].image = uTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;
        }catch (IOException e){e.printStackTrace();
        }
    }
    public void loadMap(){
        try{
            //input map txt
            InputStream is = getClass().getResourceAsStream("/maps/Worldmap.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //determine the coordinate of each tile
            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();
                while (col < gp.maxWorldCol){
                    String number[] = line.split(" ");
                    int num = Integer.parseInt(number[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch (Exception e){}
    }


    public void draw(Graphics2D g2){
        // setup a matrix for background
        int worldCol = 0;
        int worldRow = 0;


        //draw each tile
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol*gp.tileSize;
            int worldY = worldRow*gp.tileSize;

            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            if (worldX + gp.tileSize> (gp.player.worldX - gp.player.screenX)
                    && worldX - gp.tileSize < (gp.player.worldX + gp.player.screenX)
                    && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                    && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY,null);
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol){
                worldCol =0;
                worldRow++;
            }

        }

        if(drawPath == true){
            g2.setColor(new Color(255,0,0,70));
            for (int i = 0; i <gp.pFinder.pathList.size(); i++){
                int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;

                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.fillRect(screenX,screenY,gp.tileSize,gp.tileSize);
            }
        }
    }

}
