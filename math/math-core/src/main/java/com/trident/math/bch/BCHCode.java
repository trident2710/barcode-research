package com.trident.math.bch;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisFieldOverPolyElement;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.apache.commons.math3.linear.FieldMatrix;

public class BCHCode {
    private final FieldMatrix<GaloisFieldOverPrimeElement> generator;
    private final FieldMatrix<GaloisFieldOverPolyElement> correction;

    public BCHCode(FieldMatrix<GaloisFieldOverPrimeElement> generator,
                   FieldMatrix<GaloisFieldOverPolyElement> correction) {
        this.generator = generator;
        this.correction = correction;
    }

    public static BCHCode of(FieldMatrix<GaloisFieldOverPrimeElement> generator,
                             FieldMatrix<GaloisFieldOverPolyElement> correction) {
        return new BCHCode(generator, correction);
    }

    public FieldMatrix<GaloisFieldOverPrimeElement> encode(FieldMatrix<GaloisFieldOverPrimeElement> message) {
        Preconditions.checkArgument(message.getRowDimension() == 1);
        Preconditions.checkArgument(message.getColumnDimension() == generator.getRowDimension());
        Preconditions.checkArgument(message.getField().equals(generator.getField()));
        return message.multiply(generator);
    }
}
