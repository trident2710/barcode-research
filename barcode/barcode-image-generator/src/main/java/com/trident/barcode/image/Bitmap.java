package com.trident.barcode.image;

import org.immutables.value.Value;

@Value.Immutable
public interface Bitmap {
    @Value.Parameter
    int[][] data();
}
