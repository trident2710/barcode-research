package com.trident.barcode.codec;

import com.trident.barcode.model.Barcode;
import com.trident.barcode.padding.NearestSquarePaddingStrategy;
import com.trident.barcode.transform.CharsetSwitchAppender;
import com.trident.barcode.transform.PaddingAppender;

import java.util.Optional;

public class SimpleIntermediateCodeStrategy implements BarcodeIntermediateCodeStrategy {

    @Override
    public Barcode buildIntermediateCode(Barcode code) {
        return Optional.of(code)
                .map(barcode -> new CharsetSwitchAppender().transform(barcode))
                .map(barcode -> new PaddingAppender(new NearestSquarePaddingStrategy()).transform(barcode))
                .orElseThrow();
    }
}
