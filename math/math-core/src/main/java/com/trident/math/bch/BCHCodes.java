package com.trident.math.bch;

import com.trident.math.field.GFPElement;
import com.trident.math.field.GFPMElement;

import static com.trident.math.field.GaloisFields.GF3;
import static com.trident.math.field.GaloisFields.GF_3_2;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrix;

public final class BCHCodes {
    private BCHCodes() {
    }

    public static BCHCode<GFPElement, GFPMElement> BCH_8_3 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{2, 0, 1, 1, 2, 1, 0, 0},
                    new long[]{0, 2, 0, 1, 1, 2, 1, 0},
                    new long[]{0, 0, 2, 0, 1, 1, 2, 1},
            }, GF3),
            toFieldMatrix(new long[][]{
                    new long[]{1, 3, 7, 8, 2, 6, 5, 4},
                    new long[]{1, 7, 2, 5, 1, 7, 2, 5},
                    new long[]{1, 8, 5, 3, 2, 4, 7, 6},
                    new long[]{1, 2, 1, 2, 1, 2, 1, 2},
            }, GF_3_2));
}
