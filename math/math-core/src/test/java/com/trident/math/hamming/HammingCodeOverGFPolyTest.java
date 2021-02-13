package com.trident.math.hamming;

import com.trident.math.field.GaloisFieldOverPoly;
import com.trident.math.field.GaloisFieldOverPolyElement;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF_2_2;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrix;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingCodeOverGFPolyTest {

    // H(7, 4)
    // 0 0 0 0 1 0 0
    // 1 1 1 0 0 1 0
    // 1 2 3 1 0 0 1
    private static final FieldMatrix<GaloisFieldOverPolyElement> GENERATOR =
            toFieldMatrix(new long[][]{
                    new long[]{0, 0, 0, 1},
                    new long[]{1, 1, 1, 0},
                    new long[]{1, 2, 3, 1}
            }, GF_2_2);


    private static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_CODE = new HammingCode<>(GENERATOR);

    @Test
    void testEncode() {

        var message = toFieldMatrixRow(new long[]{0, 1, 2, 3}, GF_2_2);
        var code = HAMMING_CODE.encode(message);
        var expected = toFieldMatrixRow(new long[]{0, 1, 2, 3, 3, 3, 0}, GF_2_2);

        assertEquals(expected, code);
    }

    @Test
    void testSyndromeCorrect() {
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 3}, GF_2_2);
        var code = HAMMING_CODE.encode(message);
        var syndrome = HAMMING_CODE.syndrome(code);
        var expected = toFieldMatrixRow(new long[]{0, 0, 0}, GF_2_2);

        assertEquals(expected, syndrome.getSyndromeRow());
    }

    @Test
    void testSyndromeError() {
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 3}, GF_2_2);
        var code = HAMMING_CODE.encode(message);
        var error = toFieldMatrixRow(new long[]{1, 0, 0, 0, 0, 0, 0}, GF_2_2);
        var codeWithError = code.add(error);
        var syndrome = HAMMING_CODE.syndrome(codeWithError);
        var expectedSyndrome = toFieldMatrixRow(new long[]{0, 1, 1}, GF_2_2);


        assertEquals(expectedSyndrome, syndrome.getSyndromeRow());
    }
}
