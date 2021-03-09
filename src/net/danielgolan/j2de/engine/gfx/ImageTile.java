package net.danielgolan.j2de.engine.gfx;

public class ImageTile extends GImage{
    private int tileWidth, tileHeight;

    public ImageTile(String path, int tileWidth, int tileHeight) {
        super(path);

        setTileWidth(tileWidth);
        setTileHeight(tileHeight);
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
