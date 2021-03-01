package com.trident.math.field;

import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import com.trident.math.NumberUtil;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NullArgumentException;

import java.util.Objects;

import static com.trident.math.PolynomialStringsUtil.polyToString;

public class GFPMElement implements GFElement<GFPMElement> {

    private final GFPM field;
    private final long digitalRepresentation;
    final UnivariatePolynomialZp64 internal_value;

    GFPMElement(GFPM field, UnivariatePolynomialZp64 poly) {
        this.field = field;
        this.digitalRepresentation = toDigitalRepresentation(poly);
        this.internal_value = poly;
    }

    static GFPMElement from(GFPM field, UnivariatePolynomialZp64 poly) {
        return new GFPMElement(field, poly);
    }

    @Override
    public int compareTo(GFPMElement o) {
        return internal_value.compareTo(o.internal_value);
    }

    @Override
    public GFPMElement add(GFPMElement element) throws NullArgumentException {
        return field.add(this, element);
    }

    @Override
    public GFPMElement subtract(GFPMElement element) throws NullArgumentException {
        return field.sub(this, element);
    }

    @Override
    public GFPMElement negate() {
        return field.neg(this);
    }

    @Override
    public GFPMElement multiply(int n) {
        return field.times(this, n);
    }

    @Override
    public GFPMElement multiply(GFPMElement element) throws NullArgumentException {
        return field.mul(this, element);
    }

    @Override
    public GFPMElement divide(GFPMElement element) throws NullArgumentException, MathArithmeticException {
        return field.div(this, element);
    }

    @Override
    public GFPMElement reciprocal() throws MathArithmeticException {
        return field.inv(this);
    }

    @Override
    public GFPM getField() {
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
        GFPMElement that = (GFPMElement) o;
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
