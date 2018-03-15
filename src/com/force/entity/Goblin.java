package com.force.entity;

import com.force.GamePanel;
import com.force.graphics.Sprite;
import com.force.util.Vector2f;

import java.awt.*;

/**
 * Created by student on 3/11/18.
 */
public class Goblin extends Enemy {

    protected int s, s2;
    protected long moveTurn, chargeTurn;
    protected boolean charging;
    protected float chargeXdir, chargeYdir;

    public Goblin(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size, false);

        s = 2;
        s2 = 4;
        moveTurn = System.currentTimeMillis();
        chargeTurn = -GamePanel.oldFrameCount * 50 - 10;

        bounds.setWidth(42);
        bounds.setHeight(20);
        bounds.setXOffset(0);
        bounds.setYOffset(40);
        setLives(2);
    }

    public void render(Graphics2D g) {
        g.setColor(Color.red);
        g.drawRect((int) pos.getWorldVar().x + (int) bounds.getXOffset(), (int) pos.getWorldVar().y + (int) bounds.getYOffset(), (int) bounds.getWidth(), (int) bounds.getHeight());

        g.drawImage(animate.getImage(), (int) (pos.getWorldVar().x) - 10, (int) (pos.getWorldVar().y), size, size, null);

    }

    public void move() {

        UP = 3;
        DOWN = 0;
        LEFT = 1;
        RIGHT = 2;

        int rand = (int) (Math.random() * 4);

//        rand = 3;


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


        if (up) {
            dy = -s;
            dx = 0;
        }
        if (down) {
            dy = s;
            dx = 0;
        }
        if (left) {
            dx = -s;
            dy = 0;
        }
        if (right) {
            dx = s;
            dy = 0;
        }

        if (!fallen) {
            if (!tc.collisionTile(dx, 0)) {
                pos.x += dx;
            }
            if (!tc.collisionTile(0, dy)) {
                pos.y += dy;
            }
        } else {
            moveTurn = System.currentTimeMillis() - GamePanel.oldFrameCount * 130;
            if (up) {
                up = false;
                down = true;
                pos.y += 2 * s;
            } else if (down) {
                down = false;
                up = true;
                pos.y -= 2 * s;
            } else if (left) {
                left = false;
                right = true;
                pos.x += 2 * s;
            } else if (right) {
                right = false;
                left = true;
                pos.x -= 2 * s;
            }
            fallen = false;
        }

//        pos.x += dx;
//        pos.y += dy;


//        System.out.println(fallen);
//        System.out.println(rand);
//            System.out.println(fallenSensor()[0] + " " + fallenSensor()[1]);


    }

    public void move2(Entity player) {

        if (System.currentTimeMillis() - chargeTurn <= GamePanel.oldFrameCount * 50) {
            charging = true;
        } else {
            charging = false;
        }

        if (getDistanceTo(player) != 0) {
            if (Math.abs(player.pos.x - this.pos.x) <= 25 && !charging && getDistanceTo(player) <= 300) {
                chargeTurn = System.currentTimeMillis();
                chargeYdir = (player.pos.y - this.pos.y) / Math.abs(player.pos.y - this.pos.y);
                chargeXdir = 0;
                if (chargeYdir > 0) {
                    down = true;
                    up = false;
                    right = false;
                    left = false;
                } else {
                    up = true;
                    down = false;
                    right = false;
                    left = false;
                }
            } else if (Math.abs(player.pos.y - this.pos.y) <= 25 && !charging && getDistanceTo(player) <= 300) {
                chargeTurn = System.currentTimeMillis();
                chargeXdir = (player.pos.x - this.pos.x) / Math.abs(player.pos.x - this.pos.x);
                chargeYdir = 0;
                if (chargeXdir > 0) {
                    right = true;
                    left = false;
                    up = false;
                    down = false;
                } else {
                    left = true;
                    right = false;
                    down = false;
                    up = false;
                }
            } else if (!charging) {
                move();
            } else {

                UP = 7;
                DOWN = 4;
                LEFT = 5;
                RIGHT = 6;

                if (!fallen) {
                    if (!tc.collisionTile(dx, 0)) {
                        pos.x += 2 * s * chargeXdir;
                    }
                    if (!tc.collisionTile(0, dy)) {
                        pos.y += 2 * s * chargeYdir;
                    }
                }
            }
        }
    }
    public void animate(){
        super.animate();
    }

    public void update(Entity player) {
        super.update();
        move2(player);

        if(getDistanceTo(player) <= 10){
            player.manageLives(-1);
        }
    }
}
