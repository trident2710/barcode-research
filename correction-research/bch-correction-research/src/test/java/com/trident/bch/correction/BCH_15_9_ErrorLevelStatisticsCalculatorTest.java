package com.trident.bch.correction;

import com.trident.bch.correction.service.BCHCorrectionErrorLevelStatisticsCalculator;
import com.trident.correction.CorrectionStatus;
import com.trident.correction.ImmutableCorrectionStatistics;
import org.junit.jupiter.api.Test;

import static com.trident.math.bch.BCHCodes.BCH_15_9;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BCH_15_9_ErrorLevelStatisticsCalculatorTest {

    @Test
    void testErrorLevel1() {
        var calculator = new BCHCorrectionErrorLevelStatisticsCalculator<>(BCH_15_9, 1);
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 0, 1, 2, 0, 1, 2}, BCH_15_9.symbolField());
        var statistics = calculator.calculateStatistics(message);
        var expectedStatistics = ImmutableCorrectionStatistics.builder()
                .errorLevel(1)
                .putStatistics(CorrectionStatus.ERROR_1, 45)
                .iterations(45)
                .build();
        assertEquals(expectedStatistics, statistics);
    }

    @Test
    void testErrorLevel2() {
        var calculator = new BCHCorrectionErrorLevelStatisticsCalculator<>(BCH_15_9, 2);
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 0, 1, 2, 0, 1, 2}, BCH_15_9.symbolField());
        var statistics = calculator.calculateStatistics(message);
        var expectedStatistics = ImmutableCorrectionStatistics.builder()
                .errorLevel(2)
                .putStatistics(CorrectionStatus.ERROR_2, 945)
                .iterations(945)
                .build();
        assertEquals(expectedStatistics, statistics);
    }
}
