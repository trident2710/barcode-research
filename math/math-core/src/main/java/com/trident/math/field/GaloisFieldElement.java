package com.trident.math.field;

import org.apache.commons.math3.FieldElement;

public interface GaloisFieldElement<T extends GaloisFieldElement<T>> extends FieldElement<T>, Comparable<T> {
    long value();
}
