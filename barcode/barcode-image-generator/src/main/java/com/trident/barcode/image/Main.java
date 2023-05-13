package com.trident.barcode.image;

public class Main {
    public static void main(String[] args) {
        var generator = BarcodeImageGeneratorFactory.base59Default("/Users/andriidy/Downloads/barcode.bmp");

        String message = "Hello, Світ!:)";
        generator.generateBarcodeImage(message);
    }
}