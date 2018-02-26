package com.force.graphics;


import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by student on 1/2/18.
 */

public class Lives {

    private BufferedImage halfHeart, emptyHeart;

    public Lives() {
        try {
            halfHeart = ImageIO.read(new File("res/entity/heart/halfheart.png"));
            emptyHeart = ImageIO.read(new File("res/entity/heart/emptyheart.png"));
        } catch (IOException e) {
            System.out.println("ERROR: Failure uploading heart images");
            e.printStackTrace();
        }
    }

    public void drawHearts(Graphics2D g2, double lives) {

        // Draws empty heart containers
        for (int i = 0; i < 6; i += 2) {
            g2.drawImage(emptyHeart, 32 + (16 * i), 50, emptyHeart.getWidth(), emptyHeart.getHeight(), null);
        }

        // Draws half hearts accordingly to screen
        for (int i = 0; i < lives * 2; i++) {
            if (i % 2 == 0) {
                g2.drawImage(halfHeart, 32 + (16 * i), 50, halfHeart.getWidth(), halfHeart.getHeight(), null);
            } else {
                g2.drawImage(halfHeart, 48 + (16 * i), 50, -halfHeart.getWidth(), halfHeart.getHeight(), null);
            }
        }
    }
}
