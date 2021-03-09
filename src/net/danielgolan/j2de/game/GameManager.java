package net.danielgolan.j2de.game;

import net.danielgolan.j2de.engine.Game;
import net.danielgolan.j2de.engine.JEngine;
import net.danielgolan.j2de.engine.JGRenderer;
import net.danielgolan.j2de.engine.gfx.GImage;
import net.danielgolan.j2de.engine.gfx.ImageTile;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameManager implements Game {
    private final ImageTile image = new ImageTile("/myhead.png", 16, 16);

    public GameManager() {

    }

    @Override
    public void update(JEngine jEngine, float deltaTime) {
        if (jEngine.getInput().isKeyDown(KeyEvent.VK_A))
            System.out.println("A is pressed");
        temp += deltaTime * 20;
        if (temp > 3)
            temp = 0;
    }

    float temp = 0;

    @Override
    public void render(JEngine jEngine, JGRenderer jgRenderer) {
        jgRenderer.drawImageTile(image, jEngine.getInput().getMouseX() - 8, jEngine.getInput().getMouseY() - 8, (int) temp, 0);
    }

    public static void main(String[] args) {
         JEngine jEngine = new JEngine(new GameManager());

         jEngine.start();
    }
}
