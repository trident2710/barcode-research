package com.trident.math.reedsolomon;

import com.trident.math.field.GFP;
import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF11;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.generatorPolynomial;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReedSolomonGeneratorPolynomialsTest {

    @Test
    void testGF11R6() {
        var expected = toFieldMatrixRow(new long[]{2, 8, 2, 7, 5, 6, 1}, GF11);
        assertEquals(expected, generatorPolynomial(GF11.primitiveElement(), 6));
    }

    @Test
    void testGF59R6() {
        var gf59 = GFP.of(59);
        var expected = toFieldMatrixRow(new long[]{56, 26, 42, 46, 16, 51, 1}, gf59);
        assertEquals(expected, generatorPolynomial(gf59.primitiveElement(), 6));
    }

    @Test
    void testGF41R6() {
        var gf41 = GFP.of(41);
        var expected = toFieldMatrixRow(new long[]{34, 24, 13, 34, 8, 12, 1}, gf41);
        assertEquals(expected, generatorPolynomial(gf41.primitiveElement(), 6));
    }


}