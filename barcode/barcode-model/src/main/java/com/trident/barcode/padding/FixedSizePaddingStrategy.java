package com.trident.barcode.padding;

import com.google.common.base.Preconditions;

public class FixedSizePaddingStrategy implements PaddingStrategy {

    private final int requiredLength;

    public FixedSizePaddingStrategy(int requiredLength) {
        this.requiredLength = requiredLength;
    }

    @Override
    public int getPaddingCount(int messageLength) {
        Preconditions.checkArgument(messageLength <= requiredLength,
                "Message length should be less or equal to required length");
        return requiredLength - messageLength;
    }
}
