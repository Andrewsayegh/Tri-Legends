package com.force.states;

import com.force.GamePanel;
import com.force.util.KeyHandler;
import com.force.util.MouseHandler;
import com.force.util.Vector2f;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {

    private GameState[] states;

    public static Vector2f map;

    public static final int NUMBEROFSTATES = 4;
    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;

    private int currentState;


    public GameStateManager() {
        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);
        states = new GameState[NUMBEROFSTATES];
        currentState = PLAY;
        loadState(currentState);
    }


    private void loadState(int state) {
        if (state == MENU)
            states[state] = new MenuState(this);
        else if (state == PLAY)
            states[state] = new PlayState(this);
       else if (state == PAUSE)
           states[state] = new PauseState(this);
       else if (state == GAMEOVER)
           states[state] = new GameOverState(this);
    }

    private void unloadState(int state) {
        states[state] = null;
    }

    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }


    public void update(){
        Vector2f.setWorldVar(map.x, map.y);
        if (states[currentState] != null) states[currentState].update();


    }
    public void input(MouseHandler mouse, KeyHandler key){
        if (states[currentState] != null) states[currentState].input(mouse, key);

    }
    public void render(Graphics2D g){
        if (states[currentState] != null) states[currentState].render(g);

    }
}
