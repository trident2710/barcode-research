package com.trident.barcode.reedsolomon;

import com.trident.barcode.codec.BarcodeEncoderImpl;
import com.trident.barcode.model.BarcodeDictionaries;
import com.trident.barcode.model.ImmutableCode;
import com.trident.math.reedsolomon.ReedSolomonCode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.GF_41_R6;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SBRSPEncoderTest {


    @Test
    void test() {
        var message = "52AM'Ю";

        var encoder = new BarcodeEncoderImpl(BarcodeDictionaries.BASE_41,
                new SBRSPCodingStrategy(BarcodeDictionaries.BASE_41, new ReedSolomonCode(GF_41_R6)));

        var encoded = encoder.encode(message);

        assertEquals(ImmutableCode.of(List.of(
                2, 1, 1, 3, 2, 2, 2, 1, 1,
                2, 0, 2, 3, 1, 2, 0, 3, 0,
                2, 1, 3, 0, 1, 0, 0, 0, 3,
                1, 0, 1, 2, 3, 1, 0, 2, 0,
                1, 2, 3, 3, 3, 2, 0, 3, 1,
                2, 3, 0, 3, 3, 2, 1, 2, 3,
                1, 1, 2, 2, 0, 1, 2, 0, 1,
                2, 0, 3, 1, 3, 3, 1, 0, 1,
                2, 0, 3, 1, 3, 3, 1, 0, 1,
                2, 1, 1, 3, 2, 2, 2, 1, 1,
                3, 3, 1, 1, 0, 3, 1, 0, 3,
                1, 0, 1, 2, 3, 1, 0, 2, 0,
                3, 1, 2, 2, 2, 1, 0, 2, 3,
                1, 0, 1, 2, 3, 1, 0, 2, 0,
                1, 1, 2, 2, 0, 1, 2, 0, 1,
                2, 1, 3, 0, 1, 0, 0, 0, 3
        )), encoded);
    }

}