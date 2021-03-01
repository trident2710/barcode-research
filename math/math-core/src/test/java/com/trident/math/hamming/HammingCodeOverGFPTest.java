package com.trident.math.hamming;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.hamming.HammingCodes.HAMMING_5_3_GF_5;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HammingCodeOverGFPTest {

    @Test
    void testEncode() {
        var message = toFieldMatrixRow(new long[]{1, 2, 3}, GF5);
        var code = HAMMING_5_3_GF_5.encode(message);
        var expected = toFieldMatrixRow(new long[]{1, 2, 3, 1, 4}, GF5);

        assertEquals(expected, code);
    }

    @Test
    void testSyndromeCorrect() {
        var message = toFieldMatrixRow(new long[]{1, 2, 3}, GF5);
        var code = HAMMING_5_3_GF_5.encode(message);
        var syndrome = HAMMING_5_3_GF_5.syndrome(code);
        var expected = toFieldMatrixRow(new long[]{0, 0}, GF5);

        assertEquals(expected, syndrome.getSyndromeRow());
    }

    @Test
    void testSyndromeError() {
        var message = toFieldMatrixRow(new long[]{1, 2, 3}, GF5);
        var code = HAMMING_5_3_GF_5.encode(message);
        var error = toFieldMatrixRow(new long[]{1, 0, 0, 0, 0}, GF5);
        var codeWithError = code.add(error);
        var syndrome = HAMMING_5_3_GF_5.syndrome(codeWithError);
        var expectedSyndrome = toFieldMatrixRow(new long[]{1, 1}, GF5);

        assertEquals(expectedSyndrome, syndrome.getSyndromeRow());
    }
}