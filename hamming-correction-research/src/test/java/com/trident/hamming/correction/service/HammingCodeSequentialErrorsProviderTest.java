package com.trident.hamming.correction.service;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFieldOverPrimeType.GF5;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HammingCodeSequentialErrorsProviderTest {

    private static final GaloisFieldOverPrimeElement ONE = GF5.field().getOne();
    private static final GaloisFieldOverPrimeElement TWO = GF5.field().getOfValue(2);
    private static final GaloisFieldOverPrimeElement THREE = GF5.field().getOfValue(3);

    private static final FieldMatrix<GaloisFieldOverPrimeElement> GENERATOR = createMatrixOfRows(
            matrixRow(ONE, ONE, ONE),
            matrixRow(ONE, TWO, THREE)
    );

    private static final HammingCode HAMMING_CODE = new HammingCode(GENERATOR);

    @Test
    void testErrorsLevel1() {
        var iterator = new HammingCodeSequentialErrorsProvider(1, HAMMING_CODE);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(20, count);
    }

    @Test
    void testErrorsLevel2() {
        var iterator = new HammingCodeSequentialErrorsProvider(2, HAMMING_CODE);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(160, count);
    }

    @Test
    void testErrorsLevel3() {
        var iterator = new HammingCodeSequentialErrorsProvider(3, HAMMING_CODE);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(640, count);
    }

    @Test
    void testErrorsLevel4() {
        var iterator = new HammingCodeSequentialErrorsProvider(4, HAMMING_CODE);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(1280, count);
    }

}