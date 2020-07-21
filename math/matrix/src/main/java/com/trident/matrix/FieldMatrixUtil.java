package com.trident.matrix;

import com.google.common.base.Preconditions;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.SparseFieldMatrix;

public final class FieldMatrixUtil {
    private FieldMatrixUtil() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    public static <T extends FieldElement<T>> FieldMatrix<T> concatRight(FieldMatrix<T> first, FieldMatrix<T> second) {
        Preconditions.checkArgument(first.getRowDimension() == second.getRowDimension());
        int columns = first.getColumnDimension() + second.getColumnDimension();
        int rows = first.getRowDimension();
        var matrix = new SparseFieldMatrix<>(first.getField(), rows, columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j < first.getColumnDimension()) {
                    matrix.addToEntry(i, j, first.getEntry(i, j));
                } else {
                    matrix.addToEntry(i, j, second.getEntry(i, j - first.getColumnDimension()));
                }
            }
        }
        return matrix;
    }

    public static <T extends FieldElement<T>> FieldMatrix<T> matrixRow(T... elements) {
        return MatrixUtils.createRowFieldMatrix(elements);
    }
}
