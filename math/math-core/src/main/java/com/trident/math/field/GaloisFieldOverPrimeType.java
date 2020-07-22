package com.trident.math.field;

import org.apache.commons.math3.Field;

import java.util.Arrays;

public enum GaloisFieldOverPrimeType {
    GF3(new GaloisFieldOverPrime(3)),
    GF5(new GaloisFieldOverPrime(5)),
    GF7(new GaloisFieldOverPrime(7)),
    GF11(new GaloisFieldOverPrime(11)),
    GF13(new GaloisFieldOverPrime(13)),
    GF17(new GaloisFieldOverPrime(17)),
    GF19(new GaloisFieldOverPrime(19)),
    GF23(new GaloisFieldOverPrime(23)),
    GF29(new GaloisFieldOverPrime(29)),
    GF31(new GaloisFieldOverPrime(31)),
    GF37(new GaloisFieldOverPrime(37)),
    GF41(new GaloisFieldOverPrime(41)),
    GF43(new GaloisFieldOverPrime(43)),
    GF47(new GaloisFieldOverPrime(47)),
    GF53(new GaloisFieldOverPrime(53)),
    GF59(new GaloisFieldOverPrime(59)),
    GF61(new GaloisFieldOverPrime(61)),
    GF67(new GaloisFieldOverPrime(67)),
    GF71(new GaloisFieldOverPrime(71)),
    GF73(new GaloisFieldOverPrime(73)),
    GF79(new GaloisFieldOverPrime(79)),
    GF83(new GaloisFieldOverPrime(83)),
    GF89(new GaloisFieldOverPrime(89)),
    GF97(new GaloisFieldOverPrime(97)),
    ;

    private final GaloisFieldOverPrime field;

    GaloisFieldOverPrimeType(GaloisFieldOverPrime field) {
        this.field = field;
    }

    public GaloisFieldOverPrime field() {
        return field;
    }

    public static GaloisFieldOverPrimeType getForField(Field<GaloisFieldOverPrimeElement> field) {
        return Arrays.stream(values())
                .filter(type -> type.field().equals(field))
                .findFirst()
                .orElse(null);
    }
}
