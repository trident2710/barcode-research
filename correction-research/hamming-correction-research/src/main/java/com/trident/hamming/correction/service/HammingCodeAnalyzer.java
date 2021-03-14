package com.trident.hamming.correction.service;

import com.trident.hamming.correction.report.HammingCorrectionReportWriter;
import com.trident.math.field.GF;
import com.trident.math.field.GFElement;
import com.trident.math.hamming.HammingCode;

public class HammingCodeAnalyzer<FE extends GFElement<FE>, F extends GF<FE>> {
    private final HammingCode<FE, F> hammingCode;
    private final HammingCorrectionReportWriter writer;
    private final FE[] sample;

    public HammingCodeAnalyzer(HammingCode<FE, F> hammingCode, HammingCorrectionReportWriter writer, FE[] sample) {
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
