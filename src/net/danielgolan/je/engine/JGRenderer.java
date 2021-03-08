package net.danielgolan.je.engine;

import java.awt.image.DataBufferInt;

public class JGRenderer {
    private int pixelWidth, pixelHeight;
    private int[] pixels;
    public final boolean BLACK_SCREEN;

    public JGRenderer (JEngine jEngine, boolean blackScreen){
        pixelWidth = jEngine.getWidth();
        pixelHeight = jEngine.getHeight();
        pixels = ((DataBufferInt) jEngine.getGWindow().getJImage().getRaster().getDataBuffer()).getData();

        this.BLACK_SCREEN = blackScreen;
    }

    public JGRenderer (JEngine jEngine){
        this(jEngine, true);
    }

    public void clear(){
        for (int i = 0; i < pixels.length; i++){
            if (BLACK_SCREEN){
                pixels[i] = 0;
            } else {
                pixels[i] += i;
            }
        }
    }
}
