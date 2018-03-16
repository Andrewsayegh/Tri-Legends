package com.force.states;

import com.force.GamePanel;
import com.force.entity.Bat;
import com.force.entity.Enemy;
import com.force.entity.Goblin;
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
    private ArrayList<Enemy> enemies;
    private ArrayList<Goblin> goblins;

    public static Vector2f map;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        map = new Vector2f();
        Vector2f.setWorldVar(map.x, map.y);

        tm = new TileManager("tile/tilemap.xml");
        font = new Font("font/font.png", 10, 10);

        player = new Player(new Sprite("entity/player/linkFormatted.png"), new Vector2f(0 + (GamePanel.width / 2) - 32, 0 + (GamePanel.height / 2) - 32), 64);

        enemies = new ArrayList<Enemy>();
        bats = new ArrayList<Bat>();
        goblins = new ArrayList<Goblin>();

        for (int i = 0; i < 1; i++) {
            Goblin goblin = new Goblin(new Sprite("entity/enemies/Goblin.png"), new Vector2f(0 + (GamePanel.width / 2) , 0 + (GamePanel.height / 2) + 200), 64);
            goblins.add(goblin);
        }

        for (int i = 0; i < 10; i++) {
            Bat bat = new Bat(new Sprite("entity/enemies/bat-spritesheet-calciumtrice.png"), new Vector2f((float)Math.random()*6400, (float)Math.random()*6400), 64);
            bats.add(bat);
        }
        for (Bat bat: bats) {
            enemies.add(bat);
        }
        for (Goblin gob: goblins){
            enemies.add(gob);
        }
    }

    public void update() {
        Vector2f.setWorldVar(map.x, map.y);
        if (player.isDead())
            gsm.setState(GameStateManager.GAMEOVER);
        else {
            player.update();
            player.checkCollision(enemies);
            for (Bat bat: bats) {
                bat.update(player, 300);
            }
            for (Goblin gob: goblins) {
                gob.update(player);
            }
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
        for (Goblin gob: goblins) {
            gob.render(g);
        }
    }
}

