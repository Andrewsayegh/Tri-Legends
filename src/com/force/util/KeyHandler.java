package com.force.util;

import com.force.GamePanel;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class KeyHandler implements KeyListener {

    public static List<Key> keys = new ArrayList<Key>();

    public class Key {
        public int presses, absorbs;
        public boolean isDown, clicked;

        public Key() {
            keys.add(this);
        }

        public void toggle(boolean pressed) {
            if (pressed != isDown) {
                isDown = pressed;
            }
            if (pressed) {
                presses++;
            }
        }

        public void tick() {
            if (absorbs < presses) {
                absorbs++;
                clicked = true;
            } else {
                clicked = false;
            }
        }
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key right = new Key();
    public Key left = new Key();
    public Key attack = new Key();
    public Key special = new Key();
    public Key menu = new Key();
    public Key enter = new Key();
    public Key escape = new Key();


    public KeyHandler(GamePanel panel) {
        panel.addKeyListener(this);
    }

    public void releaseAll() {
        for (Key k : keys) {
            k.isDown = false;
        }
    }

    public void tick() {
        for (Key k : keys) {
            k.tick();
        }
    }

    public void toggle(KeyEvent e, boolean pressed){
        if (e.getKeyCode() == KeyEvent.VK_W) up.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_S) down.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_A) left.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_D) right.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_COMMA) attack.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_PERIOD) special.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_M) menu.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) enter.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) escape.toggle(pressed);
    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }


    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }
}
