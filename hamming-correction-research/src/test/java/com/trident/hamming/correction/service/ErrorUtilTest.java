package com.trident.hamming.correction.service;


import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static com.trident.math.field.GaloisFieldOverPrime.GF5;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorUtilTest {

    @Test
    void test() {
        var errors = new HashSet<FieldMatrix<GaloisFieldOverPrimeElement>>();
        for (int i = 0; i < 10000; i++) {
            var error = ErrorUtil.randomError(GF5, 2, 5);
            errors.add(error);
        }
        assertEquals(160, errors.size());
    }
}