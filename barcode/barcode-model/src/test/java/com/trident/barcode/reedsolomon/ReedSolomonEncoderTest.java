package com.trident.barcode.reedsolomon;

import com.trident.barcode.model.BarcodeDictionaries;
import com.trident.barcode.model.ImmutableCode;
import com.trident.math.reedsolomon.ReedSolomonCode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.GF_59_R6;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReedSolomonEncoderTest {
    @Test
    void test() {
        var actual = new ReedSolomonEncoder(BarcodeDictionaries.BASE_59,
                new ReedSolomonCode(GF_59_R6))
                .encode("HeLo, Світ!");

        assertEquals(ImmutableCode.of(List.of(
                3, 3, 2, 3, 2, 1, 2, 2, 2,
                3, 1, 1, 0, 2, 0, 2, 3, 0,
                0, 3, 3, 0, 2, 0, 3, 1, 1,
                3, 1, 1, 0, 2, 0, 2, 3, 0,
                0, 0, 2, 1, 2, 2, 3, 3, 2,
                2, 2, 0, 1, 0, 2, 3, 3, 0,
                1, 2, 2, 0, 3, 0, 3, 1, 0,
                2, 0, 0, 0, 3, 0, 2, 3, 1,
                1, 0, 2, 1, 0, 2, 2, 1, 1,
                0, 2, 1, 2, 2, 3, 3, 2, 0,
                2, 3, 1, 1, 3, 2, 2, 1, 2,
                0, 1, 2, 2, 0, 3, 0, 3, 1,
                3, 2, 3, 3, 1, 1, 3, 0, 0,
                3, 3, 1, 1, 1, 2, 3, 3, 1,
                3, 1, 3, 1, 0, 2, 1, 0, 2,
                0, 2, 0, 1, 3, 2, 1, 0, 1,
                1, 2, 2, 0, 3, 0, 3, 1, 0,
                1, 2, 0, 1, 1, 2, 0, 2, 2,
                0, 3, 0, 2, 1, 3, 2, 0, 2,
                2, 0, 2, 1, 1, 2, 1, 0, 3,
                0, 3, 0, 2, 1, 3, 2, 0, 2,
                1, 1, 1, 0, 1, 0, 0, 0, 1,
                3, 0, 1, 3, 0, 1, 1, 3, 3,
                2, 3, 3, 0, 1, 0, 1, 2, 0,
                0, 2, 2, 0, 1, 0, 2, 3, 3
        )), actual);
    }

}