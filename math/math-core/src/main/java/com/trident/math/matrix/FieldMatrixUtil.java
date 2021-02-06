package com.trident.math.matrix;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisField;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.MatrixUtils;

@SuppressWarnings("unused")
public final class FieldMatrixUtil {
    private FieldMatrixUtil() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    public static <FieldElem extends FieldElement<FieldElem>> FieldMatrix<FieldElem> concatRight(FieldMatrix<FieldElem> first, FieldMatrix<FieldElem> second) {
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

    public static <FieldElem extends FieldElement<FieldElem>> FieldMatrix<FieldElem> concatBottom(FieldMatrix<FieldElem> first, FieldMatrix<FieldElem> second) {
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
    public static <FieldElem extends FieldElement<FieldElem>> FieldMatrix<FieldElem> createMatrixOfRows(FieldMatrix<FieldElem>... rows) {
        Preconditions.checkArgument(rows != null && rows.length > 0);
        FieldMatrix<FieldElem> result = null;
        for (FieldMatrix<FieldElem> row : rows) {
            result = result == null
                    ? row
                    : concatBottom(result, row);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <FieldElem extends FieldElement<FieldElem>> FieldMatrix<FieldElem> createMatrixOfColumns(FieldMatrix<FieldElem>... columns) {
        Preconditions.checkArgument(columns != null && columns.length > 0);
        FieldMatrix<FieldElem> result = null;
        for (FieldMatrix<FieldElem> column : columns) {
            result = result == null
                    ? column
                    : concatRight(result, column);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <FieldElem extends FieldElement<FieldElem>> FieldMatrix<FieldElem> matrixRow(FieldElem... elements) {
        return MatrixUtils.createRowFieldMatrix(elements);
    }

    /**
     * Creates matrix row with given elements and appends with zeros if size > elements.length
     */
    public static <FieldElem extends FieldElement<FieldElem>> FieldMatrix<FieldElem> matrixRowBuffered(int size, FieldElem... elements) {
        Preconditions.checkArgument(elements.length <= size);
        Preconditions.checkArgument(size > 0);
        var row = matrixRowOfValue(elements[0].getField().getZero(), size);
        for (int i = 0; i < elements.length; i++) {
            row.addToEntry(0, i, elements[i]);
        }
        return row;
    }

    @SuppressWarnings("unchecked")
    public static <FieldElem extends FieldElement<FieldElem>> FieldMatrix<FieldElem> matrixColumn(FieldElem... elements) {
        return MatrixUtils.createColumnFieldMatrix(elements);
    }

    public static <FieldElem extends FieldElement<FieldElem>> FieldMatrix<FieldElem> matrixRowOfValue(FieldElem value, int size) {
        var row = new Array2DRowFieldMatrix<>(value.getField(), 1, size);
        for (int i = 0; i < size; i++) {
            row.addToEntry(0, i, value);
        }
        return row;
    }

    public static <FieldElem extends FieldElement<FieldElem>> FieldMatrix<FieldElem> matrixColumnOfValue(FieldElem value, int size) {
        var row = new Array2DRowFieldMatrix<>(value.getField(), size, 1);
        for (int i = 0; i < size; i++) {
            row.addToEntry(i, 0, value);
        }
        return row;
    }

    public static <FieldElem extends FieldElement<FieldElem>> boolean hasRow(FieldMatrix<FieldElem> matrix, FieldMatrix<FieldElem> row) {
        Preconditions.checkArgument(row.getRowDimension() == 1);
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            var column = matrix.getRowMatrix(i);
            if (row.equals(column)) {
                return true;
            }
        }
        return false;
    }

    public static <FieldElem extends FieldElement<FieldElem>> boolean hasColumn(FieldMatrix<FieldElem> matrix, FieldMatrix<FieldElem> row) {
        Preconditions.checkArgument(row.getColumnDimension() == 1);
        for (int i = 0; i < matrix.getColumnDimension(); i++) {
            var column = matrix.getColumnMatrix(i);
            if (row.equals(column)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Shifts each element of the row to the right by @steps and sets zero element of field to it's previous position
     */
    public static <FieldElem extends FieldElement<FieldElem>> FieldMatrix<FieldElem> shiftRowRight(FieldMatrix<FieldElem> row, int steps) {
        Preconditions.checkArgument(row.getRowDimension() == 1);
        Preconditions.checkArgument(steps > 0 && steps < row.getColumnDimension());
        var shifted = matrixRowOfValue(row.getField().getZero(), row.getColumnDimension());
        for (int i = 0; i < row.getColumnDimension() - steps; i++) {
            shifted.setEntry(0, i + steps, row.getEntry(0, i));
        }
        return shifted;
    }

    public static <FieldElem extends FieldElement<FieldElem>> FieldMatrix<FieldElem> toFieldMatrix(long[][] values, GaloisField<FieldElem> field) {
        int rows = values.length;
        int columns = values[0].length;

        var matrix = new Array2DRowFieldMatrix<>(field, rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix.addToEntry(i, j, field.getOfValue(values[i][j]));
            }
        }

        return matrix;
    }

    public static <FieldElem extends FieldElement<FieldElem>> FieldMatrix<FieldElem> toFieldMatrixRow(long[] values, GaloisField<FieldElem> field) {
        int columns = values.length;

        var matrix = new Array2DRowFieldMatrix<>(field, 1, columns);
        for (int i = 0; i < columns; i++) {
            matrix.addToEntry(0, i, field.getOfValue(values[i]));
        }

        return matrix;
    }
}
