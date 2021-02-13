package com.trident.math;

import cc.redberry.rings.IntegersZp64;
import cc.redberry.rings.Rings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RingsTest {

    private static final IntegersZp64 GF5 = Rings.Zp64(5);

    @Test
    void testAdd() {
        assertEquals(2, GF5.add(3, 4));
    }

    @Test
    void testDiv() {
        assertEquals(3, GF5.divide(4, 3));
    }

    @Test
    void testMul() {
        assertEquals(1, GF5.multiply(2, 3));
    }

    @Test
    void testSub() {
        assertEquals(3, GF5.subtract(2, 4));
    }

    @Test
    void testModulus() {
        assertEquals(1, GF5.modulus(11));
        assertEquals(3, GF5.modulus(28));
        assertEquals(4, GF5.modulus(9));
    }
}
