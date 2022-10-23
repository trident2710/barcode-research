package com.trident.math;

import org.junit.jupiter.api.Test;

import static com.trident.math.PolyUtil.addPolynomials;
import static com.trident.math.PolyUtil.multiplyPolynomials;
import static com.trident.math.PolyUtil.polyToString;
import static com.trident.math.PolyUtil.subtractPolynomials;
import static com.trident.math.field.GaloisFields.GF11;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;


class PolyUtilTest {


    @Test
    void testMultiply() {
        var first = toFieldMatrixRow(new long[]{-1, 1}, GF11);
        var second = toFieldMatrixRow(new long[]{-1, 1}, GF11);

        var expected = toFieldMatrixRow(new long[]{1, -2, 1}, GF11);

        assertEquals(expected, multiplyPolynomials(first, second));

    }

    @Test
    void testAdd() {
        var first = toFieldMatrixRow(new long[]{-1, 1}, GF11);
        var second = toFieldMatrixRow(new long[]{-1, 1}, GF11);

        var expected = toFieldMatrixRow(new long[]{-2, 2}, GF11);

        assertEquals(expected, addPolynomials(first, second));

    }

    @Test
    void testSubtract() {
        var first = toFieldMatrixRow(new long[]{1, 1}, GF11);
        var second = toFieldMatrixRow(new long[]{0, 1}, GF11);

        var expected = toFieldMatrixRow(new long[]{1}, GF11);

        assertEquals(expected, subtractPolynomials(first, second));

    }

    @Test
    void testToString() {
        var poly = new long[]{1, 0, 2, 0, 3, 0, 4, 0, 5};
        var expected = "5x^8 + 4x^6 + 3x^4 + 2x^2 + 1";
        assertEquals(expected, polyToString(poly));
    }

    @Test
    void testToString_0() {
        var poly = new long[]{0};
        var expected = "0";
        assertEquals(expected, polyToString(poly));
    }

    @Test
    void testToString_1() {
        var poly = new long[]{1};
        var expected = "1";
        assertEquals(expected, polyToString(poly));
    }

    @Test
    void testToString_monomial() {
        var poly = new long[]{0, 3, 0};
        var expected = "3x";
        assertEquals(expected, polyToString(poly));
    }

}