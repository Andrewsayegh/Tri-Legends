package com.force.states;

import com.force.GamePanel;
import com.force.util.KeyHandler;
import com.force.util.MouseHandler;
import com.force.util.Vector2f;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {

    private ArrayList<GameState> states;

    public static Vector2f map;

    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;


    public GameStateManager() {
        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);
        states = new ArrayList<GameState>();
        states.add(new PlayState(this));
    }

    public void removeState(int state){
        states.remove(state);
    }

    public void addState(int state){
        if (!states.contains(state)) {
            switch (state) {
                case PLAY:
                    states.add(new PlayState(this));
                    break;
                case MENU:
                    states.add(new MenuState(this));
                    break;
                case PAUSE:
                    states.add(new PauseState(this));
                    break;
                case GAMEOVER:
                    states.add(new GameOverState(this));
                    break;
            }
        } else {
            return;
        }
    }

    public void addAndRemove(int state){
        states.remove(0);
        addState(state);
    }

    public void update(){
        Vector2f.setWorldVar(map.x, map.y);
        for (GameState s: states) {
            s.update();
        }

    }
    public void input(MouseHandler mouse, KeyHandler key){
        for (GameState s: states) {
            s.input(mouse, key);
        }
    }
    public void render(Graphics2D g){
        for (GameState s: states) {
            s.render(g);
        }
    }
}
