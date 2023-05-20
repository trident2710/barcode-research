package com.trident.barcode.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BufferedImageFileWriter {

    private final String path;

    public BufferedImageFileWriter(String path) {
        this.path = path;
    }

    public void write(BufferedImage image) {
        savePNG(image, path);
    }

    private static void savePNG(final BufferedImage bi, final String path) {
        try {
            ImageIO.write(bi, "bmp", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
