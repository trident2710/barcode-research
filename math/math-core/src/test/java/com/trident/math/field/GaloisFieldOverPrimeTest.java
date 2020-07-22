package com.trident.math.field;

import static com.trident.math.field.GaloisFieldOverPrimeType.GF5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GaloisFieldOverPrimeTest {
    private static final GaloisFieldOverPrimeElement ZERO = GF5.field().getZero();
    private static final GaloisFieldOverPrimeElement ONE = GF5.field().getOne();
    private static final GaloisFieldOverPrimeElement TWO = GF5.field().getOfValue(2);
    private static final GaloisFieldOverPrimeElement THREE = GF5.field().getOfValue(3);
    private static final GaloisFieldOverPrimeElement FOUR = GF5.field().getOfValue(4);
    private static final GaloisFieldOverPrimeElement FIVE = GF5.field().getOfValue(5);
    private static final GaloisFieldOverPrimeElement TWENTY_THREE = GF5.field().getOfValue(23);

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