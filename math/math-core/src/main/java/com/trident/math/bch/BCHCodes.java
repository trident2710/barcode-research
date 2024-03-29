package com.trident.math.bch;

import com.trident.math.field.GFPElement;
import com.trident.math.field.GFPMExtensionElement;
import com.trident.math.field.GFPMSimpleElement;

import static com.trident.math.field.GaloisFields.GF3;
import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.field.GaloisFields.GF7;
import static com.trident.math.field.GaloisFields.GF_2_2;
import static com.trident.math.field.GaloisFields.GF_3_2;
import static com.trident.math.field.GaloisFields.GF_3_3;
import static com.trident.math.field.GaloisFields.GF_4_2;
import static com.trident.math.field.GaloisFields.GF_5_2;
import static com.trident.math.field.GaloisFields.GF_7_2;
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

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_GF5_24_16 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1},

            }, GF5), GF5.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 5, 23, 22, 17, 24, 2, 10, 16, 19, 9, 18, 4, 20, 7, 8, 13, 6, 3, 15, 14, 11, 21, 12},
                    new long[]{1, 23, 17, 2, 16, 9, 4, 7, 13, 3, 14, 21, 1, 23, 17, 2, 16, 9, 4, 7, 13, 3, 14, 21},
                    new long[]{1, 22, 2, 19, 4, 8, 3, 11, 1, 22, 2, 19, 4, 8, 3, 11, 1, 22, 2, 19, 4, 8, 3, 11},
                    new long[]{1, 17, 16, 4, 13, 14, 1, 17, 16, 4, 13, 14, 1, 17, 16, 4, 13, 14, 1, 17, 16, 4, 13, 14},
            }, GF_5_2), GF_5_2.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_GF5_14_6 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0, 0},
                    new long[]{0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0},
                    new long[]{0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0},
                    new long[]{0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0},
                    new long[]{0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0},
                    new long[]{0, 0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1},

            }, GF5), GF5.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 5, 23, 22, 17, 24, 2, 10, 16, 19, 9, 18, 4, 20},
                    new long[]{1, 23, 17, 2, 16, 9, 4, 7, 13, 3, 14, 21, 1, 23},
                    new long[]{1, 22, 2, 19, 4, 8, 3, 11, 1, 22, 2, 19, 4, 8},
                    new long[]{1, 17, 16, 4, 13, 14, 1, 17, 16, 4, 13, 14, 1, 17},
            }, GF_5_2), GF_5_2.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_GF5_13_5 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0, 0},
                    new long[]{0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0},
                    new long[]{0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0},
                    new long[]{0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0},
                    new long[]{0, 0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1},

            }, GF5), GF5.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 5, 23, 22, 17, 24, 2, 10, 16, 19, 9, 18, 4},
                    new long[]{1, 23, 17, 2, 16, 9, 4, 7, 13, 3, 14, 21, 1},
                    new long[]{1, 22, 2, 19, 4, 8, 3, 11, 1, 22, 2, 19, 4},
                    new long[]{1, 17, 16, 4, 13, 14, 1, 17, 16, 4, 13, 14, 1},
            }, GF_5_2), GF_5_2.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_GF5_12_4 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0, 0},
                    new long[]{0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0},
                    new long[]{0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0},
                    new long[]{0, 0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1},

            }, GF5), GF5.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 5, 23, 22, 17, 24, 2, 10, 16, 19, 9, 18},
                    new long[]{1, 23, 17, 2, 16, 9, 4, 7, 13, 3, 14, 21},
                    new long[]{1, 22, 2, 19, 4, 8, 3, 11, 1, 22, 2, 19},
                    new long[]{1, 17, 16, 4, 13, 14, 1, 17, 16, 4, 13, 14},
            }, GF_5_2), GF_5_2.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_GF5_11_3 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{4, 1, 4, 2, 0, 4, 4, 3, 1, 0, 0},
                    new long[]{0, 4, 1, 4, 2, 0, 4, 4, 3, 1, 0},
                    new long[]{0, 0, 4, 1, 4, 2, 0, 4, 4, 3, 1},

            }, GF5), GF5.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 5, 23, 22, 17, 24, 2, 10, 16, 19, 9},
                    new long[]{1, 23, 17, 2, 16, 9, 4, 7, 13, 3, 14},
                    new long[]{1, 22, 2, 19, 4, 8, 3, 11, 1, 22, 2},
                    new long[]{1, 17, 16, 4, 13, 14, 1, 17, 16, 4, 13},
            }, GF_5_2), GF_5_2.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_GF5_10_2 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{4, 1, 4, 2, 0, 4, 4, 3, 1, 0},
                    new long[]{0, 4, 1, 4, 2, 0, 4, 4, 3, 1},

            }, GF5), GF5.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 5, 23, 22, 17, 24, 2, 10, 16, 19},
                    new long[]{1, 23, 17, 2, 16, 9, 4, 7, 13, 3},
                    new long[]{1, 22, 2, 19, 4, 8, 3, 11, 1, 22},
                    new long[]{1, 17, 16, 4, 13, 14, 1, 17, 16, 4},
            }, GF_5_2), GF_5_2.getClass());


    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_GF7_13_5 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{4, 6, 1, 2, 5, 0, 0, 5, 1, 0, 0, 0, 0},
                    new long[]{0, 4, 6, 1, 2, 5, 0, 0, 5, 1, 0, 0, 0},
                    new long[]{0, 0, 4, 6, 1, 2, 5, 0, 0, 5, 1, 0, 0},
                    new long[]{0, 0, 0, 4, 6, 1, 2, 5, 0, 0, 5, 1, 0},
                    new long[]{0, 0, 0, 0, 4, 6, 1, 2, 5, 0, 0, 5, 1},

            }, GF7), GF7.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 7, 46, 38, 41, 13, 39, 48, 3, 21, 33, 9, 11},
                    new long[]{1, 46, 41, 39, 3, 33, 11, 12, 2, 36, 26, 22, 6},
                    new long[]{1, 38, 39, 21, 11, 32, 36, 19, 6, 18, 17, 28, 45},
                    new long[]{1, 41, 3, 11, 2, 26, 6, 15, 4, 45, 5, 30, 1},
            }, GF_7_2), GF_7_2.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_GF7_12_4 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{4, 6, 1, 2, 5, 0, 0, 5, 1, 0, 0, 0},
                    new long[]{0, 4, 6, 1, 2, 5, 0, 0, 5, 1, 0, 0},
                    new long[]{0, 0, 4, 6, 1, 2, 5, 0, 0, 5, 1, 0},
                    new long[]{0, 0, 0, 4, 6, 1, 2, 5, 0, 0, 5, 1},

            }, GF7), GF7.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 7, 46, 38, 41, 13, 39, 48, 3, 21, 33, 9},
                    new long[]{1, 46, 41, 39, 3, 33, 11, 12, 2, 36, 26, 22},
                    new long[]{1, 38, 39, 21, 11, 32, 36, 19, 6, 18, 17, 28},
                    new long[]{1, 41, 3, 11, 2, 26, 6, 15, 4, 45, 5, 30},
            }, GF_7_2), GF_7_2.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_GF7_11_3 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{4, 6, 1, 2, 5, 0, 0, 5, 1, 0, 0},
                    new long[]{0, 4, 6, 1, 2, 5, 0, 0, 5, 1, 0},
                    new long[]{0, 0, 4, 6, 1, 2, 5, 0, 0, 5, 1},

            }, GF7), GF7.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 7, 46, 38, 41, 13, 39, 48, 3, 21, 33},
                    new long[]{1, 46, 41, 39, 3, 33, 11, 12, 2, 36, 26},
                    new long[]{1, 38, 39, 21, 11, 32, 36, 19, 6, 18, 17},
                    new long[]{1, 41, 3, 11, 2, 26, 6, 15, 4, 45, 5},
            }, GF_7_2), GF_7_2.getClass());

    public static BCHCode<GFPElement, GFPMSimpleElement> BCH_GF7_10_2 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{4, 6, 1, 2, 5, 0, 0, 5, 1, 0},
                    new long[]{0, 4, 6, 1, 2, 5, 0, 0, 5, 1},

            }, GF7), GF7.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 7, 46, 38, 41, 13, 39, 48, 3, 21},
                    new long[]{1, 46, 41, 39, 3, 33, 11, 12, 2, 36},
                    new long[]{1, 38, 39, 21, 11, 32, 36, 19, 6, 18},
                    new long[]{1, 41, 3, 11, 2, 26, 6, 15, 4, 45},
            }, GF_7_2), GF_7_2.getClass());

    public static BCHCode<GFPMSimpleElement, GFPMExtensionElement> BCH_GF4_14_8 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1},
            }, GF_2_2), GF_2_2.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 4, 6, 14, 5, 2, 8, 11, 7, 10, 3, 12, 13, 9},
                    new long[]{1, 6, 5, 8, 7, 3, 13, 15, 4, 14, 2, 11, 10, 12},
                    new long[]{1, 14, 8, 10, 13, 1, 14, 8, 10, 13, 1, 14, 8, 10},
                    new long[]{1, 5, 7, 13, 4, 2, 10, 9, 6, 8, 3, 15, 14, 11},
            }, GF_4_2), GF_4_2.getClass());

    public static BCHCode<GFPMSimpleElement, GFPMExtensionElement> BCH_GF4_13_7 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0, 0, 0},
                    new long[]{0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0, 0},
                    new long[]{0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0},
                    new long[]{0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0},
                    new long[]{0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0},
                    new long[]{0, 0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0},
                    new long[]{0, 0, 0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1},
            }, GF_2_2), GF_2_2.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 4, 6, 14, 5, 2, 8, 11, 7, 10, 3, 12, 13},
                    new long[]{1, 6, 5, 8, 7, 3, 13, 15, 4, 14, 2, 11, 10},
                    new long[]{1, 14, 8, 10, 13, 1, 14, 8, 10, 13, 1, 14, 8},
                    new long[]{1, 5, 7, 13, 4, 2, 10, 9, 6, 8, 3, 15, 14},
            }, GF_4_2), GF_4_2.getClass());

    public static BCHCode<GFPMSimpleElement, GFPMExtensionElement> BCH_GF4_12_6 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0, 0},
                    new long[]{0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0, 0},
                    new long[]{0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0, 0},
                    new long[]{0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0, 0},
                    new long[]{0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1, 0},
                    new long[]{0, 0, 0, 0, 0, 1, 2, 2, 1, 1, 3, 1},
            }, GF_2_2), GF_2_2.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 4, 6, 14, 5, 2, 8, 11, 7, 10, 3, 12},
                    new long[]{1, 6, 5, 8, 7, 3, 13, 15, 4, 14, 2, 11},
                    new long[]{1, 14, 8, 10, 13, 1, 14, 8, 10, 13, 1, 14},
                    new long[]{1, 5, 7, 13, 4, 2, 10, 9, 6, 8, 3, 15},
            }, GF_4_2), GF_4_2.getClass());

    public static BCHCode<GFPMSimpleElement, GFPMExtensionElement> BCH_GF4_11_5 = new BCHCode<>(
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

    public static BCHCode<GFPMSimpleElement, GFPMExtensionElement> BCH_GF4_10_4 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 2, 2, 1, 1, 3, 1, 0, 0, 0},
                    new long[]{0, 1, 2, 2, 1, 1, 3, 1, 0, 0},
                    new long[]{0, 0, 1, 2, 2, 1, 1, 3, 1, 0},
                    new long[]{0, 0, 0, 1, 2, 2, 1, 1, 3, 1},
            }, GF_2_2), GF_2_2.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 4, 6, 14, 5, 2, 8, 11, 7, 10},
                    new long[]{1, 6, 5, 8, 7, 3, 13, 15, 4, 14},
                    new long[]{1, 14, 8, 10, 13, 1, 14, 8, 10, 13},
                    new long[]{1, 5, 7, 13, 4, 2, 10, 9, 6, 8},
            }, GF_4_2), GF_4_2.getClass());

    public static BCHCode<GFPMSimpleElement, GFPMExtensionElement> BCH_GF4_9_3 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 2, 2, 1, 1, 3, 1, 0, 0},
                    new long[]{0, 1, 2, 2, 1, 1, 3, 1, 0},
                    new long[]{0, 0, 1, 2, 2, 1, 1, 3, 1},
            }, GF_2_2), GF_2_2.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 4, 6, 14, 5, 2, 8, 11, 7},
                    new long[]{1, 6, 5, 8, 7, 3, 13, 15, 4},
                    new long[]{1, 14, 8, 10, 13, 1, 14, 8, 10},
                    new long[]{1, 5, 7, 13, 4, 2, 10, 9, 6},
            }, GF_4_2), GF_4_2.getClass());

    public static BCHCode<GFPMSimpleElement, GFPMExtensionElement> BCH_GF4_8_2 = new BCHCode<>(
            toFieldMatrix(new long[][]{
                    new long[]{1, 2, 2, 1, 1, 3, 1, 0},
                    new long[]{0, 1, 2, 2, 1, 1, 3, 1},
            }, GF_2_2), GF_2_2.getClass(),
            toFieldMatrix(new long[][]{
                    new long[]{1, 4, 6, 14, 5, 2, 8, 11},
                    new long[]{1, 6, 5, 8, 7, 3, 13, 15},
                    new long[]{1, 14, 8, 10, 13, 1, 14, 8},
                    new long[]{1, 5, 7, 13, 4, 2, 10, 9},
            }, GF_4_2), GF_4_2.getClass());
}
