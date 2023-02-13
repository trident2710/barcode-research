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
import static com.trident.math.hamming.HammingCodes.HAMMING_7_4_GF_5;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Hamming7_4GF_5Test {

    private static final HammingCorrectionReportWriter WRITER = new HammingCorrectionReportWriter(new PrintStream(OutputStream.nullOutputStream()), false);

    @Test
    void testErrorLevel1() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_5))
                .errorLevel(1)
                .corrected(28)
                .detected(0)
                .noErrors(0)
                .iterations(28)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(7, 1, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel2() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_5))
                .errorLevel(2)
                .corrected(120)
                .detected(216)
                .noErrors(0)
                .iterations(336)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(7, 2, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel3() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_5))
                .errorLevel(3)
                .corrected(680)
                .detected(1520)
                .noErrors(40)
                .iterations(2240)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(7, 3, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel4() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_5))
                .errorLevel(4)
                .corrected(2220)
                .detected(6660)
                .noErrors(80)
                .iterations(8960)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(7, 4, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel5() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_5))
                .errorLevel(5)
                .corrected(4140)
                .detected(17240)
                .noErrors(124)
                .iterations(21504)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(7, 5, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel6() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_5))
                .errorLevel(6)
                .corrected(6072)
                .detected(22380)
                .noErrors(220)
                .iterations(28672)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(7, 6, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }
}