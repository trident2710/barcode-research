package com.trident.math.field;

import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import org.apache.commons.math3.Field;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NullArgumentException;

import java.util.Objects;

public class GaloisFieldOverPolyElement implements GaloisFieldElement<GaloisFieldOverPolyElement> {

    private final GaloisFieldOverPoly field;
    private final UnivariatePolynomialZp64 value;

    GaloisFieldOverPolyElement(GaloisFieldOverPoly field, UnivariatePolynomialZp64 value) {
        this.field = field;
        this.value = value;
    }

    static GaloisFieldOverPolyElement from(GaloisFieldOverPoly field, UnivariatePolynomialZp64 value) {
        return new GaloisFieldOverPolyElement(field, value);
    }

    UnivariatePolynomialZp64 getValue() {
        return value;
    }

    @Override
    public int compareTo(GaloisFieldOverPolyElement o) {
        return value.compareTo(o.value);
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
        return field.mul(this, n);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GaloisFieldOverPolyElement that = (GaloisFieldOverPolyElement) o;
        return Objects.equals(field, that.field) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
