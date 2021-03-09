package net.danielgolan.j2de.engine;

import net.danielgolan.j2de.engine.gfx.Font;
import net.danielgolan.j2de.engine.gfx.GImage;
import net.danielgolan.j2de.engine.gfx.ImageRequest;
import net.danielgolan.j2de.engine.gfx.ImageTile;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JGRenderer {
    private Font font = Font.STANDARD;
    private final List<ImageRequest> imageRequestList = new ArrayList<>();

    private final int pixelWidth, pixelHeight;
    private final int[] pixels, zBuffer;
    public final boolean BLACK_SCREEN;
    private boolean processing;
    private int zDepth = 0;

    public JGRenderer (JEngine jEngine, boolean blackScreen){
        pixelWidth = jEngine.getWidth();
        pixelHeight = jEngine.getHeight();
        pixels = ((DataBufferInt) jEngine.getGWindow().getJImage().getRaster().getDataBuffer()).getData();
        zBuffer = new int[pixels.length];

        this.BLACK_SCREEN = blackScreen;
    }

    public JGRenderer (JEngine jEngine){
        this(jEngine, true);
    }

    public void clear(){
        for (int i = 0; i < pixels.length; i++){
            if (BLACK_SCREEN){
                pixels[i] = 0;
                zBuffer[i] = 0;
            } else {
                pixels[i] += i;
                zBuffer[i] += i;
            }
        }
    }

    public void process(){
        processing = true;

        imageRequestList.sort(Comparator.comparingInt(ImageRequest::getZDepth));

        imageRequestList.forEach(imageRequest -> drawImage(imageRequest.getImage(), imageRequest.getOffX(), imageRequest.getOffY()));

        imageRequestList.clear();

        processing = false;
    }

    public void setPixel(int x, int y, int value) {
        final int
                ALPHA = ((value >> 24) & 0xff),
                INDEX = x + (y * pixelWidth);

        if ((x < 0 || x >= pixelWidth || y < 0 || y >= pixelHeight) || ALPHA == 0|| zBuffer[INDEX] > zDepth)
            return;

        zBuffer[INDEX] = zDepth;

        if (ALPHA == 255) pixels[INDEX] = value;
        else {
            int pixelColor = pixels[INDEX];

            int newRed = ((pixelColor >> 16) & 0xff) - (int) ((((pixelColor >> 16) & 0xff) - ((value >> 8) & 0xff)) * ALPHA / 255f);
            int newGreen = ((pixelColor >> 8) & 0xff) - (int) ((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * ALPHA / 255f);
            int newBlue = (pixelColor & 0xff) - (int) (((pixelColor & 0xff) - (value & 0xff)) * (ALPHA / 255f));

            pixels[INDEX] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
        }
    }

    public void drawImage(GImage image, int offX, int offY){
        if (image.isAlpha() && !processing){
            imageRequestList.add(new ImageRequest(image, zDepth, offX, offY));
            return;
        }

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

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY){
        if (image.isAlpha() && !processing){
            imageRequestList.add(new ImageRequest(image.getTile(tileX, tileY), zDepth, offX, offY));
            return;
        }

        if (offX < - image.getTileWidth() || offY < - image.getTileHeight() || offX >= pixelWidth || offY >= pixelHeight) return;

        int newX = 0, newY = 0, newWidth = image.getTileWidth(), newHeight = image.getTileHeight();

        if (offX < 0) newX -= offX;
        if (offY < 0) newY -= offY;
        if (newWidth + offX >= pixelWidth) newWidth -= newWidth + offX - pixelWidth;
        if (newHeight + offY >= pixelHeight) newHeight -= newHeight + offY - pixelHeight;

        for (int x = newX; x < newWidth; x++)
            for (int y = newY; y < newHeight; y++)
                setPixel(x + offX, y + offY, image.getPixels()
                        [(x + tileX * image.getTileWidth()) + (y + tileY * image.getTileHeight()) * image.getWidth()]);
    }

    public void drawText(String text, int offX, int offY, int offStart, int color){
        text = text.toUpperCase();
        int offset = 0;

        for (int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i) - offStart;

            for (int y = 0; y < font.FONT_IMAGE.getHeight(); y++) {
                for (int x = 0; x < font.WIDTHS[unicode]; x++) {
                    if (font.FONT_IMAGE.getPixels()[(x + font.OFFSETS[unicode]) + y * font.FONT_IMAGE.getWidth()] == 0xffffffff) {
                        setPixel(x + offX + offset, y + offY, color);
                    }
                }
            }

            offset += font.WIDTHS[unicode];
        }
    }

    public void drawRect(int offX, int offY, int width, int height, int color){
        for (int y = 0; y <= height; y++) {
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
        }
        for (int x = 0; x <= width; x++) {
            setPixel(x + offX, offY, color);
            setPixel(x + offX, offY + height, color);
        }
    }

    public void fillRect(int offX, int offY, int width, int height, int color){
        if (offX < - width || offY < - height || offX >= pixelWidth || offY >= pixelHeight) return;

        int newX = 0, newY = 0, newWidth = width, newHeight = height;

        if (offX < 0) newX -= offX;
        if (offY < 0) newY -= offY;
        if (newWidth + offX >= pixelWidth) newWidth -= newWidth + offX - pixelWidth;
        if (newHeight + offY >= pixelHeight) newHeight -= newHeight + offY - pixelHeight;

        for (int y = newY; y <= newHeight; y++) for (int x = newX; x <= newWidth; x++)
            setPixel(x + offX, y + offY, color);
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getzDepth() {
        return zDepth;
    }

    public void setzDepth(int zDepth) {
        this.zDepth = zDepth;
    }
}
