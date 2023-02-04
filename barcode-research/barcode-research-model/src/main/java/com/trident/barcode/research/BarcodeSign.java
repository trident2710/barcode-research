package com.trident.barcode.research;

import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;

@Value.Immutable
public interface BarcodeSign {

    @Value.Parameter
    BarcodeCharsetType charset();

    @Value.Parameter
    BarcodeSignType type();

    @Value.Parameter
    Optional<BarcodeCharsetType> switchesTo();

    @Value.Parameter
    List<Integer> codeRepresentation();

    @Value.Parameter
    int digitalRepresentation();

    @Value.Parameter
    String stringRepresentation();
}
