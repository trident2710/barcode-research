package com.trident.barcode.research;

public interface PaddingStrategy {

    PaddingStrategy NO_PADDING = messageLength -> 0;

    int getPaddingCount(int messageLength);
}
