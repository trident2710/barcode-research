package com.trident.hamming.correction;

import com.trident.hamming.correction.report.ImmutableHammingCorrectionReport;
import com.trident.hamming.correction.service.*;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.math.field.GaloisFieldOverPrimeType.GF5;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        var analyzer = new HammingCorrectionAnalyzer(errorProvider, EmptyWriter.getInstance());
        assertEquals(expected, analyzer.analyzeHammingCodeCorrection(HAMMING_CODE));
    }

    @Test
    void testCustom1LevelErrors() {
        var errorProvider = new HammingCodeCustomErrorProvider(HAMMING_CODE, 1, List.of(
                matrixRow(ONE, ZERO, ZERO, ZERO, ZERO),
                matrixRow(TWO, ZERO, ZERO, ZERO, ZERO),
                matrixRow(THREE, ZERO, ZERO, ZERO, ZERO)
        ));
        var message = matrixRow(FOUR, ZERO, THREE);
        var analyzer = new HammingCorrectionAnalyzer(errorProvider, EmptyWriter.getInstance());
        var result = analyzer.analyzeHammingCodeCorrection(HAMMING_CODE, message);

        assertEquals(3, result.corrected());
    }

    @Test
    void test2LevelErrors() {
        var errorProvider = new HammingCodeAllErrorsProvider(2, HAMMING_CODE, 160);
        var analyzer = new HammingCorrectionAnalyzer(errorProvider, EmptyWriter.getInstance());
        var message = matrixRow(FOUR, ZERO, THREE);
        var result = analyzer.analyzeHammingCodeCorrection(HAMMING_CODE, message);
        assertEquals(40, result.detected());
        assertEquals(120, result.corrected());
    }

    @Test
    void test3LevelErrors() {
        var errorProvider = new HammingCodeAllErrorsProvider(3, HAMMING_CODE, 640);
        var analyzer = new HammingCorrectionAnalyzer(errorProvider, EmptyWriter.getInstance());
        var message = matrixRow(FOUR, ZERO, THREE);
        var result = analyzer.analyzeHammingCodeCorrection(HAMMING_CODE, message);
        assertEquals(40, result.noErrors());
        assertEquals(80, result.detected());
        assertEquals(520, result.corrected());
    }
}
