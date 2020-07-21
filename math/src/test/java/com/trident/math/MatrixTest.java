package com.trident.math;

import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.apache.commons.math3.linear.SparseFieldMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MatrixTest {
    private static final GaloisFieldOverPrime GF_5 = new GaloisFieldOverPrime(5L);
    private static final GaloisFieldOverPrimeElement ONE = new GaloisFieldOverPrimeElement(GF_5, 1L);
    private static final GaloisFieldOverPrimeElement TWO = new GaloisFieldOverPrimeElement(GF_5, 2L);
    private static final GaloisFieldOverPrimeElement THREE = new GaloisFieldOverPrimeElement(GF_5, 3L);
    private static final GaloisFieldOverPrimeElement FOUR = new GaloisFieldOverPrimeElement(GF_5, 4L);

    @Test
    void testMultiply() {
        var row = new SparseFieldMatrix<>(GF_5, 1, 3);
        row.addToEntry(0, 0, ONE);
        row.addToEntry(0, 1, TWO);
        row.addToEntry(0, 2, THREE);

        var column = new SparseFieldMatrix<>(GF_5, 3, 1);
        column.addToEntry(0, 0, TWO);
        column.addToEntry(1, 0, TWO);
        column.addToEntry(2, 0, TWO);

        var multiply = row.multiply(column);
        var expected = new SparseFieldMatrix<>(GF_5, 1, 1);
        expected.addToEntry(0, 0, TWO);
        Assertions.assertEquals(expected, multiply);
    }

    @Test
    void testTranspose() {
        var matrix = new SparseFieldMatrix<>(GF_5, 2, 2);
        matrix.addToEntry(0, 0, ONE);
        matrix.addToEntry(0, 1, TWO);
        matrix.addToEntry(1, 0, THREE);
        matrix.addToEntry(1, 1, FOUR);

        var expected = new SparseFieldMatrix<>(GF_5, 2, 2);
        expected.addToEntry(0, 0, ONE);
        expected.addToEntry(0, 1, THREE);
        expected.addToEntry(1, 0, TWO);
        expected.addToEntry(1, 1, FOUR);

        Assertions.assertEquals(expected, matrix.transpose());
    }
}
