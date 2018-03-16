package com.force.tiles.blocks;

import com.force.util.AABB;
import com.force.util.Vector2f;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class HoleBlock extends Block {

    public HoleBlock(BufferedImage img, Vector2f pos, int width, int height) {
        super(img, pos, width, height);
    }

    public boolean update(AABB p) {

        return false;
    }

    public boolean isInside(AABB p) {
        if (p.getPos().x + p.getXOffset() < pos.x) return false;
        if (p.getPos().y + p.getYOffset() < pos.y) return false;
        if (width + pos.x < p.getWidth() + (p.getPos().x + p.getXOffset())) return false;
        if (height + pos.y < p.getHeight() + (p.getPos().y + p.getYOffset())) return false;

        return true;
    }

    public void render(Graphics2D g) {
        super.render(g);

        /**
         * DEBUGING COLLISION *
         * SHOW COLLISION BOXES */
//        g.setColor(Color.green);
//        g.drawRect((int) pos.getWorldVar().x, (int) pos.getWorldVar().y, width, height);
    }
}
