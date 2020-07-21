package com.trident.hamming;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisFieldElement;
import com.trident.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.MatrixUtils;

public class HammingCode<T extends GaloisFieldElement<T>> {
    private final FieldMatrix<T> generator;


    public HammingCode(FieldMatrix<T> generator) {
        this.generator = generator;
    }

    public FieldMatrix<T> encode(FieldMatrix<T> message) {
        Preconditions.checkArgument(message.getRowDimension() == 1);
        Preconditions.checkArgument(message.getColumnDimension() == generator.getColumnDimension());

        var identity = MatrixUtils.createFieldIdentityMatrix(generator.getField(), message.getColumnDimension());
        var generatorT = generator.transpose();
        var matrix = FieldMatrixUtil.concatRight(identity, generatorT);

        return message.multiply(matrix);
    }

    public FieldMatrix<T> syndrome(FieldMatrix<T> code) {
        Preconditions.checkArgument(code.getRowDimension() == 1);

        var generatorT = generator.transpose();
        int messageLength = generatorT.getRowDimension();
        int expectedLength = generatorT.getColumnDimension() + messageLength;
        Preconditions.checkArgument(code.getColumnDimension() == expectedLength);

        var message = code.getSubMatrix(0, 0, 0, messageLength - 1);
        var correction = code.getSubMatrix(0, 0, messageLength, expectedLength - 1);

        return message.multiply(generatorT).subtract(correction);
    }
}
