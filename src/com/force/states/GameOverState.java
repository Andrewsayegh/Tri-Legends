package com.force.states;

import com.force.GamePanel;
import com.force.graphics.Font;
import com.force.graphics.Sprite;
import com.force.util.KeyHandler;
import com.force.util.MouseHandler;
import com.force.util.Vector2f;

import java.awt.Graphics2D;

public class GameOverState extends GameState {

    private Font font;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
        font = new Font("font/font.png", 10, 10);

    }

    public void update() {

    }

    public void input(MouseHandler mouse, KeyHandler key) {
    }

    public void render(Graphics2D g) {

        String message = "Game Over";
        Sprite.drawArray(g, font, message, new Vector2f(GamePanel.width - message.length() * 32, GamePanel.height - message.length() * 5), 32, 24);

    }
}
