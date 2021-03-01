package com.trident.math.field;

public interface GFPM<T extends GFPMElement<T>> extends GF<T> {

    long[] irreduciblePoly();

    int exponent();
}
