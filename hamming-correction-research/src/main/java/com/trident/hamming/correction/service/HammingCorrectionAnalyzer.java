package com.trident.hamming.correction.service;

import com.trident.hamming.correction.report.HammingCorrectionReport;
import com.trident.hamming.correction.report.HammingCorrectionReportWriter;
import com.trident.hamming.correction.report.ImmutableHammingCorrectionReport;
import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;
import org.apache.commons.math3.linear.FieldMatrix;

import static com.trident.hamming.correction.report.HammingCorrectionReportWriter.reportToString;
import static com.trident.math.field.GaloisFieldElementUtil.randomRow;
import static com.trident.math.io.FieldMatrixIOUtil.writeAsString;

public final class HammingCorrectionAnalyzer {

    private final HammingCodeErrorProvider errorProvider;
    private final HammingCorrectionReportWriter writer;

    public HammingCorrectionAnalyzer(HammingCodeErrorProvider errorProvider, HammingCorrectionReportWriter writer) {
        this.errorProvider = errorProvider;
        this.writer = writer;
    }

    public HammingCorrectionReport analyzeHammingCodeCorrection(HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> hammingCode, FieldMatrix<GaloisFieldOverPrimeElement> message) {
        var code = hammingCode.encode(message);
        writer.info(String.format("Start analyzing: %s%n", writeAsString(message)));
        writer.info(String.format("Encoded message: %s%n", writeAsString(code)));

        int corrected = 0;
        int detected = 0;
        int noErrors = 0;
        int iterations = 0;
        int errorLevel = errorProvider.errorLevel();

        while (errorProvider.hasNext()) {
            var error = errorProvider.next();
            var codeWithError = code.add(error);
            var syndrome = hammingCode.syndrome(codeWithError);

            writer.trace("------------------------------------------------------");
            writer.trace(String.format("Adding error: %s%n", writeAsString(error)));
            writer.trace(String.format("Code with error: %s%n", writeAsString(codeWithError)));
            writer.trace(String.format("Syndrome: %s%n", writeAsString(syndrome.getSyndromeRow())));

            if (syndrome.hasError()) {
                var errorValue = syndrome.getErrorValue().get();
                var errorPosition = syndrome.getErrorPosition().get();

                writer.trace(String.format("Error value: %s%n", errorValue));
                writer.trace(String.format("Error position: %s%n", writeAsString(errorPosition)));

                boolean canCorrectError = syndrome.canCorrectError();
                if (canCorrectError) {
                    writer.trace("Result: Corrected");
                    corrected++;
                } else {
                    writer.trace("Result: Detected");
                    detected++;
                }
            } else {
                writer.trace("Result: No error");
                noErrors++;
            }
            iterations++;
        }
        var result = buildReport(hammingCode, iterations, errorLevel, corrected, detected, noErrors);
        writer.info("------------------------------------------------------");
        writer.info(String.format("Final result: %s%n", reportToString(result)));
        return result;
    }

    public HammingCorrectionReport analyzeHammingCodeCorrection(HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> hammingCode) {
        var message = randomRow(hammingCode.getField(), hammingCode.informationalLength(), new GaloisFieldOverPrimeElement[0]);
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
