package com.trident.math.bch;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.FiniteFieldEquation.solveLinearEquation;
import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.field.GaloisFields.GF_3_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FiniteFieldEquationTest {

    @Test
    void testFieldOverPrime() {
        var a = GF5.getOfValue(2);
        var b = GF5.getOfValue(4);
        var expectedX = GF5.getOfValue(3);
        var actual = solveLinearEquation(GF5, a, b);
        assertEquals(expectedX, actual);
    }

    @Test
    void testFieldOverPoly() {
        var a = GF_3_2.getOfValue(1);
        var b = GF_3_2.getOfValue(1);
        var expectedX = GF_3_2.getOfValue(2);
        var actual = solveLinearEquation(GF_3_2, a, b);
        assertEquals(expectedX, actual);
    }


    @Test
    void testZeros() {
        assertThrows(Exception.class, () -> solveLinearEquation(GF5, GF5.getZero(), GF5.getOne()));
        assertEquals(GF5.getZero(), solveLinearEquation(GF5, GF5.getOne(), GF5.getZero()));
    }
}