package com.trident.hamming.correction.service;

import com.trident.correction.FieldErrorVectorProvider;
import com.trident.hamming.correction.report.HammingCorrectionReport;
import com.trident.hamming.correction.report.HammingCorrectionReportWriter;
import com.trident.hamming.correction.report.ImmutableHammingCorrectionReport;
import com.trident.math.field.GF;
import com.trident.math.field.GFElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Arrays;

import static com.trident.hamming.correction.report.HammingCorrectionReportWriter.reportToString;
import static com.trident.math.field.GFElementUtil.randomRow;
import static com.trident.math.io.FieldMatrixIOUtil.writeAsString;

public final class HammingErrorLevelAnalyzer<FE extends GFElement<FE>, F extends GF<FE>> {

    private final FieldErrorVectorProvider<FE> errorProvider;
    private final HammingCorrectionReportWriter writer;
    private final HammingCode<FE, F> hammingCode;
    private final FE[] sample;

    public HammingErrorLevelAnalyzer(FieldErrorVectorProvider<FE> errorProvider, HammingCorrectionReportWriter writer, HammingCode<FE, F> hammingCode, FE[] sample) {
        this.errorProvider = errorProvider;
        this.writer = writer;
        this.hammingCode = hammingCode;
        this.sample = Arrays.copyOf(sample, sample.length);
    }

    public HammingCorrectionReport analyzeHammingCodeErrorLevel(FieldMatrix<FE> message) {
        var code = hammingCode.encode(message);
        writer.info("------------------------------------------------------");
        writer.info(String.format("Start analyzing: %s%n", writeAsString(message)));
        writer.info(String.format("Error level: %s", errorProvider.errorLevel()));

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
        writer.info(String.format("Final result: %s%n", reportToString(result)));
        return result;
    }

    public HammingCorrectionReport analyzeHammingCodeErrorLevel() {
        var message = randomRow(hammingCode.getField(), hammingCode.informationalLength(), sample);
        return analyzeHammingCodeErrorLevel(message);
    }

    private HammingCorrectionReport buildReport(HammingCode<FE, F> hammingCode, int iterations, int errorLevel, int corrected, int detected, int noErrors) {
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
