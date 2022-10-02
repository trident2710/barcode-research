package com.trident.math.field;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GFPSimplePrimitiveElementCalculator.primitiveElement;
import static com.trident.math.field.GaloisFields.GF11;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GFPSimplePrimitiveElementCalculatorTest {

    @Test
    void testGF11() {
        assertEquals(GF11.getOfValue(2), primitiveElement(GF11));
    }
}