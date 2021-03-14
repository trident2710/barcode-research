package com.trident.correction;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF_2_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GF4ErrorVectorProviderTest {

    @Test
    void testErrorsLevel1() {
        var iterator = new FieldErrorVectorSequentialProvider<>(7, 1, GF_2_2);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 1) * 3
        assertEquals(21, count);
    }

    @Test
    void testErrorsLevel2() {
        var iterator = new FieldErrorVectorSequentialProvider<>(7, 2, GF_2_2);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 2) * 3^2
        assertEquals(189, count);
    }

    @Test
    void testErrorsLevel3() {
        var iterator = new FieldErrorVectorSequentialProvider<>(7, 3, GF_2_2);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 3) * 3^3
        assertEquals(945, count);
    }

    @Test
    void testErrorsLevel4() {
        var iterator = new FieldErrorVectorSequentialProvider<>(7, 4, GF_2_2);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 4) * 3^4
        assertEquals(2835, count);
    }

    @Test
    void testErrorsLevel5() {
        var iterator = new FieldErrorVectorSequentialProvider<>(7, 5, GF_2_2);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 5) * 3^5
        assertEquals(5103, count);
    }

    @Test
    void testErrorsLevel6() {
        var iterator = new FieldErrorVectorSequentialProvider<>(7, 6, GF_2_2);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 6) * 3^6
        assertEquals(5103, count);
    }

    @Test
    void testErrorsLevel7() {
        var iterator = new FieldErrorVectorSequentialProvider<>(7, 7, GF_2_2);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        // C(7, 7) * 3^7
        assertEquals(2187, count);
    }
}
