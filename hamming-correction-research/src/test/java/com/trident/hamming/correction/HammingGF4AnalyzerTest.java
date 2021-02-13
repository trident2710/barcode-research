package com.trident.hamming.correction;

import com.trident.hamming.correction.report.HammingCorrectionReportWriter;
import com.trident.hamming.correction.report.ImmutableHammingCorrectionReport;
import com.trident.hamming.correction.service.HammingCodeSequentialErrorsProvider;
import com.trident.hamming.correction.service.HammingErrorLevelAnalyzer;
import com.trident.math.field.GaloisFieldOverPolyElement;
import com.trident.math.io.converter.HammingCodeConverter;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;

import static com.trident.math.hamming.HammingCodes.HAMMING_7_4_GF_2_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingGF4AnalyzerTest {

    private static final HammingCorrectionReportWriter WRITER = new HammingCorrectionReportWriter(new PrintStream(OutputStream.nullOutputStream()), false);

    @Test
    void testErrorLevel1() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_2_2))
                .errorLevel(1)
                .corrected(21)
                .detected(0)
                .noErrors(0)
                .iterations(21)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(1, HAMMING_7_4_GF_2_2);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_2_2, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel2() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_2_2))
                .errorLevel(2)
                .corrected(99)
                .detected(90)
                .noErrors(0)
                .iterations(189)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(2, HAMMING_7_4_GF_2_2);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_2_2, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel3() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_2_2))
                .errorLevel(3)
                .corrected(330)
                .detected(582)
                .noErrors(33)
                .iterations(945)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(3, HAMMING_7_4_GF_2_2);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_2_2, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel4() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_2_2))
                .errorLevel(4)
                .corrected(990)
                .detected(1812)
                .noErrors(33)
                .iterations(2835)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(4, HAMMING_7_4_GF_2_2);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_2_2, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel5() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_2_2))
                .errorLevel(5)
                .corrected(1425)
                .detected(3612)
                .noErrors(66)
                .iterations(5103)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(5, HAMMING_7_4_GF_2_2);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_2_2, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel6() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_2_2))
                .errorLevel(6)
                .corrected(1647)
                .detected(3378)
                .noErrors(78)
                .iterations(5103)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(6, HAMMING_7_4_GF_2_2);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_2_2, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel7() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_7_4_GF_2_2))
                .errorLevel(7)
                .corrected(864)
                .detected(1278)
                .noErrors(45)
                .iterations(2187)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(7, HAMMING_7_4_GF_2_2);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_7_4_GF_2_2, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }
}
