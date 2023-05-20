package com.trident.barcode.image;

public class Main {
    public static void main(String[] args) {
        var message = "52AM'Ю";
        var path = "/Users/andriidy/Downloads/barcode.bmp";

        var generator = BarcodeImageGenerators.BASE_41_SBRSP;
        var image = generator.generateBarcodeImage(message);
        var imageWriter = new BufferedImageFileWriter(path);

        imageWriter.write(image);
    }
}