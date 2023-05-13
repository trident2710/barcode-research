package com.trident.barcode;

import com.google.common.base.Preconditions;
import com.trident.barcode.model.Barcode;
import com.trident.barcode.model.BarcodeDictionary;
import com.trident.barcode.model.ImmutableBarcode;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class BarcodeRawExtractor {

    private final BarcodeDictionary dictionary;

    public BarcodeRawExtractor(BarcodeDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Barcode extract(String message) {
        Preconditions.checkArgument(!message.isEmpty());
        var signs = Arrays.stream(message.chars().toArray())
                .boxed()
                .map(chr -> String.valueOf((char) chr.intValue()))
                .map(dictionary::findSign)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        return ImmutableBarcode.of(dictionary, signs);
    }
}
