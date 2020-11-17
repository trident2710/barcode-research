package com.trident.math.field;

import cc.redberry.rings.IntegersZp64;
import cc.redberry.rings.Rings;
import org.apache.commons.math3.FieldElement;

import java.util.Objects;

public final class GaloisFieldOverPrime implements GaloisField<GaloisFieldOverPrimeElement> {

    public static final GaloisFieldOverPrime GF5 = new GaloisFieldOverPrime(5);

    private final IntegersZp64 field;

    public GaloisFieldOverPrime(long prime) {
        this.field = Rings.Zp64(prime);
    }

    @Override
    public GaloisFieldOverPrimeElement add(GaloisFieldOverPrimeElement first, GaloisFieldOverPrimeElement second) {
        return new GaloisFieldOverPrimeElement(this, field.add(first.digitalRepresentation(), second.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement sub(GaloisFieldOverPrimeElement first, GaloisFieldOverPrimeElement second) {
        return new GaloisFieldOverPrimeElement(this, field.subtract(first.digitalRepresentation(), second.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement mul(GaloisFieldOverPrimeElement first, GaloisFieldOverPrimeElement second) {
        return new GaloisFieldOverPrimeElement(this, field.multiply(first.digitalRepresentation(), second.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement div(GaloisFieldOverPrimeElement first, GaloisFieldOverPrimeElement second) {
        return new GaloisFieldOverPrimeElement(this, field.divide(first.digitalRepresentation(), second.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement inv(GaloisFieldOverPrimeElement element) {
        return new GaloisFieldOverPrimeElement(this, field.reciprocal(element.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement neg(GaloisFieldOverPrimeElement element) {
        return new GaloisFieldOverPrimeElement(this, field.negate(element.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement mod(GaloisFieldOverPrimeElement value) {
        return new GaloisFieldOverPrimeElement(this, field.modulus(value.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement getZero() {
        return getOfValue(0L);
    }

    @Override
    public GaloisFieldOverPrimeElement getOne() {
        return getOfValue(1L);
    }

    @Override
    public Class<? extends FieldElement<GaloisFieldOverPrimeElement>> getRuntimeClass() {
        return GaloisFieldOverPrimeElement.class;
    }

    @Override
    public GaloisFieldOverPrimeElement getOfValue(long value) {
        return new GaloisFieldOverPrimeElement(this, field.modulus(value));
    }

    @Override
    public long prime() {
        return field.modulus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GaloisFieldOverPrime that = (GaloisFieldOverPrime) o;
        return Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }

    @Override
    public String toString() {
        return field.toString();
    }
}
