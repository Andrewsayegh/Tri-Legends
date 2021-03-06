package com.force.entity;


import com.force.GamePanel;
import com.force.graphics.Sprite;
import com.force.states.PlayState;
import com.force.util.KeyHandler;
import com.force.util.MouseHandler;
import com.force.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    public Player(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size, true);
        acceleration = 4f;
        tvConstant = -0.5f;
        bounds.setWidth(42);
        bounds.setHeight(20);
        bounds.setXOffset(12);
        bounds.setYOffset(40);
    }

    public void move() {

        if (!up && !down && !left && !right) {
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

        if (down && firstPressed == 0) {            //1 up, 2 down, 3 left, 4 right, 0 None
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
            aDir = 1;
        }
        if (firstPressed == 2) {
            dir = 2;
            if (left)
                dir = 3;
            if (right)
                dir = 4;
            aDir = 2;
        }
        if (firstPressed == 3) {
            dir = 3;
            if (up)
                dir = 1;
            if (down)
                dir = 2;
            aDir = 3;
        }
        if (firstPressed == 4) {
            dir = 4;
            if (up)
                dir = 1;
            if (down)
                dir = 2;
            aDir = 4;
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

        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
    }

    public void setHitBoxDirection() {

        if (up) {
            hitBounds.setYOffset(-size / 2);
            hitBounds.setXOffset(0);
        } else if (down) {
            hitBounds.setYOffset(size / 2);
            hitBounds.setXOffset(0);
        } else if (left) {
            hitBounds.setXOffset(-size / 2);
            hitBounds.setYOffset(0);
        } else if (right) {
            hitBounds.setXOffset(size / 2);
            hitBounds.setYOffset(0);
        }
    }


    public void checkCollision(ArrayList<Enemy> e) {
        setHitBoxDirection();
        for (Enemy enemy : e) {
            if (attack && hitBounds.collides(enemy.getBounds())) {
                enemy.manageLives(-1);
            }
        }

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
            if (tc.collisonWall(dx, 0)) {
                PlayState.map.x -= dx;
                pos.x -= dx;
            }
            if (tc.collisonWall(0, dy)) {
                PlayState.map.y -= dy;
                pos.y -= dy;
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
            g.setColor(Color.MAGENTA);
            g.drawOval((int) (hitBounds.getPos().getWorldVar().x + hitBounds.getXOffset()), (int) (hitBounds.getPos().getWorldVar().y + hitBounds.getYOffset()), (int) hitBounds.getWidth(), (int) hitBounds.getHeight());
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

    public void animate() {
        if (fallen) {
            if (currentAnimation != FALLEN || animate.getDelay() == -1) {
                setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), 15);
            }
//        } else if (attack){
//            if(currentAnimation != ATTACK || animate.getDelay() == -1) {
//                setAnimation(ATTACK, sprite.getSpriteArray(ATTACK),5);
//            }
        } else if (firstPressed == 1 && up) {
            if (currentAnimation != UP || animate.getDelay() == -1) {
                setAnimation(UP, sprite.getSpriteArray(UP), 5);
            }
        } else if (firstPressed == 2 && down) {
            if (currentAnimation != DOWN || animate.getDelay() == -1) {
                setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
            }
        } else if (firstPressed == 4 && right) {
            if (currentAnimation != RIGHT || animate.getDelay() == -1) {
                setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
            }
        } else if (firstPressed == 3 && left) {
            if (currentAnimation != LEFT || animate.getDelay() == -1) {
                setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
            }
        } else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
        }
    }
}
