package com.trident.math.field;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.trident.math.field.GaloisFields.GF_4_2;
import static com.trident.math.field.GaloisFields.GF_8_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GaloisFieldOverPolyExtensionTest {

    @Test
    void test_gf_4_2() {
        var field = GF_4_2;
        var expectedValues = List.of(1L, 4L, 6L, 14L, 5L, 2L, 8L, 11L, 7L, 10L, 3L, 12L, 13L, 9L, 15L);

        var elem = field.getOne();
        var actualValues = new ArrayList<Long>();
        do {
            actualValues.add(elem.digitalRepresentation());
            elem = field.mul(elem, field.getOfValue(4));
        } while (!elem.equals(field.getOne()));

        assertEquals(expectedValues, List.copyOf(actualValues));
    }

    @Test
    void test_gf_8_2() {
        var field = GF_8_2;
        var expectedValues = List.of(1L, 8L, 11L, 19L, 14L, 59L, 34L, 55L, 9L, 3L, 24L, 29L,
                53L, 25L, 21L, 62L, 10L, 27L, 5L, 40L, 44L, 12L, 43L, 52L, 17L, 30L, 45L, 4L, 32L, 39L,
                31L, 37L, 15L, 51L, 41L, 36L, 7L, 56L, 58L, 42L, 60L, 26L, 13L, 35L, 63L, 2L, 16L, 22L,
                38L, 23L, 46L, 28L, 61L, 18L, 6L, 48L, 49L, 57L, 50L, 33L, 47L, 20L, 54L);

        var elem = field.getOne();
        var actualValues = new ArrayList<Long>();
        do {
            actualValues.add(elem.digitalRepresentation());
            elem = field.mul(elem, field.getOfValue(8));
        } while (!elem.equals(field.getOne()));

        assertEquals(expectedValues, List.copyOf(actualValues));
    }
}
