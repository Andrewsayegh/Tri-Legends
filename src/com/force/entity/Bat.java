package com.force.entity;

import com.force.GamePanel;
import com.force.graphics.Sprite;
import com.force.states.PlayState;
import com.force.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.Color;


public class Bat extends Enemy {

    public int s = 2;

    public long moveTurn = System.currentTimeMillis();

    public Bat(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size, false);
        acceleration = 1.5f;

        bounds.setWidth(42);
        bounds.setHeight(20);
        bounds.setXOffset(12);
        bounds.setYOffset(40);
        setLives(1);
    }


    public void render(Graphics2D g) {
        g.setColor(Color.blue);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());
        g.drawImage(animate.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }

    public void move() {
        int rand = (int) (Math.random() * 4);
        if (System.currentTimeMillis() - moveTurn >= GamePanel.oldFrameCount * 150) {
            if (rand == 0) {
                up = true;
            } else {
                up = false;
            }
            if (rand == 1) {
                down = true;
            } else {
                down = false;
            }
            if (rand == 2) {
                left = true;
            } else {
                left = false;
            }
            if (rand == 3) {
                right = true;
            } else {
                right = false;
            }
            moveTurn = System.currentTimeMillis();
        }
        if (up && pos.y > 0)
            pos.y += -s;
        if (down && pos.y < 6400)
            pos.y += s;
        if (left && pos.x > 0)
            pos.x += -s;
        if (right && pos.x < 6400)
            pos.x += s;
    }


    public void move2(Entity player) {
        if (getDistanceTo(player) != 0) {
            float vectorx = (this.pos.x - player.pos.x) / getDistanceTo(player);
            float vectory = (this.pos.y - player.pos.y) / getDistanceTo(player);

            pos.x -= s * vectorx;
            pos.y -= s * vectory;
        }

        if (getDistanceTo(player) <= 10) {
            player.manageLives(-.5);
        }
    }

    public void update(Entity player, int radius) {
        fallen = false;
        super.update();
        if (LIVES > 0) {
            if (getDistanceTo(player) >= radius) {
                move();
            } else if (getDistanceTo(player) <= radius) {
                move2(player);
            }

            if (!tc.collisionTile(dx, 0)) {
                PlayState.map.x += dx;
                pos.x += dx;
            }
            if (!tc.collisionTile(0, dy)) {
                PlayState.map.y += dy;
                pos.y += dy;
            }
        }
    }
}
