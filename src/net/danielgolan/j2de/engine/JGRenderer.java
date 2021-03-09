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
        if (offX < - image.getWidth() || offY < - image.getHeight() || offX >= pixelWidth || offY >= pixelHeight) return;

        int newX = 0, newY = 0, newWidth = image.getWidth(), newHeight = image.getHeight();

        if (offX < 0) newX -= offX;
        if (offY < 0) newY -= offY;
        if (newWidth + offX >= pixelWidth) newWidth -= newWidth + offX - pixelWidth;
        if (newHeight + offY >= pixelHeight) newHeight -= newHeight + offY - pixelHeight;

        for (int x = newX; x < newWidth; x++)
            for (int y = newY; y < newHeight; y++)
                setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getWidth()]);
    }
}
