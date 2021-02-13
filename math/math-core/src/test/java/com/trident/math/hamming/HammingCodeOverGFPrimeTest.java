package com.trident.math.hamming;

import com.trident.math.field.GaloisFieldOverPolyElement;
import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.field.GaloisFields.GF_2_2;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrix;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HammingCodeOverGFPrimeTest {

    private static final FieldMatrix<GaloisFieldOverPrimeElement> GENERATOR =
            toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1},
                    new long[]{1, 2, 3}
            }, GF5);

    private static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_CODE = new HammingCode<>(GENERATOR);

    @Test
    void testEncode() {
        var message = toFieldMatrixRow(new long[]{1, 2, 3}, GF5);
        var code = HAMMING_CODE.encode(message);
        var expected = toFieldMatrixRow(new long[]{1, 2, 3, 1, 4}, GF5);

        assertEquals(expected, code);
    }

    @Test
    void testSyndromeCorrect() {
        var message = toFieldMatrixRow(new long[]{1, 2, 3}, GF5);
        var code = HAMMING_CODE.encode(message);
        var syndrome = HAMMING_CODE.syndrome(code);
        var expected = toFieldMatrixRow(new long[]{0, 0}, GF5);

        assertEquals(expected, syndrome.getSyndromeRow());
    }

    @Test
    void testSyndromeError() {
        var message = toFieldMatrixRow(new long[]{1, 2, 3}, GF5);
        var code = HAMMING_CODE.encode(message);
        var error = toFieldMatrixRow(new long[]{1, 0, 0, 0, 0}, GF5);
        var codeWithError = code.add(error);
        var syndrome = HAMMING_CODE.syndrome(codeWithError);
        var expectedSyndrome = toFieldMatrixRow(new long[]{1, 1}, GF5);

        assertEquals(expectedSyndrome, syndrome.getSyndromeRow());
    }
}