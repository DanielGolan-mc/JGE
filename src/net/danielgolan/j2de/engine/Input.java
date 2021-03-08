package net.danielgolan.j2de.engine;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public final JEngine J_ENGINE;

    public static final int NUM_KEYS = 256;
    private final boolean[] keys = new boolean[NUM_KEYS];
    private final boolean[] keysLast = new boolean[NUM_KEYS];

    public static final int NUM_BUTTONS = 5;
    private final boolean[] buttons = new boolean[NUM_BUTTONS];
    private final boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    private int mouseX = 0, mouseY = 0,
            scroll = 0;

    public Input(JEngine jEngine) {
        this.J_ENGINE = jEngine;

        jEngine.getGWindow().getCanvas().addKeyListener(this);
        jEngine.getGWindow().getCanvas().addMouseListener(this);
        jEngine.getGWindow().getCanvas().addMouseWheelListener(this);
        jEngine.getGWindow().getCanvas().addMouseMotionListener(this);
    }

    public void update() {
        scroll = 0;

        System.arraycopy(keys, 0, keysLast, 0, NUM_KEYS);
        System.arraycopy(keys, 0, buttonsLast, 0, NUM_BUTTONS);
    }

    public boolean isKey(int keyCode) {
        return keys[keyCode];
    }
    public boolean isKeyUp (int keyCode) {
        return !keys[keyCode] && keysLast[keyCode];
    }
    public boolean isKeyDown (int keyCode) {
        return keys[keyCode] && !keysLast[keyCode];
    }

    public boolean isButton (int button) {
        return buttons[button];
    }
    public boolean isButtonUp (int button) {
        return !buttons[button] && buttonsLast[button];
    }
    public boolean isButtonDown (int button) {
        return buttons[button] && !buttonsLast[button];
    }

    public void keyTyped(KeyEvent e) {

    }
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    public void mouseClicked(MouseEvent e) {

    }
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }
    public void mouseDragged(MouseEvent e) {
        mouseX = (int) (e.getX() / J_ENGINE.getScale());
        mouseY = (int) (e.getY() / J_ENGINE.getScale());
    }
    public void mouseMoved(MouseEvent e) {
        mouseX = (int) (e.getX() / J_ENGINE.getScale());
        mouseY = (int) (e.getY() / J_ENGINE.getScale());
    }
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getScroll() {
        return scroll;
    }
}
