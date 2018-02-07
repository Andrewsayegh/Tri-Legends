package com.force.entity;


import com.force.GamePanel;
import com.force.graphics.Sprite;
import com.force.states.PlayState;
import com.force.util.KeyHandler;
import com.force.util.MouseHandler;
import com.force.util.Vector2f;

import java.awt.*;

public class Player extends Entity {
    protected Rectangle rect;

    public Player(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size);
//
        acceleration = 4f;
        tvConstant = -.5f;
        bounds.setWidth(42);
        bounds.setHeight(20);
        bounds.setXOffset(10);
        bounds.setYOffset(40);
    }

    public void move(Entity enemy) {
        // TODO: 12/19/17 Fix this:

        if (up){
            dir = (float)(Math.PI/2);
            if(right)
                dir -= (float)(Math.PI/4);
            if (left)
                dir += (float)(Math.PI/4);

            dx += acceleration*Math.cos(dir);
            dy -= acceleration*Math.sin(dir);
        }

        if (down){
            dir = (float)(-Math.PI/2);
            if(right)
                dir += (float)(Math.PI/4);
            if (left)
                dir -= (float)(Math.PI/4);

            dx += acceleration*Math.cos(dir);
            dy -= acceleration*Math.sin(dir);
        }

        if (left && !(down || up)){
            dir = (float)(Math.PI);

            dx += acceleration*Math.cos(dir);
            dy -= acceleration*Math.sin(dir);
        }
        if (right && !(down || up)){
            dir = 0;

            dx += acceleration*Math.cos(dir);
            dy -= acceleration*Math.sin(dir);
        }



        System.out.println(dir);

        dx += tvConstant*dx;
        dy += tvConstant*dy;



    }


    private void resetPosition() {
        System.out.println("Reseting Player... ");
        pos.x = GamePanel.width / 2 - 32;
        PlayState.map.x = 0;

        pos.y = GamePanel.height /2 - 32;
        PlayState.map.y = 0;

        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 8);

    }

    public void update(Entity enemy) {
        super.update();


        if(!fallen) {

            move(enemy);
            if(!tc.collisionTile(dx, 0)) {
                PlayState.map.x += dx;
                pos.x += dx;
            }
            if(!tc.collisionTile(0, dy)) {
                PlayState.map.y += dy;
                pos.y += dy;
            }
        } else {
            if(animate.hasPlayedOnce()) {
                resetPosition();
                dx = 0;
                dy = 0;
                fallen = false;
                manageLives(-1);
            }
        }
    }


    public void render(Graphics2D g) {
        g.setColor(Color.green);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());

        if(attack) {
            g.setColor(Color.red);

          if(attackbox() != null)
              g.draw(attackbox());
        }

        g.drawImage(animate.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);

        lives.drawHearts(g, LIVES);
    }


    public void input(MouseHandler mouse, KeyHandler key) {

        if (mouse.getButton() == 1) {
            System.out.println("Player: " + pos.x + ", " + pos.y);
        }

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
