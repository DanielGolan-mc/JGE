package net.danielgolan.j2de.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GWindow {
    public final JFrame J_FRAME;
    private final BufferedImage jImage;
    private final Canvas canvas;
    private final BufferStrategy bufferStrategy;
    private final Graphics g;

    public GWindow(JEngine jEngine){
        Dimension s = new Dimension((int) (jEngine.getWidth() * jEngine.getScale()), (int) (jEngine.getHeight() * jEngine.getScale()));
        jImage = new BufferedImage(jEngine.getWidth(), jEngine.getHeight(), BufferedImage.TYPE_INT_RGB);
        J_FRAME = new JFrame(jEngine.getTitle());
        canvas = new Canvas();

        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);

        J_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        J_FRAME.setLayout(new BorderLayout());
        J_FRAME.add(canvas, BorderLayout.CENTER);
        J_FRAME.pack();
        J_FRAME.setLocationRelativeTo(null);
        J_FRAME.setResizable(false);

        J_FRAME.setVisible(true);

        canvas.createBufferStrategy(2);

        g = (bufferStrategy = canvas.getBufferStrategy()).getDrawGraphics();
    }

    public void update(){
        g.drawImage(jImage, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        bufferStrategy.show();
    }

    public BufferedImage getJImage() {
        return jImage;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
