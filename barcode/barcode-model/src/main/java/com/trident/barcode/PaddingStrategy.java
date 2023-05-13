package com.trident.barcode;

public interface PaddingStrategy {

    PaddingStrategy NO_PADDING = messageLength -> 0;

    int getPaddingCount(int messageLength);
}
