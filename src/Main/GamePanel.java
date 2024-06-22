package Main;

import AI.PathFinder;
import Stack.Stack;
import entity.Entity;

import entity.Player;
import objects.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    Thread gameThread; //start gameclock
    //call all the SINGLETON
    public TileManager tileM = TileManager.createTileManager(this);
    public KeyHandler keyH = KeyHandler.createKeyHandler(this); //call the key handler
    public UI ui = UI.createUI(this);
    public  AssetSetter aSetter = AssetSetter.createAssetSetter(this);
    public CollisionChecker collisionChecker = CollisionChecker.createCollisionChecker(this);
    public EventHandler eHandler = EventHandler.createEventHandler(this);
    public PathFinder pFinder = PathFinder.createPathFinder(this);

    //ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[10];

    Stack<Entity> entityList = new Stack<Entity>();



    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int gameWinState =7;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }


    public void setupGame(){
        aSetter.setObject();
//        aSetter.setNPC();
        aSetter.setMonster();
        gameState = titleState;
    }

    public void retry(){
        player.setDefaultPosition();
        player.restoreHP();
        aSetter.setMonster();
    }

    public void restart(){
        if (player.jobType == 1){
            player.setWarriorDefault();
            aSetter.setObject();
            aSetter.setMonster();
            eHandler.restartEven(39, 48);
        }
        if (player.jobType == 2){
            player.setArcherDefault();
            aSetter.setObject();
            aSetter.setMonster();
            eHandler.restartEven(39, 48);
        }
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
            player.addObserver(eHandler);
            player.update();
            for (int i = 0; i<npc.length;i++){
                if(npc[i] != null){
                    npc[i].update();

                }
            }
            for (int i = 0; i<monster.length;i++){
                if(monster[i] != null){
                    if (monster[i].alive && !monster[i].dying){
                    monster[i].update();
                    }
                    if (!monster[i].alive){
                        monster[i] = null;
                    }
                }
            }
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
            //TILE
            tileM.draw(g2);
            //ENTITY
            entityList.push(player);
            for (int i = 0 ; i < npc.length; i++){
                if (npc[i] != null){
                    entityList.push(npc[i]);
                }
            }
            for (int i = 0 ; i < obj.length; i++){
                if (obj[i] != null){
                    entityList.push(obj[i]);
                }
            }
            for (int i = 0 ; i < monster.length; i++){
                if (monster[i] != null){
                    entityList.push(monster[i]);
                }
            }
            //SORT THE ARRAY LIST
            sortStack();
//            Collections.sort(entityList, new Comparator<Entity>() {
//                @Override
//                public int compare(Entity e1, Entity e2) {
//                    int result = Integer.compare(e1.worldY,e2.worldY);
//                    return result;
//                }
//            });

            while (!entityList.isEmpty()) {
                Entity entity = entityList.pop();
                entity.draw(g2);
            }
            //UI
            ui.draw(g2);
        }


        if (keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            System.out.println("drawTime: "+passed);}
        g2.dispose();
    }

    public void sortStack() {
        ArrayList<Entity> tempList = new ArrayList<>();

        // Pop all elements from the stack into tempList
        while (!entityList.isEmpty()) {
            tempList.add(entityList.pop());
        }

        // Sort the tempList by worldY
        tempList.sort(Comparator.comparingInt(e -> e.worldY));

        // Push the sorted elements back onto the stack
        for (Entity entity : tempList) {
            entityList.push(entity);
        }
    }

}
