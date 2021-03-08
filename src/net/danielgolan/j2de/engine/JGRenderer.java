package net.danielgolan.j2de.engine;

import net.danielgolan.j2de.engine.gfx.GImage;

import java.awt.image.DataBufferInt;

public class JGRenderer {
    private final int pixelWidth, pixelHeight;
    private final int[] pixels;
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

    public void setPixel(int x, int y, int value) {
        if ((x < 0 || x >= pixelWidth || y < 0 || y >= pixelHeight) || value == 0xffff00ff){
            return;
        }

        pixels[x + y * pixelWidth] = value;
    }

    public void drawImage(GImage image, int offX, int offY){
        for (int x = 0; x < image.getWidth(); x++)
            for (int y = 0; y < image.getHeight(); y++)
                setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getWidth()]);
    }
}
