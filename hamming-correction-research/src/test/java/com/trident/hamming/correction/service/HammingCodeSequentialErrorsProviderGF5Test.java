package com.trident.hamming.correction.service;

import org.junit.jupiter.api.Test;

import static com.trident.math.hamming.HammingCodes.HAMMING_5_3_GF_5;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HammingCodeSequentialErrorsProviderGF5Test {

    @Test
    void testErrorsLevel1() {
        var iterator = new HammingCodeSequentialErrorsProvider<>(1, HAMMING_5_3_GF_5);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(20, count);
    }

    @Test
    void testErrorsLevel2() {
        var iterator = new HammingCodeSequentialErrorsProvider<>(2, HAMMING_5_3_GF_5);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(160, count);
    }

    @Test
    void testErrorsLevel3() {
        var iterator = new HammingCodeSequentialErrorsProvider<>(3, HAMMING_5_3_GF_5);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(640, count);
    }

    @Test
    void testErrorsLevel4() {
        var iterator = new HammingCodeSequentialErrorsProvider<>(4, HAMMING_5_3_GF_5);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(1280, count);
    }
}