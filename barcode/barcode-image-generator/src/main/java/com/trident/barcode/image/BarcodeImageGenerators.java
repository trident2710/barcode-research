package com.trident.barcode.image;

import com.trident.barcode.codec.BarcodeCodecs;

public final class BarcodeImageGenerators {

    public static final BarcodeImageGenerator BASE_59_SIMPLE =
            new BarcodeImageGenerator(
                    BarcodeCodecs.BASE_59_SIMPLE,
                    new BarcodeBitmapGenerator(),
                    new BitmapBufferedImageGeneratorImpl(ColorSchemes.DEFAULT_5_COLORS),
                    ImmutableBarcodeParams.of(9, 30)
            );

    public static final BarcodeImageGenerator BASE_59_RS =
            new BarcodeImageGenerator(
                    BarcodeCodecs.BASE_59_RS,
                    new BarcodeBitmapGenerator(),
                    new BitmapBufferedImageGeneratorImpl(ColorSchemes.DEFAULT_5_COLORS),
                    ImmutableBarcodeParams.of(9, 30)
            );

    public static final BarcodeImageGenerator BASE_41_RS =
            new BarcodeImageGenerator(
                    BarcodeCodecs.BASE_41_RS,
                    new BarcodeBitmapGenerator(),
                    new BitmapBufferedImageGeneratorImpl(ColorSchemes.DEFAULT_5_COLORS),
                    ImmutableBarcodeParams.of(9, 30)
            );

    public static BarcodeImageGenerator BASE_41_SBRSP =
            new BarcodeImageGenerator(
                    BarcodeCodecs.BASE_41_SBRSP,
                    new BarcodeBitmapGenerator(),
                    new BitmapBufferedImageGeneratorImpl(ColorSchemes.DEFAULT_5_COLORS),
                    ImmutableBarcodeParams.of(9, 30)
            );

    private BarcodeImageGenerators() {
    }
}
