package com.trident.math.io;

import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.FieldMatrix;

public final class FieldMatrixIOUtil {
    private FieldMatrixIOUtil() {
        throw new RuntimeException("Cannot be instantiated");
    }

    public static <T extends FieldElement<T>> String writeAsString(FieldMatrix<T> matrix) {
        final int nRows = matrix.getRowDimension();
        final int nCols = matrix.getColumnDimension();
        final var res = new StringBuilder();
        res.append("(");
        for (int i = 0; i < nRows; ++i) {
            if (i > 0) {
                res.append("\n ");
            }
            for (int j = 0; j < nCols; ++j) {
                if (j > 0) {
                    res.append(", ");
                }
                res.append(matrix.getEntry(i, j));
            }
        }
        res.append(")");
        return res.toString();
    }
}
