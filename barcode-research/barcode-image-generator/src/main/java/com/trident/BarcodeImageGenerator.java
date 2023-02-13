package com.trident;

import com.trident.barcode.research.BarcodeCodecImpl;
import com.trident.barcode.research.DefaultBarcodeEncoder;
import com.trident.barcode.research.model.BarcodeDictionaries;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public final class BarcodeImageGenerator {

    public void generateBarcodeImage(String message, String path) {
        var codec = new BarcodeCodecImpl(new DefaultBarcodeEncoder(BarcodeDictionaries.BASE_59), x -> null);
        var code = codec.encode(message);
        int signItemsInSign = 9;
        int pixels = 30;
        int grouping = 3;


        var signsCount = code.data().size() / signItemsInSign;

        var bitmap = generateBitmap(signsCount, pixels, grouping);

        printSigns(code.data(), bitmap, pixels, grouping, signItemsInSign, grouping);

        var image = toBufferedImage(bitmap);

        savePNG(image, path);
    }

    private BufferedImage toBufferedImage(int[][] data) {
        int rows = data.length;
        int columns = data[0].length;
        final BufferedImage res = new BufferedImage(rows, columns, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                res.setRGB(x, y, color(data[x][y]).getRGB());
            }
        }
        return res;
    }

    private Color color(int value) {
        switch (value) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.WHITE;
            case 2:
                return Color.RED;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.BLUE;
            default:
                return Color.CYAN;
        }
    }

    private static void savePNG(final BufferedImage bi, final String path) {
        try {
            ImageIO.write(bi, "bmp", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[][] generateBitmap(int signsCount, int signPixels, int grouping) {
        int signLines = signsCount % grouping == 0 ? signsCount / grouping : signsCount / grouping + 1;

        return new int[signLines * grouping * signPixels][grouping * grouping * signPixels];

    }

    private void printSigns(List<Integer> data, int[][] bitmap, int pixels, int signsInLine, int signItemsInSign, int grouping) {
        int signsCount = data.size() / signItemsInSign;

        for (int signIndex = 0; signIndex < signsCount; signIndex++) {
            int signRow = signIndex / signsInLine;
            int signColumn = signIndex % signsInLine;
            List<Integer> signItems = data.subList(signIndex * signItemsInSign, (signIndex + 1) * signItemsInSign);
            printSign(signItems, signRow, signColumn, grouping, bitmap, pixels);
        }

    }

    private void printSign(List<Integer> signItems, int signRow, int signColumn, int grouping, int[][] bitmap, int pixels) {
        for (int signItemIndex = 0; signItemIndex < signItems.size(); signItemIndex++) {
            int signItemRow = signItemIndex / grouping;
            int signItemColumn = signItemIndex % grouping;
            printSignItem(signItems.get(signItemIndex), signItemRow, signItemColumn, signRow, signColumn, pixels, grouping, bitmap);
        }
    }

    private void printSignItem(int signItem, int signItemRow, int signItemColumn, int signRow, int signColumn, int pixels, int grouping, int[][] bitmap) {
        for (int i = 0; i < pixels; i++) {
            for (int j = 0; j < pixels; j++) {
                bitmap[(signRow * grouping * pixels) + (signItemRow * pixels) + i][(signColumn * grouping * pixels) + (signItemColumn * pixels) + j] = signItem + 1;
            }
        }
    }

//    private List<BarcodeSign> signs(List<Integer> data, int pixels) {
//        var result = new ArrayList<BarcodeSign>();
//        int signCount = getSignsCount(9, data);
//
//        for (int signIndex = 0; signIndex < signCount; signIndex++) {
//            var signData = getSignData(signIndex, 9, data);
//            result.add(sign(signData, pixels));
//        }
//
//        return result;
//    }
//
//    private BarcodeSign sign(List<Integer> data, int pixels) {
//        var items = new ArrayList<BarcodeSignItem>();
//        for (int i = 0; i < data.size(); i++) {
//            items.add(signItem(data.get(i), pixels));
//        }
//        return ImmutableBarcodeSign.builder()
//                .items(items)
//                .build();
//    }


//    private BarcodeSignItem signItem(int item, int size) {
//        int[][] result = new int[size][size];
//
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                result[i][j] = item;
//            }
//        }
//
//        return ImmutableBarcodeSignItem.builder()
//                .data(result)
//                .build();
//    }
//
//    private List<Integer> getSignData(int index, int signSize, List<Integer> data) {
//        return data.subList(index * signSize, (index + 1) * signSize);
//    }
//
//    private int getSignsCount(int signSize, List<Integer> data) {
//        return data.size() / signSize;
//    }

//    private int[][] createBarcodeImageData(List<BarcodeSign> signs, int signsInLine, int signPixels) {
//        int signLines = signs.size() / signsInLine + 1;
//        var image = new int[signsInLine * signPixels][signLines * signPixels];
//        for (int signIndex = 0; signIndex < signs.size(); signIndex++) {
//            var sign = signs.get(signIndex);
//            int row = signIndex / signsInLine;
//            int column = signIndex % signsInLine;
//            printSignItem(image, row, column, signPixels, sign.data());
//        }
//    }

//    private void printSignItem(int[][] bitmap, int row, int column, int signPixels, int[][] signItemData) {
//        for (int i = 0; i < signPixels; i++) {
//            for (int j = 0; j < signPixels; j++) {
//                bitmap[row * signPixels + i][column * signPixels + j] = signItemData[i][j];
//            }
//        }
//    }

//    private List<List<BarcodeSign>> splitToLines(List<BarcodeSign> signs, int lineSize) {
//        int linesCount = signs.size() % lineSize == 0 ? signs.size() / lineSize : signs.size() / lineSize + 1;
//        var result = new ArrayList<List<BarcodeSign>>();
//        for (int i = 0; i < linesCount; i++) {
//            if (i % lineSize == 0) {
//                result.add(new ArrayList<>());
//            }
//            result.get(i).add(line(i, signs, lineSize)
//        }
//        return result;
//    }

//    private List<BarcodeSign> line(int lineIndex, List<BarcodeSign> signs, int lineSize) {
//        return signs.subList(lineIndex * lineSize, (lineIndex + 1) * lineSize);
//    }

//    @Value.Immutable
//    interface BarcodeSign {
//        List<BarcodeSignItem> items();
//    }
//
//    @Value.Immutable
//    interface BarcodeSignItem {
//        int[][] data();
//    }
}
