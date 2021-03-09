package net.danielgolan.j2de.engine.gfx;

public class Font {
    public static final Font STANDARD = new Font("/assets/fonts/standard.png", 59);

    public final GImage FONT_IMAGE;
    public final int[] OFFSETS;
    public final int[] WIDTHS;

    public Font(String path, int letters){
        this.FONT_IMAGE = new GImage(path);
        this.OFFSETS = new int[letters];
        this.WIDTHS = new int[letters];

        for (int i = 0, unicode = 0; i < FONT_IMAGE.getWidth(); i++) {
            if (FONT_IMAGE.getPixels()[i] == 0xff0000ff){
                OFFSETS[unicode] = i;
            }

            if (FONT_IMAGE.getPixels()[i] == 0xffffff00){
                WIDTHS[unicode] = i - OFFSETS[unicode];
                unicode++;
            }
        }
    }
}
