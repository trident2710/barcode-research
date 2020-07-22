package com.trident.hamming.correction.service;

import static com.trident.math.hamming.HammingSyndromeUtil.calculateErrorPosition;
import static com.trident.math.hamming.HammingSyndromeUtil.calculateErrorValue;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import org.apache.commons.math3.linear.FieldMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HammingErrorAnalyzer {
    private static final Logger LOGGER = LoggerFactory.getLogger(HammingErrorAnalyzer.class);

    public static void analyzeError(HammingCode hammingCode, FieldMatrix<GaloisFieldOverPrimeElement> message, FieldMatrix<GaloisFieldOverPrimeElement> error) {
        var code = hammingCode.encode(message);
        LOGGER.info("Input message: {}. Code: {}", message, code);
        var codeWithError = code.add(error);
        var syndrome = hammingCode.syndrome(codeWithError);
        LOGGER.info("Adding error: {}. Resulting code: {}. Syndrome: {}.", error, codeWithError, syndrome);
        calculateErrorValue(syndrome)
                .ifPresent(errorValue -> {
                    var errorPosition = calculateErrorPosition(syndrome, errorValue);
                    LOGGER.info("Got error: {}. Position: {}", errorValue, errorPosition);
                });
    }
}
