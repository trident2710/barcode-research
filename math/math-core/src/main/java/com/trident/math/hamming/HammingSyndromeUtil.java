package com.trident.math.hamming;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.field.GaloisFieldOverPrimeUtil;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Optional;

public final class HammingSyndromeUtil {
    private HammingSyndromeUtil() {
        throw new IllegalArgumentException("Cannot be instantiated");
    }

    public static Optional<GaloisFieldOverPrimeElement> calculateErrorValue(FieldMatrix<GaloisFieldOverPrimeElement> syndrome) {
        Preconditions.checkArgument(syndrome.getRowDimension() == 1);
        return GaloisFieldOverPrimeUtil.getFirstNonZero(syndrome);
    }

    public static FieldMatrix<GaloisFieldOverPrimeElement> calculateErrorPosition(FieldMatrix<GaloisFieldOverPrimeElement> syndrome,
            GaloisFieldOverPrimeElement errorValue) {
        Preconditions.checkArgument(!errorValue.equals(errorValue.getField().getZero()));
        return syndrome.scalarMultiply(errorValue.reciprocal());
    }
}
