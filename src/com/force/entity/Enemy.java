package com.force.entity;

import com.force.graphics.Sprite;
import com.force.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Created by student on 12/20/17.
 */
public class Enemy extends Entity {
    public int s = 3;

    public Enemy(Sprite sprite, Vector2f orgin, int size, boolean hasInvincibility) {
        super(sprite, orgin, size, hasInvincibility);
    }

    public void render(Graphics2D g) {
        g.setColor(Color.blue);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());
        g.drawImage(animate.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }

    public void update() {
        super.update();
    }


    public float getDistanceTo(Entity player) {
        return (float) (Math.sqrt(((this.pos.x - player.pos.x) * (this.pos.x - player.pos.x))
                + ((this.pos.y - player.pos.y) * (this.pos.y - player.pos.y))));
    }
}
