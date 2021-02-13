package com.trident.math.field;

import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import com.trident.math.NumberUtil;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NullArgumentException;

import java.util.Objects;

import static com.trident.math.PolynomialStringsUtil.polyToString;

public class GaloisFieldOverPolyElement implements GaloisFieldElement<GaloisFieldOverPolyElement> {

    private final GaloisFieldOverPoly field;
    private final long digitalRepresentation;
    final UnivariatePolynomialZp64 internal_value;

    GaloisFieldOverPolyElement(GaloisFieldOverPoly field, UnivariatePolynomialZp64 poly) {
        this.field = field;
        this.digitalRepresentation = toDigitalRepresentation(poly);
        this.internal_value = poly;
    }

    static GaloisFieldOverPolyElement from(GaloisFieldOverPoly field, UnivariatePolynomialZp64 poly) {
        return new GaloisFieldOverPolyElement(field, poly);
    }

    @Override
    public int compareTo(GaloisFieldOverPolyElement o) {
        return internal_value.compareTo(o.internal_value);
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
        return field.times(this, n);
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
    public GaloisFieldOverPoly getField() {
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
        GaloisFieldOverPolyElement that = (GaloisFieldOverPolyElement) o;
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
