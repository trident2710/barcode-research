package com.trident.hamming.correction.service;

import static com.trident.math.field.GaloisFieldOverPrimeUtil.randomRow;

import com.trident.math.hamming.HammingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HammingCorrectionMeter {
    private static final Logger LOGGER = LoggerFactory.getLogger(HammingCorrectionMeter.class);

    public static void analyzeHammingCode(HammingCode hammingCode) {
        LOGGER.info("Start analyzing {}", hammingCode);

        int iterations;
        for (int i = 1; i < 3; i++) {
            LOGGER.info("Analyzing {}-degree error", i);
            iterations = 10;
            while (iterations-- > 0) {
                var message = randomRow(hammingCode.getField(), hammingCode.informationalLength());
                var error = ErrorUtil.randomError(hammingCode.getField(), i, hammingCode.totalLength());
                HammingErrorAnalyzer.analyzeError(hammingCode, message, error);
            }
        }
    }

}
