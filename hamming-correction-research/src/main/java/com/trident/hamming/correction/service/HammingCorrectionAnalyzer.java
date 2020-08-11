package com.trident.hamming.correction.service;

import static com.trident.math.field.GaloisFieldOverPrimeUtil.randomRow;
import static com.trident.math.hamming.HammingSyndromeUtil.calculateErrorPosition;
import static com.trident.math.hamming.HammingSyndromeUtil.calculateErrorValue;
import static com.trident.math.hamming.HammingSyndromeUtil.canCorrectError;

import com.trident.hamming.correction.report.HammingCorrectionReport;
import com.trident.hamming.correction.report.ImmutableHammingCorrectionReport;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;
import org.apache.commons.math3.linear.FieldMatrix;

public final class HammingCorrectionAnalyzer {
    private HammingCodeErrorProvider errorProvider;

    public HammingCorrectionAnalyzer(HammingCodeErrorProvider errorProvider) {
        this.errorProvider = errorProvider;
    }

    public HammingCorrectionReport analyzeHammingCodeCorrection(HammingCode hammingCode, FieldMatrix<GaloisFieldOverPrimeElement> message) {
        var code = hammingCode.encode(message);

        System.out.println("Message: " + message);
        System.out.println("Code: " + code);

        int corrected = 0;
        int detected = 0;
        int noErrors = 0;
        int iterations = 0;
        int errorLevel = errorProvider.errorLevel();

        while (errorProvider.hasNext()) {
            System.out.println("\n\n");
            var error = errorProvider.next();
            System.out.println("Error: " + error);
            var codeWithError = code.add(error);
            System.out.println("Code with error: " + codeWithError);
            var syndrome = hammingCode.syndrome(codeWithError);
            System.out.println("Syndrome: " + syndrome);

            var valueOptional = calculateErrorValue(syndrome);
            if (valueOptional.isPresent()) {
                var errorValue = valueOptional.get();
                System.out.println("Error value: " + errorValue);
                var errorPosition = calculateErrorPosition(syndrome, errorValue);
                System.out.println("Error position: " + errorPosition);

                if (canCorrectError(errorPosition, hammingCode.getFullMatrix())) {
                    System.out.println("Corrected");
                    corrected++;
                } else {
                    System.out.println("Detected");
                    detected++;
                }
            } else {
                noErrors++;
            }
            iterations++;
        }
        return buildReport(hammingCode, iterations, errorLevel, corrected, detected, noErrors);
    }

    public HammingCorrectionReport analyzeHammingCodeCorrection(HammingCode hammingCode) {
        var message = randomRow(hammingCode.getField(), hammingCode.informationalLength());
        return analyzeHammingCodeCorrection(hammingCode, message);
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
