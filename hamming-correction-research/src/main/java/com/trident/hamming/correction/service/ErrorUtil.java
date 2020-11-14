package com.trident.hamming.correction.service;

import static com.trident.math.field.GaloisFieldElementUtil.randomNonZero;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRowOfValue;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ErrorUtil {
    private ErrorUtil() {
        throw new IllegalStateException("cannot be instantiated");
    }

    public static FieldMatrix<GaloisFieldOverPrimeElement> randomError(GaloisFieldOverPrime field, int number, int codeSize) {
        Preconditions.checkArgument(number <= codeSize);
        var errorPositions = errorPositions(number, codeSize);
        var error = matrixRowOfValue(field.getZero(), codeSize);
        errorPositions.forEach(position -> {
            error.addToEntry(0, position, randomNonZero(field));
        });
        return error;
    }

    private static List<Integer> errorPositions(int number, int codeSize) {
        Preconditions.checkArgument(number <= codeSize);
        var errorPositions = IntStream.range(0, codeSize)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(errorPositions);
        return errorPositions.subList(0, number);
    }
}
