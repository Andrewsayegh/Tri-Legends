package com.force.tiles.blocks;

import com.force.util.AABB;
import com.force.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class WallBlock extends Block {

    public WallBlock(BufferedImage img, Vector2f pos, int width, int height) {
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

    }
}
