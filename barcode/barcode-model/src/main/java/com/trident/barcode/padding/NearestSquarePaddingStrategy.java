package com.trident.barcode.padding;

public class NearestSquarePaddingStrategy implements PaddingStrategy {

    @Override
    public int getPaddingCount(int messageLength) {
        return nearestSquare(messageLength) - messageLength;
    }

    private int nearestSquare(int n) {
        int root = 2;
        while (root * root < n) {
            root++;
        }
        return root * root;
    }
}
