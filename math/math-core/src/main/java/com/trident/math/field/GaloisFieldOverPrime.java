package com.trident.math.field;

import cc.redberry.rings.IntegersZp64;
import cc.redberry.rings.Rings;
import org.apache.commons.math3.FieldElement;

import java.util.Objects;

public final class GaloisFieldOverPrime implements GaloisField<GaloisFieldOverPrimeElement> {

    private final IntegersZp64 internal_field;

    private GaloisFieldOverPrime(long prime) {
        this.internal_field = Rings.Zp64(prime);
    }

    public static GaloisFieldOverPrime of(long prime) {
        return new GaloisFieldOverPrime(prime);
    }

    @Override
    public GaloisFieldOverPrimeElement add(GaloisFieldOverPrimeElement first, GaloisFieldOverPrimeElement second) {
        return new GaloisFieldOverPrimeElement(this, internal_field.add(first.digitalRepresentation(), second.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement sub(GaloisFieldOverPrimeElement first, GaloisFieldOverPrimeElement second) {
        return new GaloisFieldOverPrimeElement(this, internal_field.subtract(first.digitalRepresentation(), second.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement mul(GaloisFieldOverPrimeElement first, GaloisFieldOverPrimeElement second) {
        return new GaloisFieldOverPrimeElement(this, internal_field.multiply(first.digitalRepresentation(), second.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement div(GaloisFieldOverPrimeElement first, GaloisFieldOverPrimeElement second) {
        return new GaloisFieldOverPrimeElement(this, internal_field.divide(first.digitalRepresentation(), second.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement inv(GaloisFieldOverPrimeElement element) {
        return new GaloisFieldOverPrimeElement(this, internal_field.reciprocal(element.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement neg(GaloisFieldOverPrimeElement element) {
        return new GaloisFieldOverPrimeElement(this, internal_field.negate(element.digitalRepresentation()));
    }

    @Override
    public GaloisFieldOverPrimeElement mod(GaloisFieldOverPrimeElement value) {
        return new GaloisFieldOverPrimeElement(this, internal_field.modulus(value.digitalRepresentation()));
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
        return new GaloisFieldOverPrimeElement(this, internal_field.modulus(value));
    }

    @Override
    public long prime() {
        return internal_field.modulus;
    }

    @Override
    public long elementsCount() {
        return prime();
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
        return Objects.equals(internal_field, that.internal_field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internal_field);
    }

    @Override
    public String toString() {
        return internal_field.toString();
    }
}
