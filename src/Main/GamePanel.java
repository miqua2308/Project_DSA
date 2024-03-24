package Main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //Screen setting
    final int originalTileSize = 16; //16x16 tile , this will use to determine size of everything
    final int scale = 3; // the scale
    public final int tileSize = originalTileSize*scale; // this is 1 tile size 48x48

    final int maxScreenCol = 16; //col of the screen , => 768
    final int maxScreenRow = 12; // row of the screen => 576
    final int screenWidth = maxScreenCol*tileSize; // 768
    final int screenHeight = maxScreenRow*tileSize; // 576

    int FPS = 60;

    //Important setting
    KeyHandler keyH = new KeyHandler(); //call the key handler
    Thread gameThread; //start gameclock
    Player player = new Player(this,keyH);
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
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
        player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
        g2.drawLine(100,100, 200, 100);
        g2.dispose();
    }

}
