package com.trident.math.bch;

import org.junit.jupiter.api.Test;

import static com.trident.math.bch.BCHCodes.BCH_8_3;
import static com.trident.math.field.GaloisFieldOverPrime.GF3;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BCHCodeTest {

    @Test
    void testEncode() {
        var message = toFieldMatrixRow(new long[]{1, 0, 2}, GF3);
        var encoded = BCH_8_3.encode(message);
        var expected = toFieldMatrixRow(new long[]{2, 0, 2, 1, 1, 0, 1, 2}, GF3);
        assertEquals(expected, encoded);
    }
}