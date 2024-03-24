package com.trident.math;

import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;

import static com.trident.math.PolyUtil.*;
import static com.trident.math.field.GaloisFields.GF11;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.*;


class PolyUtilTest {

    @Test
    public void testParsePolynomial() {
        assertArrayEquals(new long[]{2, 3, 0, 0, 5}, stringToPoly("5x^4+3x+2"));
    }

    @Test
    public void testParsePolynomial_noCoefficient() {
        assertArrayEquals(new long[]{1, 1}, stringToPoly("x+1"));
    }

    @Test
    public void testParsePolynomial_spaces() {
        assertArrayEquals(new long[]{2, 3, 0, 0, 5}, stringToPoly(" 5x^4 + 3x + 2 "));
    }

    @Test
    public void testParsePolynomial_monomial_degree0() {
        assertArrayEquals(new long[]{3}, stringToPoly("3"));
    }

    @Test
    public void testParsePolynomial_monomial_degree1() {
        assertArrayEquals(new long[]{0, 3}, stringToPoly("3x"));
    }

    @Test
    public void testParsePolynomial_monomial_degree2() {
        assertArrayEquals(new long[]{0, 0, 3}, stringToPoly("3x^2"));
    }

    @Test
    void testMultiply() {
        var first = toFieldMatrixRow(new long[]{-1, 1}, GF11);
        var second = toFieldMatrixRow(new long[]{-1, 1}, GF11);

        var expected = toFieldMatrixRow(new long[]{1, -2, 1}, GF11);

        assertEquals(expected, multiplyPolynomials(first, second));

    }

    @Test
    void testDivide() {
        var first = toFieldMatrixRow(new long[]{2, 7, 4, 8, 2, 8, 2, 1, 3}, GF11);
        var second = toFieldMatrixRow(new long[]{2, 8, 2, 7, 5, 6, 1}, GF11);
        var expected = toFieldMatrixRow(new long[]{1, 5, 3}, GF11);

        assertEquals(Pair.create(expected, toFieldMatrixRow(new long[]{0}, GF11)), dividePolynomialsWithRest(first, second));
    }

    @Test
    void testDivide_self() {
        var first = toFieldMatrixRow(new long[]{2, 7, 4, 8, 2, 8, 2, 1, 3}, GF11);
        var second = toFieldMatrixRow(new long[]{2, 7, 4, 8, 2, 8, 2, 1, 3}, GF11);
        var expected = toFieldMatrixRow(new long[]{1}, GF11);

        assertEquals(Pair.create(expected, toFieldMatrixRow(new long[]{0}, GF11)), dividePolynomialsWithRest(first, second));
    }

    @Test
    void testDivide_one() {
        var first = toFieldMatrixRow(new long[]{2, 7, 4, 8, 2, 8, 2, 1, 3}, GF11);
        var second = toFieldMatrixRow(new long[]{1}, GF11);
        var expected = toFieldMatrixRow(new long[]{2, 7, 4, 8, 2, 8, 2, 1, 3}, GF11);

        assertEquals(Pair.create(expected, toFieldMatrixRow(new long[]{0}, GF11)), dividePolynomialsWithRest(first, second));
    }

    @Test
    void testDivide_higherDegree() {
        var first = toFieldMatrixRow(new long[]{2, 7, 4, 8, 2, 8, 2, 1, 3}, GF11);
        var second = toFieldMatrixRow(new long[]{2, 7, 4, 8, 2, 8, 2, 1, 3, 1}, GF11);

        assertThrows(Exception.class, () -> dividePolynomialsWithRest(first, second));
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
