package com.trident.math.field;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFieldOverPoly.GF9;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GF9Test {
    private static final long[][] SUB_MAP = new long[][]{
            {0, 2, 1, 6, 8, 7, 3, 5, 4},
            {1, 0, 2, 7, 6, 8, 4, 3, 5},
            {2, 1, 0, 8, 7, 6, 5, 4, 3},
            {3, 5, 4, 0, 2, 1, 6, 8, 7},
            {4, 3, 5, 1, 0, 2, 7, 6, 8},
            {5, 4, 3, 2, 1, 0, 8, 7, 6},
            {6, 8, 7, 3, 5, 4, 0, 2, 1},
            {7, 6, 8, 4, 3, 5, 1, 0, 2},
            {8, 7, 6, 5, 4, 3, 2, 1, 0}
    };

    private static final long[][] ADD_MAP = new long[][]{
            {0, 1, 2, 3, 4, 5, 6, 7, 8},
            {1, 2, 0, 4, 5, 3, 7, 8, 6},
            {2, 0, 1, 5, 3, 4, 8, 6, 7},
            {3, 4, 5, 6, 7, 8, 0, 1, 2},
            {4, 5, 3, 7, 8, 6, 1, 2, 0},
            {5, 3, 4, 8, 6, 7, 2, 0, 1},
            {6, 7, 8, 0, 1, 2, 3, 4, 5},
            {7, 8, 6, 1, 2, 0, 4, 5, 3},
            {8, 6, 7, 2, 0, 1, 5, 3, 4}
    };

    private static final long[][] MUL_MAP = new long[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 2, 3, 4, 5, 6, 7, 8},
            {0, 2, 1, 6, 8, 7, 3, 5, 4},
            {0, 3, 6, 7, 1, 4, 5, 8, 2},
            {0, 4, 8, 1, 5, 6, 2, 3, 7},
            {0, 5, 7, 4, 6, 2, 8, 1, 3},
            {0, 6, 3, 5, 2, 8, 7, 4, 1},
            {0, 7, 5, 8, 3, 1, 4, 2, 6},
            {0, 8, 4, 2, 7, 3, 1, 6, 5}
    };

    private static final long[][] DIV_MAP = new long[][]{
            {-1, 0, 0, 0, 0, 0, 0, 0, 0},
            {-1, 1, 2, 4, 3, 7, 8, 5, 6},
            {-1, 2, 1, 8, 6, 5, 4, 7, 3},
            {-1, 3, 6, 1, 7, 8, 2, 4, 5},
            {-1, 4, 8, 5, 1, 3, 7, 6, 2},
            {-1, 5, 7, 6, 4, 1, 3, 2, 8},
            {-1, 6, 3, 2, 5, 4, 1, 8, 7},
            {-1, 7, 5, 3, 8, 2, 6, 1, 4},
            {-1, 8, 4, 7, 2, 6, 5, 3, 1}
    };

    private static final long[] INV_MAP = new long[]{
            -1, 1, 2, 4, 3, 7, 8, 5, 6
    };

    @Test
    void testEquality() {
        for (int i = 0; i < 9; i++) {
            var first = GF9.getOfValue(i);
            for (int j = 0; j < 9; j++) {
                var second = GF9.getOfValue(j);
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
        for (int i = 0; i < 9; i++) {
            var first = GF9.getOfValue(i);
            for (int j = 0; j < 9; j++) {
                var second = GF9.getOfValue(j);
                var expected = GF9.getOfValue(SUB_MAP[i][j]);
                assertEquals(expected, GF9.sub(first, second));
            }
        }
    }

    @Test
    void testAdd() {
        for (int i = 0; i < 9; i++) {
            var first = GF9.getOfValue(i);
            for (int j = 0; j < 9; j++) {
                var second = GF9.getOfValue(j);
                var expected = GF9.getOfValue(ADD_MAP[i][j]);
                assertEquals(expected, GF9.add(first, second));
            }
        }
    }

    @Test
    void testMul() {
        for (int i = 0; i < 9; i++) {
            var first = GF9.getOfValue(i);
            for (int j = 0; j < 9; j++) {
                var second = GF9.getOfValue(j);
                var expected = GF9.getOfValue(MUL_MAP[i][j]);
                assertEquals(expected, GF9.mul(first, second), "(" + i + "*" + j + ")");
            }
        }
    }

    @Test
    void testDiv() {
        for (int i = 0; i < 9; i++) {
            var first = GF9.getOfValue(i);
            for (int j = 0; j < 9; j++) {
                var second = GF9.getOfValue(j);
                if (j == 0) {
                    assertThrows(Exception.class, () -> GF9.div(first, second));
                } else {
                    var expected = GF9.getOfValue(DIV_MAP[i][j]);
                    assertEquals(expected, GF9.div(first, second));
                }
            }
        }
    }

    @Test
    void testInv() {
        for (int i = 0; i < 9; i++) {
            if (i == 0) {
                assertThrows(Exception.class, () -> GF9.inv(GF9.getOfValue(0)));
            } else {
                var val = GF9.getOfValue(i);
                var expected = GF9.getOfValue(INV_MAP[i]);
                assertEquals(expected, GF9.inv(val));
            }
        }
    }
}
