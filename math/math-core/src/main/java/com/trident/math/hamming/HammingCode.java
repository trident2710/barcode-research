package com.trident.math.hamming;

import static com.trident.math.matrix.FieldMatrixUtil.concatRight;
import static org.apache.commons.math3.linear.MatrixUtils.createFieldIdentityMatrix;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Objects;

public class HammingCode {
    private final FieldMatrix<GaloisFieldOverPrimeElement> generator;
    private final FieldMatrix<GaloisFieldOverPrimeElement> fullMatrix;


    public HammingCode(FieldMatrix<GaloisFieldOverPrimeElement> generator) {
        this.generator = generator;
        this.fullMatrix = concatRight(generator, createFieldIdentityMatrix(generator.getField(), generator.getRowDimension()));
    }

    public FieldMatrix<GaloisFieldOverPrimeElement> getGenerator() {
        return generator;
    }

    public FieldMatrix<GaloisFieldOverPrimeElement> getFullMatrix() {
        return fullMatrix;
    }

    public GaloisFieldOverPrime getField() {
        return (GaloisFieldOverPrime) getGenerator().getField();
    }

    public FieldMatrix<GaloisFieldOverPrimeElement> encode(FieldMatrix<GaloisFieldOverPrimeElement> message) {
        Preconditions.checkArgument(message.getRowDimension() == 1);
        Preconditions.checkArgument(message.getColumnDimension() == generator.getColumnDimension());

        var identity = createFieldIdentityMatrix(generator.getField(), message.getColumnDimension());
        var generatorT = generator.transpose();
        var matrix = concatRight(identity, generatorT);

        return message.multiply(matrix);
    }

    public FieldMatrix<GaloisFieldOverPrimeElement> syndrome(FieldMatrix<GaloisFieldOverPrimeElement> code) {
        Preconditions.checkArgument(code.getRowDimension() == 1);

        var generatorT = generator.transpose();
        int messageLength = generatorT.getRowDimension();
        int expectedLength = generatorT.getColumnDimension() + messageLength;
        Preconditions.checkArgument(code.getColumnDimension() == expectedLength);

        var message = code.getSubMatrix(0, 0, 0, messageLength - 1);
        var correction = code.getSubMatrix(0, 0, messageLength, expectedLength - 1);

        return message.multiply(generatorT).subtract(correction);
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
        HammingCode that = (HammingCode) o;
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
