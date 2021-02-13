package com.trident.math.bch;

import org.junit.jupiter.api.Test;

import static com.trident.math.bch.BCHCodes.BCH_8_3;
import static com.trident.math.field.GaloisFields.GF3;
import static com.trident.math.field.GaloisFields.GF_3_2;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BCHCodeTest {

    @Test
    void testEncode() {
        var message = toFieldMatrixRow(new long[]{1, 0, 2}, GF3);
        var encoded = BCH_8_3.encode(message);
        var expected = toFieldMatrixRow(new long[]{2, 0, 2, 1, 1, 0, 1, 2}, GF3);
        assertEquals(expected, encoded);

        var expectedSyndrome = new BCHCodeSyndrome<>(encoded, toFieldMatrixRow(new long[]{0, 0, 0, 0}, GF_3_2));
        assertEquals(expectedSyndrome, BCH_8_3.syndrome(encoded));
    }

    @Test
    void testError() {
        var code_with_error = toFieldMatrixRow(new long[]{1, 0, 2, 2, 1, 0, 1, 2}, GF3);
        var expectedSyndrome = new BCHCodeSyndrome<>(code_with_error, toFieldMatrixRow(new long[]{7, 4, 5, 1}, GF_3_2));
        assertEquals(expectedSyndrome, BCH_8_3.syndrome(code_with_error));
    }
}