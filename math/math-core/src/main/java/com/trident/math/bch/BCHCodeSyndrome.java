package com.trident.math.bch;

import com.trident.math.field.GaloisFieldElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Objects;

public class BCHCodeSyndrome<Symbol extends GaloisFieldElement<Symbol>, Locator extends GaloisFieldElement<Locator>> {
    private final FieldMatrix<Symbol> message;
    private final FieldMatrix<Locator> syndrome;

    BCHCodeSyndrome(FieldMatrix<Symbol> message,
                    FieldMatrix<Locator> syndrome) {
        this.message = message;
        this.syndrome = syndrome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BCHCodeSyndrome<?, ?> that = (BCHCodeSyndrome<?, ?>) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(syndrome, that.syndrome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, syndrome);
    }

    @Override
    public String toString() {
        return "BCHCodeSyndrome{" +
                "message=" + message +
                ", syndrome=" + syndrome +
                '}';
    }
}
