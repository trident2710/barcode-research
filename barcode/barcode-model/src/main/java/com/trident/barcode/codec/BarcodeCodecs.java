package com.trident.barcode.codec;

import com.trident.barcode.correction.CorrectionStrategies;
import com.trident.barcode.model.BarcodeDictionaries;
import com.trident.barcode.reedsolomon.RSCodingStrategy;
import com.trident.barcode.reedsolomon.ReedSolomonDecoder;
import com.trident.barcode.reedsolomon.SBRSPCodingStrategy;
import com.trident.math.reedsolomon.ReedSolomonCode;

import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.GF_41_R6;
import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.GF_59_R6;

public final class BarcodeCodecs {

    public static BarcodeCodec BASE_59_SIMPLE = new BarcodeCodecImpl(
            new BarcodeEncoderImpl(BarcodeDictionaries.BASE_59, new SimpleCodingStrategy()),
            new SimpleDecoder(BarcodeDictionaries.BASE_59, CorrectionStrategies.BCH_9_3)
    );

    public static BarcodeCodec BASE_59_RS = new BarcodeCodecImpl(
            new BarcodeEncoderImpl(
                    BarcodeDictionaries.BASE_59,
                    new RSCodingStrategy(new ReedSolomonCode(GF_59_R6))),
            new ReedSolomonDecoder(BarcodeDictionaries.BASE_59,
                    new ReedSolomonCode(GF_59_R6),
                    CorrectionStrategies.BCH_9_3)
    );

    public static BarcodeCodec BASE_41_RS = new BarcodeCodecImpl(
            new BarcodeEncoderImpl(
                    BarcodeDictionaries.BASE_41,
                    new RSCodingStrategy(new ReedSolomonCode(GF_41_R6))
            ),
            new ReedSolomonDecoder(BarcodeDictionaries.BASE_41,
                    new ReedSolomonCode(GF_41_R6),
                    CorrectionStrategies.BCH_9_3)
    );

    public static BarcodeCodec BASE_41_SBRSP = new BarcodeCodecImpl(
            new BarcodeEncoderImpl(
                    BarcodeDictionaries.BASE_41,
                    new SBRSPCodingStrategy(BarcodeDictionaries.BASE_41, new ReedSolomonCode(GF_41_R6))
            ),
            x -> null
    );
}
