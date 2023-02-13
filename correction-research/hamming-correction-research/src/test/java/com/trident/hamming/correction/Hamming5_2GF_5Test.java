package com.trident.hamming.correction;

import com.trident.correction.FieldErrorVectorSequentialProvider;
import com.trident.hamming.correction.report.HammingCorrectionReportWriter;
import com.trident.hamming.correction.report.ImmutableHammingCorrectionReport;
import com.trident.hamming.correction.service.HammingErrorLevelAnalyzer;
import com.trident.math.field.GFPElement;
import com.trident.math.io.converter.HammingCodeConverter;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;

import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.hamming.HammingCodes.HAMMING_5_2_GF_5;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Hamming5_2GF_5Test {

    private static final HammingCorrectionReportWriter WRITER = new HammingCorrectionReportWriter(new PrintStream(OutputStream.nullOutputStream()), false);


    @Test
    void testErrorLevel1() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_5_2_GF_5))
                .errorLevel(1)
                .corrected(20)
                .detected(0)
                .noErrors(0)
                .iterations(20)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(5, 1, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_5_2_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel2() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_5_2_GF_5))
                .errorLevel(2)
                .corrected(12)
                .detected(148)
                .noErrors(0)
                .iterations(160)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(5, 2, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_5_2_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel3() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_5_2_GF_5))
                .errorLevel(3)
                .corrected(84)
                .detected(552)
                .noErrors(4)
                .iterations(640)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(5, 3, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_5_2_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel4() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_5_2_GF_5))
                .errorLevel(4)
                .corrected(216)
                .detected(1052)
                .noErrors(12)
                .iterations(1280)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(5, 4, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_5_2_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel5() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_5_2_GF_5))
                .errorLevel(5)
                .corrected(168)
                .detected(848)
                .noErrors(8)
                .iterations(1024)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(5, 5, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_5_2_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }
}
