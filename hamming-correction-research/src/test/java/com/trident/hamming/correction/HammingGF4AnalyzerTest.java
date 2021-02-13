package com.trident.hamming.correction;

import com.trident.hamming.correction.report.HammingCorrectionReportWriter;
import com.trident.hamming.correction.report.ImmutableHammingCorrectionReport;
import com.trident.hamming.correction.service.HammingCodeSequentialErrorsProvider;
import com.trident.hamming.correction.service.HammingErrorLevelAnalyzer;
import com.trident.math.field.GaloisFieldOverPoly;
import com.trident.math.field.GaloisFieldOverPolyElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;

import static com.trident.math.field.GaloisFields.GF_2_2;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrix;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingGF4AnalyzerTest {
    private static final FieldMatrix<GaloisFieldOverPolyElement> GENERATOR =
            toFieldMatrix(new long[][]{
                    new long[]{0, 0, 0, 1},
                    new long[]{1, 1, 1, 0},
                    new long[]{1, 2, 3, 1}
            }, GF_2_2);

    private static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_CODE = new HammingCode<>(GENERATOR);

    private static final HammingCorrectionReportWriter WRITER = new HammingCorrectionReportWriter(new PrintStream(OutputStream.nullOutputStream()), false);

    @Test
    void testErrorLevel1() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(1)
                .corrected(21)
                .detected(0)
                .noErrors(0)
                .iterations(21)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(1, HAMMING_CODE);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_CODE, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel2() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(2)
                .corrected(99)
                .detected(90)
                .noErrors(0)
                .iterations(189)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(2, HAMMING_CODE);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_CODE, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel3() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(3)
                .corrected(330)
                .detected(582)
                .noErrors(33)
                .iterations(945)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(3, HAMMING_CODE);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_CODE, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel4() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(4)
                .corrected(990)
                .detected(1812)
                .noErrors(33)
                .iterations(2835)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(4, HAMMING_CODE);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_CODE, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel5() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(5)
                .corrected(1425)
                .detected(3612)
                .noErrors(66)
                .iterations(5103)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(5, HAMMING_CODE);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_CODE, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel6() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(6)
                .corrected(1647)
                .detected(3378)
                .noErrors(78)
                .iterations(5103)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(6, HAMMING_CODE);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_CODE, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }

    @Test
    void testErrorLevel7() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(7)
                .corrected(864)
                .detected(1278)
                .noErrors(45)
                .iterations(2187)
                .build();
        var errorProvider = new HammingCodeSequentialErrorsProvider<>(7, HAMMING_CODE);
        var analyzer = new HammingErrorLevelAnalyzer<>(errorProvider, WRITER, HAMMING_CODE, new GaloisFieldOverPolyElement[0]);
        assertEquals(expected, analyzer.analyzeHammingCodeErrorLevel());
    }
}
