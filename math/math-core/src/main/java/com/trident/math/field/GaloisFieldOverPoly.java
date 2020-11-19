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

    public static GaloisFieldOverPoly GF4 = new GaloisFieldOverPoly(2, 2, new long[]{1, 1, 1});
    public static GaloisFieldOverPoly GF9 = new GaloisFieldOverPoly(3, 2, new long[]{2, 1, 1});

    private final FiniteField<UnivariatePolynomialZp64> field;
    private final long prime;
    private final int exponent;
    private final long[] irreduciblePoly;

    public GaloisFieldOverPoly(long prime, int exponent, long[] irreduciblePoly) {
        this.field = Rings.GF(UnivariatePolynomialZp64.create(prime, irreduciblePoly));
        this.prime = prime;
        this.exponent = exponent;
        this.irreduciblePoly = irreduciblePoly;
    }

    @Override
    public GaloisFieldOverPolyElement add(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return from(this, field.add(first.getPolyValue(), second.getPolyValue()));
    }

    @Override
    public GaloisFieldOverPolyElement sub(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return from(this, field.subtract(first.getPolyValue(), second.getPolyValue()));
    }

    @Override
    public GaloisFieldOverPolyElement mul(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return from(this, field.multiply(first.getPolyValue(), second.getPolyValue()));
    }

    @Override
    public GaloisFieldOverPolyElement div(GaloisFieldOverPolyElement first, GaloisFieldOverPolyElement second) {
        return from(this, field.divideExact(first.getPolyValue(), second.getPolyValue()));
    }

    @Override
    public GaloisFieldOverPolyElement inv(GaloisFieldOverPolyElement element) {
        return from(this, field.reciprocal(element.getPolyValue()));
    }

    @Override
    public GaloisFieldOverPolyElement neg(GaloisFieldOverPolyElement element) {
        return from(this, field.negate(element.getPolyValue()));
    }

    @Override
    public GaloisFieldOverPolyElement mod(GaloisFieldOverPolyElement element) {
        return from(this, field.valueOf(element.getPolyValue()));
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
        return from(this, field.valueOf(fromDigitalValue(value)));
    }

    @Override
    public long prime() {
        return prime;
    }

    @Override
    public long elementsCount() {
        return prime() * exponent();
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
