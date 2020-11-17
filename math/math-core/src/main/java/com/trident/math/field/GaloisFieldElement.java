package com.trident.math.field;

import org.apache.commons.math3.FieldElement;

public interface GaloisFieldElement<T extends GaloisFieldElement<T>> extends FieldElement<T>, Comparable<T> {
    // Digital representation.
    // for prime field -> value mod prime
    // for GF(p^n) -> poly coefficients as n based numeric system to decimal system
    // for GF(2^2) over x^2 + x + 1:
    // 0 -> 0
    // 1 -> 1
    // 2 -> x
    // 3 -> x + 1
    // 4 -> x^2 -> x + 1
    // ...
    long digitalRepresentation();
}
