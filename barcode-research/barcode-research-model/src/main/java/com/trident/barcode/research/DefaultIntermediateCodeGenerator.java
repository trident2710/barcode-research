package com.trident.barcode.research;

import com.google.common.annotations.VisibleForTesting;
import com.trident.barcode.research.model.BarcodeDictionary;
import com.trident.barcode.research.model.BarcodeSign;

import java.util.ArrayList;
import java.util.List;

public class DefaultIntermediateCodeGenerator implements IntermediateCodeGenerator {

    private final BarcodeDictionary barcodeDictionary;

    public DefaultIntermediateCodeGenerator(BarcodeDictionary barcodeDictionary) {
        this.barcodeDictionary = barcodeDictionary;
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
        return intermediateCode;
    }
}
