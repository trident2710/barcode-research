package com.trident.math.field;

import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import org.apache.commons.math3.Field;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NullArgumentException;

public class GaloisFieldOverPolyElement implements GaloisFieldElement<GaloisFieldOverPolyElement> {

    private final GaloisFieldOverPoly field;
    private final long[] params;

    public GaloisFieldOverPolyElement(GaloisFieldOverPoly field, long[] params) {
        this.field = field;
        this.params = params;
    }

    public static GaloisFieldOverPolyElement from(GaloisFieldOverPoly field, UnivariatePolynomialZp64 value) {
        return new GaloisFieldOverPolyElement(field, value.stream().toArray());
    }

    UnivariatePolynomialZp64 value() {
        return UnivariatePolynomialZp64.create(field.getPower(), params);
    }

    @Override
    public int compareTo(GaloisFieldOverPolyElement o) {
        // TODO: implement
        return 0;
    }

    @Override
    public GaloisFieldOverPolyElement add(GaloisFieldOverPolyElement element) throws NullArgumentException {
        return field.add(this, element);
    }

    @Override
    public GaloisFieldOverPolyElement subtract(GaloisFieldOverPolyElement element) throws NullArgumentException {
        return field.sub(this, element);
    }

    @Override
    public GaloisFieldOverPolyElement negate() {
        return field.neg(this);
    }

    @Override
    public GaloisFieldOverPolyElement multiply(int n) {
        var res = this;
        for (int i = 1; i < n; i++) {
            res = this.add(res);
        }
        return res;
    }

    @Override
    public GaloisFieldOverPolyElement multiply(GaloisFieldOverPolyElement element) throws NullArgumentException {
        return field.mul(this, element);
    }

    @Override
    public GaloisFieldOverPolyElement divide(GaloisFieldOverPolyElement element) throws NullArgumentException, MathArithmeticException {
        return field.div(this, element);
    }

    @Override
    public GaloisFieldOverPolyElement reciprocal() throws MathArithmeticException {
        return field.inv(this);
    }

    @Override
    public Field<GaloisFieldOverPolyElement> getField() {
        return field;
    }
}
