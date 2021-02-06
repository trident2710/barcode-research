package com.trident.math.matrix;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFieldOverPoly.GF9;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrix;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GaloisFieldMatrixUtilTest {

    @Test
    void testToFieldMatrix() {
        var expected = createMatrixOfRows(
                matrixRow(GF9.getOfValue(1), GF9.getOfValue(3), GF9.getOfValue(7), GF9.getOfValue(8), GF9.getOfValue(2), GF9.getOfValue(6), GF9.getOfValue(5), GF9.getOfValue(4)),
                matrixRow(GF9.getOfValue(1), GF9.getOfValue(7), GF9.getOfValue(2), GF9.getOfValue(5), GF9.getOfValue(1), GF9.getOfValue(7), GF9.getOfValue(2), GF9.getOfValue(5)),
                matrixRow(GF9.getOfValue(1), GF9.getOfValue(8), GF9.getOfValue(5), GF9.getOfValue(3), GF9.getOfValue(2), GF9.getOfValue(4), GF9.getOfValue(7), GF9.getOfValue(6))
        );

        var actual = toFieldMatrix(new long[][]{
                new long[]{1, 3, 7, 8, 2, 6, 5, 4},
                new long[]{1, 7, 2, 5, 1, 7, 2, 5},
                new long[]{1, 8, 5, 3, 2, 4, 7, 6},
        }, GF9);

        assertEquals(expected, actual);
    }
}
