package com.trident.hamming.correction.service;

import com.trident.hamming.correction.report.HammingCorrectionReportWriter;
import com.trident.math.field.GaloisField;
import com.trident.math.field.GaloisFieldElement;
import com.trident.math.hamming.HammingCode;

public class HammingCodeAnalyzer<GFElement extends GaloisFieldElement<GFElement>, GF extends GaloisField<GFElement>> {
    private final HammingCode<GFElement, GF> hammingCode;
    private final HammingCorrectionReportWriter writer;
    private final GFElement[] sample;

    public HammingCodeAnalyzer(HammingCode<GFElement, GF> hammingCode, HammingCorrectionReportWriter writer, GFElement[] sample) {
        this.hammingCode = hammingCode;
        this.writer = writer;
        this.sample = sample;
    }

    public void analyzeHammingCode() {
        int maxErrorLevel = hammingCode.getFullMatrix().getColumnDimension();
        int errorLevel = 1;
        while (errorLevel <= maxErrorLevel) {
            var errorProvider = new HammingCodeSequentialErrorsProvider<>(errorLevel, hammingCode);
            var errorAnalyzer = new HammingErrorLevelAnalyzer<>(errorProvider, writer, hammingCode, sample);
            errorAnalyzer.analyzeHammingCodeErrorLevel();
            errorLevel++;
        }
    }
}
