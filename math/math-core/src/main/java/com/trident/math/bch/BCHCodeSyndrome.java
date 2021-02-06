package com.trident.math.bch;

import com.trident.math.field.GaloisFieldOverPolyElement;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Objects;

public class BCHCodeSyndrome {
    private final FieldMatrix<GaloisFieldOverPrimeElement> message;
    private final FieldMatrix<GaloisFieldOverPolyElement> syndrome;

    private BCHCodeSyndrome(FieldMatrix<GaloisFieldOverPrimeElement> message,
                            FieldMatrix<GaloisFieldOverPolyElement> syndrome) {
        this.message = message;
        this.syndrome = syndrome;
    }

    public static BCHCodeSyndrome of(FieldMatrix<GaloisFieldOverPrimeElement> message,
                                     FieldMatrix<GaloisFieldOverPolyElement> syndrome) {
        return new BCHCodeSyndrome(message, syndrome);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BCHCodeSyndrome that = (BCHCodeSyndrome) o;
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
