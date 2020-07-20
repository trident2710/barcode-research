package com.trident.math.field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GaloisFieldOverPrimeTest {

    private static final GaloisFieldOverPrime GF_5 = new GaloisFieldOverPrime(5L);
    private static final GaloisFieldOverPrimeElement ZERO = new GaloisFieldOverPrimeElement(GF_5, 0L);
    private static final GaloisFieldOverPrimeElement ONE = new GaloisFieldOverPrimeElement(GF_5, 1L);
    private static final GaloisFieldOverPrimeElement TWO = new GaloisFieldOverPrimeElement(GF_5, 2L);
    private static final GaloisFieldOverPrimeElement THREE = new GaloisFieldOverPrimeElement(GF_5, 3L);
    private static final GaloisFieldOverPrimeElement FOUR = new GaloisFieldOverPrimeElement(GF_5, 4L);
    private static final GaloisFieldOverPrimeElement FIVE = new GaloisFieldOverPrimeElement(GF_5, 5L);
    private static final GaloisFieldOverPrimeElement TWENTY_THREE = new GaloisFieldOverPrimeElement(GF_5, 23L);

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
        assertEquals(TWENTY_THREE, TWENTY_THREE.multiply(ONE));
    }

    @Test
    void testDivideOnOne() {
        assertEquals(TWENTY_THREE, TWENTY_THREE.multiply(ONE));
    }

    @Test
    void testMultiply() {
        assertEquals(ONE, THREE.multiply(TWO));
        assertEquals(ONE, TWO.multiply(THREE));
    }

    @Test
    void testDivide() {
        assertEquals(TWO, FOUR.divide(TWO));
    }

    @Test
    void testAdd() {
        assertEquals(ZERO, TWO.add(THREE));
    }

    @Test
    void testModulo() {
        assertEquals(ZERO, FIVE);
        assertEquals(THREE, TWENTY_THREE);
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