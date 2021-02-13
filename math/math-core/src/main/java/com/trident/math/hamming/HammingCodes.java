package com.trident.math.hamming;

import com.trident.math.field.GaloisFieldOverPoly;
import com.trident.math.field.GaloisFieldOverPolyElement;
import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;

import static com.trident.math.field.GaloisFields.GF3;
import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.field.GaloisFields.GF7;
import static com.trident.math.field.GaloisFields.GF_2_2;
import static com.trident.math.field.GaloisFields.GF_2_3;
import static com.trident.math.field.GaloisFields.GF_3_2;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrix;

@SuppressWarnings("unused")
public final class HammingCodes {
    private HammingCodes() {
    }

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_12_9_GF_3 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1, 1, 1, 1, 1, 0},
                    new long[]{0, 0, 1, 1, 1, 2, 2, 2, 1},
                    new long[]{1, 2, 0, 1, 2, 0, 1, 2, 1}
            }, GF3));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_11_8_GF_3 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1, 1, 1, 1, 1},
                    new long[]{0, 0, 1, 1, 1, 2, 2, 2},
                    new long[]{1, 2, 0, 1, 2, 0, 1, 2}
            }, GF3));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_10_7_GF_3 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1, 1, 1, 1},
                    new long[]{0, 0, 1, 1, 1, 2, 2},
                    new long[]{1, 2, 0, 1, 2, 0, 1}
            }, GF3));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_9_6_GF_3 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1, 1, 1},
                    new long[]{0, 0, 1, 1, 1, 2},
                    new long[]{1, 2, 0, 1, 2, 0}
            }, GF3));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_8_5_GF_3 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1, 1},
                    new long[]{0, 0, 1, 1, 1},
                    new long[]{1, 2, 0, 1, 2}
            }, GF3));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_7_4_GF_3 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1},
                    new long[]{0, 0, 1, 1},
                    new long[]{1, 2, 0, 1}
            }, GF3));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_6_3_GF_3 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1},
                    new long[]{0, 0, 1},
                    new long[]{1, 2, 0}
            }, GF3));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_9_6_GF_5 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{0, 0, 0, 0, 1, 1},
                    new long[]{1, 1, 1, 1, 0, 0},
                    new long[]{1, 2, 3, 4, 1, 2}
            }, GF5));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_8_5_GF_5 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{0, 0, 0, 0, 1},
                    new long[]{1, 1, 1, 1, 0},
                    new long[]{1, 2, 3, 4, 1}
            }, GF5));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_6_4_GF_5 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1},
                    new long[]{1, 2, 3, 4}
            }, GF5));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_5_3_GF_5 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1},
                    new long[]{1, 2, 3}
            }, GF5));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_4_2_GF_5 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1},
                    new long[]{1, 2}
            }, GF5));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_8_6_GF_7 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1, 1, 1},
                    new long[]{1, 2, 3, 4, 5, 6}
            }, GF7));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_7_5_GF_7 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1, 1},
                    new long[]{1, 2, 3, 4, 5}
            }, GF7));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_6_4_GF_7 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1},
                    new long[]{1, 2, 3, 4}
            }, GF7));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_5_3_GF_7 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1},
                    new long[]{1, 2, 3}
            }, GF7));

    public static final HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> HAMMING_4_2_GF_7 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1},
                    new long[]{1, 2}
            }, GF7));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_11_8_GF_2_2 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{0, 0, 0, 1, 1, 1, 1, 1},
                    new long[]{1, 1, 1, 0, 0, 0, 1, 1},
                    new long[]{1, 2, 3, 1, 2, 3, 0, 1}
            }, GF_2_2));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_10_7_GF_2_2 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{0, 0, 0, 1, 1, 1, 1},
                    new long[]{1, 1, 1, 0, 0, 0, 1},
                    new long[]{1, 2, 3, 1, 2, 3, 0}
            }, GF_2_2));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_9_6_GF_2_2 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{0, 0, 0, 1, 1, 1},
                    new long[]{1, 1, 1, 0, 0, 0},
                    new long[]{1, 2, 3, 1, 2, 3}
            }, GF_2_2));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_8_5_GF_2_2 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{0, 0, 0, 1, 1},
                    new long[]{1, 1, 1, 0, 0},
                    new long[]{1, 2, 3, 1, 2}
            }, GF_2_2));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_7_4_GF_2_2 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{0, 0, 0, 1},
                    new long[]{1, 1, 1, 0},
                    new long[]{1, 2, 3, 1}
            }, GF_2_2));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_5_3_GF_2_2 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1},
                    new long[]{1, 2, 3}
            }, GF_2_2));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_4_2_GF_2_2 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1},
                    new long[]{1, 2}
            }, GF_2_2));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_7_5_GF_2_3 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1, 1},
                    new long[]{1, 2, 3, 4, 5}
            }, GF_2_3));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_6_4_GF_2_3 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1},
                    new long[]{1, 2, 3, 4}
            }, GF_2_3));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_5_3_GF_2_3 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1},
                    new long[]{1, 2, 3}
            }, GF_2_3));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_4_2_GF_2_3 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1},
                    new long[]{1, 2}
            }, GF_2_3));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_7_5_GF_3_2 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1, 1},
                    new long[]{1, 2, 3, 4, 5}
            }, GF_3_2));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_6_4_GF_3_2 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1, 1},
                    new long[]{1, 2, 3, 4}
            }, GF_3_2));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_5_3_GF_3_2 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1, 1},
                    new long[]{1, 2, 3}
            }, GF_3_2));

    public static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_4_2_GF_3_2 =
            new HammingCode<>(toFieldMatrix(new long[][]{
                    new long[]{1, 1},
                    new long[]{1, 2}
            }, GF_3_2));

}