package com.trident.math.bch;

import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
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

        var expectedSyndrome = BCHCodeSyndrome.of(encoded, new Array2DRowFieldMatrix<>(GF_3_2, 1, 4));
        assertEquals(expectedSyndrome, BCH_8_3.syndrome(encoded));
    }
}