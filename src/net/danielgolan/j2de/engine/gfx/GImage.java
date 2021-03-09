package net.danielgolan.j2de.engine.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GImage {
    private int width, height;
    private int[] pixels;
    private boolean alpha = false;

    public GImage (String path){
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(GImage.class.getResourceAsStream(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert image != null;

        width = image.getWidth();
        height = image.getHeight();
        pixels = image.getRGB(0, 0, width, height, null, 0, width);

        image.flush();
    }

    public GImage (int[] pixels, int width, int height){
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public boolean isAlpha() {
        return alpha;
    }
    public void setAlpha(boolean alpha) {
        this.alpha = alpha;
    }
}
