package com.trident.math.bch;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisFieldOverPoly;
import com.trident.math.field.GaloisFieldOverPolyElement;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.apache.commons.math3.linear.FieldMatrix;

public class BCHCode {
    private final FieldMatrix<GaloisFieldOverPrimeElement> generator;
    private final FieldMatrix<GaloisFieldOverPolyElement> correction;

    private BCHCode(FieldMatrix<GaloisFieldOverPrimeElement> generator,
                    FieldMatrix<GaloisFieldOverPolyElement> correction) {
        this.generator = generator;
        this.correction = correction;
    }

    public static BCHCode of(FieldMatrix<GaloisFieldOverPrimeElement> generator,
                             FieldMatrix<GaloisFieldOverPolyElement> correction) {
        checkCorrect(generator, correction);
        return new BCHCode(generator, correction);
    }

    public FieldMatrix<GaloisFieldOverPrimeElement> encode(FieldMatrix<GaloisFieldOverPrimeElement> message) {
        Preconditions.checkArgument(message.getRowDimension() == 1);
        Preconditions.checkArgument(message.getColumnDimension() == generator.getRowDimension());
        Preconditions.checkArgument(message.getField().equals(generator.getField()));
        return message.multiply(generator);
    }

    private static void checkCorrect(FieldMatrix<GaloisFieldOverPrimeElement> generator,
                              FieldMatrix<GaloisFieldOverPolyElement> correction) {
        var correctionT = correction.transpose();
        for (int i = 0; i < generator.getRowDimension(); i++) {
            var field = (GaloisFieldOverPoly) correctionT.getField();
            var sum = correctionT.getField().getZero();
            for (int j = 0; j < correctionT.getRowDimension(); j++) {
                sum = field.add(sum, field.times(correctionT.getEntry(j, i), (int) generator.getEntry(i, j).digitalRepresentation()));
            }
            Preconditions.checkArgument(sum.equals(field.getZero()), "generator and correction do not meet G * H^t = 0 condition");
        }
    }
}
