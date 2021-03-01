package com.trident.math.field;

import cc.redberry.rings.Rings;
import cc.redberry.rings.poly.FiniteField;
import cc.redberry.rings.poly.univar.UnivariatePolynomial;
import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import org.apache.commons.math3.FieldElement;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

import static com.trident.math.field.GFPMExtensionElement.from;

public class GFPMExtension implements GFPM<GFPMExtensionElement> {

    private final FiniteField<UnivariatePolynomial<UnivariatePolynomialZp64>> internal_field;
    private final GFPMSimple coefficientsField;
    private final int exponent;
    private final long[] irreduciblePoly;

    private GFPMExtension(GFPMSimple coefficientsField, int exponent, long[] irreduciblePoly) {
        this.coefficientsField = coefficientsField;
        this.exponent = exponent;
        this.irreduciblePoly = irreduciblePoly;
        this.internal_field = createField(coefficientsField, irreduciblePoly);
    }

    private static FiniteField<UnivariatePolynomial<UnivariatePolynomialZp64>> createField(GFPMSimple coefficientsField, long[] irreduciblePoly) {
        var coefficients = Arrays.stream(irreduciblePoly)
                .mapToObj(c -> coefficientToPolynomial(c, coefficientsField))
                .collect(Collectors.toList())
                .toArray(new UnivariatePolynomialZp64[]{});
        var poly = UnivariatePolynomial.create(coefficientsField.internal_field, coefficients);
        return Rings.GF(poly);
    }

    private static UnivariatePolynomialZp64 coefficientToPolynomial(long coefficientValue, GFPMSimple coefficientsField) {
        var element = coefficientsField.getOfValue(coefficientValue);
        return element.internal_value;
    }

    public static GFPMExtension of(GFPMSimple coefficientsField, int exponent, long[] irreduciblePoly) {
        return new GFPMExtension(coefficientsField, exponent, irreduciblePoly);
    }

    public GFPMSimple getCoefficientsField() {
        return coefficientsField;
    }

    @Override
    public GFPMExtensionElement add(GFPMExtensionElement first, GFPMExtensionElement second) {
        return from(this, internal_field.add(first.internal_value, second.internal_value));
    }

    @Override
    public GFPMExtensionElement sub(GFPMExtensionElement first, GFPMExtensionElement second) {
        return from(this, internal_field.subtract(first.internal_value, second.internal_value));
    }

    @Override
    public GFPMExtensionElement mul(GFPMExtensionElement first, GFPMExtensionElement second) {
        return from(this, internal_field.multiply(first.internal_value, second.internal_value));
    }

    @Override
    public GFPMExtensionElement div(GFPMExtensionElement first, GFPMExtensionElement second) {
        return from(this, internal_field.divideExact(first.internal_value, second.internal_value));
    }

    @Override
    public GFPMExtensionElement inv(GFPMExtensionElement element) {
        return from(this, internal_field.reciprocal(element.internal_value));
    }

    @Override
    public GFPMExtensionElement neg(GFPMExtensionElement element) {
        return from(this, internal_field.negate(element.internal_value));
    }

    @Override
    public GFPMExtensionElement mod(GFPMExtensionElement element) {
        return from(this, internal_field.valueOf(element.internal_value));
    }

    @Override
    public GFPMExtensionElement getZero() {
        return from(this, internal_field.getZero());
    }

    @Override
    public GFPMExtensionElement getOne() {
        return from(this, internal_field.getOne());
    }

    @Override
    public GFPMExtensionElement getOfValue(long value) {
        return GFPMExtensionElement.fromDigitalRepresentation(this, value);
    }

    @Override
    public long prime() {
        return coefficientsField.elementsCount();
    }

    @Override
    public long[] irreduciblePoly() {
        return irreduciblePoly;
    }

    @Override
    public int exponent() {
        return exponent;
    }

    @Override
    public long elementsCount() {
        return (long) Math.pow(prime(), exponent);
    }

    @Override
    public Class<? extends FieldElement<GFPMExtensionElement>> getRuntimeClass() {
        return GFPMExtensionElement.class;
    }

    @Override
    public Iterator<GFPMExtensionElement> iterator() {
        return new Iterator<>() {
            private GFPMExtensionElement next = getOne();

            @Override
            public boolean hasNext() {
                return !next.equals(getZero());
            }

            @Override
            public GFPMExtensionElement next() {
                var out = next;
                next = next.multiply(getOfValue(prime()));
                if (next.equals(getOne())) {
                    next = getZero();
                }
                return out;
            }
        };
    }
}
