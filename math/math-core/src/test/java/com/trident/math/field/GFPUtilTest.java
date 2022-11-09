package com.trident.math.field;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GFPUtil.primitiveElement;
import static com.trident.math.field.GaloisFields.GF11;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GFPUtilTest {

    @Test
    void testPrimitiveElement() {
        assertEquals(GF11.getOfValue(2), primitiveElement(GF11));
    }

    @Test
    void testPowerOfPrimitive() {
        assertEquals(2, GFPUtil.powerOfPrimitive(GF11.getOfValue(4)));
        assertEquals(4, GFPUtil.powerOfPrimitive(GF11.getOfValue(5)));
        assertEquals(7, GFPUtil.powerOfPrimitive(GF11.getOfValue(7)));
        assertEquals(5, GFPUtil.powerOfPrimitive(GF11.getOfValue(10)));
    }
}