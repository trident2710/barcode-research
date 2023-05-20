package com.trident.barcode.image;

import java.awt.image.BufferedImage;

public class BitmapBufferedImageGeneratorImpl implements BitmapBufferedImageGenerator {

    private final ColorScheme colorScheme;

    public BitmapBufferedImageGeneratorImpl(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }

    @Override
    public BufferedImage generate(Bitmap bitmap) {
        return toBufferedImage(bitmap);
    }

    private BufferedImage toBufferedImage(Bitmap bitmap) {
        int[][] data = bitmap.data();
        int rows = data.length;
        int columns = data[0].length;
        final BufferedImage res = new BufferedImage(rows, columns, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                res.setRGB(x, y, colorScheme.color(data[x][y]).getRGB());
            }
        }
        return res;
    }
}
