package com.trident.barcode.reedsolomon;

import com.trident.barcode.codec.BarcodeIntermediateCodeStrategy;
import com.trident.barcode.model.Barcode;
import com.trident.barcode.padding.NearestSquarePaddingStrategy;
import com.trident.barcode.padding.PaddingStrategy;
import com.trident.barcode.transform.CharsetSwitchAppender;
import com.trident.barcode.transform.PaddingAppender;
import com.trident.math.reedsolomon.ReedSolomonCode;

import java.util.Optional;

public class ReedSolomonIntermediateCodeStrategy implements BarcodeIntermediateCodeStrategy {

    private final ReedSolomonCode reedSolomonCode;

    public ReedSolomonIntermediateCodeStrategy(ReedSolomonCode reedSolomonCode) {
        this.reedSolomonCode = reedSolomonCode;
    }

    @Override
    public Barcode buildIntermediateCode(Barcode code) {
        return Optional.of(code)
                .map(barcode -> new CharsetSwitchAppender().transform(barcode))
                .map(barcode -> new PaddingAppender(paddingStrategy()).transform(barcode))
                .map(barcode -> new ReedSolomonErrorCorrectingCodeGenerator(reedSolomonCode).transform(barcode))
                .orElseThrow();
    }

    private PaddingStrategy paddingStrategy() {
        return length -> new NearestSquarePaddingStrategy().getPaddingCount(length + reedSolomonCode.getControlDigitsCount());
    }
}
