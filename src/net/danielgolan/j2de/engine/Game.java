package net.danielgolan.j2de.engine;

public abstract class Game {
    public abstract void update(JEngine jEngine, float deltaTIme);
    public abstract void render(JEngine jEngine, JGRenderer jgRenderer);
}
