package com.trident.math.field;

import cc.redberry.rings.Rings;
import cc.redberry.rings.poly.FiniteField;
import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import org.apache.commons.math3.FieldElement;

public class GaloisFieldOverPoly implements GaloisField<GaloisFieldOverPolyElement> {

    private final FiniteField<UnivariatePolynomialZp64> field;

    public GaloisFieldOverPoly(long prime, int exponent) {
        this.field = Rings.GF(prime, exponent);
    }

    @Override
    public GaloisFieldOverPolyElement add(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return GaloisFieldOverPolyElement.from(this, field.add(first.value(), second.value()));
    }

    @Override
    public GaloisFieldOverPolyElement sub(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return GaloisFieldOverPolyElement.from(this, field.subtract(first.value(), second.value()));
    }

    @Override
    public GaloisFieldOverPolyElement mul(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return GaloisFieldOverPolyElement.from(this, field.multiply(first.value(), second.value()));
    }

    @Override
    public GaloisFieldOverPolyElement div(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return GaloisFieldOverPolyElement.from(this, field.divideExact(first.value(), second.value()));
    }

    @Override
    public GaloisFieldOverPolyElement inv(GaloisFieldOverPolyElement element) {
        return GaloisFieldOverPolyElement.from(this, field.reciprocal(element.value()));
    }

    @Override
    public GaloisFieldOverPolyElement neg(GaloisFieldOverPolyElement element) {
        return GaloisFieldOverPolyElement.from(this, field.negate(element.value()));
    }

    @Override
    public GaloisFieldOverPolyElement mod(GaloisFieldOverPolyElement element) {
        return GaloisFieldOverPolyElement.from(this, field.valueOf(element.value()));
    }

    @Override
    public GaloisFieldOverPolyElement getZero() {
        return GaloisFieldOverPolyElement.from(this, field.getZero());
    }

    @Override
    public GaloisFieldOverPolyElement getOne() {
        return GaloisFieldOverPolyElement.from(this, field.getOne());
    }

    @Override
    public Class<? extends FieldElement<GaloisFieldOverPolyElement>> getRuntimeClass() {
        return GaloisFieldOverPolyElement.class;
    }

    public int getPower() {
        return field.degree();
    }
}
