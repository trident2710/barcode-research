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
import static com.trident.math.hamming.HammingCodes.HAMMING_6_3_GF_5;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Hamming6_3GF_5Test {

    private static final HammingCorrectionReportWriter WRITER = new HammingCorrectionReportWriter(new PrintStream(OutputStream.nullOutputStream()), false);


    @Test
    void testErrorLevel1() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_6_3_GF_5))
                .errorLevel(1)
                .corrected(24)
                .detected(0)
                .noErrors(0)
                .iterations(24)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(6, 1, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_6_3_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel2() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_6_3_GF_5))
                .errorLevel(2)
                .corrected(48)
                .detected(192)
                .noErrors(0)
                .iterations(240)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(6, 2, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_6_3_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel3() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_6_3_GF_5))
                .errorLevel(3)
                .corrected(272)
                .detected(992)
                .noErrors(16)
                .iterations(1280)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(6, 3, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_6_3_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel4() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_6_3_GF_5))
                .errorLevel(4)
                .corrected(736)
                .detected(3072)
                .noErrors(32)
                .iterations(3840)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(6, 4, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_6_3_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel6() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_6_3_GF_5))
                .errorLevel(6)
                .corrected(920)
                .detected(3132)
                .noErrors(44)
                .iterations(4096)
                .build();
        var errorProvider = new FieldErrorVectorSequentialProvider<>(6, 6, GF5);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_6_3_GF_5, new GFPElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }
}
