package com.trident.math.hamming;

import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.jupiter.api.Test;

class HammingCodeTest {

    private static final GaloisFieldOverPrime GF5 = new GaloisFieldOverPrime(5);
    private static final GaloisFieldOverPrimeElement ZERO = new GaloisFieldOverPrimeElement(GF5, 0L);
    private static final GaloisFieldOverPrimeElement ONE = new GaloisFieldOverPrimeElement(GF5, 1L);
    private static final GaloisFieldOverPrimeElement TWO = new GaloisFieldOverPrimeElement(GF5, 2L);
    private static final GaloisFieldOverPrimeElement THREE = new GaloisFieldOverPrimeElement(GF5, 3L);
    private static final GaloisFieldOverPrimeElement FOUR = new GaloisFieldOverPrimeElement(GF5, 4L);

    private static final FieldMatrix<GaloisFieldOverPrimeElement> GENERATOR = MatrixUtils.createFieldMatrix(new GaloisFieldOverPrimeElement[][]{
            new GaloisFieldOverPrimeElement[]{ONE, ONE, ONE},
            new GaloisFieldOverPrimeElement[]{ONE, TWO, THREE}
    });

    private static final HammingCode<GaloisFieldOverPrimeElement> HAMMING_CODE = new HammingCode<>(GENERATOR);

    @Test
    void testEncode() {
        var message = matrixRow(ONE, TWO, THREE);
        var code = HAMMING_CODE.encode(message);
        var expected = matrixRow(ONE, TWO, THREE, ONE, FOUR);

        assertEquals(expected, code);
    }

    @Test
    void testSyndromeCorrect() {
        var message = matrixRow(ONE, TWO, THREE);
        var code = HAMMING_CODE.encode(message);
        var syndrome = HAMMING_CODE.syndrome(code);
        var expected = matrixRow(ZERO, ZERO);

        assertEquals(expected, syndrome);
    }

    @Test
    void testSyndromeError() {
        var message = matrixRow(ONE, TWO, THREE);
        var code = HAMMING_CODE.encode(message);
        var error = matrixRow(ONE, ZERO, ZERO, ZERO, ZERO);
        var codeWithError = code.add(error);
        var syndrome = HAMMING_CODE.syndrome(codeWithError);
        var expectedSyndrome = matrixRow(ONE, ONE);

        assertEquals(expectedSyndrome, syndrome);
    }
}