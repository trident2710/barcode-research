package com.trident.math.field;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;

public interface GaloisField<T extends FieldElement<T>> extends Field<T> {
    T add(T first, T second);

    T sub(T first, T second);

    T mul(T first, T second);

    T div(T first, T second);

    T inv(T element);

    long mod(long value);
}
