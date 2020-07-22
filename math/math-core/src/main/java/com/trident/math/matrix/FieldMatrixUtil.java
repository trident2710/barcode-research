package com.trident.math.matrix;

import com.google.common.base.Preconditions;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.MatrixUtils;

@SuppressWarnings("unused")
public final class FieldMatrixUtil {
    private FieldMatrixUtil() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    public static <T extends FieldElement<T>> FieldMatrix<T> concatRight(FieldMatrix<T> first, FieldMatrix<T> second) {
        Preconditions.checkArgument(first.getRowDimension() == second.getRowDimension());
        int columns = first.getColumnDimension() + second.getColumnDimension();
        int rows = first.getRowDimension();
        var matrix = new Array2DRowFieldMatrix<>(first.getField(), rows, columns);

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

    public static <T extends FieldElement<T>> FieldMatrix<T> concatBottom(FieldMatrix<T> first, FieldMatrix<T> second) {
        Preconditions.checkArgument(first.getColumnDimension() == second.getColumnDimension());
        int rows = first.getRowDimension() + second.getRowDimension();
        int columns = first.getColumnDimension();
        var matrix = new Array2DRowFieldMatrix<>(first.getField(), rows, columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i < first.getRowDimension()) {
                    matrix.addToEntry(i, j, first.getEntry(i, j));
                } else {
                    matrix.addToEntry(i, j, second.getEntry(i - first.getRowDimension(), j));
                }
            }
        }
        return matrix;
    }

    @SuppressWarnings("unchecked")
    public static <T extends FieldElement<T>> FieldMatrix<T> createMatrixOfRows(FieldMatrix<T>... rows) {
        Preconditions.checkArgument(rows != null && rows.length > 0);
        FieldMatrix<T> result = null;
        for (FieldMatrix<T> row : rows) {
            result = result == null
                    ? row
                    : concatBottom(result, row);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends FieldElement<T>> FieldMatrix<T> createMatrixOfColumns(FieldMatrix<T>... columns) {
        Preconditions.checkArgument(columns != null && columns.length > 0);
        FieldMatrix<T> result = null;
        for (FieldMatrix<T> column : columns) {
            result = result == null
                    ? column
                    : concatRight(result, column);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends FieldElement<T>> FieldMatrix<T> matrixRow(T... elements) {
        return MatrixUtils.createRowFieldMatrix(elements);
    }

    @SuppressWarnings("unchecked")
    public static <T extends FieldElement<T>> FieldMatrix<T> matrixColumn(T... elements) {
        return MatrixUtils.createColumnFieldMatrix(elements);
    }

    public static <T extends FieldElement<T>> FieldMatrix<T> matrixRowOfValue(T value, int size) {
        var row = new Array2DRowFieldMatrix<>(value.getField(), 1, size);
        for (int i = 0; i < size; i++) {
            row.addToEntry(0, i, value);
        }
        return row;
    }

    public static <T extends FieldElement<T>> FieldMatrix<T> matrixColumnOfValue(T value, int size) {
        var row = new Array2DRowFieldMatrix<>(value.getField(), size, 1);
        for (int i = 0; i < size; i++) {
            row.addToEntry(i, 0, value);
        }
        return row;
    }

    public static <T extends FieldElement<T>> boolean hasRow(FieldMatrix<T> matrix, FieldMatrix<T> row) {
        Preconditions.checkArgument(row.getRowDimension() == 1);
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            var column = matrix.getRowMatrix(i);
            if (row.equals(column)) {
                return true;
            }
        }
        return false;
    }

    public static <T extends FieldElement<T>> boolean hasColumn(FieldMatrix<T> matrix, FieldMatrix<T> row) {
        Preconditions.checkArgument(row.getColumnDimension() == 1);
        for (int i = 0; i < matrix.getColumnDimension(); i++) {
            var column = matrix.getColumnMatrix(i);
            if (row.equals(column)) {
                return true;
            }
        }
        return false;
    }
}
