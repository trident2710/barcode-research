package com.trident.hamming.correction.service;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.util.Combinations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HammingCodeRandomErrorsDiscreteProvider implements HammingCodeErrorProvider {
    private final int errorLevel;
    private final HammingCode hammingCode;
    private List<FieldMatrix<GaloisFieldOverPrimeElement>> errors;
    private final int totalErrors;
    private int iteration = 0;
    private Combinations combinations;

    public HammingCodeRandomErrorsDiscreteProvider(int errorLevel, HammingCode hammingCode, int totalErrors) {
        this.errorLevel = errorLevel;
        this.hammingCode = hammingCode;
        this.combinations = new Combinations(hammingCode.totalLength(), errorLevel);
        this.totalErrors = totalErrors;
        this.errors = List.copyOf(generateErrors());
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

    private Set<FieldMatrix<GaloisFieldOverPrimeElement>> generateErrors() {
        var result = new HashSet<FieldMatrix<GaloisFieldOverPrimeElement>>();

        while (result.size() < totalErrors) {
            result.add(ErrorUtil.randomError(hammingCode.getField(), errorLevel, hammingCode.totalLength()));
        }
        return result;
    }
}
