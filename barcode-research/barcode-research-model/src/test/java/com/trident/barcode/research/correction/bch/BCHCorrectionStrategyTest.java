package com.trident.barcode.research.correction.bch;

import com.trident.barcode.research.correction.CorrectionStatus;
import com.trident.barcode.research.model.ImmutableCode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.barcode.research.correction.CorrectionStrategies.BCH_9_3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BCHCorrectionStrategyTest {

    @Test
    void testCorrect() {
        var message = ImmutableCode.of(List.of(1, 0, 2, 3, 2, 2, 3, 0, 3));
        var correction = BCH_9_3.correct(message);

        assertEquals(CorrectionStatus.NO_ERROR, correction.status());
        assertEquals(message, correction.correctedCode().orElseThrow());
    }

    @Test
    void testSingleError() {
        var message = ImmutableCode.of(List.of(2, 0, 2, 3, 2, 2, 3, 0, 3));
        var correction = BCH_9_3.correct(message);
        var expectedCorrected = ImmutableCode.of(List.of(1, 0, 2, 3, 2, 2, 3, 0, 3));

        assertEquals(CorrectionStatus.ERROR_CORRECTED, correction.status());
        assertEquals(expectedCorrected, correction.correctedCode().orElseThrow());
    }

    @Test
    void testDoubleError() {
        var message = ImmutableCode.of(List.of(2, 1, 2, 3, 2, 2, 3, 0, 3));
        var correction = BCH_9_3.correct(message);
        var expectedCorrected = ImmutableCode.of(List.of(1, 0, 2, 3, 2, 2, 3, 0, 3));

        assertEquals(CorrectionStatus.ERROR_CORRECTED, correction.status());
        assertEquals(expectedCorrected, correction.correctedCode().orElseThrow());
    }

    @Test
    void testDetected() {
        var message = ImmutableCode.of(List.of(2, 1, 3, 3, 2, 2, 3, 0, 3));
        var correction = BCH_9_3.correct(message);

        assertEquals(CorrectionStatus.ERROR_DETECTED, correction.status());
        assertTrue(correction.correctedCode().isEmpty());
    }
}