package com.trident.barcode.model;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface Barcode {

    @Value.Parameter
    BarcodeDictionary dictionary();

    @Value.Parameter
    List<BarcodeSign> signs();
}
