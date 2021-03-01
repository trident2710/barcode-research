package com.trident.math.hamming;

import com.google.common.base.Preconditions;
import com.trident.math.field.GF;
import com.trident.math.field.GFElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Objects;

import static com.trident.math.matrix.FieldMatrixUtil.concatRight;
import static org.apache.commons.math3.linear.MatrixUtils.createFieldIdentityMatrix;

public class HammingCode<FE extends GFElement<FE>, F extends GF<FE>> {
    private final FieldMatrix<FE> generator;
    private final FieldMatrix<FE> fullMatrix;

    public HammingCode(FieldMatrix<FE> generator) {
        this.generator = generator;
        this.fullMatrix = concatRight(generator, createFieldIdentityMatrix(generator.getField(), generator.getRowDimension()));
    }

    public FieldMatrix<FE> getGenerator() {
        return generator;
    }

    public FieldMatrix<FE> getFullMatrix() {
        return fullMatrix;
    }

    public F getField() {
        return (F) getGenerator().getField();
    }

    public FieldMatrix<FE> encode(FieldMatrix<FE> message) {
        Preconditions.checkArgument(message.getRowDimension() == 1);
        Preconditions.checkArgument(message.getColumnDimension() == generator.getColumnDimension());

        var identity = createFieldIdentityMatrix(generator.getField(), message.getColumnDimension());
        var generatorT = generator.transpose();
        var matrix = concatRight(identity, generatorT);

        return message.multiply(matrix);
    }

    public HammingCodeSyndrome<FE> syndrome(FieldMatrix<FE> code) {
        Preconditions.checkArgument(code.getRowDimension() == 1);

        var generatorT = generator.transpose();
        int messageLength = generatorT.getRowDimension();
        int expectedLength = generatorT.getColumnDimension() + messageLength;
        Preconditions.checkArgument(code.getColumnDimension() == expectedLength);

        var message = code.getSubMatrix(0, 0, 0, messageLength - 1);
        var correction = code.getSubMatrix(0, 0, messageLength, expectedLength - 1);

        return new HammingCodeSyndrome<>(message.multiply(generatorT).subtract(correction), this);
    }

    public int totalLength() {
        return generator.getRowDimension() + generator.getColumnDimension();
    }

    public int informationalLength() {
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
        HammingCode<?, ?> that = (HammingCode<?, ?>) o;
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
