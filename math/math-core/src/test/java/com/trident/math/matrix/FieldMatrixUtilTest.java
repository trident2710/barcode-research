package com.trident.math.matrix;

import static com.trident.math.field.GaloisFieldType.GF5;
import static com.trident.math.matrix.FieldMatrixUtil.concatBottom;
import static com.trident.math.matrix.FieldMatrixUtil.concatRight;
import static org.apache.commons.math3.linear.MatrixUtils.createFieldIdentityMatrix;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FieldMatrixUtilTest {

    @Test
    void testConcatRight() {
        var first = createFieldIdentityMatrix(GF5.field(), 5);
        var second = createFieldIdentityMatrix(GF5.field(), 5);

        var result = concatRight(first, second);

        for (int i = 0; i < first.getRowDimension(); i++) {
            for (int j = 0; j < first.getColumnDimension() + second.getColumnDimension(); j++) {
                if (i == j || (j >= first.getColumnDimension() && i == j - first.getColumnDimension())) {
                    assertEquals(GF5.field().getOne(), result.getEntry(i, j));
                } else {
                    assertEquals(GF5.field().getZero(), result.getEntry(i, j));
                }
            }
        }
    }

    @Test
    void testConcatBottom() {
        var first = createFieldIdentityMatrix(GF5.field(), 5);
        var second = createFieldIdentityMatrix(GF5.field(), 5);

        var result = concatBottom(first, second);

        for (int i = 0; i < first.getRowDimension() + second.getColumnDimension(); i++) {
            for (int j = 0; j < first.getColumnDimension(); j++) {
                if (i == j || (i >= first.getRowDimension() && i - first.getRowDimension() == j)) {
                    assertEquals(GF5.field().getOne(), result.getEntry(i, j));
                } else {
                    assertEquals(GF5.field().getZero(), result.getEntry(i, j));
                }
            }
        }
    }

}