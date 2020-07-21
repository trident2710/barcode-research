package com.trident.math.field;

import org.apache.commons.math3.Field;

import java.util.Arrays;

public enum GaloisFieldType {
    GF5(new GaloisFieldOverPrime(5));

    private final GaloisFieldOverPrime field;

    GaloisFieldType(GaloisFieldOverPrime field) {
        this.field = field;
    }

    public GaloisFieldOverPrime field() {
        return field;
    }

    public static GaloisFieldType getForField(Field<GaloisFieldOverPrimeElement> field) {
        return Arrays.stream(values())
                .filter(type -> type.field().equals(field))
                .findFirst()
                .orElse(null);
    }
}
