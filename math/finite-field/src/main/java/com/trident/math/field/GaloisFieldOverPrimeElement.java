package com.trident.math.field;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NullArgumentException;

import java.util.Objects;

public class GaloisFieldOverPrimeElement implements GaloisFieldElement<GaloisFieldOverPrimeElement> {

    private final GaloisFieldOverPrime field;
    private final long value;

    public GaloisFieldOverPrimeElement(GaloisFieldOverPrime field, long value) {
        this.field = field;
        this.value = field.mod(value);
    }

    @Override
    public GaloisFieldOverPrimeElement add(GaloisFieldOverPrimeElement a) throws NullArgumentException {
        return field.add(this, a);
    }

    @Override
    public GaloisFieldOverPrimeElement subtract(GaloisFieldOverPrimeElement a) throws NullArgumentException {
        return field.sub(this, a);
    }

    @Override
    public GaloisFieldOverPrimeElement negate() {
        return field.inv(this);
    }

    @Override
    public GaloisFieldOverPrimeElement multiply(int n) {
        return field.mul(this, new GaloisFieldOverPrimeElement(field, n));
    }

    @Override
    public GaloisFieldOverPrimeElement multiply(GaloisFieldOverPrimeElement a) throws NullArgumentException {
        return field.mul(this, a);
    }

    @Override
    public GaloisFieldOverPrimeElement divide(GaloisFieldOverPrimeElement a) throws NullArgumentException, MathArithmeticException {
        return field.div(this, a);
    }

    @Override
    public GaloisFieldOverPrimeElement reciprocal() throws MathArithmeticException {
        return field.inv(this);
    }

    @Override
    public Field<GaloisFieldOverPrimeElement> getField() {
        return field;
    }

    long value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GaloisFieldOverPrimeElement that = (GaloisFieldOverPrimeElement) o;
        return value == that.value
                && Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, value);
    }
}
