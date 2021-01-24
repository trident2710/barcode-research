package com.trident.math.bch;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisField;
import com.trident.math.field.GaloisFieldElement;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;

import static com.trident.math.matrix.FieldMatrixUtil.concatRight;
import static org.apache.commons.math3.linear.MatrixUtils.createFieldIdentityMatrix;

public class BCHCode<GFElement extends GaloisFieldElement<GFElement>, GF extends GaloisField<GFElement>> {
    private final FieldMatrix<GFElement> generator;
    private final FieldMatrix<GFElement> fullMatrix;

    public BCHCode(FieldMatrix<GFElement> generator, int size) {
        Preconditions.checkArgument(generator.getRowDimension() == 1);
        Preconditions.checkArgument(size > generator.getColumnDimension());
        this.generator = generator;
        this.fullMatrix = createFullMatrix(generator, size);
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

    private FieldMatrix<GFElement> createFullMatrix(FieldMatrix<GFElement> generator, int size) {
        var rows = size - generator.getColumnDimension() + 1;

        var row = FieldMatrixUtil.matrixRowBuffered(size, generator.getRow(0));
        var result = row;

        for (int i = 1; i < rows; i++) {
            var shifted = FieldMatrixUtil.shiftRowRight(row, 1);
            result = FieldMatrixUtil.concatBottom(result, shifted);
            row = shifted;
        }
        return result;
    }

    public FieldMatrix<GFElement> encode(FieldMatrix<GFElement> message) {
        Preconditions.checkArgument(message.getRowDimension() == 1);
        Preconditions.checkArgument(message.getColumnDimension() == fullMatrix.getRowDimension());
        return message.multiply(fullMatrix);
    }
}
