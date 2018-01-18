package com.force.entity;

import com.force.graphics.Sprite;
import com.force.states.PlayState;
import com.force.util.Vector2f;

import java.awt.*;

/**
 * Created by student on 12/20/17.
 */
public class Enemy extends Entity {

    public long lastTurn = System.currentTimeMillis();

    public Enemy(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size);
        acceleration = 1.5f;
        decelleration = 1.5f;
        maxSpeed = 3f;

        bounds.setWidth(42);
        bounds.setHeight(20);
        bounds.setXOffset(12);
        bounds.setYOffset(40);

    }

    public void render(Graphics2D g) {
        g.setColor(Color.blue);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());
        g.drawImage(animate.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);

    }

    public void move() {
        int rand = (int) (Math.random() * 4);
        if (System.currentTimeMillis() - lastTurn >= 3000) {


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
            lastTurn = System.currentTimeMillis();
        }

        if (up)
            pos.y += -1;
        if (down)
            pos.y += 1;
        if (left)
            pos.x += -1;
        if (right)
            pos.x += 1;


    }


    public void move2(Entity player){
        pos.x += player.dx;
        pos.y += player.dy;


    }

    public void update(Entity player){
        super.update();
        if(LIVES > 0) {
            move();

            if(getDistanceTo(player) <= 100){
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
        } else {
            if(animate.hasPlayedOnce()) {
                //resetPosition();
                dx = 0;
                dy = 0;
                fallen = false;
                manageLives(-1);
            }
        }
    }

    public float getDistanceTo(Entity player){
        return (float)(Math.sqrt(((this.pos.x - player.pos.x)*(this.pos.x - player.pos.x)) + ((this.pos.y - player.pos.y)*(this.pos.y - player.pos.y))));
    }

}
