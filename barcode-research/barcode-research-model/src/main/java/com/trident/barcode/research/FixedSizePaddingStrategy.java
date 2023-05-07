package com.trident.barcode.research;

public class FixedSizePaddingStrategy implements PaddingStrategy {

    private final int sideLength;

    public FixedSizePaddingStrategy(int sideLength) {
        this.sideLength = sideLength;
    }

    @Override
    public int getPaddingCount(int messageLength) {
        int desiredSize = sideLength * sideLength;
        if (messageLength > desiredSize) {
            throw new IllegalArgumentException("Message is too long");
        }
        return desiredSize - messageLength;
    }
}
