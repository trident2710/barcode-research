package com.trident.math.field;

import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolynomialZp64Test {

    @Test
    void testMultiply() {
        var first = UnivariatePolynomialZp64.create(11, new long[]{2, 8, 2, 7, 5, 6, 1});
        var second = UnivariatePolynomialZp64.create(11, new long[]{1, 5, 3});
        var expected = UnivariatePolynomialZp64.create(11, new long[]{2, 7, 4, 8, 2, 8, 2, 1, 3});

        assertEquals(expected, first.multiply(second));
    }
}
