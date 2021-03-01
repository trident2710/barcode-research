package com.trident.math.field;

import org.apache.commons.math3.FieldElement;

public interface GFElement<T extends GFElement<T>> extends FieldElement<T>, Comparable<T> {
    // Digital representation.
    // for GF(p) -> value mod p
    // for GF(p^n):
    // 0 -> 0
    // x -> 1
    // a2*x^2 + a1*x + a0 -> [a2 a1 a0] to decimal,
    // for example, for prime = 2: x^2 + x + 1  = 1*4 + 1*2 + 1*1 =  7
    long digitalRepresentation();
}
