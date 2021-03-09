package net.danielgolan.j2de.engine.gfx;

public class ImageRequest {
    private final GImage image;
    private final int zDepth, offX, offY;

    public ImageRequest(GImage image, int zDepth, int offX, int offY) {
        this.image = image;
        this.zDepth = zDepth;
        this.offX = offX;
        this.offY = offY;
    }

    public GImage getImage() {
        return image;
    }

    public int getZDepth() {
        return zDepth;
    }

    public int getOffX() {
        return offX;
    }

    public int getOffY() {
        return offY;
    }
}
