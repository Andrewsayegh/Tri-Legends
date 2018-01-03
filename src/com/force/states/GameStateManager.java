package com.force.states;

import com.force.GamePanel;
import com.force.util.KeyHandler;
import com.force.util.MouseHandler;
import com.force.util.Vector2f;
import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {

    private ArrayList<GameState> states;

    public static Vector2f map;

    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;
    private GameState presentState;
    private PlayState play;
    private MenuState menu;
    private PauseState pause;
    private GameOverState gameover;


    public GameStateManager() {
        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);
//        states = new ArrayList<GameState>();
//        states.add(new PlayState(this));

        play = new PlayState(this);
        menu = new MenuState(this);
        pause = new PauseState(this);
        gameover = new GameOverState(this);

        presentState = play;
    }

//    public void removeState(int state){
//        states.remove(state);
//    }

//    public void addState(int state){
//        if (!states.contains(state)) {
//            switch (state) {
//                case PLAY:
//                    states.add(new PlayState(this));
//                    break;
//                case MENU:
//                    states.add(new MenuState(this));
//                    break;
//                case PAUSE:
//                    states.add(new PauseState(this));
//                    break;
//                case GAMEOVER:
//                    states.add(new GameOverState(this));
//                    break;
//            }
//        } else {
//            return;
//        }
//    }
//
//    public void addAndRemove(int state){
//        states.remove(0);
//        addState(state);
//    }

    public void setState(int state) {
        switch (state) {
            case PLAY:
                presentState = play;
                break;
            case MENU:
                presentState = menu;
                break;
            case PAUSE:
                presentState = pause;
                break;
            case GAMEOVER:
                presentState = gameover;
                break;
        }

    }

    public void update(){
        Vector2f.setWorldVar(map.x, map.y);
//        for (GameState s: states) {
//            s.update();
//        }
        presentState.update();

    }
    public void input(MouseHandler mouse, KeyHandler key){
//        for (GameState s: states) {
//            s.input(mouse, key);
//        }
        presentState.input(mouse, key);
    }
    public void render(Graphics2D g){
//        for (GameState s: states) {
//            s.render(g);
//        }
        presentState.render(g);
    }
}
