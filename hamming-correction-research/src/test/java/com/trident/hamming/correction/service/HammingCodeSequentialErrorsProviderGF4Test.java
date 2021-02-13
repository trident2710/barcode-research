package com.trident.hamming.correction.service;

import org.junit.jupiter.api.Test;

import static com.trident.math.hamming.HammingCodes.HAMMING_7_4_GF_2_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingCodeSequentialErrorsProviderGF4Test {

    @Test
    void testErrorsLevel1() {
        var iterator = new HammingCodeSequentialErrorsProvider<>(1, HAMMING_7_4_GF_2_2);
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
        var iterator = new HammingCodeSequentialErrorsProvider<>(2, HAMMING_7_4_GF_2_2);
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
        var iterator = new HammingCodeSequentialErrorsProvider<>(3, HAMMING_7_4_GF_2_2);
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
        var iterator = new HammingCodeSequentialErrorsProvider<>(4, HAMMING_7_4_GF_2_2);
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
        var iterator = new HammingCodeSequentialErrorsProvider<>(5, HAMMING_7_4_GF_2_2);
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
        var iterator = new HammingCodeSequentialErrorsProvider<>(6, HAMMING_7_4_GF_2_2);
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
        var iterator = new HammingCodeSequentialErrorsProvider<>(7, HAMMING_7_4_GF_2_2);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 7) * 3^7
        assertEquals(2187, count);
    }
}
