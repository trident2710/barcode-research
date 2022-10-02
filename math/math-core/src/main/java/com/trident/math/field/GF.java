package com.trident.math.field;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;

import java.util.Iterator;
import java.util.stream.Stream;

public interface GF<T extends FieldElement<T>> extends Field<T> {
    T add(T first, T second);

    T sub(T first, T second);

    T mul(T first, T second);

    default T pow(T elem, int power) {
        if (power == 0) {
            return getOne();
        }

        return Stream.generate(() -> elem)
                .limit(power)
                .reduce(this::mul)
                .orElseThrow();
    }

    T div(T first, T second);

    T inv(T element);

    T neg(T element);

    T mod(T element);

    default T times(T elem, int times) {
        if (times < 0) {
            throw new IllegalArgumentException("times should be positive");
        }

        if (times == 0) {
            return getZero();
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
    // x -> 1
    // a2*x^2 + a1*x + a0 -> [a2 a1 a0] to decimal,
    // for example, for GF(4): x^2 + x + 1 -> 7
    T getOfValue(long value);

    long prime();

    long elementsCount();

    Iterator<T> iterator();
}
