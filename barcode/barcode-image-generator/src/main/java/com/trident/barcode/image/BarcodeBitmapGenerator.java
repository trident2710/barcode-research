package com.trident.barcode.image;

import com.trident.barcode.model.Code;

import java.util.List;

public class BarcodeBitmapGenerator {

    public Bitmap generateBarcodeBitmap(Code barcodeData, BarcodeParams params) {
        var bitmap = generateBitmap(barcodeData, params);
        printSigns(barcodeData, bitmap, params);
        return ImmutableBitmap.of(bitmap.data);
    }

    private static int signsCount(Code barcodeData, BarcodeParams params) {
        // TODO: signItemsInSign is not BarcodeParams
        return barcodeData.data().size() / params.signItemsInSign();
    }

    private static int barcodeDimension(Code barcodeData, BarcodeParams params) {
        return (int) Math.sqrt(signsCount(barcodeData, params));
    }

    private MutableBitmap generateBitmap(Code barcodeData, BarcodeParams params) {
        int barcodeDimension = barcodeDimension(barcodeData, params);
        int sidePixels = barcodeDimension * params.signDimension() * params.pixelsPerSignItem();
        return new MutableBitmap(new int[sidePixels][sidePixels]);
    }

    private void printSigns(Code barcodeData, MutableBitmap bitmap, BarcodeParams params) {
        int barcodeDimension = barcodeDimension(barcodeData, params);
        int signsCount = signsCount(barcodeData, params);
        var data = barcodeData.data();

        for (int signIndex = 0; signIndex < signsCount; signIndex++) {
            var signCoordinates = new SignCoordinates(signIndex / barcodeDimension, signIndex % barcodeDimension);
            var signItems = data.subList(signIndex * params.signItemsInSign(), (signIndex + 1) * params.signItemsInSign());
            printSign(signItems, signCoordinates, bitmap, params);
        }
    }

    private void printSign(List<Integer> signItems, SignCoordinates signCoordinates, MutableBitmap bitmap, BarcodeParams params) {
        for (int signItemIndex = 0; signItemIndex < signItems.size(); signItemIndex++) {
            int signItemRow = signItemIndex / params.signDimension();
            int signItemColumn = signItemIndex % params.signDimension();
            var signItem = signItems.get(signItemIndex);

            var coordinates = new SignItemCoordinates(signCoordinates, signItemRow, signItemColumn);

            printSignItem(signItem, coordinates, bitmap, params);
        }
    }

    private void printSignItem(int signItem, SignItemCoordinates coordinates, MutableBitmap bitmap, BarcodeParams params) {
        int pixels = params.pixelsPerSignItem();
        int grouping = params.signDimension();
        for (int i = 0; i < pixels; i++) {
            for (int j = 0; j < pixels; j++) {
                int x = (coordinates.signCoordinates.signRow * grouping * pixels) + (coordinates.signItemRow * pixels) + i;
                int y = (coordinates.signCoordinates.signColumn * grouping * pixels) + (coordinates.signItemColumn * pixels) + j;
                bitmap.put(x, y, signItem + 1);
            }
        }
    }

    private static class MutableBitmap {
        private final int[][] data;

        public MutableBitmap(int[][] data) {
            this.data = data;
        }

        void put(int x, int y, int value) {
            data[x][y] = value;
        }
    }

    private static class SignCoordinates {
        private final int signRow;
        private final int signColumn;

        public SignCoordinates(int signRow, int signColumn) {
            this.signRow = signRow;
            this.signColumn = signColumn;
        }
    }

    private static class SignItemCoordinates {
        private final SignCoordinates signCoordinates;
        private final int signItemRow;
        private final int signItemColumn;

        public SignItemCoordinates(SignCoordinates signCoordinates, int signItemRow, int signItemColumn) {
            this.signCoordinates = signCoordinates;
            this.signItemRow = signItemRow;
            this.signItemColumn = signItemColumn;
        }
    }
}
