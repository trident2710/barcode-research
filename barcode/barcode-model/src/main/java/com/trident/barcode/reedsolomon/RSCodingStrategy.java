package com.trident.barcode.reedsolomon;

import com.trident.barcode.codec.CodingStrategy;
import com.trident.barcode.model.Barcode;
import com.trident.barcode.padding.NearestSquarePaddingStrategy;
import com.trident.barcode.padding.PaddingStrategy;
import com.trident.barcode.transform.CharsetSwitchAppender;
import com.trident.barcode.transform.PaddingAppender;
import com.trident.math.reedsolomon.ReedSolomonCode;

import java.util.Optional;

public class RSCodingStrategy implements CodingStrategy {

    private final ReedSolomonCode reedSolomonCode;

    public RSCodingStrategy(ReedSolomonCode reedSolomonCode) {
        this.reedSolomonCode = reedSolomonCode;
    }

    @Override
    public Barcode apply(Barcode code) {
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
