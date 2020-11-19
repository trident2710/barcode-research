package com.trident.hamming.correction.service;

import cc.redberry.rings.util.ArraysUtil;
import com.trident.math.field.GaloisField;
import com.trident.math.field.GaloisFieldElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.util.Combinations;

import java.util.Arrays;
import java.util.Iterator;

public class HammingCodeSequentialErrorsProvider<GFElement extends GaloisFieldElement<GFElement>, GF extends GaloisField<GFElement>> implements HammingCodeErrorProvider<GFElement> {
    private final int errorLevel;
    private final HammingCode<GFElement, GF> hammingCode;
    private final Iterator<int[]> positionsIterator;
    private Iterator<long[]> errorsIterator;
    private int[] currentPositions;

    public HammingCodeSequentialErrorsProvider(int errorLevel, HammingCode<GFElement, GF> hammingCode) {
        this.errorLevel = errorLevel;
        this.hammingCode = hammingCode;
        this.positionsIterator = new Combinations(hammingCode.totalLength(), errorLevel).iterator();
        this.errorsIterator = new SequentialVectorIterator(errorLevel, 1, hammingCode.getField().elementsCount());
        this.currentPositions = positionsIterator.next();
    }

    @Override
    public int errorLevel() {
        return errorLevel;
    }

    @Override
    public boolean hasNext() {
        return positionsIterator.hasNext() || errorsIterator.hasNext();
    }

    @Override
    public FieldMatrix<GFElement> next() {
        if (!hasNext()) {
            throw new RuntimeException();
        }

        if (!errorsIterator.hasNext()) {
            currentPositions = positionsIterator.next();
            errorsIterator = new SequentialVectorIterator(errorLevel, 1, hammingCode.getField().elementsCount());
        }

        var errors = errorsIterator.next();

        return createErrorVector(currentPositions, errors);
    }

    private FieldMatrix<GFElement> createErrorVector(int[] positions, long[] errorValues) {
        var error = FieldMatrixUtil.matrixRowOfValue(hammingCode.getField().getZero(), hammingCode.totalLength());
        for (int i = 0; i < positions.length; i++) {
            error.addToEntry(0, positions[i], hammingCode.getField().getOfValue(errorValues[i]));
        }
        return error;
    }

    private static class SequentialVectorIterator implements Iterator<long[]> {
        private final long[] current;
        private final long min;
        private final long max;

        public SequentialVectorIterator(int size, long min, long max) {
            this.current = ArraysUtil.arrayOf(min, size);
            this.current[current.length - 1] = min - 1;
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean hasNext() {
            return !Arrays.equals(current, ArraysUtil.arrayOf(max - 1, current.length));
        }

        @Override
        public long[] next() {
            if (!hasNext()) {
                throw new RuntimeException();
            }
            increment(current.length - 1);
            return current;
        }

        private void increment(int index) {
            if (index == 0) {
                ++current[index];
            } else {
                ++current[index];
                if (current[index] == max) {
                    current[index] = min;
                    increment(index - 1);
                }
            }
        }
    }
}