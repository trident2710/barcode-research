package com.trident.math.field;

import cc.redberry.rings.poly.univar.UnivariatePolynomial;
import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import com.trident.math.NumberUtil;
import org.apache.commons.math3.Field;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NullArgumentException;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.trident.math.PolynomialStringsUtil.polyToString;

public class GFPMExtensionElement implements GFPMElement<GFPMExtensionElement> {

    private final GFPMExtension field;
    final UnivariatePolynomial<UnivariatePolynomialZp64> internal_value;
    private final long digitalRepresentation;

    GFPMExtensionElement(GFPMExtension field, UnivariatePolynomial<UnivariatePolynomialZp64> internal_value) {
        this.field = field;
        this.internal_value = internal_value;
        this.digitalRepresentation = toDigitalRepresentation(internal_value);
    }

    static GFPMExtensionElement from(GFPMExtension field, UnivariatePolynomial<UnivariatePolynomialZp64> polyValue) {
        return new GFPMExtensionElement(field, polyValue);
    }

    @Override
    public long digitalRepresentation() {
        return toDigitalRepresentation(internal_value);
    }

    @Override
    public int compareTo(GFPMExtensionElement o) {
        return internal_value.compareTo(o.internal_value);
    }

    @Override
    public GFPMExtensionElement add(GFPMExtensionElement a) throws NullArgumentException {
        return field.add(this, a);
    }

    @Override
    public GFPMExtensionElement subtract(GFPMExtensionElement a) throws NullArgumentException {
        return field.sub(this, a);
    }

    @Override
    public GFPMExtensionElement negate() {
        return field.neg(this);
    }

    @Override
    public GFPMExtensionElement multiply(int n) {
        return field.times(this, n);
    }

    @Override
    public GFPMExtensionElement multiply(GFPMExtensionElement a) throws NullArgumentException {
        return field.mul(this, a);
    }

    @Override
    public GFPMExtensionElement divide(GFPMExtensionElement a) throws NullArgumentException, MathArithmeticException {
        return field.div(this, a);
    }

    @Override
    public GFPMExtensionElement reciprocal() throws MathArithmeticException {
        return field.inv(this);
    }

    @Override
    public Field<GFPMExtensionElement> getField() {
        return field;
    }

    static GFPMExtensionElement fromDigitalRepresentation(GFPMExtension field, long value) {
        var coefficients = Arrays.stream(NumberUtil.toNBased(value, field.prime()))
                .boxed()
                .map(field.getCoefficientsField()::getOfValue)
                .map(elem -> elem.internal_value)
                .collect(Collectors.toList())
                .toArray(new UnivariatePolynomialZp64[]{});
        var polyValue = UnivariatePolynomial.create(field.getCoefficientsField().internal_field, coefficients);
        return GFPMExtensionElement.from(field, polyValue);
    }

    private long toDigitalRepresentation(UnivariatePolynomial<UnivariatePolynomialZp64> value) {
        var coefficients = toPolyRepresentation(value);
        return NumberUtil.fromNBased(coefficients, field.prime());
    }

    private long[] toPolyRepresentation(UnivariatePolynomial<UnivariatePolynomialZp64> value) {
        return value.stream()
                .map(c -> GFPMSimpleElement.from(field.getCoefficientsField(), c))
                .map(GFPMSimpleElement::digitalRepresentation)
                .mapToLong(l -> l)
                .toArray();
    }

    @Override
    public String toString() {
        return digitalRepresentation + " (" + polyToString(toPolyRepresentation(internal_value)) + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GFPMExtensionElement that = (GFPMExtensionElement) o;
        return internal_value.equals(that.internal_value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internal_value);
    }
}
