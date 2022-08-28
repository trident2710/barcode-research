package com.trident.bch.correction;

import com.trident.bch.correction.service.BCHCorrectionErrorLevelStatisticsCalculator;
import com.trident.correction.CorrectionStatus;
import com.trident.correction.ImmutableCorrectionStatistics;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.trident.math.bch.BCHCodes.BCH_GF5_24_16;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
public class BCH_24_16_GF5_StatsTest {

    @Test
    void testErrorLevel1() {
        var calculator = new BCHCorrectionErrorLevelStatisticsCalculator<>(BCH_GF5_24_16, 1);
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3}, BCH_GF5_24_16.symbolField());
        var statistics = calculator.calculateStatistics(message);
        var expectedStatistics = ImmutableCorrectionStatistics.builder()
                .errorLevel(1)
                .putStatistics(CorrectionStatus.ERROR_1, 96)
                .iterations(96)
                .build();
        assertEquals(expectedStatistics, statistics);
    }

    @Test
    void testErrorLevel2() {
        var calculator = new BCHCorrectionErrorLevelStatisticsCalculator<>(BCH_GF5_24_16, 2);
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3}, BCH_GF5_24_16.symbolField());
        var statistics = calculator.calculateStatistics(message);
        var expectedStatistics = ImmutableCorrectionStatistics.builder()
                .errorLevel(2)
                .putStatistics(CorrectionStatus.ERROR_2, 4416)
                .iterations(4416)
                .build();
        assertEquals(expectedStatistics, statistics);
    }

    @Test
    void testErrorLevel3() {
        var calculator = new BCHCorrectionErrorLevelStatisticsCalculator<>(BCH_GF5_24_16, 3);
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3}, BCH_GF5_24_16.symbolField());
        var statistics = calculator.calculateStatistics(message);
        var expectedStatistics = ImmutableCorrectionStatistics.builder()
                .errorLevel(3)
                .putStatistics(CorrectionStatus.ERROR_1, 5088)
                .putStatistics(CorrectionStatus.ERROR_2, 32832)
                .putStatistics(CorrectionStatus.DETECTED, 91616)
                .iterations(129536)
                .build();
        assertEquals(expectedStatistics, statistics);
    }

    @Test
    void testErrorLevel4() {
        var calculator = new BCHCorrectionErrorLevelStatisticsCalculator<>(BCH_GF5_24_16, 4);
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3}, BCH_GF5_24_16.symbolField());
        var statistics = calculator.calculateStatistics(message);
        var expectedStatistics = ImmutableCorrectionStatistics.builder()
                .errorLevel(4)
                .putStatistics(CorrectionStatus.ERROR_1, 102144)
                .putStatistics(CorrectionStatus.ERROR_2, 770784)
                .putStatistics(CorrectionStatus.DETECTED, 1847328)
                .iterations(2720256)
                .build();
        assertEquals(expectedStatistics, statistics);
    }

    @Test
    void testErrorLevel5() {
        var calculator = new BCHCorrectionErrorLevelStatisticsCalculator<>(BCH_GF5_24_16, 5);
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3}, BCH_GF5_24_16.symbolField());
        var statistics = calculator.calculateStatistics(message);
        var expectedStatistics = ImmutableCorrectionStatistics.builder()
                .errorLevel(5)
                .putStatistics(CorrectionStatus.ERROR_1, 1611264)
                .putStatistics(CorrectionStatus.ERROR_2, 12316704)
                .putStatistics(CorrectionStatus.DETECTED, 29596128)
                .iterations(43524096)
                .build();
        assertEquals(expectedStatistics, statistics);
    }
}
