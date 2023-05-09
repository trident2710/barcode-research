package com.trident.barcode.research;

import com.trident.barcode.research.model.BarcodeDictionaries;

public class BarcodeCodecFactory {

    public static BarcodeCodec BASE59_SQUARED = new BarcodeCodecImpl(
            new DefaultBarcodeEncoder(
                    new DefaultIntermediateCodeGenerator(
                            BarcodeDictionaries.BASE_59,
                            new NearestSquarePaddingStrategy()
                    )
            ),
            x -> null
    );
}
