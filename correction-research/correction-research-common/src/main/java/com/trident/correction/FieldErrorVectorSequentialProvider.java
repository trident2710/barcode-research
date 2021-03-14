package com.trident.correction;

import com.trident.math.field.GF;
import com.trident.math.field.GFElement;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.util.Combinations;

import java.util.Iterator;

public class FieldErrorVectorSequentialProvider <FE extends GFElement<FE>, F extends GF<FE>> implements FieldErrorVectorProvider<FE>{
    private final int vectorLength;
    private final int errorLevel;
    private final Iterator<int[]> positionsIterator;
    private final F field;
    private Iterator<long[]> errorsIterator;
    private int[] currentPositions;

    public FieldErrorVectorSequentialProvider(int vectorLength, int errorLevel, F field) {
        this.vectorLength = vectorLength;
        this.errorLevel = errorLevel;
        this.field = field;
        this.positionsIterator = new Combinations(vectorLength, errorLevel).iterator();
        this.errorsIterator = new SequentialVectorIterator(errorLevel, 1, field.elementsCount());
        this.currentPositions = positionsIterator.next();
    }

    @Override
    public int vectorLength() {
        return vectorLength;
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
    public FieldMatrix<FE> next() {
        if (!hasNext()) {
            throw new RuntimeException();
        }

        if (!errorsIterator.hasNext()) {
            currentPositions = positionsIterator.next();
            errorsIterator = new SequentialVectorIterator(errorLevel, 1, field.elementsCount());
        }

        var errors = errorsIterator.next();

        return createErrorVector(currentPositions, errors);
    }

    private FieldMatrix<FE> createErrorVector(int[] positions, long[] errorValues) {
        var error = FieldMatrixUtil.matrixRowOfValue(field.getZero(), vectorLength);
        for (int i = 0; i < positions.length; i++) {
            error.addToEntry(0, positions[i], field.getOfValue(errorValues[i]));
        }
        return error;
    }
}
