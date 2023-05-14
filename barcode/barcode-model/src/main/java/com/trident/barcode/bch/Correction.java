package com.trident.barcode.bch;

import com.trident.barcode.model.Code;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface Correction {

    @Value.Parameter
    CorrectionStatus status();

    @Value.Parameter
    Optional<Code> correctedCode();
}
