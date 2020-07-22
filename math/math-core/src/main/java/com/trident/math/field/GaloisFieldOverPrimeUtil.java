package com.trident.math.field;

import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public final class GaloisFieldOverPrimeUtil {
    private GaloisFieldOverPrimeUtil() {
        throw new IllegalArgumentException("cannot be instantiated");
    }

    public static GaloisFieldOverPrimeElement randomElement(GaloisFieldOverPrime field) {
        return field.getOfValue(ThreadLocalRandom.current().nextLong(field.modulus()));
    }

    public static GaloisFieldOverPrimeElement randomNonZero(GaloisFieldOverPrime field) {
        return field.getOfValue(ThreadLocalRandom.current().nextLong(field.modulus() - 1)).add(field.getOne());
    }

    public static FieldMatrix<GaloisFieldOverPrimeElement> randomRow(GaloisFieldOverPrime field, int size) {
        var array = new GaloisFieldOverPrimeElement[size];
        for (int i = 0; i < size; i++) {
            array[i] = randomElement(field);
        }
        return FieldMatrixUtil.matrixRow(array);
    }

    public static Optional<GaloisFieldOverPrimeElement> getFirstNonZero(FieldMatrix<GaloisFieldOverPrimeElement> matrix) {
        var zero = matrix.getField().getZero();
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                var element = matrix.getEntry(i, j);
                if (element.compareTo(zero) > 0) {
                    return Optional.of(element);
                }
            }
        }
        return Optional.empty();
    }
}
