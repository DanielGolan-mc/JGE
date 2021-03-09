package net.danielgolan.j2de.game;

import net.danielgolan.j2de.engine.Game;
import net.danielgolan.j2de.engine.JEngine;
import net.danielgolan.j2de.engine.JGRenderer;
import net.danielgolan.j2de.engine.gfx.ImageTile;

public class GameManager implements Game {
    private final ImageTile image = new ImageTile("/assets/textures/test.png", 16, 16);

    public GameManager() {

    }

    @Override
    public void update(JEngine jEngine, float deltaTime) {

    }

    @Override
    public void render(JEngine jEngine, JGRenderer jgRenderer) {

    }

    public static void main(String[] args) {
         JEngine jEngine = new JEngine(new GameManager());

         jEngine.start();
    }
}
