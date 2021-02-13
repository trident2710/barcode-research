package com.trident.math.field;

import cc.redberry.rings.Rings;
import cc.redberry.rings.poly.FiniteField;
import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import com.trident.math.NumberUtil;
import org.apache.commons.math3.FieldElement;

import java.util.Arrays;
import java.util.Objects;

import static com.trident.math.field.GaloisFieldOverPolyElement.from;

public class GaloisFieldOverPoly implements GaloisField<GaloisFieldOverPolyElement> {

    private final FiniteField<UnivariatePolynomialZp64> internal_field;
    private final long prime;
    private final int exponent;
    private final long[] irreduciblePoly;

    private GaloisFieldOverPoly(long prime, int exponent, long[] irreduciblePoly) {
        this.internal_field = Rings.GF(UnivariatePolynomialZp64.create(prime, irreduciblePoly));
        this.prime = prime;
        this.exponent = exponent;
        this.irreduciblePoly = irreduciblePoly;
    }

    public static GaloisFieldOverPoly of(long prime, int exponent, long[] irreduciblePoly) {
        return new GaloisFieldOverPoly(prime, exponent, irreduciblePoly);
    }

    @Override
    public GaloisFieldOverPolyElement add(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return from(this, internal_field.add(first.internal_value, second.internal_value));
    }

    @Override
    public GaloisFieldOverPolyElement sub(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return from(this, internal_field.subtract(first.internal_value, second.internal_value));
    }

    @Override
    public GaloisFieldOverPolyElement mul(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return from(this, internal_field.multiply(first.internal_value, second.internal_value));
    }

    @Override
    public GaloisFieldOverPolyElement div(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return from(this, internal_field.divideExact(first.internal_value, second.internal_value));
    }

    @Override
    public GaloisFieldOverPolyElement inv(GaloisFieldOverPolyElement element) {
        return from(this, internal_field.reciprocal(element.internal_value));
    }

    @Override
    public GaloisFieldOverPolyElement neg(GaloisFieldOverPolyElement element) {
        return from(this, internal_field.negate(element.internal_value));
    }

    @Override
    public GaloisFieldOverPolyElement mod(GaloisFieldOverPolyElement element) {
        return from(this, internal_field.valueOf(element.internal_value));
    }

    @Override
    public GaloisFieldOverPolyElement getZero() {
        return from(this, internal_field.getZero());
    }

    @Override
    public GaloisFieldOverPolyElement getOne() {
        return from(this, internal_field.getOne());
    }

    @Override
    public GaloisFieldOverPolyElement getOfValue(long value) {
        return from(this, internal_field.valueOf(fromDigitalValue(value)));
    }

    @Override
    public long prime() {
        return prime;
    }

    @Override
    public long elementsCount() {
        return (long) Math.pow(prime(), exponent());
    }

    public int exponent() {
        return exponent;
    }

    public long[] irreduciblePoly() {
        return Arrays.copyOf(irreduciblePoly, irreduciblePoly.length);
    }

    @Override
    public Class<? extends FieldElement<GaloisFieldOverPolyElement>> getRuntimeClass() {
        return GaloisFieldOverPolyElement.class;
    }

    private UnivariatePolynomialZp64 fromDigitalValue(long digitalValue) {
        return UnivariatePolynomialZp64.create(prime, NumberUtil.toNBased(digitalValue, prime));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GaloisFieldOverPoly that = (GaloisFieldOverPoly) o;
        return prime == that.prime &&
                exponent == that.exponent &&
                Arrays.equals(irreduciblePoly, that.irreduciblePoly);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(prime, exponent);
        result = 31 * result + Arrays.hashCode(irreduciblePoly);
        return result;
    }
}
