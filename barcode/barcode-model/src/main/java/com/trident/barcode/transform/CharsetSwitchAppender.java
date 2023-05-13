package com.trident.barcode.transform;

import com.trident.barcode.model.Barcode;
import com.trident.barcode.model.BarcodeSign;
import com.trident.barcode.model.ImmutableBarcode;

import java.util.ArrayList;
import java.util.List;

public class CharsetSwitchAppender implements BarcodeTransformer {

    public CharsetSwitchAppender() {
    }

    @Override
    public Barcode transform(Barcode barcode) {
        return appendCharsetSwitches(barcode);
    }

    private Barcode appendCharsetSwitches(Barcode barcode) {
        List<BarcodeSign> signsWithSwitchers = new ArrayList<>();
        var currentCharset = barcode.dictionary().defaultCharset();
        for (BarcodeSign sign : barcode.signs()) {
            if (sign.charset() != currentCharset) {
                var switcher = barcode.dictionary().switcher(currentCharset, sign.charset())
                        .orElseThrow(() -> new IllegalArgumentException("Unable to find switcher"));
                signsWithSwitchers.add(switcher);
                currentCharset = sign.charset();
            }
            signsWithSwitchers.add(sign);
        }
        return ImmutableBarcode.copyOf(barcode).withSigns(signsWithSwitchers);
    }
}
