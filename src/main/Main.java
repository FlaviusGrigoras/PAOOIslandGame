package main;

import javax.swing.*;

public class Main {
    public static JFrame window;

    public static void main(String[] args) {
        //Creez fereastra cu numele Island Game, se inchide din X, nu se poate da resize
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Island Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if (gamePanel.fullScreenOn) {
            window.setUndecorated(true);
        }

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        //Finalizare creare fereastra

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}