package com.trident.math.reedsolomon;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF11;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.GF_11_R6;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReedSolomonCodeTest {

    @Test
    void test() {
        var message = toFieldMatrixRow(new long[]{1, 5, 3}, GF11);
        var expected = toFieldMatrixRow(new long[]{2, 7, 4, 8, 2, 8, 2, 1, 3}, GF11);
        var code = new ReedSolomonCode(GF_11_R6);

        assertEquals(expected, code.encode(message));
    }

}