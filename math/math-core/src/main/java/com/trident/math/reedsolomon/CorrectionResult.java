package com.trident.math.reedsolomon;


import com.trident.math.field.GFPElement;
import org.apache.commons.math3.linear.FieldMatrix;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface CorrectionResult {

    @Value.Parameter
    CorrectionStatus status();

    @Value.Parameter
    FieldMatrix<GFPElement> message();

    @Value.Parameter
    Optional<FieldMatrix<GFPElement>> correctionVector();

    default Optional<FieldMatrix<GFPElement>> correctedMessage() {
        return correctionVector().map(correction -> {
            var corrected = message().copy();
            for (int i = 0; i < correction.getColumnDimension(); i++) {
                corrected.setEntry(0, i, message().getEntry(0, i).subtract(correction.getEntry(0, i)));
            }
            return corrected;
        });
    }

    enum CorrectionStatus {

        SUCCESS,
        TOO_MUCH_ERASURE,
        MORE_THAN_XI_ERRORS,
    }
}
