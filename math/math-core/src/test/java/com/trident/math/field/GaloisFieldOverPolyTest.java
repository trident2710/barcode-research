package com.trident.math.field;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFieldOverPoly.GF4;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GaloisFieldOverPolyTest {
    private static final GaloisFieldOverPolyElement ZERO = GF4.getZero();
    private static final GaloisFieldOverPolyElement ONE = GF4.getOne();
    private static final GaloisFieldOverPolyElement TWO = GF4.getOfValue(2);
    private static final GaloisFieldOverPolyElement THREE = GF4.getOfValue(3);
    private static final GaloisFieldOverPolyElement FOUR = GF4.getOfValue(4);
    private static final GaloisFieldOverPolyElement FIVE = GF4.getOfValue(5);

    @Test
    void testMultiplyOnZero() {
        assertEquals(ZERO, ONE.multiply(ZERO));
        assertEquals(ZERO, ZERO.multiply(ZERO));
    }

    @Test
    void testDivideOnZero() {
        assertThrows(Exception.class, () -> ONE.divide(ZERO));
        assertThrows(Exception.class, () -> ZERO.divide(ZERO));
    }

    @Test
    void testInverseZero() {
        assertThrows(Exception.class, ZERO::reciprocal);
    }

    @Test
    void testMultiplyOnOne() {
        assertEquals(TWO, TWO.multiply(ONE));
    }

    @Test
    void testDivideOnOne() {
        assertEquals(TWO, TWO.divide(ONE));
    }

    @Test
    void testMultiply() {
        assertEquals(ONE, THREE.multiply(TWO));
        assertEquals(ONE, TWO.multiply(THREE));
    }

    @Test
    void testDivide() {
        assertEquals(TWO, THREE.divide(TWO));
    }

    @Test
    void testAdd() {
        assertEquals(ONE, THREE.add(TWO));
    }

    @Test
    void testModulo() {
        assertEquals(ONE, FOUR);
        assertEquals(TWO, FIVE);
    }

    @Test
    void testMultiplicationSymmetric() {
        assertEquals(THREE.multiply(TWO), TWO.multiply(THREE));
    }

    @Test
    void testAdditionSymmetric() {
        assertEquals(THREE.add(TWO), TWO.add(THREE));
    }

    @Test
    void testInverse() {
        assertEquals(THREE, TWO.reciprocal());
        assertEquals(TWO, THREE.reciprocal());
    }
}
