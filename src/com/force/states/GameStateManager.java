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
    private GameState[] gameStates;
    private int currentState;

    private PauseState pauseState;
    private boolean paused;

    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;
    public static final int NUMGAMESTATES = 4;


    public GameStateManager() {
        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);

        gameStates = new GameState[NUMGAMESTATES];

        pauseState = new PauseState(this);
        paused = false;

        currentState = PLAY;
        loadState(currentState);

    }

    private void loadState(int state) {

        switch (state) {
            case MENU:
                gameStates[state] = new MenuState(this);
                break;
            case PLAY:
                gameStates[state] = new PlayState(this);
                break;
            case PAUSE:
                gameStates[state] = new PauseState(this);
                break;
            case GAMEOVER:
                gameStates[state] = new GameOverState(this);
                break;
        }
    }

    private void unloadState(int state) {
        gameStates[state] = null;
    }

    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }

    public void setPaused(boolean b) {
        paused = b;
    }

    public void update() {
        if (paused) {
            pauseState.update();
            return;
        }
        if (gameStates[currentState] != null) gameStates[currentState].update();
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        gameStates[currentState].input(mouse, key);
    }

    public void render(Graphics2D g2d) {
        if (paused) {
            pauseState.render(g2d);
            return;
        }
        if (gameStates[currentState] != null) gameStates[currentState].render(g2d);
        else {
            g2d.setColor(java.awt.Color.BLACK);
            g2d.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
    }
}
