package com.trident.barcode.reedsolomon;

import com.trident.barcode.codec.BarcodeIntermediateCodeStrategy;
import com.trident.barcode.model.Barcode;
import com.trident.barcode.model.BarcodeDictionary;
import com.trident.barcode.padding.NearestSquarePaddingStrategy;
import com.trident.barcode.transform.CharsetSwitchAppender;
import com.trident.barcode.transform.CodeSizeAppender;
import com.trident.barcode.transform.PaddingAppender;
import com.trident.math.reedsolomon.ReedSolomonCode;

import java.util.Optional;

public class SBRSPIntermediateCodeStrategy implements BarcodeIntermediateCodeStrategy {

    private final BarcodeDictionary barcodeDictionary;
    private final ReedSolomonCode reedSolomonCode;

    public SBRSPIntermediateCodeStrategy(BarcodeDictionary barcodeDictionary,
                                         ReedSolomonCode reedSolomonCode) {
        this.barcodeDictionary = barcodeDictionary;
        this.reedSolomonCode = reedSolomonCode;
    }

    @Override
    public Barcode buildIntermediateCode(Barcode code) {
        return Optional.of(code)
                .map(barcode -> new CharsetSwitchAppender().transform(barcode))
                .map(barcode -> new ReedSolomonErrorCorrectingCodeGenerator(reedSolomonCode).transform(barcode))
                .map(barcode -> new CodeSizeAppender(barcodeDictionary).transform(barcode))
                .map(barcode -> new PaddingAppender(new NearestSquarePaddingStrategy()).transform(barcode))
                .orElseThrow();
    }
}