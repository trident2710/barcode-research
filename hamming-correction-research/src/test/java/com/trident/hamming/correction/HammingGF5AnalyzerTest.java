package com.trident.hamming.correction;

import com.trident.hamming.correction.report.HammingCorrectionReportWriter;
import com.trident.hamming.correction.report.ImmutableHammingCorrectionReport;
import com.trident.hamming.correction.service.HammingCodeSequentialErrorsProvider;
import com.trident.hamming.correction.service.HammingErrorLevelAnalyzer;
import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;

import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrix;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingGF5AnalyzerTest {

    private static final FieldMatrix<GaloisFieldOverPrimeElement> GENERATOR =
            toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1},
                    new long[]{1, 2, 3}
            }, GF5);

    private static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_CODE = new HammingCode<>(GENERATOR);

    private static final HammingCorrectionReportWriter WRITER = new HammingCorrectionReportWriter(new PrintStream(OutputStream.nullOutputStream()), false);

    @Test
    void testErrorLevel1() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(1)
                .corrected(20)
                .detected(0)
                .noErrors(0)
                .iterations(20)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(1, HAMMING_CODE);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_CODE, new GaloisFieldOverPrimeElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel2() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(2)
                .corrected(120)
                .detected(40)
                .noErrors(0)
                .iterations(160)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(2, HAMMING_CODE);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_CODE, new GaloisFieldOverPrimeElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel3() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(3)
                .corrected(520)
                .detected(80)
                .noErrors(40)
                .iterations(640)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(3, HAMMING_CODE);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_CODE, new GaloisFieldOverPrimeElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel4() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(4)
                .corrected(1020)
                .detected(220)
                .noErrors(40)
                .iterations(1280)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(4, HAMMING_CODE);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_CODE, new GaloisFieldOverPrimeElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel5() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(5)
                .corrected(820)
                .detected(160)
                .noErrors(44)
                .iterations(1024)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(5, HAMMING_CODE);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_CODE, new GaloisFieldOverPrimeElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }
}
