package net.danielgolan.j2de.engine;

public interface Game {
    public abstract void update(JEngine jEngine, float deltaTime);
    public abstract void render(JEngine jEngine, JGRenderer jgRenderer);
}
