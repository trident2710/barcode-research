package com.trident.barcode.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BitmapImageWriterImpl implements BitmapImageWriter {

    private final ColorScheme colorScheme;
    private final String path;

    public BitmapImageWriterImpl(ColorScheme colorScheme, String path) {
        this.colorScheme = colorScheme;
        this.path = path;
    }

    @Override
    public void write(Bitmap bitmap) {
        var image = toBufferedImage(bitmap);
        savePNG(image, path);
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

    private static void savePNG(final BufferedImage bi, final String path) {
        try {
            ImageIO.write(bi, "bmp", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
