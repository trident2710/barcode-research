package com.trident.math.bch;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFieldOverPrime.GF3;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BCHCodeTest {

    @Test
    void testCreate() {
        var generator = matrixRow(GF3.getOfValue(2), GF3.getOfValue(0), GF3.getOfValue(1), GF3.getOfValue(1), GF3.getOfValue(2), GF3.getOfValue(1));
        var bch_8_3 = new BCHCode<>(generator, 8);
        var expectedFullMatrix = createMatrixOfRows(
                matrixRow(GF3.getOfValue(2), GF3.getOfValue(0), GF3.getOfValue(1), GF3.getOfValue(1), GF3.getOfValue(2), GF3.getOfValue(1), GF3.getZero(), GF3.getZero()),
                matrixRow(GF3.getZero(), GF3.getOfValue(2), GF3.getOfValue(0), GF3.getOfValue(1), GF3.getOfValue(1), GF3.getOfValue(2), GF3.getOfValue(1), GF3.getZero()),
                matrixRow(GF3.getZero(), GF3.getZero(), GF3.getOfValue(2), GF3.getOfValue(0), GF3.getOfValue(1), GF3.getOfValue(1), GF3.getOfValue(2), GF3.getOfValue(1)));
        assertEquals(expectedFullMatrix, bch_8_3.getFullMatrix());
    }

    @Test
    void testEncode() {
        // 2 0 1 1 2 1
        var generator = matrixRow(GF3.getOfValue(2), GF3.getOfValue(0), GF3.getOfValue(1), GF3.getOfValue(1), GF3.getOfValue(2), GF3.getOfValue(1));
        var bch_8_3 = new BCHCode<>(generator, 8);

        // 0 1 2
        var message = matrixRow(GF3.getOfValue(0), GF3.getOfValue(1), GF3.getOfValue(2));

        var encoded = bch_8_3.encode(message);

        // 0 2 1 1 0 1 2 2
        var expected = matrixRow(GF3.getOfValue(0), GF3.getOfValue(2), GF3.getOfValue(1), GF3.getOfValue(1), GF3.getOfValue(0), GF3.getOfValue(1), GF3.getOfValue(2), GF3.getOfValue(2));

        assertEquals(expected, encoded);
    }

}