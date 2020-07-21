package com.trident.math.field;

import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.concurrent.ThreadLocalRandom;

public final class GaloisFieldOverPrimeUtil {
    private GaloisFieldOverPrimeUtil() {
        throw new IllegalArgumentException("cannot be instantiated");
    }

    public static GaloisFieldOverPrimeElement randomElement(GaloisFieldOverPrime field) {
        return field.getOfValue(ThreadLocalRandom.current().nextLong(field.modulus()));
    }

    public static FieldMatrix<GaloisFieldOverPrimeElement> randomRow(GaloisFieldOverPrime field, int size) {
        var array = new GaloisFieldOverPrimeElement[size];
        for (int i = 0; i < size; i++) {
            array[i] = randomElement(field);
        }
        return FieldMatrixUtil.matrixRow(array);
    }

    public static GaloisFieldOverPrimeElement getMaxElement(FieldMatrix<GaloisFieldOverPrimeElement> matrix) {
        GaloisFieldOverPrimeElement max = matrix.getEntry(0, 0);
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                var element = matrix.getEntry(i, j);
                if (element.compareTo(max) > 0) {
                    max = element;
                }
            }
        }
        return max;
    }
}
