package com.trident.bch.correction;

import com.trident.bch.correction.service.BCHCorrectionErrorLevelStatisticsCalculator;
import com.trident.correction.CorrectionStatus;
import com.trident.correction.ImmutableCorrectionStatistics;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.trident.math.bch.BCHCodes.BCH_GF5_14_6;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
public class BCH_14_6_GF5_StatsTest {

    @Test
    void testErrorLevel2() {
        var calculator = new BCHCorrectionErrorLevelStatisticsCalculator<>(BCH_GF5_14_6, 2);
        var message = toFieldMatrixRow(new long[]{0, 1, 2, 0, 1, 2}, BCH_GF5_14_6.symbolField());
        var statistics = calculator.calculateStatistics(message);
        var expectedStatistics = ImmutableCorrectionStatistics.builder()
                .errorLevel(2)
                .putStatistics(CorrectionStatus.ERROR_2, 4416)
                .iterations(4416)
                .build();
        assertEquals(expectedStatistics, statistics);
    }
}
