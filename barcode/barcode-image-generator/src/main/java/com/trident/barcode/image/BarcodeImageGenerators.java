package com.trident.barcode.image;

import com.trident.barcode.codec.BarcodeCodecs;

public final class BarcodeImageGenerators {

    public static BarcodeImageGenerator base59(String path) {
        return new BarcodeImageGenerator(
                BarcodeCodecs.BASE_59,
                new BarcodeBitmapGenerator(),
                new BitmapImageWriterImpl(ColorSchemes.DEFAULT_5_COLORS, path),
                ImmutableBarcodeParams.of(9, 30)
        );
    }

    public static BarcodeImageGenerator base59ReedSolomon(String path) {
        return new BarcodeImageGenerator(
                BarcodeCodecs.BASE_59_REED_SOLOMON,
                new BarcodeBitmapGenerator(),
                new BitmapImageWriterImpl(ColorSchemes.DEFAULT_5_COLORS, path),
                ImmutableBarcodeParams.of(9, 30)
        );
    }

    private BarcodeImageGenerators() {
    }
}
