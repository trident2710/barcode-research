package com.trident.barcode;

import com.trident.barcode.model.BarcodeDictionaries;
import com.trident.barcode.padding.NearestSquarePaddingStrategy;

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
