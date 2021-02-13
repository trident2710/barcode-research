package com.trident.math.hamming;

import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HammingCodeOverGFPrimeTest {
    private static final GaloisFieldOverPrimeElement ZERO = GF5.getZero();
    private static final GaloisFieldOverPrimeElement ONE = GF5.getOne();
    private static final GaloisFieldOverPrimeElement TWO = GF5.getOfValue(2);
    private static final GaloisFieldOverPrimeElement THREE = GF5.getOfValue(3);
    private static final GaloisFieldOverPrimeElement FOUR = GF5.getOfValue(4);

    private static final FieldMatrix<GaloisFieldOverPrimeElement> GENERATOR = createMatrixOfRows(
            matrixRow(ONE, ONE, ONE),
            matrixRow(ONE, TWO, THREE)
    );

    private static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_CODE = new HammingCode<>(GENERATOR);

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

        assertEquals(expected, syndrome.getSyndromeRow());
    }

    @Test
    void testSyndromeError() {
        var message = matrixRow(ONE, TWO, THREE);
        var code = HAMMING_CODE.encode(message);
        var error = matrixRow(ONE, ZERO, ZERO, ZERO, ZERO);
        var codeWithError = code.add(error);
        var syndrome = HAMMING_CODE.syndrome(codeWithError);
        var expectedSyndrome = matrixRow(ONE, ONE);

        assertEquals(expectedSyndrome, syndrome.getSyndromeRow());
    }
}