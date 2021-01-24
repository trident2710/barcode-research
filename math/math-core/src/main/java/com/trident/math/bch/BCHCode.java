package com.trident.math.bch;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisField;
import com.trident.math.field.GaloisFieldElement;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;

public class BCHCode<GFElement extends GaloisFieldElement<GFElement>, GF extends GaloisField<GFElement>> {
    private final FieldMatrix<GFElement> generator;
    private final FieldMatrix<GFElement> fullMatrix;

    public BCHCode(FieldMatrix<GFElement> generator) {
        Preconditions.checkArgument(generator.getRowDimension() == 1);
        this.generator = generator;
        this.fullMatrix = createFullMatrix(generator);
    }

    public FieldMatrix<GFElement> getGenerator() {
        return generator;
    }

    public FieldMatrix<GFElement> getFullMatrix() {
        return fullMatrix;
    }

    public GF getField() {
        return (GF) getGenerator().getField();
    }

    private FieldMatrix<GFElement> createFullMatrix(FieldMatrix<GFElement> generator) {
        var columns = generator.getColumnDimension() * 2;
        var rows = generator.getColumnDimension() + 1;

        var row = FieldMatrixUtil.matrixRowBuffered(columns, generator.getRow(0));
        var result = row;

        for (int i = 1; i < rows; i++) {
            var shifted = FieldMatrixUtil.shiftRowRight(row, 1);
            result = FieldMatrixUtil.concatBottom(result, shifted);
            row = shifted;
        }
        return result;
    }
}
