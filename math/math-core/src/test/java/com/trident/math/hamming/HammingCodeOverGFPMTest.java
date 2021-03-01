package com.trident.math.hamming;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF_2_2;
import static com.trident.math.hamming.HammingCodes.HAMMING_7_4_GF_2_2;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingCodeOverGFPMTest {

    @Test
    void testEncode() {
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 3}, GF_2_2);
        var code = HAMMING_7_4_GF_2_2.encode(message);
        var expected = toFieldMatrixRow(new long[]{0, 1, 2, 3, 3, 3, 0}, GF_2_2);

        assertEquals(expected, code);
    }

    @Test
    void testSyndromeCorrect() {
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 3}, GF_2_2);
        var code = HAMMING_7_4_GF_2_2.encode(message);
        var syndrome = HAMMING_7_4_GF_2_2.syndrome(code);
        var expected = toFieldMatrixRow(new long[]{0, 0, 0}, GF_2_2);

        assertEquals(expected, syndrome.getSyndromeRow());
    }

    @Test
    void testSyndromeError() {
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 3}, GF_2_2);
        var code = HAMMING_7_4_GF_2_2.encode(message);
        var error = toFieldMatrixRow(new long[]{1, 0, 0, 0, 0, 0, 0}, GF_2_2);
        var codeWithError = code.add(error);
        var syndrome = HAMMING_7_4_GF_2_2.syndrome(codeWithError);
        var expectedSyndrome = toFieldMatrixRow(new long[]{0, 1, 1}, GF_2_2);


        assertEquals(expectedSyndrome, syndrome.getSyndromeRow());
    }
}
