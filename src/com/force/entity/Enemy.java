package com.force.entity;

import com.force.graphics.Sprite;
import com.force.util.Vector2f;

import java.awt.Graphics2D;

public class Enemy extends Entity {

    public Enemy(Sprite sprite, Vector2f orgin, int size, boolean hasInvincibility) {
        super(sprite, orgin, size, hasInvincibility);
    }


    public void update() {
        super.update();
    }

    public void render(Graphics2D g) {

    }

    public float getDistanceTo(Entity player) {
        return (float) (Math.sqrt(((this.pos.x - player.pos.x) * (this.pos.x - player.pos.x))
                + ((this.pos.y - player.pos.y) * (this.pos.y - player.pos.y))));
    }
}
