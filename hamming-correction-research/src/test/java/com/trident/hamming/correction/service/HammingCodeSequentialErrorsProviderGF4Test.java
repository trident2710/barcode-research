package com.trident.hamming.correction.service;

import com.trident.math.field.GaloisFieldOverPoly;
import com.trident.math.field.GaloisFieldOverPolyElement;
import com.trident.math.hamming.HammingCode;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF_2_2;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrix;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingCodeSequentialErrorsProviderGF4Test {
    private static final FieldMatrix<GaloisFieldOverPolyElement> GENERATOR =
            toFieldMatrix(new long[][]{
                    new long[]{0, 0, 0, 1},
                    new long[]{1, 1, 1, 0},
                    new long[]{1, 2, 3, 1}
            }, GF_2_2);

    private static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_CODE = new HammingCode<>(GENERATOR);

    @Test
    void testErrorsLevel1() {
        var iterator = new HammingCodeSequentialErrorsProvider<>(1, HAMMING_CODE);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 1) * 3
        assertEquals(21, count);
    }

    @Test
    void testErrorsLevel2() {
        var iterator = new HammingCodeSequentialErrorsProvider<>(2, HAMMING_CODE);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 2) * 3^2
        assertEquals(189, count);
    }

    @Test
    void testErrorsLevel3() {
        var iterator = new HammingCodeSequentialErrorsProvider<>(3, HAMMING_CODE);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 3) * 3^3
        assertEquals(945, count);
    }

    @Test
    void testErrorsLevel4() {
        var iterator = new HammingCodeSequentialErrorsProvider<>(4, HAMMING_CODE);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 4) * 3^4
        assertEquals(2835, count);
    }

    @Test
    void testErrorsLevel5() {
        var iterator = new HammingCodeSequentialErrorsProvider<>(5, HAMMING_CODE);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 5) * 3^5
        assertEquals(5103, count);
    }

    @Test
    void testErrorsLevel6() {
        var iterator = new HammingCodeSequentialErrorsProvider<>(6, HAMMING_CODE);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 6) * 3^6
        assertEquals(5103, count);
    }

    @Test
    void testErrorsLevel7() {
        var iterator = new HammingCodeSequentialErrorsProvider<>(7, HAMMING_CODE);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 7) * 3^7
        assertEquals(2187, count);
    }
}
