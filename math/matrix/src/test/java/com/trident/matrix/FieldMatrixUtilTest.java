package com.trident.matrix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.trident.math.field.GaloisFieldOverPrime;
import org.junit.jupiter.api.Test;

class FieldMatrixUtilTest {

    private static final GaloisFieldOverPrime GF5 = new GaloisFieldOverPrime(5);

    @Test
    void testIdentity() {
        var identity = FieldMatrixUtil.identityMatrix(GF5.getOne(), 10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == j) {
                    assertEquals(GF5.getOne(), identity.getEntry(i, j));
                } else {
                    assertEquals(GF5.getZero(), identity.getEntry(i, j));
                }
            }
        }
    }

    @Test
    void testConcatRight() {
        var first = FieldMatrixUtil.identityMatrix(GF5.getOne(), 5);
        var second = FieldMatrixUtil.identityMatrix(GF5.getOne(), 5);

        var result = FieldMatrixUtil.concatRight(first, second);

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

}