package com.trident.math;


import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrix;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixColumn;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixTest {

    @Test
    void testMultiply() {
        var row = toFieldMatrixRow(new long[]{1, 2, 3}, GF5);
        var column = toFieldMatrixColumn(new long[]{2, 2, 2}, GF5);
        var multiply = row.multiply(column);
        var expected = toFieldMatrixRow(new long[]{2}, GF5);
        assertEquals(expected, multiply);
    }

    @Test
    void testTranspose() {

        var matrix = toFieldMatrix(new long[][]{
                new long[]{1, 2},
                new long[]{3, 4}
        }, GF5);

        var expected = toFieldMatrix(new long[][]{
                new long[]{1, 3},
                new long[]{2, 4}
        }, GF5);

        assertEquals(expected, matrix.transpose());
    }
}
