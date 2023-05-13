package com.trident.barcode.padding;

public interface PaddingStrategy {

    PaddingStrategy NO_PADDING = messageLength -> 0;

    int getPaddingCount(int messageLength);
}
