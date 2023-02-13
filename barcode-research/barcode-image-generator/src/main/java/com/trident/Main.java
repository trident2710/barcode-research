package com.trident;

public class Main {
    public static void main(String[] args) {
        var generator = new BarcodeImageGenerator();

        generator.generateBarcodeImage("Hello, World!", "/Users/andriidy/barcode.bmp");
    }
}