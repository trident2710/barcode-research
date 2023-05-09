package com.trident.barcode.research.image;

import org.immutables.value.Value;

@Value.Immutable
public interface BarcodeParams {

    @Value.Parameter
    int signItemsInSign();

    @Value.Parameter
    int pixelsPerSignItem();

    default int signDimension() {
        return (int) Math.sqrt(signItemsInSign());
    }
}
