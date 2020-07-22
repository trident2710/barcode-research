package com.trident.hamming.correction;

import static com.trident.hamming.correction.service.HammingCorrectionAnalyzer.analyzeHammingCodeCorrection;
import static com.trident.math.field.GaloisFieldType.GF5;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.trident.hamming.correction.report.ImmutableHammingCorrectionReport;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

public class HammingCorrectionAnalyzerTest {

    private static final GaloisFieldOverPrimeElement ONE = GF5.field().getOne();
    private static final GaloisFieldOverPrimeElement TWO = GF5.field().getOfValue(2);
    private static final GaloisFieldOverPrimeElement THREE = GF5.field().getOfValue(3);

    private static final FieldMatrix<GaloisFieldOverPrimeElement> GENERATOR = createMatrixOfRows(
            matrixRow(ONE, ONE, ONE),
            matrixRow(ONE, TWO, THREE)
    );

    private static final HammingCode HAMMING_CODE = new HammingCode(GENERATOR);

    @Test
    void test() {
        var expected = ImmutableHammingCorrectionReport.builder()
                .hammingCode(HammingCodeConverter.toDto(HAMMING_CODE))
                .errorLevel(1)
                .corrected(100)
                .detected(0)
                .noErrors(0)
                .iterations(100)
                .build();
        assertEquals(expected, analyzeHammingCodeCorrection(HAMMING_CODE, 1, 100));
    }
}
