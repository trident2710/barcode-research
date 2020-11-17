package com.trident.math.field;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;

public interface GaloisField<T extends FieldElement<T>> extends Field<T> {
    T add(T first, T second);

    T sub(T first, T second);

    T mul(T first, T second);

    T div(T first, T second);

    T inv(T element);

    T neg(T element);

    T mod(T element);

    default T mul(T elem, int times) {
        if (times < 1) {
            throw new IllegalArgumentException("times must be positive");
        }

        var res = elem;
        for (int i = 1; i < times; i++) {
            res = add(res, elem);
        }
        return res;
    }

    // Digital representation.
    // for GF(p) -> value mod p
    // for GF(p^n):
    // 0 -> 0
    // 1 -> x^0 = 1
    // 2 -> x^1 = x
    //  ...
    //  n -> x^(n-1) = x^(n-1) % field_irreducible_poly
    T getOfValue(long value);

    long prime();
}
