package com.force.entity;

import com.force.graphics.Animation;
import com.force.graphics.Lives;
import com.force.graphics.Sprite;
import com.force.util.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    protected final int UP = 3;
    protected final int DOWN = 2;
    protected final int RIGHT = 0;
    protected final int LEFT = 1;
    protected final int FALLEN = 4;
    protected final int ATTACK = 5;
    protected final int SPECIAL = 6;
    protected int LIVES = 3;
    protected int currentAnimation;

    protected Animation animate;
    protected Lives lives;
    protected Sprite sprite;
    protected Vector2f pos;
    protected int size;

    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;
    protected boolean attack;
    protected boolean special;
    protected boolean fallen;
    protected int attackSpeed;
    protected int attackDurration;

    protected float dx;
    protected float dy;

    protected float maxSpeed = 3f;
    protected float acceleration = 2f;
    protected float decelleration = 0.3f;

    protected AABB hitBounds;
    protected AABB bounds;

    protected TileCollision tc;


    public Entity(Sprite sprite, Vector2f orgin, int size) {
        this.sprite = sprite;
        pos = orgin;
        this.size = size;

        bounds = new AABB(orgin, size, size);
        hitBounds = new AABB(new Vector2f(orgin.x + (size / 2), orgin.y), size, size);

        animate = new Animation();
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);

        tc = new TileCollision(this);

        lives = new Lives();

    }


    public void setAnimation(int i, BufferedImage[] frames, int delay) {
        currentAnimation = i;
        animate.setFrames(frames);
        animate.setDelay(delay);
    }

    public void animate() {
        if (up) {
            if (currentAnimation != UP || animate.getDelay() == -1) {
                setAnimation(UP, sprite.getSpriteArray(UP), 5);
            }
        } else if (down) {
            if (currentAnimation != DOWN || animate.getDelay() == -1) {
                setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
            }
        } else if (right) {
            if (currentAnimation != RIGHT || animate.getDelay() == -1) {
                setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
            }
        } else if (left) {
            if (currentAnimation != LEFT || animate.getDelay() == -1) {
                setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
            }
        } else if (fallen) {
            if (currentAnimation != FALLEN || animate.getDelay() == -1) {
                setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), 15);
            }
        } else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
        }
    }

    public void setFallen(boolean b) {
        fallen = b;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setDecelleration(float decelleration) {
        this.decelleration = decelleration;
    }

    public AABB getBounds() {
        return bounds;
    }

    public int getSize() {
        return size;
    }

    public Animation getAnimate() {
        return animate;
    }

    private void setHitBoxDirection() {
        if(up) {
            hitBounds.setYOffset(-size / 2);
            hitBounds.setXOffset(0);
        }
        else if(down) {
            hitBounds.setYOffset(size / 2);
            hitBounds.setXOffset(0);
        }
        else if(left) {
            hitBounds.setXOffset(-size / 2);
            hitBounds.setYOffset(0);
        }
        else if(right) {
            hitBounds.setXOffset(size / 2);
            hitBounds.setYOffset(0);
        }
    }

    public void update() {
        animate();
        setHitBoxDirection();
        animate.update();
    }

    public abstract void render(Graphics2D g);

    public void manageLives(int num) {
        LIVES += num;
    }

    public boolean isDead() {
        if(LIVES <= 0)
            return true;
        return false;
    }
}
