package com.trident.math.bch;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.math.field.FiniteFieldEquation.calculatePolyValue;
import static com.trident.math.field.FiniteFieldEquation.solveEquation;
import static com.trident.math.field.FiniteFieldEquation.solveLinearEquation;
import static com.trident.math.field.GaloisFields.GF11;
import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.field.GaloisFields.GF_3_2;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
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

    @Test
    void testSolveEquation() {
        var equation = toFieldMatrixRow(new long[]{1, 8, 7}, GF11);
        var expectedRoots = List.of(GF11.getOfValue(3), GF11.getOfValue(10));

        assertEquals(expectedRoots, solveEquation(equation));
    }

    @Test
    void testPolyValue() {
        var poly = toFieldMatrixRow(new long[]{1, 4, 0, 10, 1}, GF11);
        var x = GF11.getOfValue(4).reciprocal();

        assertEquals(GF11.getOne(), calculatePolyValue(poly, x));
    }
}