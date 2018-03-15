package com.force.tiles.blocks;

import com.force.util.AABB;
import com.force.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class ObjBlock extends Block {

    public ObjBlock(BufferedImage img, Vector2f pos, int width, int height) {
        super(img, pos, width, height);
    }

    public boolean update(AABB p) {
        return true;
    }

    public boolean isInside(AABB p) {
        return false;
    }

    public void render(Graphics2D g) {
        super.render(g);

        /**
         * DEBUGING COLLISION *
         * SHOW COLLISION BOXES */
         g.setColor(Color.WHITE);
         g.drawRect((int) pos.getWorldVar().x, (int) pos.getWorldVar().y, width, height);
    }
}
