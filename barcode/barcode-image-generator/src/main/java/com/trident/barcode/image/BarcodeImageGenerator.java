package com.trident.barcode.image;

import com.trident.barcode.codec.BarcodeCodec;

import java.awt.image.BufferedImage;

public class BarcodeImageGenerator {

    private final BarcodeCodec codec;
    private final BarcodeBitmapGenerator bitmapGenerator;
    private final BitmapBufferedImageGenerator bufferedImageGenerator;
    private final BarcodeParams params;

    public BarcodeImageGenerator(BarcodeCodec codec,
                                 BarcodeBitmapGenerator bitmapGenerator,
                                 BitmapBufferedImageGenerator bufferedImageGenerator,
                                 BarcodeParams params) {
        this.codec = codec;
        this.bitmapGenerator = bitmapGenerator;
        this.bufferedImageGenerator = bufferedImageGenerator;
        this.params = params;
    }

    public BufferedImage generateBarcodeImage(String message) {
        var code = codec.encode(message);
        var bitmap = bitmapGenerator.generateBarcodeBitmap(code, params);
        return bufferedImageGenerator.generate(bitmap);
    }
}
