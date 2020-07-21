package com.trident.math.hamming;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisFieldElement;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.MatrixUtils;

import java.util.Objects;

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

    private int totalLength() {
        return generator.getRowDimension() + generator.getColumnDimension();
    }

    private int informationalLength() {
        return generator.getColumnDimension();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HammingCode<?> that = (HammingCode<?>) o;
        return Objects.equals(generator, that.generator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generator);
    }

    @Override
    public String toString() {
        return "Hamming code (" + totalLength() + "," + informationalLength() + ")";
    }
}
