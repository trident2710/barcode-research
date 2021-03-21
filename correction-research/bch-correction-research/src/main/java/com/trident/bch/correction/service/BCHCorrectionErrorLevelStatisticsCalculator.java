package com.trident.bch.correction.service;

import com.trident.correction.CorrectionStatistics;
import com.trident.correction.CorrectionStatus;
import com.trident.correction.ErrorCorrectionLevelStatisticsCalculator;
import com.trident.correction.FieldErrorVectorProvider;
import com.trident.correction.FieldErrorVectorSequentialProvider;
import com.trident.correction.ImmutableCorrectionStatistics;
import com.trident.math.bch.BCHCode;
import com.trident.math.field.GFElement;
import com.trident.math.field.GFPMElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.HashMap;

public class BCHCorrectionErrorLevelStatisticsCalculator<Symbol extends GFElement<Symbol>, Locator extends GFPMElement<Locator>>
        implements ErrorCorrectionLevelStatisticsCalculator<Symbol> {

    private final BCHCode<Symbol, Locator> bch;
    private final int errorLevel;
    private final FieldErrorVectorProvider<Symbol> errorProvider;

    public BCHCorrectionErrorLevelStatisticsCalculator(BCHCode<Symbol, Locator> bch, int errorLevel) {
        this.bch = bch;
        this.errorLevel = errorLevel;
        this.errorProvider = new FieldErrorVectorSequentialProvider<>(bch.totalLength(), errorLevel, bch.symbolField());
    }

    @Override
    public int errorLevel() {
        return errorLevel;
    }

    @Override
    public CorrectionStatistics calculateStatistics(FieldMatrix<Symbol> rawMessage) {
        var encoded = bch.encode(rawMessage);
        var statistics = new HashMap<CorrectionStatus, Long>();

        int iterations = 0;
        while (errorProvider.hasNext()) {
            var errorVector = errorProvider.next();
            var status = calculateCorrectionStatus(encoded, errorVector);
            statistics.merge(status, 1L, Long::sum);
            iterations++;
        }

        return ImmutableCorrectionStatistics.builder()
                .errorLevel(errorLevel)
                .iterations(iterations)
                .putAllStatistics(statistics)
                .build();
    }


    private CorrectionStatus calculateCorrectionStatus
            (FieldMatrix<Symbol> encoded, FieldMatrix<Symbol> errorVector) {
        var withError = encoded.add(errorVector);
        var syndrome = bch.syndrome(withError);
        if (!syndrome.hasError()) {
            return CorrectionStatus.NO_ERROR;
        } else {
            int errorCount = syndrome.errorCount();
            switch (errorCount) {
                case 1:
                    return CorrectionStatus.ERROR_1;
                case 2:
                    return CorrectionStatus.ERROR_2;
                default:
                    throw new IllegalStateException("Abnormal error count: " + errorCount);
            }
        }
    }
}
