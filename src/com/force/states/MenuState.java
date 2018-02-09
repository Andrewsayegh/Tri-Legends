package com.force.states;

import com.force.GamePanel;
import com.force.graphics.Font;
import com.force.graphics.Sprite;
import com.force.util.KeyHandler;
import com.force.util.MouseHandler;
import com.force.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.Color;

public class MenuState extends GameState {

    private int currentChoice = 0;
    private String[] options = {
            "Play",
            "Settings",
            "Exit"
    };
    private Font font;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        font = new Font("font/font.png", 10, 10);
    }

    public void update() {

    }

    public void input(MouseHandler mouse, KeyHandler key) {
        if(key.enter.isDown) select();
        if (key.up.isDown){
            if (currentChoice > 0) {
                currentChoice--;
            }
        }
        if (key.down.isDown){
            if (currentChoice < options.length - 1) {
                currentChoice++;
            }
        }
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            Sprite.drawArray(g, font, options[i], new Vector2f( GamePanel.width - (options[i].length() * 32), 32 * i), 32, 32);
        }
    }

    private void select() {
        if (currentChoice == 0) {
            //Start
            gsm.setState(GameStateManager.PLAY);
        }
        if (currentChoice == 1) {
            //Help
            gsm.setState(GameStateManager.MENU);
            System.out.println("UNFINISHED -> Returned to menu");
        }
        if (currentChoice == 2) {
            System.exit(0);
        }
    }


}
