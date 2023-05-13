package com.trident.barcode.padding;

import com.google.common.base.Preconditions;

public class FixedSquarePaddingStrategy implements PaddingStrategy {

    private final int sideLength;

    public FixedSquarePaddingStrategy(int sideLength) {
        this.sideLength = sideLength;
    }

    @Override
    public int getPaddingCount(int messageLength) {
        int requiredLength = sideLength * sideLength;
        Preconditions.checkArgument(messageLength <= requiredLength,
                "Message length should be less or equal to required length");
        return requiredLength - messageLength;
    }
}
