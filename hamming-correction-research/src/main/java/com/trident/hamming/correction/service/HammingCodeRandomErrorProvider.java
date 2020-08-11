package com.trident.hamming.correction.service;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import org.apache.commons.math3.linear.FieldMatrix;

public class HammingCodeRandomErrorProvider implements HammingCodeErrorProvider {
    private final int errorLevel;
    private final int iterations;
    private final HammingCode hammingCode;
    private int iteration = 0;

    public HammingCodeRandomErrorProvider(HammingCode hammingCode, int errorLevel, int iterations) {
        this.hammingCode = hammingCode;
        this.errorLevel = errorLevel;
        this.iterations = iterations;
    }

    @Override
    public int errorLevel() {
        return errorLevel;
    }

    @Override
    public boolean hasNext() {
        return iteration++ < iterations;
    }

    @Override
    public FieldMatrix<GaloisFieldOverPrimeElement> next() {
        return ErrorUtil.randomError(hammingCode.getField(), errorLevel, hammingCode.totalLength());
    }
}
