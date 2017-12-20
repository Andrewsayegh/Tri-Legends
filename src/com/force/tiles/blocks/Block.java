package com.force.tiles.blocks;

import com.force.util.AABB;
import com.force.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Block {
    protected int width;
    protected int height;

    protected BufferedImage img;
    protected Vector2f pos;

    public Block(BufferedImage img, Vector2f pos, int width, int height) {
        this.img = img;
        this.pos = pos;
        this.width = width;
        this.height = height;
    }

    public abstract boolean update(AABB p);
    public abstract boolean isInside(AABB p);
    public Vector2f getPos() { return pos; }

    public void render(Graphics2D g) {
        g.drawImage(img, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, width, height, null);
    }
}
