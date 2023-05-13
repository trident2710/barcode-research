package com.trident.barcode;

import com.trident.barcode.model.BarcodeCharsetType;
import com.trident.barcode.model.BarcodeDictionary;
import com.trident.barcode.model.BarcodeSign;
import com.trident.barcode.padding.PaddingStrategy;

import java.util.ArrayList;
import java.util.List;

public class DefaultIntermediateCodeGenerator implements IntermediateCodeGenerator {

    private final BarcodeDictionary barcodeDictionary;
    private final PaddingStrategy paddingStrategy;

    public DefaultIntermediateCodeGenerator(BarcodeDictionary barcodeDictionary,
                                            PaddingStrategy paddingStrategy) {
        this.barcodeDictionary = barcodeDictionary;
        this.paddingStrategy = paddingStrategy;
    }

    @Override
    public List<BarcodeSign> buildIntermediateCode(String message) {
        List<BarcodeSign> intermediateCode = new ArrayList<>();
        var currentCharset = barcodeDictionary.defaultCharset();
        for (int chr : message.chars().toArray()) {
            var sign = String.valueOf((char) chr);
            var signInCurrentCharset = barcodeDictionary.findSignInCharset(currentCharset, sign);
            if (signInCurrentCharset.isEmpty()) {
                var signInDictionary = barcodeDictionary.findSign(sign)
                        .orElseThrow(() -> new IllegalArgumentException("Character " + sign + " is not supported"));
                var curr = currentCharset;
                var switcher = barcodeDictionary.switcher(currentCharset, signInDictionary.charset())
                        .orElseThrow(() -> new IllegalArgumentException("Switcher from " + curr +
                                " to " + signInDictionary.charset() + " is not supported"));
                currentCharset = signInDictionary.charset();
                intermediateCode.add(switcher);
                intermediateCode.add(signInDictionary);
            } else {
                intermediateCode.add(signInCurrentCharset.get());
            }
        }

        addPadding(intermediateCode, currentCharset);

        return intermediateCode;
    }

    private void addPadding(List<BarcodeSign> intermediateCode, BarcodeCharsetType currentCharset) {
        int paddingCount = paddingStrategy.getPaddingCount(intermediateCode.size());

        for (int i = 0; i < paddingCount; i++) {
            intermediateCode.add(barcodeDictionary.padding(currentCharset).orElseThrow());
        }
    }
}
