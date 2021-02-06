package com.trident.math.matrix;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFieldOverPoly.GF9;
import static com.trident.math.field.GaloisFieldOverPrime.GF5;
import static com.trident.math.matrix.FieldMatrixUtil.concatBottom;
import static com.trident.math.matrix.FieldMatrixUtil.concatRight;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static com.trident.math.matrix.FieldMatrixUtil.shiftRowRight;
import static com.trident.math.matrix.FieldMatrixUtil.toFieldMatrix;
import static org.apache.commons.math3.linear.MatrixUtils.createFieldIdentityMatrix;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FieldMatrixUtilTest {

    @Test
    void testConcatRight() {
        var first = createFieldIdentityMatrix(GF5, 5);
        var second = createFieldIdentityMatrix(GF5, 5);

        var result = concatRight(first, second);

        for (int i = 0; i < first.getRowDimension(); i++) {
            for (int j = 0; j < first.getColumnDimension() + second.getColumnDimension(); j++) {
                if (i == j || (j >= first.getColumnDimension() && i == j - first.getColumnDimension())) {
                    assertEquals(GF5.getOne(), result.getEntry(i, j));
                } else {
                    assertEquals(GF5.getZero(), result.getEntry(i, j));
                }
            }
        }
    }

    @Test
    void testConcatBottom() {
        var first = createFieldIdentityMatrix(GF5, 5);
        var second = createFieldIdentityMatrix(GF5, 5);

        var result = concatBottom(first, second);

        for (int i = 0; i < first.getRowDimension() + second.getColumnDimension(); i++) {
            for (int j = 0; j < first.getColumnDimension(); j++) {
                if (i == j || (i >= first.getRowDimension() && i - first.getRowDimension() == j)) {
                    assertEquals(GF5.getOne(), result.getEntry(i, j));
                } else {
                    assertEquals(GF5.getZero(), result.getEntry(i, j));
                }
            }
        }
    }

    @Test
    void testShiftRowRight() {
        var row = matrixRow(GF5.getOne(), GF5.getOfValue(2), GF5.getOfValue(3), GF5.getOfValue(4));

        assertThrows(Exception.class, () -> shiftRowRight(row, 0));
        assertThrows(Exception.class, () -> shiftRowRight(row, 4));

        var shiftedByOne = shiftRowRight(row, 1);
        var expected = matrixRow(GF5.getZero(), GF5.getOne(), GF5.getOfValue(2), GF5.getOfValue(3));
        assertEquals(expected, shiftedByOne);

        var shiftedByThree = shiftRowRight(row, 3);
        expected = matrixRow(GF5.getZero(), GF5.getZero(), GF5.getZero(), GF5.getOne());
        assertEquals(expected, shiftedByThree);
    }

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