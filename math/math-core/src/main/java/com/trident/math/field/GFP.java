package com.trident.math.field;

import cc.redberry.rings.IntegersZp64;
import cc.redberry.rings.Rings;
import org.apache.commons.math3.FieldElement;

import java.util.Iterator;
import java.util.Objects;

public final class GFP implements GF<GFPElement> {

    private final IntegersZp64 internal_field;

    private GFP(long prime) {
        this.internal_field = Rings.Zp64(prime);
    }

    public static GFP of(long prime) {
        return new GFP(prime);
    }

    @Override
    public GFPElement add(GFPElement first, GFPElement second) {
        return new GFPElement(this, internal_field.add(first.digitalRepresentation(), second.digitalRepresentation()));
    }

    @Override
    public GFPElement sub(GFPElement first, GFPElement second) {
        return new GFPElement(this, internal_field.subtract(first.digitalRepresentation(), second.digitalRepresentation()));
    }

    @Override
    public GFPElement mul(GFPElement first, GFPElement second) {
        return new GFPElement(this, internal_field.multiply(first.digitalRepresentation(), second.digitalRepresentation()));
    }

    @Override
    public GFPElement div(GFPElement first, GFPElement second) {
        return new GFPElement(this, internal_field.divide(first.digitalRepresentation(), second.digitalRepresentation()));
    }

    @Override
    public GFPElement inv(GFPElement element) {
        return new GFPElement(this, internal_field.reciprocal(element.digitalRepresentation()));
    }

    @Override
    public GFPElement neg(GFPElement element) {
        return new GFPElement(this, internal_field.negate(element.digitalRepresentation()));
    }

    @Override
    public GFPElement mod(GFPElement value) {
        return new GFPElement(this, internal_field.modulus(value.digitalRepresentation()));
    }

    @Override
    public GFPElement getZero() {
        return getOfValue(0L);
    }

    @Override
    public GFPElement getOne() {
        return getOfValue(1L);
    }

    @Override
    public Class<? extends FieldElement<GFPElement>> getRuntimeClass() {
        return GFPElement.class;
    }

    @Override
    public GFPElement getOfValue(long value) {
        return new GFPElement(this, internal_field.modulus(value));
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
    public Iterator<GFPElement> iterator() {
        return new Iterator<>() {
            private GFPElement next = getOne();

            @Override
            public boolean hasNext() {
                return !next.equals(getZero());
            }

            @Override
            public GFPElement next() {
                var out = next;
                next = next.add(getOne());
                return out;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GFP that = (GFP) o;
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
