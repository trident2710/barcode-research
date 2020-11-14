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
        this.errorsIterator = new SequentialVectorIterator(errorLevel, hammingCode.getField().getOne().value(), hammingCode.getField().modulus());
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
            errorsIterator = new SequentialVectorIterator(errorLevel, hammingCode.getField().getOne().value(), hammingCode.getField().modulus());
        }

        var errors = errorsIterator.next();

        return createErrorVector(currentPositions, errors);
    }

    private FieldMatrix<GaloisFieldOverPrimeElement> createErrorVector(int[] positions, long[] errorValues) {
        var erorr = FieldMatrixUtil.matrixRowOfValue(hammingCode.getField().getZero(), hammingCode.totalLength());
        for (int i = 0; i < positions.length; i++) {
            erorr.addToEntry(0, positions[i], new GaloisFieldOverPrimeElement(hammingCode.getField(), errorValues[i]));
        }
        return erorr;
    }
}