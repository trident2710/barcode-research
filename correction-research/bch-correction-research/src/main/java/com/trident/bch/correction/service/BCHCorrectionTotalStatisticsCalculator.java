package com.trident.bch.correction.service;

import com.trident.correction.CorrectionStatistics;
import com.trident.correction.ErrorCorrectionTotalStatisticsCalculator;
import com.trident.math.bch.BCHCode;
import com.trident.math.field.GFElement;
import com.trident.math.field.GFPMElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.HashMap;
import java.util.Map;

public class BCHCorrectionTotalStatisticsCalculator<Symbol extends GFElement<Symbol>, Locator extends GFPMElement<Locator>>
        implements ErrorCorrectionTotalStatisticsCalculator<Symbol> {

    private final BCHCode<Symbol, Locator> bch;

    public BCHCorrectionTotalStatisticsCalculator(BCHCode<Symbol, Locator> bch) {
        this.bch = bch;
    }

    @Override
    public Map<Integer, CorrectionStatistics> calculateStatistics(FieldMatrix<Symbol> message) {
        Map<Integer, CorrectionStatistics> totalStatistics = new HashMap<>();
        for (int errorLevel = 1; errorLevel <= message.getColumnDimension(); errorLevel++) {
            var levelAnalyzer = new BCHCorrectionErrorLevelStatisticsCalculator<>(bch, errorLevel);
            totalStatistics.put(errorLevel, levelAnalyzer.calculateStatistics(message));
        }
        return totalStatistics;
    }
}
