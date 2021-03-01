package com.trident.math.field;

import cc.redberry.rings.Rings;
import cc.redberry.rings.poly.FiniteField;
import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import com.trident.math.NumberUtil;
import org.apache.commons.math3.FieldElement;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import static com.trident.math.field.GFPMElement.from;

public class GFPM implements GF<GFPMElement> {

    final FiniteField<UnivariatePolynomialZp64> internal_field;
    private final long prime;
    private final int exponent;
    private final long[] irreduciblePoly;

    private GFPM(long prime, int exponent, long[] irreduciblePoly) {
        this.internal_field = Rings.GF(UnivariatePolynomialZp64.create(prime, irreduciblePoly));
        this.prime = prime;
        this.exponent = exponent;
        this.irreduciblePoly = irreduciblePoly;
    }

    public static GFPM of(long prime, int exponent, long[] irreduciblePoly) {
        return new GFPM(prime, exponent, irreduciblePoly);
    }

    @Override
    public GFPMElement add(GFPMElement first, GFPMElement second) {
        return from(this, internal_field.add(first.internal_value, second.internal_value));
    }

    @Override
    public GFPMElement sub(GFPMElement first, GFPMElement second) {
        return from(this, internal_field.subtract(first.internal_value, second.internal_value));
    }

    @Override
    public GFPMElement mul(GFPMElement first, GFPMElement second) {
        return from(this, internal_field.multiply(first.internal_value, second.internal_value));
    }

    @Override
    public GFPMElement div(GFPMElement first, GFPMElement second) {
        return from(this, internal_field.divideExact(first.internal_value, second.internal_value));
    }

    @Override
    public GFPMElement inv(GFPMElement element) {
        return from(this, internal_field.reciprocal(element.internal_value));
    }

    @Override
    public GFPMElement neg(GFPMElement element) {
        return from(this, internal_field.negate(element.internal_value));
    }

    @Override
    public GFPMElement mod(GFPMElement element) {
        return from(this, internal_field.valueOf(element.internal_value));
    }

    @Override
    public GFPMElement getZero() {
        return from(this, internal_field.getZero());
    }

    @Override
    public GFPMElement getOne() {
        return from(this, internal_field.getOne());
    }

    @Override
    public GFPMElement getOfValue(long value) {
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
    public Class<? extends FieldElement<GFPMElement>> getRuntimeClass() {
        return GFPMElement.class;
    }

    private UnivariatePolynomialZp64 fromDigitalValue(long digitalValue) {
        return UnivariatePolynomialZp64.create(prime, NumberUtil.toNBased(digitalValue, prime));
    }

    @Override
    public Iterator<GFPMElement> iterator() {
        return new Iterator<>() {
            private GFPMElement next = getOne();

            @Override
            public boolean hasNext() {
                return !next.equals(getZero());
            }

            @Override
            public GFPMElement next() {
                var out = next;
                next = next.multiply(getOfValue(prime()));
                if (next.equals(getOne())) {
                    next = getZero();
                }
                return out;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GFPM that = (GFPM) o;
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
