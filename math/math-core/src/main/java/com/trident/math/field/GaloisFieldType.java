package com.trident.math.field;

public enum GaloisFieldType {
    GF5(new GaloisFieldOverPrime(5));

    private final GaloisFieldOverPrime field;

    GaloisFieldType(GaloisFieldOverPrime field) {
        this.field = field;
    }

    public GaloisFieldOverPrime field() {
        return field;
    }
}
