package com.force.graphics;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by student on 1/2/18.
 */
public class Lives {

    private BufferedImage heart, emptyHeart;

    public Lives() {
        try {
            heart = ImageIO.read(new File("res/entity/heart.png"));
            //emptyHeart = ImageIO.read(new File("res/entity/emptyheart.png"));
        } catch (IOException e) {
            System.out.println("heart failure");
            e.printStackTrace();}
    }

    public void drawHearts(Graphics2D g2, int lives){
        for (int i = 0; i < 3; i++) {
            if(i < lives)
                g2.drawImage(heart, 30*i + 30, 50, null);
            else
                g2.drawImage(emptyHeart, 30*i + 30, 50, null);
        }
    }
}
