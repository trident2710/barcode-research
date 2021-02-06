package com.trident.math.bch;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisFieldOverPoly;
import com.trident.math.field.GaloisFieldOverPolyElement;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.FieldMatrix;

public class BCHCode {
    private final FieldMatrix<GaloisFieldOverPrimeElement> generator;
    private final FieldMatrix<GaloisFieldOverPolyElement> correctionExtendedT;

    private BCHCode(FieldMatrix<GaloisFieldOverPrimeElement> generator,
                    FieldMatrix<GaloisFieldOverPolyElement> correctionExtendedT) {
        this.generator = generator;
        this.correctionExtendedT = correctionExtendedT;
    }

    public static BCHCode of(FieldMatrix<GaloisFieldOverPrimeElement> generator,
                             FieldMatrix<GaloisFieldOverPolyElement> correctionExtended) {
        Preconditions.checkArgument(generator.getColumnDimension() == correctionExtended.getColumnDimension());
        var correctionT = correctionExtended.transpose();
        checkCorrect(generator, correctionT);
        return new BCHCode(generator, correctionExtended.transpose());
    }

    public FieldMatrix<GaloisFieldOverPrimeElement> encode(FieldMatrix<GaloisFieldOverPrimeElement> message) {
        Preconditions.checkArgument(message.getRowDimension() == 1);
        Preconditions.checkArgument(message.getColumnDimension() == generator.getRowDimension());
        Preconditions.checkArgument(message.getField().equals(generator.getField()));
        return message.multiply(generator);
    }

    public BCHCodeSyndrome syndrome(FieldMatrix<GaloisFieldOverPrimeElement> code) {
        Preconditions.checkArgument(code.getRowDimension() == 1);
        Preconditions.checkArgument(code.getColumnDimension() == correctionExtendedT.getRowDimension());
        var syndrome = calculateSyndrome(code);
        return BCHCodeSyndrome.of(code, syndrome);
    }

    private FieldMatrix<GaloisFieldOverPolyElement> calculateSyndrome(FieldMatrix<GaloisFieldOverPrimeElement> code) {
        return multiply(code, correctionExtendedT);
    }

    private static void checkCorrect(FieldMatrix<GaloisFieldOverPrimeElement> generator,
                                     FieldMatrix<GaloisFieldOverPolyElement> correctionT) {
        var multiplication = multiply(generator, correctionT);
        Preconditions.checkArgument(multiplication.equals(new Array2DRowFieldMatrix<>(correctionT.getField(), generator.getRowDimension(), correctionT.getColumnDimension())),
                "generator and correction do not meet G * H^t = 0 condition");
    }

    private static FieldMatrix<GaloisFieldOverPolyElement> multiply(FieldMatrix<GaloisFieldOverPrimeElement> primeMatrix, FieldMatrix<GaloisFieldOverPolyElement> polyMatrix) {
        var field = (GaloisFieldOverPoly) polyMatrix.getField();
        var result = new Array2DRowFieldMatrix<>(field, primeMatrix.getRowDimension(), polyMatrix.getColumnDimension());

        for (int i = 0; i < primeMatrix.getRowDimension(); i++) {
            for (int j = 0; j < polyMatrix.getColumnDimension(); j++) {
                for (int k = 0; k < primeMatrix.getColumnDimension(); k++) {
                    result.addToEntry(i, j, field.times(polyMatrix.getEntry(k, j), (int) primeMatrix.getEntry(i, k).digitalRepresentation()));
                }
            }
        }

        return result;
    }
}
