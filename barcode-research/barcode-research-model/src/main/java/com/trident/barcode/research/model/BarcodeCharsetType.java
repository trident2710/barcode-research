package com.trident.barcode.research.model;

import org.immutables.value.Value;

@Value.Immutable
public interface BarcodeCharsetType {

    @Value.Parameter
    int index();

    @Value.Parameter
    String label();
}
