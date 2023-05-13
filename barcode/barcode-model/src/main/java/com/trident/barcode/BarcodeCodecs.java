package com.trident.barcode;

import com.trident.barcode.model.BarcodeDictionaries;
import com.trident.barcode.padding.NearestSquarePaddingStrategy;
import com.trident.barcode.reedsolomon.BarcodeWithReedSolomonErrorCorrectionEncoder;
import com.trident.math.reedsolomon.ReedSolomonCode;

import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.GF_59_R6;

public final class BarcodeCodecs {

    public static BarcodeCodec BASE59 = new BarcodeCodecImpl(
            new DefaultBarcodeEncoder(
                    new DefaultIntermediateCodeGenerator(
                            BarcodeDictionaries.BASE_59,
                            new NearestSquarePaddingStrategy()
                    )
            ),
            x -> null
    );

    public static BarcodeCodec BASE59_REED_SOLOMON = new BarcodeCodecImpl(
            new BarcodeWithReedSolomonErrorCorrectionEncoder(
                    BarcodeDictionaries.BASE_59,
                    new ReedSolomonCode(GF_59_R6)
            ),
            x -> null
    );
}
