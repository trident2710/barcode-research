package com.trident.hamming.correction.service;

import com.trident.hamming.correction.report.HammingCorrectionReport;
import com.trident.hamming.correction.report.ImmutableHammingCorrectionReport;
import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;
import org.apache.commons.math3.linear.FieldMatrix;

import java.io.PrintWriter;

import static com.trident.math.field.GaloisFieldOverPrimeUtil.randomRow;
import static com.trident.math.hamming.HammingSyndromeUtil.calculateErrorPosition;
import static com.trident.math.hamming.HammingSyndromeUtil.calculateErrorValue;
import static com.trident.math.hamming.HammingSyndromeUtil.canCorrectError;
import static com.trident.math.io.FieldMatrixIOUtil.writeAsString;

public final class HammingCorrectionAnalyzer {

    private final HammingCodeErrorProvider errorProvider;
    private final PrintWriter writer;

    public HammingCorrectionAnalyzer(HammingCodeErrorProvider errorProvider, PrintWriter writer) {
        this.errorProvider = errorProvider;
        this.writer = writer;
    }

    public HammingCorrectionReport analyzeHammingCodeCorrection(HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> hammingCode, FieldMatrix<GaloisFieldOverPrimeElement> message) {
        var code = hammingCode.encode(message);
        writer.println(String.format("Start analyzing: %s", writeAsString(message)));
        writer.println(String.format("Encoded message: %s", writeAsString(code)));

        int corrected = 0;
        int detected = 0;
        int noErrors = 0;
        int iterations = 0;
        int errorLevel = errorProvider.errorLevel();

        while (errorProvider.hasNext()) {
            writer.println();
            var error = errorProvider.next();
            var codeWithError = code.add(error);
            var syndrome = hammingCode.syndrome(codeWithError);

            writer.println(String.format("\tAdding error: %s", writeAsString(error)));
            writer.println(String.format("\tCode with error: %s", writeAsString(codeWithError)));
            writer.println(String.format("\tSyndrome: %s", writeAsString(syndrome)));

            var valueOptional = calculateErrorValue(syndrome);
            if (valueOptional.isPresent()) {
                var errorValue = valueOptional.get();
                var errorPosition = calculateErrorPosition(syndrome, errorValue);

                writer.println(String.format("\tError value: %s", errorValue));
                writer.println(String.format("\tError position: %s", writeAsString(errorPosition)));

                if (canCorrectError(errorPosition, hammingCode.getFullMatrix())) {
                    writer.println("\tResult: Corrected");
                    corrected++;
                } else {
                    writer.println("\tResult: Detected");
                    detected++;
                }
            } else {
                writer.println("\tResult: No error");
                noErrors++;
            }
            iterations++;
        }
        var result = buildReport(hammingCode, iterations, errorLevel, corrected, detected, noErrors);
        writer.flush();
        return result;
    }

    public HammingCorrectionReport analyzeHammingCodeCorrection(HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> hammingCode) {
        var message = randomRow(hammingCode.getField(), hammingCode.informationalLength());
        return analyzeHammingCodeCorrection(hammingCode, message);
    }

    private static HammingCorrectionReport buildReport(HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> hammingCode, int iterations, int errorLevel, int corrected, int detected, int noErrors) {
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
