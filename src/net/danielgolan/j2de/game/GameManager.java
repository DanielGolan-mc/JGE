package net.danielgolan.j2de.game;

import net.danielgolan.j2de.engine.Game;
import net.danielgolan.j2de.engine.JEngine;
import net.danielgolan.j2de.engine.JGRenderer;
import net.danielgolan.j2de.engine.gfx.GImage;

public class GameManager implements Game {
    private final GImage image = new GImage("/assets/textures/test.png");
    private final GImage image2 = new GImage("/assets/textures/test2.png");

    public GameManager() {
        image.setAlpha(true);
    }

    @Override
    public void update(JEngine jEngine, float deltaTime) {

    }

    @Override
    public void render(JEngine jEngine, JGRenderer jgRenderer) {
        jgRenderer.drawImage(image2, 50, 50);
        jgRenderer.drawImage(image, jEngine.getInput().getMouseX(), jEngine.getInput().getMouseY());
    }

    public static void main(String[] args) {
         JEngine jEngine = new JEngine(new GameManager());

         jEngine.start();
    }
}
