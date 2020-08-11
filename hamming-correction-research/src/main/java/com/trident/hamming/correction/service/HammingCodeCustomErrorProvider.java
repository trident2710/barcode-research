package com.trident.hamming.correction.service;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.List;

public class HammingCodeCustomErrorProvider implements HammingCodeErrorProvider {
    private final HammingCode hammingCode;
    private final int errorLevel;
    private final List<FieldMatrix<GaloisFieldOverPrimeElement>> errors;
    private int iteration = 0;

    public HammingCodeCustomErrorProvider(HammingCode hammingCode, int errorLevel,
            List<FieldMatrix<GaloisFieldOverPrimeElement>> errors) {
        this.hammingCode = hammingCode;
        this.errorLevel = errorLevel;
        this.errors = errors;
    }

    @Override
    public int errorLevel() {
        return errorLevel;
    }

    @Override
    public boolean hasNext() {
        return iteration < errors.size();
    }

    @Override
    public FieldMatrix<GaloisFieldOverPrimeElement> next() {
        return errors.get(iteration++);
    }
}
