package com.trident.barcode.transform;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.trident.barcode.model.Barcode;
import com.trident.barcode.model.BarcodeDictionary;
import com.trident.barcode.model.BarcodeSign;
import com.trident.barcode.model.ImmutableBarcode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CodeSizeAppender implements BarcodeTransformer {

    private final BarcodeDictionary barcodeDictionary;
    private final int codeSizeSignCount;

    public CodeSizeAppender(BarcodeDictionary barcodeDictionary) {
        this.barcodeDictionary = barcodeDictionary;
        this.codeSizeSignCount = calculateCodeSizeSignCount(barcodeDictionary);
    }

    @Override
    public Barcode transform(Barcode barcode) {
        Preconditions.checkArgument(barcode.signs().size() < Math.pow(10, codeSizeSignCount));

        var signsWithCodeSizeFirst = new ArrayList<BarcodeSign>();
        signsWithCodeSizeFirst.addAll(encodeCodeSize(barcode.signs().size()));
        signsWithCodeSizeFirst.addAll(barcode.signs());
        return ImmutableBarcode.copyOf(barcode)
                .withSigns(signsWithCodeSizeFirst);
    }

    @VisibleForTesting
    static int calculateCodeSizeSignCount(BarcodeDictionary barcodeDictionary) {
        int elementsCount = barcodeDictionary.charset(barcodeDictionary.defaultCharset()).signs().size() - 1;
        return BigDecimal.valueOf(Math.log10(elementsCount)).setScale(0, RoundingMode.UP).intValue();
    }

    private List<BarcodeSign> encodeCodeSize(int codeSize) {
        var codeSizeDigitSigns = new ArrayList<BarcodeSign>(codeSizeSignCount);
        int code = codeSize;
        while (code > 0 || codeSizeDigitSigns.size() < codeSizeSignCount) {
            int digit = code % 10;
            codeSizeDigitSigns.add(barcodeDictionary.findSign(String.valueOf(digit)).orElseThrow());
            code /= 10;
        }
        return Lists.reverse(codeSizeDigitSigns);
    }
}
