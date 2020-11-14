package com.trident.hamming.correction.service;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.util.Combinations;

import java.util.Iterator;

public class HammingCodeSequentialErrorsProvider implements HammingCodeErrorProvider {
    private final int errorLevel;
    private final HammingCode hammingCode;
    private final Iterator<int[]> positionsIterator;
    private Iterator<long[]> errorsIterator;
    private int[] currentPositions;

    public HammingCodeSequentialErrorsProvider(int errorLevel, HammingCode hammingCode) {
        this.errorLevel = errorLevel;
        this.hammingCode = hammingCode;
        this.positionsIterator = new Combinations(hammingCode.totalLength(), errorLevel).iterator();
        this.errorsIterator = new SequentialVectorIterator(errorLevel, 1, hammingCode.getField().modulus());
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
    public FieldMatrix<GaloisFieldOverPrimeElement> next() {
        if (!hasNext()) {
            throw new RuntimeException();
        }

        if (!errorsIterator.hasNext()) {
            currentPositions = positionsIterator.next();
            errorsIterator = new SequentialVectorIterator(errorLevel, 1, hammingCode.getField().modulus());
        }

        var errors = errorsIterator.next();

        return createErrorVector(currentPositions, errors);
    }

    private FieldMatrix<GaloisFieldOverPrimeElement> createErrorVector(int[] positions, long[] errorValues) {
        var error = FieldMatrixUtil.matrixRowOfValue(hammingCode.getField().getZero(), hammingCode.totalLength());
        for (int i = 0; i < positions.length; i++) {
            error.addToEntry(0, positions[i], hammingCode.getField().getOfValue(errorValues[i]));
        }
        return error;
    }
}