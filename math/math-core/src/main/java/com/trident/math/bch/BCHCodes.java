package com.trident.math.bch;

import com.trident.math.field.GFPElement;
import com.trident.math.field.GFPMExtensionElement;
import com.trident.math.field.GFPMSimpleElement;

import static com.trident.math.field.GaloisFields.GF3;
import static com.trident.math.field.GaloisFields.GF_2_2;
import static com.trident.math.field.GaloisFields.GF_3_2;
import static com.trident.math.field.GaloisFields.GF_3_3;
import static com.trident.math.field.GaloisFields.GF_4_2;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrix;

public final class BCHCodes {
    private BCHCodes() {
    }

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_8_3 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{2, 0, 1, 1, 2, 1, 0, 0},
                    new long[]{0, 2, 0, 1, 1, 2, 1, 0},
                    new long[]{0, 0, 2, 0, 1, 1, 2, 1},
            }, GF3), GF3.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 3, 7, 8, 2, 6, 5, 4},
                    new long[]{1, 7, 2, 5, 1, 7, 2, 5},
                    new long[]{1, 8, 5, 3, 2, 4, 7, 6},
                    new long[]{1, 2, 1, 2, 1, 2, 1, 2},
            }, GF_3_2), GF_3_2.getClass());

    public static BCHCode<GFPMSimpleElement, GFPMExtensionElement> BCH_15_9 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1},
            }, GF_2_2), GF_2_2.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 4, 6, 14, 5, 2, 8, 11, 7, 10, 3, 12, 13, 9, 15},
                    new long[]{1, 6, 5, 8, 7, 3, 13, 15, 4, 14, 2, 11, 10, 12, 9},
                    new long[]{1, 14, 8, 10, 13, 1, 14, 8, 10, 13, 1, 14, 8, 10, 13},
                    new long[]{1, 5, 7, 13, 4, 2, 10, 9, 6, 8, 3, 15, 14, 11, 12},
            }, GF_4_2), GF_4_2.getClass());

    public static BCHCode<GFPMSimpleElement, GFPMExtensionElement> BCH_11_5 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0},
                    new long[]{0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0},
                    new long[]{0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0},
                    new long[]{0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0},
                    new long[]{0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1},
            }, GF_2_2), GF_2_2.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 4, 6, 14, 5, 2, 8, 11, 7, 10, 3},
                    new long[]{1, 6, 5, 8, 7, 3, 13, 15, 4, 14, 2},
                    new long[]{1, 14, 8, 10, 13, 1, 14, 8, 10, 13, 1},
                    new long[]{1, 5, 7, 13, 4, 2, 10, 9, 6, 8, 3},
            }, GF_4_2), GF_4_2.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_18_9 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1},
            }, GF3), GF3.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 3, 9, 5, 15, 23, 13, 17, 20, 4, 12, 14, 11, 2, 6, 18, 7, 21},
                    new long[]{1, 9, 15, 13, 20, 12, 11, 6, 7, 16, 22, 8, 25, 1, 9, 15, 13, 20},
                    new long[]{1, 5, 13, 4, 11, 18, 16, 10, 25, 3, 15, 17, 12, 2, 7, 26, 8, 19},
                    new long[]{1, 15, 20, 11, 7, 22, 25, 9, 13, 12, 6, 16, 8, 1, 15, 20, 11, 7},
            }, GF_3_3), GF_3_3.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_17_8 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1},
            }, GF3), GF3.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 3, 9, 5, 15, 23, 13, 17, 20, 4, 12, 14, 11, 2, 6, 18, 7},
                    new long[]{1, 9, 15, 13, 20, 12, 11, 6, 7, 16, 22, 8, 25, 1, 9, 15, 13},
                    new long[]{1, 5, 13, 4, 11, 18, 16, 10, 25, 3, 15, 17, 12, 2, 7, 26, 8},
                    new long[]{1, 15, 20, 11, 7, 22, 25, 9, 13, 12, 6, 16, 8, 1, 15, 20, 11},
            }, GF_3_3), GF_3_3.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_16_7 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1},
            }, GF3), GF3.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 3, 9, 5, 15, 23, 13, 17, 20, 4, 12, 14, 11, 2, 6, 18},
                    new long[]{1, 9, 15, 13, 20, 12, 11, 6, 7, 16, 22, 8, 25, 1, 9, 15},
                    new long[]{1, 5, 13, 4, 11, 18, 16, 10, 25, 3, 15, 17, 12, 2, 7, 26},
                    new long[]{1, 15, 20, 11, 7, 22, 25, 9, 13, 12, 6, 16, 8, 1, 15, 20},
            }, GF_3_3), GF_3_3.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_15_6 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0},
                    new long[]{0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0},
                    new long[]{0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0},
                    new long[]{0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0},
                    new long[]{0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0},
                    new long[]{0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1},
            }, GF3), GF3.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 3, 9, 5, 15, 23, 13, 17, 20, 4, 12, 14, 11, 2, 6},
                    new long[]{1, 9, 15, 13, 20, 12, 11, 6, 7, 16, 22, 8, 25, 1, 9},
                    new long[]{1, 5, 13, 4, 11, 18, 16, 10, 25, 3, 15, 17, 12, 2, 7},
                    new long[]{1, 15, 20, 11, 7, 22, 25, 9, 13, 12, 6, 16, 8, 1, 15},
            }, GF_3_3), GF_3_3.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_14_5 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 0},
                    new long[]{0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0},
                    new long[]{0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0},
                    new long[]{0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0},
                    new long[]{0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1},
            }, GF3), GF3.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 3, 9, 5, 15, 23, 13, 17, 20, 4, 12, 14, 11, 2},
                    new long[]{1, 9, 15, 13, 20, 12, 11, 6, 7, 16, 22, 8, 25, 1},
                    new long[]{1, 5, 13, 4, 11, 18, 16, 10, 25, 3, 15, 17, 12, 2},
                    new long[]{1, 15, 20, 11, 7, 22, 25, 9, 13, 12, 6, 16, 8, 1},
            }, GF_3_3), GF_3_3.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_13_4 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0},
                    new long[]{0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0},
                    new long[]{0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0},
                    new long[]{0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1},
            }, GF3), GF3.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 3, 9, 5, 15, 23, 13, 17, 20, 4, 12, 14, 11},
                    new long[]{1, 9, 15, 13, 20, 12, 11, 6, 7, 16, 22, 8, 25},
                    new long[]{1, 5, 13, 4, 11, 18, 16, 10, 25, 3, 15, 17, 12},
                    new long[]{1, 15, 20, 11, 7, 22, 25, 9, 13, 12, 6, 16, 8},
            }, GF_3_3), GF_3_3.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_12_3 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0, 0},
                    new long[]{0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 0},
                    new long[]{0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 2, 1},
            }, GF3), GF3.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 3, 9, 5, 15, 23, 13, 17, 20, 4, 12, 14},
                    new long[]{1, 9, 15, 13, 20, 12, 11, 6, 7, 16, 22, 8},
                    new long[]{1, 5, 13, 4, 11, 18, 16, 10, 25, 3, 15, 17},
                    new long[]{1, 15, 20, 11, 7, 22, 25, 9, 13, 12, 6, 16},
            }, GF_3_3), GF_3_3.getClass());
}
