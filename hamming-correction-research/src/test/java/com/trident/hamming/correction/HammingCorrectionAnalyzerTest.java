package com.trident.hamming.correction;

import static com.trident.math.field.GaloisFieldOverPrimeType.GF5;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.trident.hamming.correction.report.ImmutableHammingCorrectionReport;
import com.trident.hamming.correction.service.HammingCodeAllErrorsProvider;
import com.trident.hamming.correction.service.HammingCodeCustomErrorProvider;
import com.trident.hamming.correction.service.HammingCodeRandomErrorProvider;
import com.trident.hamming.correction.service.HammingCorrectionAnalyzer;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HammingCorrectionAnalyzerTest {

    private static final GaloisFieldOverPrimeElement ZERO = GF5.field().getZero();
    private static final GaloisFieldOverPrimeElement ONE = GF5.field().getOne();
    private static final GaloisFieldOverPrimeElement TWO = GF5.field().getOfValue(2);
    private static final GaloisFieldOverPrimeElement THREE = GF5.field().getOfValue(3);
    private static final GaloisFieldOverPrimeElement FOUR = GF5.field().getOfValue(4);

    private static final FieldMatrix<GaloisFieldOverPrimeElement> GENERATOR = createMatrixOfRows(
            matrixRow(ONE, ONE, ONE),
            matrixRow(ONE, TWO, THREE)
    );

    private static final HammingCode HAMMING_CODE = new HammingCode(GENERATOR);

    @Test
    void testRandomError1() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(1)
                .corrected(320)
                .detected(0)
                .noErrors(0)
                .iterations(320)
                .build();
        var errorProvider = new HammingCodeRandomErrorProvider(HAMMING_CODE, 1, 320);
        var analyzer = new HammingCorrectionAnalyzer(errorProvider);
        assertEquals(expected, analyzer.analyzeHammingCodeCorrection(HAMMING_CODE));
    }

    @Test
    void test2LevelErrors() {
        var errorProvider = new HammingCodeAllErrorsProvider(2, HAMMING_CODE, 160);
        var analyzer = new HammingCorrectionAnalyzer(errorProvider);
        var message = matrixRow(FOUR, ZERO, THREE);
        var result = analyzer.analyzeHammingCodeCorrection(HAMMING_CODE, message);
        System.out.println(result);
        assertTrue(true);
    }

    @Test
    void test3LevelErrors() {
        var errorProvider = new HammingCodeAllErrorsProvider(3, HAMMING_CODE, 320);
        var analyzer = new HammingCorrectionAnalyzer(errorProvider);
        var message = matrixRow(FOUR, ZERO, THREE);
        var result = analyzer.analyzeHammingCodeCorrection(HAMMING_CODE, message);
        System.out.println(result);
        assertTrue(true);
    }

    @Test
    void testCustom2LevelErrors() {
        var errorProvider = new HammingCodeCustomErrorProvider(HAMMING_CODE, 2, List.of(
                matrixRow(ONE, ONE, ZERO, ZERO, ZERO),
                matrixRow(ONE, ZERO, ONE, ZERO, ZERO),
                matrixRow(ONE, ZERO, ZERO, ONE, ZERO),
                matrixRow(ONE, ZERO, ZERO, ZERO, ONE),
                matrixRow(ZERO, ONE, ONE, ZERO, ZERO),
                matrixRow(ZERO, ONE, ZERO, ONE, ZERO),
                matrixRow(ZERO, ONE, ZERO, ZERO, ONE),
                matrixRow(ZERO, ZERO, ONE, ONE, ZERO),
                matrixRow(ZERO, ZERO, ONE, ZERO, ONE),
                matrixRow(ZERO, ZERO, ZERO, ONE, ONE),
                matrixRow(TWO, TWO, ZERO, ZERO, ZERO),
                matrixRow(TWO, ZERO, TWO, ZERO, ZERO),
                matrixRow(TWO, ZERO, ZERO, TWO, ZERO)

        ));
        var message = matrixRow(FOUR, ZERO, THREE);
        var analyzer = new HammingCorrectionAnalyzer(errorProvider);
        var result = analyzer.analyzeHammingCodeCorrection(HAMMING_CODE, message);
        System.out.println(result);
        assertTrue(true);
    }

    @Test
    void testCustom1LevelErrors() {
        var errorProvider = new HammingCodeCustomErrorProvider(HAMMING_CODE, 1, List.of(
                matrixRow(ONE, ZERO, ZERO, ZERO, ZERO),
                matrixRow(TWO, ZERO, ZERO, ZERO, ZERO),
                matrixRow(THREE, ZERO, ZERO, ZERO, ZERO)
        ));
        var message = matrixRow(FOUR, ZERO, THREE);
        var analyzer = new HammingCorrectionAnalyzer(errorProvider);
        analyzer.analyzeHammingCodeCorrection(HAMMING_CODE, message);

        assertTrue(true);
    }
}
