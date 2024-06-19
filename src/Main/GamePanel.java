package Main;

import entity.Entity;

import entity.Player;
import objects.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //Screen setting
    final int originalTileSize = 16; //16x16 tile , this will use to determine size of everything
    final int scale = 3; // the scale
    public final int tileSize = originalTileSize*scale; // this is 1 tile size 48x48
    public final int maxScreenCol = 16; //col of the screen , => 768
    public final int maxScreenRow = 12; // row of the screen => 576
    public final int screenWidth = maxScreenCol*tileSize; // 768
    public final int screenHeight = maxScreenRow*tileSize; // 576

    //World setting
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;

    int FPS = 60;

    //Important setting
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this); //call the key handler
    public UI ui = new UI(this);
    Thread gameThread; //start gameclock
    public  AssetSetter aSetter = new AssetSetter(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public EventHandler eHandler = new EventHandler(this);

    //ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];



    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }


    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer =0;
        int drawCount = 0;

        while (gameThread != null){
            currentTime =System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >=1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer > 1000000000){
                System.out.println("FPS: "+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update(){

        if(gameState == playState){
            player.update();
        }
        else if(gameState == pauseState){
            //maybe
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //debug
        long drawStart = 0;
        if (keyH.checkDrawTime == true){drawStart = System.nanoTime();}

        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }//other
        else{


            tileM.draw(g2);

            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    obj[i].draw(g2,this);
                }
            }
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].draw(g2);
                }
            }
            player.draw(g2);
            ui.draw(g2);
        }


        if (keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            System.out.println("drawTime: "+passed);}
        g2.dispose();
    }

}
