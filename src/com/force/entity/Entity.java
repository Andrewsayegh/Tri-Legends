package com.force.entity;

import com.force.GamePanel;
import com.force.graphics.Animation;
import com.force.graphics.Lives;
import com.force.graphics.Sprite;
import com.force.util.AABB;
import com.force.util.TileCollision;
import com.force.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Entity {

    protected final int UP = 3;
    protected final int DOWN = 2;
    protected final int RIGHT = 0;
    protected final int LEFT = 1;
    protected final int FALLEN = 4;
    protected final int ATTACK = 5;
    protected final int SPECIAL = 6;
    protected double LIVES = 3;

    protected int currentAnimation;
    protected boolean invincibility;
    protected long invincibilityCounter;
    protected boolean hasInvincibility;

    protected Animation animate;
    protected Lives lives;
    protected Sprite sprite;
    protected Vector2f pos;
    protected int size;

    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;
    protected int firstPressed;
    protected boolean attack;
    protected boolean special;
    protected boolean fallen;
    protected int attackSpeed;
    protected int attackDurration;

    protected float dx;
    protected float dy;

    protected float acceleration;
    protected float tvConstant;
    protected int dir;

    protected AABB hitBounds;
    protected AABB bounds;

    protected TileCollision tc;

    public Entity(Sprite sprite, Vector2f orgin, int size, boolean hasInvincibility) {
        this.sprite = sprite;
        pos = orgin;
        this.size = size;

        bounds = new AABB(orgin, size, size);
        hitBounds = new AABB(new Vector2f(orgin.x + (size / 2), orgin.y), size, size);

        animate = new Animation();
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);

        tc = new TileCollision(this);
        lives = new Lives();

        invincibility = false;
        this.hasInvincibility = hasInvincibility;
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
//        } else if (attack){
//            if(currentAnimation != ATTACK || animate.getDelay() == -1) {
//                setAnimation(ATTACK, sprite.getSpriteArray(ATTACK),5);
//            }
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


    public void update() {
        animate();
        setHitBoxDirection();
        manageInvincibility(50); // -> long in frames
        animate.update();
    }

    public abstract void render(Graphics2D g);

    public void manageLives(double num) {
        if (num < 0 && !invincibility && hasInvincibility) {
            LIVES += num;
            invincibility = true;
            invincibilityCounter = System.currentTimeMillis();
        }
    }

    public boolean isDead() {
        if (LIVES <= 0)
            return true;
        return false;
    }

    public void setLives(int lives){
        LIVES = lives;
    }

    public void manageInvincibility(int count) {
        if (invincibility) {
            if (System.currentTimeMillis() - invincibilityCounter >= GamePanel.oldFrameCount * count) {
                invincibility = false;
            }
        }
    }
}
