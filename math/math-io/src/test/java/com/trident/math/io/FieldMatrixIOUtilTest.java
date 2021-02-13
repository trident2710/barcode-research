package com.trident.math.io;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrix;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldMatrixIOUtilTest {

    @Test
    void testWriteAsString() {
        var matrix = toFieldMatrix(new long[][]{
                new long[]{1, 2, 3, 4},
                new long[]{1, 2, 3, 4},
                new long[]{1, 2, 3, 4}
        }, GF5);
        var result = FieldMatrixIOUtil.writeAsString(matrix);
        assertEquals("(1, 2, 3, 4\n" +
                " 1, 2, 3, 4\n" +
                " 1, 2, 3, 4)", result);
    }

}