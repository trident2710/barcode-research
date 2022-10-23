package com.trident.math.reedsolomon;

import org.immutables.value.Value;

@Value.Immutable
public interface CorrectionResult {

    @Value.Parameter
    CorrectionStatus status();

    enum CorrectionStatus {
        TOO_MUCH_ERASURE,
        MORE_THAN_XI_ERRORS,
    }
}
