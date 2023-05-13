package com.trident.barcode.image;

import com.trident.barcode.BarcodeCodecFactory;

public class BarcodeImageGeneratorFactory {

    public static BarcodeImageGenerator base59Default(String path) {
        return new BarcodeImageGenerator(
                BarcodeCodecFactory.BASE59_SQUARED,
                new BarcodeBitmapGenerator(),
                new BitmapImageWriterImpl(ColorSchemes.DEFAULT_5_COLORS, path),
                ImmutableBarcodeParams.of(9, 30)
        );
    }
}
