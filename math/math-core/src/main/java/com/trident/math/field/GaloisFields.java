package com.trident.math.field;

public final class GaloisFields {

    public static final GaloisFieldOverPrime GF3 = GaloisFieldOverPrime.of(3);

    public static final GaloisFieldOverPrime GF5 = GaloisFieldOverPrime.of(5);

    public static GaloisFieldOverPoly GF_2_2 = GaloisFieldOverPoly.of(2, 2, new long[]{1, 1, 1});

    public static GaloisFieldOverPoly GF_3_2 = GaloisFieldOverPoly.of(3, 2, new long[]{2, 1, 1});

    private GaloisFields() {
    }


}
