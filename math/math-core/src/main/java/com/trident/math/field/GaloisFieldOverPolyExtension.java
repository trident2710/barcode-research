package com.trident.math.field;

import cc.redberry.rings.Rings;
import cc.redberry.rings.poly.FiniteField;
import cc.redberry.rings.poly.univar.UnivariatePolynomial;
import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import org.apache.commons.math3.FieldElement;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.trident.math.field.GaloisFieldOverPolyExtensionElement.from;

public class GaloisFieldOverPolyExtension implements GaloisField<GaloisFieldOverPolyExtensionElement> {

    private final FiniteField<UnivariatePolynomial<UnivariatePolynomialZp64>> internal_field;
    private final GaloisFieldOverPoly coefficientsField;
    private final int exponent;
    private final long[] irreduciblePoly;

    private GaloisFieldOverPolyExtension(GaloisFieldOverPoly coefficientsField, int exponent, long[] irreduciblePoly) {
        this.coefficientsField = coefficientsField;
        this.exponent = exponent;
        this.irreduciblePoly = irreduciblePoly;
        this.internal_field = createField(coefficientsField, irreduciblePoly);
    }

    private static FiniteField<UnivariatePolynomial<UnivariatePolynomialZp64>> createField(GaloisFieldOverPoly coefficientsField, long[] irreduciblePoly) {
        var coefficients = Arrays.stream(irreduciblePoly)
                .mapToObj(c -> coefficientToPolynomial(c, coefficientsField))
                .collect(Collectors.toList())
                .toArray(new UnivariatePolynomialZp64[]{});
        var poly = UnivariatePolynomial.create(coefficientsField.internal_field, coefficients);
        return Rings.GF(poly);
    }

    private static UnivariatePolynomialZp64 coefficientToPolynomial(long coefficientValue, GaloisFieldOverPoly coefficientsField) {
        var element = coefficientsField.getOfValue(coefficientValue);
        return element.internal_value;
    }

    public static GaloisFieldOverPolyExtension of(GaloisFieldOverPoly coefficientsField, int exponent, long[] irreduciblePoly) {
        return new GaloisFieldOverPolyExtension(coefficientsField, exponent, irreduciblePoly);
    }

    public GaloisFieldOverPoly getCoefficientsField() {
        return coefficientsField;
    }

    @Override
    public GaloisFieldOverPolyExtensionElement add(GaloisFieldOverPolyExtensionElement first, GaloisFieldOverPolyExtensionElement second) {
        return from(this, internal_field.add(first.internal_value, second.internal_value));
    }

    @Override
    public GaloisFieldOverPolyExtensionElement sub(GaloisFieldOverPolyExtensionElement first, GaloisFieldOverPolyExtensionElement second) {
        return from(this, internal_field.subtract(first.internal_value, second.internal_value));
    }

    @Override
    public GaloisFieldOverPolyExtensionElement mul(GaloisFieldOverPolyExtensionElement first, GaloisFieldOverPolyExtensionElement second) {
        return from(this, internal_field.multiply(first.internal_value, second.internal_value));
    }

    @Override
    public GaloisFieldOverPolyExtensionElement div(GaloisFieldOverPolyExtensionElement first, GaloisFieldOverPolyExtensionElement second) {
        return from(this, internal_field.divideExact(first.internal_value, second.internal_value));
    }

    @Override
    public GaloisFieldOverPolyExtensionElement inv(GaloisFieldOverPolyExtensionElement element) {
        return from(this, internal_field.reciprocal(element.internal_value));
    }

    @Override
    public GaloisFieldOverPolyExtensionElement neg(GaloisFieldOverPolyExtensionElement element) {
        return from(this, internal_field.negate(element.internal_value));
    }

    @Override
    public GaloisFieldOverPolyExtensionElement mod(GaloisFieldOverPolyExtensionElement element) {
        return from(this, internal_field.valueOf(element.internal_value));
    }

    @Override
    public GaloisFieldOverPolyExtensionElement getZero() {
        return from(this, internal_field.getZero());
    }

    @Override
    public GaloisFieldOverPolyExtensionElement getOne() {
        return from(this, internal_field.getOne());
    }

    @Override
    public GaloisFieldOverPolyExtensionElement getOfValue(long value) {
        return GaloisFieldOverPolyExtensionElement.fromDigitalRepresentation(this, value);
    }

    @Override
    public long prime() {
        return coefficientsField.elementsCount();
    }

    @Override
    public long elementsCount() {
        return (long) Math.pow(prime(), exponent);
    }

    @Override
    public Class<? extends FieldElement<GaloisFieldOverPolyExtensionElement>> getRuntimeClass() {
        return GaloisFieldOverPolyExtensionElement.class;
    }
}
