package com.trident.barcode.image;

import com.trident.barcode.codec.BarcodeCodecs;

public final class BarcodeImageGenerators {

    public static BarcodeImageGenerator base59Simple(String path) {
        return new BarcodeImageGenerator(
                BarcodeCodecs.BASE_59_SIMPLE,
                new BarcodeBitmapGenerator(),
                new BitmapImageWriterImpl(ColorSchemes.DEFAULT_5_COLORS, path),
                ImmutableBarcodeParams.of(9, 30)
        );
    }

    public static BarcodeImageGenerator base59Rs(String path) {
        return new BarcodeImageGenerator(
                BarcodeCodecs.BASE_59_RS,
                new BarcodeBitmapGenerator(),
                new BitmapImageWriterImpl(ColorSchemes.DEFAULT_5_COLORS, path),
                ImmutableBarcodeParams.of(9, 30)
        );
    }

    public static BarcodeImageGenerator base41Rs(String path) {
        return new BarcodeImageGenerator(
                BarcodeCodecs.BASE_41_RS,
                new BarcodeBitmapGenerator(),
                new BitmapImageWriterImpl(ColorSchemes.DEFAULT_5_COLORS, path),
                ImmutableBarcodeParams.of(9, 30)
        );
    }

    public static BarcodeImageGenerator base41SBRSP(String path) {
        return new BarcodeImageGenerator(
                BarcodeCodecs.BASE_41_SBRSP,
                new BarcodeBitmapGenerator(),
                new BitmapImageWriterImpl(ColorSchemes.DEFAULT_5_COLORS, path),
                ImmutableBarcodeParams.of(9, 30)
        );
    }

    private BarcodeImageGenerators() {
    }
}
