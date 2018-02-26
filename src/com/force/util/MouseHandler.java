package com.force.util;

import com.force.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private static int mouseX = -1;
    private static int mouseY = -1;
    private static int mouseB = -1;

    public MouseHandler(GamePanel panel) {
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
    }

    public int getMouseX() {
        return mouseX;
    }
    public int getMouseY() {
        return mouseY;
    }
    public int getButton() {
        return mouseB;
    }
    public void mouseClicked(MouseEvent e) { }
    public void mousePressed(MouseEvent e) {
        mouseB = e.getButton();
    }
    public void mouseReleased(MouseEvent e) {
        mouseB = -1;
    }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    public void mouseMoved(MouseEvent e) { }
}
