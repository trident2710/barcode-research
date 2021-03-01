package com.trident.math.field;

import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public final class GFElementUtil {
    private GFElementUtil() {
        throw new IllegalArgumentException("cannot be instantiated");
    }

    public static <K extends GFElement<K>, T extends GF<? extends GFElement<K>>> K randomElement(T field) {
        return (K) field.getOfValue(ThreadLocalRandom.current().nextLong());
    }

    public static <K extends GFElement<K>, T extends GF<? extends GFElement<K>>> K randomNonZero(T field) {
        var value = (K) field.getOfValue(ThreadLocalRandom.current().nextLong());
        return value.equals(field.getZero())
                ? value.add((K) field.getOne())
                : value;
    }

    public static <K extends GFElement<K>, T extends GF<? extends GFElement<K>>> FieldMatrix<K> randomRow(T field, int size, K[] sample) {
        var list = new ArrayList<K>();
        for (int i = 0; i < size; i++) {
            list.add(i, randomElement(field));
        }
        return FieldMatrixUtil.matrixRow(list.toArray(sample));
    }

    public static <K extends GFElement<K>> Optional<K> getFirstNonZero(FieldMatrix<K> matrix) {
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
