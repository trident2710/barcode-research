package com.trident.correction;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF5;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GF5ErrorVectorProviderTest {
    @Test
    void testErrorsLevel1() {
        var iterator = new FieldErrorVectorSequentialProvider<>(5, 1, GF5);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(20, count);
    }

    @Test
    void testErrorsLevel2() {
        var iterator = new FieldErrorVectorSequentialProvider<>(5, 2, GF5);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(160, count);
    }

    @Test
    void testErrorsLevel3() {
        var iterator = new FieldErrorVectorSequentialProvider<>(5, 3, GF5);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(640, count);
    }

    @Test
    void testErrorsLevel4() {
        var iterator = new FieldErrorVectorSequentialProvider<>(5, 4, GF5);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(1280, count);
    }
}
