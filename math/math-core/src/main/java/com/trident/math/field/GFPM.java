package com.trident.math.field;

public interface GFPM<T extends GFElement<T>> extends GF<T> {

    long[] irreduciblePoly();

    int exponent();
}
