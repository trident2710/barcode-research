package com.trident.math.io;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFieldOverPrimeType.GF5;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldMatrixIOUtilTest {
    private static final GaloisFieldOverPrimeElement ONE = GF5.field().getOne();
    private static final GaloisFieldOverPrimeElement TWO = GF5.field().getOfValue(2);
    private static final GaloisFieldOverPrimeElement THREE = GF5.field().getOfValue(3);
    private static final GaloisFieldOverPrimeElement FOUR = GF5.field().getOfValue(4);

    @Test
    void testWriteAsString() {
        var matrix = createMatrixOfRows(
                matrixRow(ONE, TWO, THREE, FOUR),
                matrixRow(ONE, TWO, THREE, FOUR),
                matrixRow(ONE, TWO, THREE, FOUR)
        );
        var result = FieldMatrixIOUtil.writeAsString(matrix);
        assertEquals("(1, 2, 3, 4\n" +
                " 1, 2, 3, 4\n" +
                " 1, 2, 3, 4)", result);
    }

}