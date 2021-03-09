package net.danielgolan.j2de.engine.gfx;

public class ImageTile extends GImage{
    private int tileWidth, tileHeight;

    public ImageTile(String path, int tileWidth, int tileHeight) {
        super(path);

        setTileWidth(tileWidth);
        setTileHeight(tileHeight);
    }

    public GImage getTile(int tileX, int tileY) throws IndexOutOfBoundsException{
        int[] pixels = new int[getTileWidth() * getTileHeight()];

        for (int x = 0; x < tileWidth; x++)
            for (int y = 0; y < tileHeight; y++)
                pixels[x + y * tileWidth] = getPixels()[(x + tileX * tileWidth) + (y + tileY * tileHeight) * this.getWidth()];

        return new GImage(pixels, getTileWidth(), getTileHeight());
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }
}
