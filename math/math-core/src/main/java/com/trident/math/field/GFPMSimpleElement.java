package com.trident.math.field;

import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import com.trident.math.NumberUtil;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NullArgumentException;

import java.util.Objects;

import static com.trident.math.PolyUtil.polyToString;

public class GFPMSimpleElement implements GFPMElement<GFPMSimpleElement> {

    private final GFPMSimple field;
    private final long digitalRepresentation;
    final UnivariatePolynomialZp64 internal_value;

    GFPMSimpleElement(GFPMSimple field, UnivariatePolynomialZp64 poly) {
        this.field = field;
        this.digitalRepresentation = toDigitalRepresentation(poly);
        this.internal_value = poly;
    }

    static GFPMSimpleElement from(GFPMSimple field, UnivariatePolynomialZp64 poly) {
        return new GFPMSimpleElement(field, poly);
    }

    @Override
    public int compareTo(GFPMSimpleElement o) {
        return internal_value.compareTo(o.internal_value);
    }

    @Override
    public GFPMSimpleElement add(GFPMSimpleElement element) throws NullArgumentException {
        return field.add(this, element);
    }

    @Override
    public GFPMSimpleElement subtract(GFPMSimpleElement element) throws NullArgumentException {
        return field.sub(this, element);
    }

    @Override
    public GFPMSimpleElement negate() {
        return field.neg(this);
    }

    @Override
    public GFPMSimpleElement multiply(int n) {
        return field.times(this, n);
    }

    @Override
    public GFPMSimpleElement multiply(GFPMSimpleElement element) throws NullArgumentException {
        return field.mul(this, element);
    }

    @Override
    public GFPMSimpleElement divide(GFPMSimpleElement element) throws NullArgumentException, MathArithmeticException {
        return field.div(this, element);
    }

    @Override
    public GFPMSimpleElement reciprocal() throws MathArithmeticException {
        return field.inv(this);
    }

    @Override
    public GFPMSimple getField() {
        return field;
    }

    @Override
    public long digitalRepresentation() {
        return digitalRepresentation;
    }

    private long toDigitalRepresentation(UnivariatePolynomialZp64 value) {
        return NumberUtil.fromNBased(value.stream().toArray(), field.prime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GFPMSimpleElement that = (GFPMSimpleElement) o;
        return Objects.equals(field, that.field) &&
                Objects.equals(internal_value, that.internal_value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, internal_value);
    }

    @Override
    public String toString() {
        return digitalRepresentation + " (" + polyToString(internal_value.stream().toArray()) + ")";
    }
}
