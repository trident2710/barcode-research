package com.trident.barcode.research.image;

import com.trident.barcode.research.BarcodeCodec;

public class BarcodeImageGenerator {

    private final BarcodeCodec codec;
    private final BarcodeBitmapGenerator bitmapGenerator;
    private final BitmapImageWriter imageWriter;
    private final BarcodeParams params;

    public BarcodeImageGenerator(BarcodeCodec codec,
                                 BarcodeBitmapGenerator bitmapGenerator,
                                 BitmapImageWriter imageWriter,
                                 BarcodeParams params) {
        this.codec = codec;
        this.bitmapGenerator = bitmapGenerator;
        this.imageWriter = imageWriter;
        this.params = params;
    }

    public void generateBarcodeImage(String message) {
        var code = codec.encode(message);
        var bitmap = bitmapGenerator.generateBarcodeBitmap(code, params);
        imageWriter.write(bitmap);
    }
}
