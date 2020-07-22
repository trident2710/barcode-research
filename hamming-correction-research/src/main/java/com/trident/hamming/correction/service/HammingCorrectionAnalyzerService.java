package com.trident.hamming.correction.service;

import static com.trident.math.field.GaloisFieldOverPrimeUtil.randomRow;
import static com.trident.math.hamming.HammingSyndromeUtil.calculateErrorPosition;
import static com.trident.math.hamming.HammingSyndromeUtil.calculateErrorValue;
import static com.trident.math.hamming.HammingSyndromeUtil.canCorrectError;

import com.trident.hamming.correction.report.HammingCorrectionReport;
import com.trident.hamming.correction.report.ImmutableHammingCorrectionReport;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;

public final class HammingCorrectionAnalyzerService {

    public static HammingCorrectionReport analyzeHammingCodeCorrection(HammingCode hammingCode, int errorLevel, int iterations) {
        var message = randomRow(hammingCode.getField(), hammingCode.informationalLength());
        var code = hammingCode.encode(message);

        int corrected = 0;
        int detected = 0;
        int noErrors = 0;
        int count = iterations;

        while (count-- > 0) {
            var error = ErrorUtil.randomError(hammingCode.getField(), errorLevel, hammingCode.totalLength());
            var codeWithError = code.add(error);
            var syndrome = hammingCode.syndrome(codeWithError);

            var valueOptional = calculateErrorValue(syndrome);
            if (valueOptional.isPresent()) {
                var errorValue = valueOptional.get();
                var errorPosition = calculateErrorPosition(syndrome, errorValue);

                if (canCorrectError(errorPosition, hammingCode.getFullMatrix())) {
                    corrected++;
                } else {
                    detected++;
                }
            } else {
                noErrors++;
            }
        }
        return buildReport(hammingCode, iterations, errorLevel, corrected, detected, noErrors);
    }

    private static HammingCorrectionReport buildReport(HammingCode hammingCode, int iterations, int errorLevel, int corrected, int detected, int noErrors) {
        return ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(hammingCode))
                .iterations(iterations)
                .errorLevel(errorLevel)
                .corrected(corrected)
                .detected(detected)
                .noErrors(noErrors)
                .build();
    }
}
