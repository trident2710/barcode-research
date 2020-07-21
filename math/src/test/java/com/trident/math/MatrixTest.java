package com.trident.math;

import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixColumn;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.junit.jupiter.api.Test;

class MatrixTest {
    private static final GaloisFieldOverPrime GF5 = new GaloisFieldOverPrime(5);
    private static final GaloisFieldOverPrimeElement ONE = GF5.getOne();
    private static final GaloisFieldOverPrimeElement TWO = GF5.getOfValue(2);
    private static final GaloisFieldOverPrimeElement THREE = GF5.getOfValue(3);
    private static final GaloisFieldOverPrimeElement FOUR = GF5.getOfValue(4);

    @Test
    void testMultiply() {
        var row = matrixRow(ONE, TWO, THREE);
        var column = matrixColumn(TWO, TWO, TWO);
        var multiply = row.multiply(column);
        var expected = matrixRow(TWO);
        assertEquals(expected, multiply);
    }

    @Test
    void testTranspose() {
        var matrix = createMatrixOfRows(
                matrixRow(ONE, TWO),
                matrixRow(THREE, FOUR)
        );

        var expected = createMatrixOfRows(
                matrixRow(ONE, THREE),
                matrixRow(TWO, FOUR)
        );

        assertEquals(expected, matrix.transpose());
    }
}
