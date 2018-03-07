package com.force.states;

import com.force.GamePanel;
import com.force.entity.Bat;
import com.force.entity.Player;
import com.force.graphics.Font;
import com.force.graphics.Sprite;
import com.force.tiles.TileManager;
import com.force.util.KeyHandler;
import com.force.util.MouseHandler;
import com.force.util.Vector2f;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class PlayState extends GameState {

    private Font font;
    private Player player;
    private TileManager tm;
    private ArrayList<Bat> bats;
    private Bat bat;

    public static Vector2f map;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        map = new Vector2f();
        Vector2f.setWorldVar(map.x, map.y);

        tm = new TileManager("tile/tilemap.xml");
        font = new Font("font/font.png", 10, 10);

        player = new Player(new Sprite("entity/player/linkFormatted.png"), new Vector2f(0 + (GamePanel.width / 2) - 32, 0 + (GamePanel.height / 2) - 32), 64);

        bats = new ArrayList<Bat>();
        for (int i = 0; i < 10; i++) {
            Bat bat = new Bat(new Sprite("entity/enemies/bat-spritesheet-calciumtrice.png"), new Vector2f((float)Math.random()*6400, (float)Math.random()*6400), 64);
            bats.add(bat);
        }
        bat = new Bat(new Sprite("entity/enemies/bat-spritesheet-calciumtrice.png"), new Vector2f(0 + 100, 0 + 100), 64);
    }

    public void update() {
        Vector2f.setWorldVar(map.x, map.y);
        if (player.isDead())
            gsm.setState(GameStateManager.GAMEOVER);
        else {
            player.update();
            for (Bat bat: bats) {
                bat.update(player, 300);
            }
            bat.update(player, 300);

        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        player.input(mouse, key);
    }

    public void render(Graphics2D g) {
        tm.render(g);

        String fps = GamePanel.oldFrameCount + " FPS";
        Sprite.drawArray(g, font, fps, new Vector2f(GamePanel.width - fps.length() * 32, 32), 32, 24);

        player.render(g);
        for (Bat bat: bats) {
            bat.render(g);
        }
        bat.render(g);
    }

}

