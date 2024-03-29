package com.trident.math.matrix;

import com.trident.math.field.GF;
import com.trident.math.field.GFElement;
import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.FieldMatrix;

public final class GaloisFieldMatrixUtil {

    private GaloisFieldMatrixUtil() {
    }

    public static <GFElem extends GFElement<GFElem>> FieldMatrix<GFElem> toFieldMatrix(long[][] values, GF<GFElem> field) {
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

    public static <GFElem extends GFElement<GFElem>> FieldMatrix<GFElem> toFieldMatrixRow(long[] values, GF<GFElem> field) {
        return toFieldMatrix(new long[][]{values}, field);
    }

    public static <GFElem extends GFElement<GFElem>> FieldMatrix<GFElem> toFieldMatrixColumn(long[] values, GF<GFElem> field) {
        return toFieldMatrix(new long[][]{values}, field).transpose();
    }
}
