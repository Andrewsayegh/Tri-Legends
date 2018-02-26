package com.force.entity;


import com.force.GamePanel;
import com.force.graphics.Sprite;
import com.force.states.PlayState;
import com.force.util.KeyHandler;
import com.force.util.MouseHandler;
import com.force.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    protected Rectangle rect;

    public Player(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size, true);
        acceleration = 4f;
        tvConstant = -.5f;
        bounds.setWidth(42);
        bounds.setHeight(20);
        bounds.setXOffset(10);
        bounds.setYOffset(40);
    }

    public void move() {

        if (!up && !down && !left && !right) {
            down = true;
            firstPressed = 0;
            dir = 0;
        }

        switch (dir) {
            case 1:
                dy -= acceleration; //up
                break;
            case 2:
                dy += acceleration; //down
                break;
            case 3:
                dx -= acceleration; //left
                break;
            case 4:
                dx += acceleration; // right
        }

        if (up && firstPressed == 0) {
            firstPressed = 1;
//            dir = 1;
        } else if (firstPressed == 1 && !up) {
            firstPressed = 0;
        }

        if (down && firstPressed == 0) {
            firstPressed = 2;
//            dir = 2;
        } else if (firstPressed == 2 && !down) {
            firstPressed = 0;
        }

        if (left && firstPressed == 0) {
            firstPressed = 3;
//            dir = 3;
        } else if (firstPressed == 3 && !left) {
            firstPressed = 0;
        }

        if (right && firstPressed == 0) {
            firstPressed = 4;
//            dir = 4;
        } else if (firstPressed == 4 && !right) {
            firstPressed = 0;
        }


        if (firstPressed == 1) {
            dir = 1;
            if (left)
                dir = 3;
            if (right)
                dir = 4;
        }
        if (firstPressed == 2) {
            dir = 2;
            if (left)
                dir = 3;
            if (right)
                dir = 4;
        }
        if (firstPressed == 3) {
            dir = 3;
            if (up)
                dir = 1;
            if (down)
                dir = 2;
        }
        if (firstPressed == 4) {
            dir = 4;
            if (up)
                dir = 1;
            if (down)
                dir = 2;
        }
        dx += tvConstant * dx;
        dy += tvConstant * dy;
    }

    private void resetPosition() {
        System.out.println("Reseting Player... ");
        pos.x = GamePanel.width / 2 - 32;
        PlayState.map.x = 0;

        pos.y = GamePanel.height / 2 - 32;
        PlayState.map.y = 0;

        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 8);
    }

    public Rectangle attackBox(Rectangle rect) {
        System.out.println(firstPressed);
        switch (firstPressed) {
            case (0):
                rect = new Rectangle((int) (pos.getWorldVar().x + 6), (int) (pos.getWorldVar().y), 50, 30);
                break;
            case (1):
                if (up)
                    rect = new Rectangle((int) (pos.getWorldVar().x + 6), (int) (pos.getWorldVar().y), 50, 30);
                break;
            case (4):
                if (right)
                    rect = new Rectangle((int) (pos.getWorldVar().x) + 60, (int) (pos.getWorldVar().y + 22), 30, 50);
                break;
            case (3):
                if (left)
                    rect = new Rectangle((int) (pos.getWorldVar().x) - 27, (int) (pos.getWorldVar().y + 22), 30, 50);
                break;
            case (2):
                if (down)
                    rect = new Rectangle((int) (pos.getWorldVar().x + 6), (int) (pos.getWorldVar().y) + 70, 50, 30);
                break;
        }
        return rect;
    }

    public void update() {
        super.update();

        if (!fallen) {
            move();
            if (!tc.collisionTile(dx, 0)) {
                PlayState.map.x += dx;
                pos.x += dx;
            }
            if (!tc.collisionTile(0, dy)) {
                PlayState.map.y += dy;
                pos.y += dy;
            }
        } else {
            if (animate.hasPlayedOnce()) {
                resetPosition();
                dx = 0;
                dy = 0;
                fallen = false;
                manageLives(-1);
                System.out.println("fallen");
            }
        }
    }

    public void render(Graphics2D g) {
        g.setColor(Color.green);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());

        if (attack) {
            rect = attackBox(rect);
            g.setColor(Color.red);
            g.draw(rect);
        }
        if (invincibility) {
            BufferedImage image = animate.getImage();
            Composite oldC = g.getComposite();

            AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f);
            g.setComposite(alcom);
            g.drawImage(image, (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
            g.setComposite(oldC);
        } else
            g.drawImage(animate.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
        lives.drawHearts(g, LIVES);
    }


    public void input(MouseHandler mouse, KeyHandler key) {

        if (!fallen) {
            if (key.up.isDown) {
                up = true;
            } else {
                up = false;
            }
            if (key.down.isDown) {
                down = true;
            } else {
                down = false;
            }
            if (key.left.isDown) {
                left = true;
            } else {
                left = false;
            }
            if (key.right.isDown) {
                right = true;
            } else {
                right = false;
            }

            if (key.attack.isDown) {
                attack = true;
            } else {
                attack = false;
            }
            if (key.special.isDown) {
                special = true;
            } else {
                special = false;
            }
        } else {
            up = false;
            down = false;
            right = false;
            left = false;
        }
    }

}
