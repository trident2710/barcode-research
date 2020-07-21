package com.trident.math.hamming;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.field.GaloisFieldOverPrimeUtil;
import org.apache.commons.math3.linear.FieldMatrix;

public final class SyndromeUtil {
    private SyndromeUtil() {
        throw new IllegalArgumentException("Cannot be instantiated");
    }

    public static GaloisFieldOverPrimeElement calculateErrorValue(FieldMatrix<GaloisFieldOverPrimeElement> syndrome) {
        Preconditions.checkArgument(syndrome.getRowDimension() == 1);
        return GaloisFieldOverPrimeUtil.getMaxElement(syndrome);
    }

    public static FieldMatrix<GaloisFieldOverPrimeElement> calculateErrorPosition(FieldMatrix<GaloisFieldOverPrimeElement> syndrome, GaloisFieldOverPrimeElement errorValue) {
        return syndrome.scalarMultiply(errorValue.reciprocal());
    }
}
