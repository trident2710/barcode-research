package com.trident.math.field;

import cc.redberry.rings.Rings;
import cc.redberry.rings.poly.FiniteField;
import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import org.apache.commons.math3.FieldElement;

import static com.trident.math.field.GaloisFieldOverPolyElement.from;

public class GaloisFieldOverPoly implements GaloisField<GaloisFieldOverPolyElement> {

    public static GaloisFieldOverPoly GF4 = new GaloisFieldOverPoly(2, 2);

    private final FiniteField<UnivariatePolynomialZp64> field;

    public GaloisFieldOverPoly(long prime, int exponent) {
        this.field = Rings.GF(prime, exponent);
    }

    @Override
    public GaloisFieldOverPolyElement add(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return from(this, field.add(first.getValue(), second.getValue()));
    }

    @Override
    public GaloisFieldOverPolyElement sub(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return from(this, field.subtract(first.getValue(), second.getValue()));
    }

    @Override
    public GaloisFieldOverPolyElement mul(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return from(this, field.multiply(first.getValue(), second.getValue()));
    }

    @Override
    public GaloisFieldOverPolyElement div(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return from(this, field.divideExact(first.getValue(), second.getValue()));
    }

    @Override
    public GaloisFieldOverPolyElement inv(GaloisFieldOverPolyElement element) {
        return from(this, field.reciprocal(element.getValue()));
    }

    @Override
    public GaloisFieldOverPolyElement neg(GaloisFieldOverPolyElement element) {
        return from(this, field.negate(element.getValue()));
    }

    @Override
    public GaloisFieldOverPolyElement mod(GaloisFieldOverPolyElement element) {
        return from(this, field.valueOf(element.getValue()));
    }

    @Override
    public GaloisFieldOverPolyElement getZero() {
        return from(this, field.getZero());
    }

    @Override
    public GaloisFieldOverPolyElement getOne() {
        return from(this, field.getOne());
    }

    @Override
    public GaloisFieldOverPolyElement getOfValue(long value) {
        return mod(from(this, monomial(value - 1)));
    }

    @Override
    public Class<? extends FieldElement<GaloisFieldOverPolyElement>> getRuntimeClass() {
        return GaloisFieldOverPolyElement.class;
    }

    private UnivariatePolynomialZp64 monomial(long exponent) {
        return UnivariatePolynomialZp64.monomial(field.degree(), 1, (int) exponent);
    }
}
