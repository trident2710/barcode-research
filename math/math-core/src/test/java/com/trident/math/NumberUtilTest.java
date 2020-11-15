package com.trident.math;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberUtilTest {

    @Test
    void testFromTernary() {
        var ternary = new long[]{1, 0, 2, 1, 2, 2};
        var expectedDecimal = 694;
        assertEquals(expectedDecimal, NumberUtil.fromNBased(ternary, 3));
    }

    @Test
    void testToTernary() {
        var decimal = 693;
        var expectedTernary = new long[]{0, 0, 2, 1, 2, 2};
        var actual = NumberUtil.toNBased(decimal, 3);
        assertArrayEquals(expectedTernary, actual, "Expected: " + Arrays.toString(expectedTernary) + " actual: " + Arrays.toString(actual));
    }

    @Test
    void testZero() {
        var ternary = new long[]{0};
        assertEquals(0, NumberUtil.fromNBased(ternary, 1234));
        assertArrayEquals(new long[]{0}, NumberUtil.toNBased(0, 4321));
    }

}