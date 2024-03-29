package com.trident.barcode.reedsolomon;

import com.trident.barcode.model.ImmutableCode;
import com.trident.math.reedsolomon.ReedSolomonCode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.barcode.correction.CorrectionStrategies.BCH_9_3;
import static com.trident.barcode.model.BarcodeDictionaries.BASE_41;
import static com.trident.barcode.model.BarcodeDictionaries.BASE_59;
import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.GF_41_R6;
import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.GF_59_R6;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReedSolomonDecoderTest {

    @Test
    void testBase59() {
        var code = ImmutableCode.of(List.of(
                2, 2, 2, 1, 2, 3, 2, 3, 3,
                0, 3, 2, 0, 2, 0, 1, 1, 3,
                1, 1, 3, 0, 2, 0, 3, 3, 0,
                0, 3, 2, 0, 2, 0, 1, 1, 3,
                2, 3, 3, 2, 2, 1, 2, 0, 0,
                0, 3, 3, 2, 0, 1, 0, 2, 2,
                0, 1, 3, 0, 3, 0, 2, 2, 1,
                1, 3, 2, 0, 3, 0, 0, 0, 2,
                1, 1, 2, 2, 0, 1, 2, 0, 1,
                0, 2, 3, 3, 2, 2, 1, 2, 0,
                2, 1, 2, 2, 3, 1, 1, 3, 2,
                1, 3, 0, 3, 0, 2, 2, 1, 0,
                0, 0, 3, 1, 1, 3, 3, 2, 3,
                1, 3, 3, 2, 1, 1, 1, 3, 3,
                2, 0, 1, 2, 0, 1, 3, 1, 3,
                1, 0, 1, 2, 3, 1, 0, 2, 0,
                0, 1, 3, 0, 3, 0, 2, 2, 1,
                2, 2, 0, 2, 1, 1, 0, 2, 1,
                2, 0, 2, 3, 1, 2, 0, 3, 0,
                3, 0, 1, 2, 1, 1, 2, 0, 2,
                2, 0, 2, 3, 1, 2, 0, 3, 0,
                1, 0, 0, 0, 1, 0, 1, 1, 1,
                3, 3, 1, 1, 0, 3, 1, 0, 3,
                0, 2, 1, 0, 1, 0, 3, 3, 2,
                3, 3, 2, 0, 1, 0, 2, 2, 0
        ));


        var decoder = new ReedSolomonDecoder(BASE_59, new ReedSolomonCode(GF_59_R6), BCH_9_3);

        assertEquals("HeLo, Світ!", decoder.decode(code));
    }

    @Test
    void testBase41() {
        var code = ImmutableCode.of(List.of(
                2, 1, 3, 0, 1, 0, 0, 0, 3,
                1, 0, 1, 2, 3, 1, 0, 2, 0,
                1, 2, 3, 3, 3, 2, 0, 3, 1,
                2, 3, 0, 3, 3, 2, 1, 2, 3,
                1, 1, 2, 2, 0, 1, 2, 0, 1,
                2, 0, 3, 1, 3, 3, 1, 0, 1,
                2, 0, 3, 1, 3, 3, 1, 0, 1,
                2, 1, 1, 3, 2, 2, 2, 1, 1,
                1, 1, 0, 1, 3, 3, 0, 1, 3,
                0, 2, 0, 2, 3, 1, 2, 0, 3,
                0, 2, 0, 2, 3, 1, 2, 0, 3,
                3, 3, 2, 0, 1, 0, 2, 2, 0,
                1, 0, 3, 1, 0, 3, 2, 3, 2,
                1, 2, 0, 2, 2, 1, 3, 1, 2,
                0, 1, 1, 3, 0, 2, 0, 3, 3,
                3, 3, 2, 0, 1, 0, 2, 2, 0
        ));

        var decoder = new ReedSolomonDecoder(BASE_41, new ReedSolomonCode(GF_41_R6), BCH_9_3);

        assertEquals("52AM'Ю", decoder.decode(code));
    }

}