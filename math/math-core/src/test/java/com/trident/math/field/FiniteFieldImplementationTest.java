package com.trident.math.field;

import cc.redberry.rings.poly.FiniteField;
import cc.redberry.rings.poly.univar.UnivariatePolynomialZ64;
import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FiniteFieldImplementationTest {

    // GF(2^2) irreducible poly = x^2 + x + 1
    private final FiniteField<UnivariatePolynomialZp64> field = new FiniteField<>(UnivariatePolynomialZ64.create(1, 1, 1).modulus(2));

    @Test
    void testDegree() {
        assertEquals(2, field.degree());
    }

    @Test
    void testAdd() {
        // 7 -> x^6 -> 1
        var seven = UnivariatePolynomialZp64.monomial(field.degree(), 1, 6);
        // 6 -> x^5 -> x + 1
        var six = UnivariatePolynomialZp64.monomial(field.degree(), 1, 5);

        // x + 1 + 1 = x
        var expected = UnivariatePolynomialZp64.monomial(field.degree(), 1, 1);
        assertEquals(expected, field.add(seven, six));
    }

    @Test
    void testSymmetry() {
        // 3 -> x^2 -> x + 1
        var three = UnivariatePolynomialZp64.monomial(field.degree(), 1, 2);
        assertEquals(field.getZero(), field.add(three, three));
        assertEquals(field.getZero(), field.subtract(three, three));
    }

    @Test
    void testSub() {
        // 3 -> x^2 -> x + 1
        var three = UnivariatePolynomialZp64.monomial(field.degree(), 1, 2);
        // 1 -> x
        var one = UnivariatePolynomialZp64.monomial(field.degree(), 1, 1);

        // x + 1 + x = 1
        assertEquals(field.getOne(), field.add(three, one));
        assertEquals(field.getOne(), field.subtract(three, one));
    }

    @Test
    void testMul() {
        // 7 -> x^6 -> 1
        var seven = UnivariatePolynomialZp64.monomial(field.degree(), 1, 6);
        // 6 -> x^5 -> x + 1
        var six = UnivariatePolynomialZp64.monomial(field.degree(), 1, 5);

        var expected = UnivariatePolynomialZ64.create(1, 1).modulus(2);
        assertEquals(expected, field.multiply(seven, six));
    }

    @Test
    void testInverse() {
        assertThrows(Exception.class, () -> field.reciprocal(field.getZero()));
        assertEquals(field.getOne(), field.reciprocal(field.getOne()));
        // 2 -> x^1 -> x
        var two = UnivariatePolynomialZp64.monomial(field.degree(), 1, 1);
        // 3 -> x^2 -> x + 1
        var three = UnivariatePolynomialZ64.create(1, 1).modulus(2);
        assertEquals(two, field.reciprocal(three));
        assertEquals(three, field.reciprocal(two));
    }

    @Test
    void testNegate() {
        var three = UnivariatePolynomialZ64.create(1, 1).modulus(2);
        // 3 + 3 = 3 - 3 = 0
        assertEquals(three, field.negate(three));
    }

    @Test
    void testDivide() {
        // 2 -> x^1 -> x
        var two = UnivariatePolynomialZp64.monomial(field.degree(), 1, 1);
        // 3 -> x^2 -> x + 1
        var three = UnivariatePolynomialZ64.create(1, 1).modulus(2);

        // 2 % 3 = 2 * inv(3) = 2 * 2 = x*x = x^2 = x + 1 = 3
        assertEquals(three, field.divideExact(two, three));
    }

    @Test
    void testModulo() {
        // x^3
        var four = UnivariatePolynomialZp64.monomial(field.degree(), 1, 3);
        // 1 ( x^3 % x^2 + x + 1 = 1)

        assertEquals(field.getOne(), field.valueOf(four));

        // x^5
        var six = UnivariatePolynomialZp64.monomial(field.degree(), 1, 5);
        // x^2 = x + 1
        var three = UnivariatePolynomialZ64.create(1, 1).modulus(2);

        assertEquals(field.valueOf(six), three);
    }
}
