package com.force;

import javax.swing.JFrame;

public class Window extends JFrame {

    public Window(){
        setTitle("Tri-Legend");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GamePanel(1280, 720));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
