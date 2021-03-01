package com.trident.math.field;


import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GFPTest {

    @Test
    void testMultiplyOnZero() {
        assertEquals(GF5.getZero(), GF5.getOne().multiply(GF5.getZero()));
        assertEquals(GF5.getZero(), GF5.getZero().multiply(GF5.getZero()));
    }

    @Test
    void testDivideOnZero() {
        assertThrows(Exception.class, () -> GF5.getOne().divide(GF5.getZero()));
        assertThrows(Exception.class, () -> GF5.getZero().divide(GF5.getZero()));
    }

    @Test
    void testInverseZero() {
        assertThrows(Exception.class, GF5.getZero()::reciprocal);
    }

    @Test
    void testMultiplyOnOne() {
        assertEquals(GF5.getOfValue(23), GF5.getOfValue(23).multiply(GF5.getOne()));
    }

    @Test
    void testDivideOnOne() {
        assertEquals(GF5.getOfValue(23), GF5.getOfValue(23).divide(GF5.getOne()));
    }

    @Test
    void testMultiply() {
        assertEquals(GF5.getOne(), GF5.getOfValue(3).multiply(GF5.getOfValue(2)));
        assertEquals(GF5.getOne(), GF5.getOfValue(2).multiply(GF5.getOfValue(3)));
    }

    @Test
    void testDivide() {
        assertEquals(GF5.getOfValue(2), GF5.getOfValue(4).divide(GF5.getOfValue(2)));
    }

    @Test
    void testAdd() {
        assertEquals(GF5.getZero(), GF5.getOfValue(2).add(GF5.getOfValue(3)));
    }

    @Test
    void testModulo() {
        assertEquals(GF5.getZero(), GF5.getOfValue(5));
        assertEquals(GF5.getOfValue(3), GF5.getOfValue(23));
    }

    @Test
    void testMultiplicationSymmetric() {
        assertEquals(GF5.getOfValue(3).multiply(GF5.getOfValue(2)), GF5.getOfValue(2).multiply(GF5.getOfValue(3)));
    }

    @Test
    void testAdditionSymmetric() {
        assertEquals(GF5.getOfValue(3).add(GF5.getOfValue(2)), GF5.getOfValue(2).add(GF5.getOfValue(3)));
    }

    @Test
    void testInverse() {
        assertEquals(GF5.getOfValue(3), GF5.getOfValue(2).reciprocal());
        assertEquals(GF5.getOfValue(2), GF5.getOfValue(3).reciprocal());
    }

    @Test
    void testIterator() {
        var iterator = GF5.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(4, count);
    }
}