package com.trident.math.field;

import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NullArgumentException;

import java.util.Objects;

public class GFPElement implements GFElement<GFPElement> {

    private final GFP field;
    private final long value;

    GFPElement(GFP field, long value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public GFPElement add(GFPElement a) throws NullArgumentException {
        return field.add(this, a);
    }

    @Override
    public GFPElement subtract(GFPElement a) throws NullArgumentException {
        return field.sub(this, a);
    }

    @Override
    public GFPElement negate() {
        return field.neg(this);
    }

    @Override
    public GFPElement multiply(int n) {
        return field.times(this, n);
    }

    @Override
    public GFPElement multiply(GFPElement a) throws NullArgumentException {
        return field.mul(this, a);
    }

    @Override
    public GFPElement divide(GFPElement a) throws NullArgumentException, MathArithmeticException {
        return field.div(this, a);
    }

    @Override
    public GFPElement reciprocal() throws MathArithmeticException {
        return field.inv(this);
    }

    @Override
    public GFP getField() {
        return field;
    }

    @Override
    public int compareTo(GFPElement o) {
        return Long.compare(this.value, o.value);
    }

    @Override
    public long digitalRepresentation() {
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
        GFPElement that = (GFPElement) o;
        return value == that.value
                && Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
