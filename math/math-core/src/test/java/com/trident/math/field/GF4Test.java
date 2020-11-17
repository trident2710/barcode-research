package com.trident.math.field;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFieldOverPoly.GF4;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GF4Test {
    private static final long[][] SUB_MAP = new long[][]{
            {0, 1, 2, 3},
            {1, 0, 3, 2},
            {2, 3, 0, 1},
            {3, 2, 1, 0},
    };

    private static final long[][] ADD_MAP = new long[][]{
            {0, 1, 2, 3},
            {1, 0, 3, 2},
            {2, 3, 0, 1},
            {3, 2, 1, 0}
    };

    private static final long[][] MUL_MAP = new long[][]{
            {0, 0, 0, 0},
            {0, 1, 2, 3},
            {0, 2, 3, 1},
            {0, 3, 1, 2}
    };

    private static final long[][] DIV_MAP = new long[][]{
            {-1, 0, 0, 0},
            {-1, 1, 3, 2},
            {-1, 2, 1, 3},
            {-1, 3, 2, 1}
    };

    private static final long[] INV_MAP = new long[]{-1, 1, 3, 2};

    @Test
    void testEquality() {
        for (int i = 0; i < 4; i++) {
            var first = GF4.getOfValue(i);
            for (int j = 0; j < 4; j++) {
                var second = GF4.getOfValue(j);
                if (i != j) {
                    assertNotEquals(second, first);
                } else {
                    assertEquals(second, first);
                }
            }
        }
    }

    @Test
    void testSub() {
        for (int i = 0; i < 4; i++) {
            var first = GF4.getOfValue(i);
            for (int j = 0; j < 4; j++) {
                var second = GF4.getOfValue(j);
                var expected = GF4.getOfValue(SUB_MAP[i][j]);
                assertEquals(expected, GF4.sub(first, second));
            }
        }
    }

    @Test
    void testAdd() {
        for (int i = 0; i < 4; i++) {
            var first = GF4.getOfValue(i);
            for (int j = 0; j < 4; j++) {
                var second = GF4.getOfValue(j);
                var expected = GF4.getOfValue(ADD_MAP[i][j]);
                assertEquals(expected, GF4.add(first, second));
            }
        }
    }

    @Test
    void testMul() {
        for (int i = 0; i < 4; i++) {
            var first = GF4.getOfValue(i);
            for (int j = 0; j < 4; j++) {
                var second = GF4.getOfValue(j);
                var expected = GF4.getOfValue(MUL_MAP[i][j]);
                assertEquals(expected, GF4.mul(first, second), "(" + i + "*" + j + ")");
            }
        }
    }

    @Test
    void testDiv() {
        for (int i = 0; i < 4; i++) {
            var first = GF4.getOfValue(i);
            for (int j = 0; j < 4; j++) {
                var second = GF4.getOfValue(j);
                if (j == 0) {
                    assertThrows(Exception.class, () -> GF4.div(first, second));
                } else {
                    var expected = GF4.getOfValue(DIV_MAP[i][j]);
                    assertEquals(expected, GF4.div(first, second));
                }
            }
        }
    }

    @Test
    void testInv() {
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                assertThrows(Exception.class, () -> GF4.inv(GF4.getOfValue(0)));
            } else {
                var val = GF4.getOfValue(i);
                var expected = GF4.getOfValue(INV_MAP[i]);
                assertEquals(expected, GF4.inv(val));
            }
        }
    }
}
