package net.danielgolan.je.engine;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JEngine implements Runnable{
    public final static double UPDATE_CAP = 1/60.0;

    private Thread thread;
    private GWindow gWindow;
    private JGRenderer jgRenderer;
    private Input input;

    private boolean running = false;
    private int width = 320, height = 240;
    private float scale = 4;
    private String title = "JEngine v0.0.1";

    public JEngine() {
        //TODO: Make Constructor
    }

    public void start(){
        gWindow = new GWindow(this);
        jgRenderer = new JGRenderer(this);
        input = new Input(this);

        thread = new Thread(this);
        thread.run();
    }

    public void stop(){
        running = false;
        //TODO: Stop Game
    }

    public void run() {
        running = true;

        boolean render = false;
        double firstTime, passedTime, unprocessedTime, frameTime,
                lastTime = System.nanoTime() / 1000000000.0;
        int frames, fps;
        firstTime = passedTime = unprocessedTime = frameTime = .0;
        frames = fps = 0;

        while (running) {
            render = false;

            passedTime = (firstTime = System.nanoTime() / 1000000000.0) - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while (unprocessedTime >= UPDATE_CAP){
                unprocessedTime -= UPDATE_CAP;

                render = true;

                System.out.println(input.getScroll() + " " + input.getMouseX() + " " + input.getMouseY());

                input.update();

                if (frameTime >= 1.0){
                    fps = frames;

                    frameTime = frames = 0;

                    System.out.println("FPS: " + fps);
                }
            }

            if (render) {
                jgRenderer.clear();
                gWindow.update();
                frames++;
                //TODO Render
            }
            else try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        dispose();
    }

    private void dispose() {
        //TODO: Dispose Game
    }

    public static void main(String[] args) {
        new JEngine().start();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GWindow getGWindow() {
        return gWindow;
    }
}
