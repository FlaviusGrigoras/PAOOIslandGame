package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        //Test 2
        //Creez fereastra cu numele Island Game, se inchide din X, nu se poate da resize
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Island Game");

        GamePanel gamePanel=new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        //Finalizare creare fereastra

        gamePanel.startGameThread();
    }
}