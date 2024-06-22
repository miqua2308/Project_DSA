/* Name: Nguyen Phuc Minh Quan ITDSIU22163
 Purpose:This class contains the main method to start the game. It initializes the game window and starts the game loop.
*/
package Main;


import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame window = new JFrame(); //create a window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Mystery");

        //Main.GamePanel
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); //add game screen
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.setupGame();
        gamePanel.startGameThread();

    }
}
