package com.trident.barcode.image;

public class Main {
    public static void main(String[] args) {
        var generator = BarcodeImageGenerators.base41Rs("/Users/andriidy/Downloads/barcode.bmp");

        String message = "52AM'Ю";
        generator.generateBarcodeImage(message);
    }
}