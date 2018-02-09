package com.force.util;


import com.force.entity.Entity;

public class AABB { // Stands for Axis-Aligned Bounding Box

    private Vector2f pos;
    private float xOffset = 0;
    private float yOffset = 0;
    private float width;
    private float height;
    private float radius;
    private int size;
    private Entity entity;

    public AABB(Vector2f pos, int width, int height) {
        this.pos = pos;
        this.width = width;
        this.height = height;

        size = Math.max(width, height);
    }

    public AABB(Vector2f pos, int radius, Entity entity) {
        this.pos = pos;
        this.radius = radius;
        this.entity = entity;

        size = radius;
    }

    public Vector2f getPos() {
        return pos;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRadius() {
        return radius;
    }

    public void setBox(Vector2f pos, int width, int height) {
        this.pos = pos;
        this.width = width;
        this.height = height;

        size = Math.max(width, height);
    }

    public void setCircle(Vector2f pos, int radius) {
        this.pos = pos;
        this.radius = radius;

        size = radius;
    }

    public void setXOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean collides(AABB bBox) {
        float ax = ((pos.getWorldVar().x + (xOffset)) + (width / 2));
        float ay = ((pos.getWorldVar().y + (yOffset)) + (height / 2));
        float bx = ((bBox.pos.getWorldVar().x + (bBox.xOffset)) + (width / 2));
        float by = ((bBox.pos.getWorldVar().y + (bBox.yOffset)) + (height / 2));

        if (Math.abs(ax - bx) < (this.width / 2) + (bBox.width / 2)) {
            if (Math.abs(ay - by) < (this.height / 2) + (bBox.height / 2)) {
                return true;
            }
        }
        return false;
    }

    public boolean colCircleBox(AABB aBox) {

        float cx = (float) (pos.getWorldVar().x + radius / Math.sqrt(2) - entity.getSize() / Math.sqrt(2));
        float cy = (float) (pos.getWorldVar().y + radius / Math.sqrt(2) - entity.getSize() / Math.sqrt(2));

        float xDelta = cx - Math.max(aBox.pos.getWorldVar().x + (aBox.getWidth() / 2), Math.min(cx, aBox.pos.getWorldVar().x));
        float yDelta = cy - Math.max(aBox.pos.getWorldVar().y + (aBox.getWidth() / 2), Math.min(cy, aBox.pos.getWorldVar().y));

        if ((xDelta * xDelta + yDelta * yDelta) < ((this.radius / Math.sqrt(2)) * (this.radius / Math.sqrt(2)))) {
            return true;
        }
        return false;
    }
}
