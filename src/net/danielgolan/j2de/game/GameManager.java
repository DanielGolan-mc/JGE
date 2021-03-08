package net.danielgolan.j2de.game;

import net.danielgolan.j2de.engine.Game;
import net.danielgolan.j2de.engine.JEngine;
import net.danielgolan.j2de.engine.JGRenderer;
import net.danielgolan.j2de.engine.gfx.GImage;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameManager extends Game {
    private final GImage image = new GImage("/myhead.png");

    public GameManager() {

    }

    @Override
    public void update(JEngine jEngine, float deltaTIme) {
        if (jEngine.getInput().isKeyDown(KeyEvent.VK_A))
            System.out.println("A is pressed");
    }

    @Override
    public void render(JEngine jEngine, JGRenderer jgRenderer) {
        jgRenderer.drawImage(image, jEngine.getInput().getMouseX(), jEngine.getInput().getMouseY());
    }

    public static void main(String[] args) {
         JEngine jEngine = new JEngine(new GameManager());

         jEngine.start();
    }
}
