package com.trident.math.bch;

import org.junit.jupiter.api.Test;

import static com.trident.math.bch.BCHCodes.BCH_GF4_11_5;
import static com.trident.math.field.GaloisFields.GF_2_2;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BCH_GF4_11_5Test {

    @Test
    void testEncode() {
        var message = toFieldMatrixRow(new long[]{2, 0, 1, 3, 1}, GF_2_2);
        var encoded = BCH_GF4_11_5.encode(message);
        var expected = toFieldMatrixRow(new long[]{2, 3, 2, 3, 0, 3, 2, 1, 2, 0, 1}, GF_2_2);
        assertEquals(expected, encoded);

        var syndrome = BCH_GF4_11_5.syndrome(encoded);
        assertFalse(syndrome.hasError());
    }

    @Test
    void testSingleError() {
        var codeWithError = toFieldMatrixRow(new long[]{2, 3, 1, 3, 0, 3, 2, 1, 2, 0, 1}, GF_2_2);
        var syndrome = BCH_GF4_11_5.syndrome(codeWithError);
        var expectedCorrection = toFieldMatrixRow(new long[]{0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0}, GF_2_2);
        assertEquals(expectedCorrection, syndrome.getCorrectionResult().getCorrection());
    }

    @Test
    void testDoubleError() {
        var codeWithError = toFieldMatrixRow(new long[]{2, 1, 2, 3, 0, 2, 2, 1, 2, 0, 1}, GF_2_2);
        var syndrome = BCH_GF4_11_5.syndrome(codeWithError);
        var expectedCorrection = toFieldMatrixRow(new long[]{0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0}, GF_2_2);
        assertEquals(expectedCorrection, syndrome.getCorrectionResult().getCorrection());
    }
}
