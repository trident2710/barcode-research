package com.trident.barcode.research.model;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface BarcodeCharset {

    @Value.Parameter
    BarcodeCharsetType charsetType();

    @Value.Parameter
    List<BarcodeSign> signs();
}
