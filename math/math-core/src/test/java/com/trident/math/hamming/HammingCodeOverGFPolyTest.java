package com.trident.math.hamming;

import com.trident.math.field.GaloisFieldOverPoly;
import com.trident.math.field.GaloisFieldOverPolyElement;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFieldOverPoly.GF4;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingCodeOverGFPolyTest {
    private static final GaloisFieldOverPolyElement ZERO = GF4.getZero();
    private static final GaloisFieldOverPolyElement ONE = GF4.getOne();
    private static final GaloisFieldOverPolyElement TWO = GF4.getOfValue(2);
    private static final GaloisFieldOverPolyElement THREE = GF4.getOfValue(3);

    // H(7, 4)
    // 0 0 0 0 1 0 0
    // 1 1 1 0 0 1 0
    // 1 2 3 1 0 0 1
    private static final FieldMatrix<GaloisFieldOverPolyElement> GENERATOR = createMatrixOfRows(
            matrixRow(ZERO, ZERO, ZERO, ONE),
            matrixRow(ONE, ONE, ONE, ZERO),
            matrixRow(ONE, TWO, THREE, ONE)
    );

    private static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_CODE = new HammingCode<>(GENERATOR);

    @Test
    void testEncode() {
        var message = matrixRow(ZERO, ONE, TWO, THREE);
        var code = HAMMING_CODE.encode(message);
        var expected = matrixRow(ZERO, ONE, TWO, THREE, THREE, THREE, ZERO);

        assertEquals(expected, code);
    }

    @Test
    void testSyndromeCorrect() {
        var message = matrixRow(ZERO, ONE, TWO, THREE);
        var code = HAMMING_CODE.encode(message);
        var syndrome = HAMMING_CODE.syndrome(code);
        var expected = matrixRow(ZERO, ZERO, ZERO);

        assertEquals(expected, syndrome.getSyndromeRow());
    }

    @Test
    void testSyndromeError() {
        var message = matrixRow(ZERO, ONE, TWO, THREE);
        var code = HAMMING_CODE.encode(message);
        var error = matrixRow(ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO);
        var codeWithError = code.add(error);
        var syndrome = HAMMING_CODE.syndrome(codeWithError);
        var expectedSyndrome = matrixRow(ZERO, ONE, ONE);

        assertEquals(expectedSyndrome, syndrome.getSyndromeRow());
    }
}
