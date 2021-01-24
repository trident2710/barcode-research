package com.trident.math.bch;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFieldOverPrime.GF5;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BCHCodeTest {

    @Test
    void testCreate() {
        var generator = matrixRow(GF5.getOne(), GF5.getOfValue(2), GF5.getOfValue(3), GF5.getOfValue(4));
        var bch = new BCHCode<>(generator);
        var expectedFullMatrix = createMatrixOfRows(
                matrixRow(GF5.getOne(), GF5.getOfValue(2), GF5.getOfValue(3), GF5.getOfValue(4), GF5.getZero(), GF5.getZero(), GF5.getZero(), GF5.getZero()),
                matrixRow(GF5.getZero(), GF5.getOne(), GF5.getOfValue(2), GF5.getOfValue(3), GF5.getOfValue(4), GF5.getZero(), GF5.getZero(), GF5.getZero()),
                matrixRow(GF5.getZero(), GF5.getZero(), GF5.getOne(), GF5.getOfValue(2), GF5.getOfValue(3), GF5.getOfValue(4), GF5.getZero(), GF5.getZero()),
                matrixRow(GF5.getZero(), GF5.getZero(), GF5.getZero(), GF5.getOne(), GF5.getOfValue(2), GF5.getOfValue(3), GF5.getOfValue(4), GF5.getZero()),
                matrixRow(GF5.getZero(), GF5.getZero(), GF5.getZero(), GF5.getZero(), GF5.getOne(), GF5.getOfValue(2), GF5.getOfValue(3), GF5.getOfValue(4)));
        assertEquals(expectedFullMatrix, bch.getFullMatrix());
    }

}