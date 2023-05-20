package com.trident.barcode.codec;

import com.trident.barcode.BarcodeRawExtractor;
import com.trident.barcode.model.BarcodeDictionary;
import com.trident.barcode.model.BarcodeSign;
import com.trident.barcode.model.Code;
import com.trident.barcode.model.ImmutableCode;
import com.trident.barcode.padding.NearestSquarePaddingStrategy;
import com.trident.barcode.padding.PaddingStrategy;
import com.trident.barcode.reedsolomon.ReedSolomonErrorCorrectingCodeGenerator;
import com.trident.barcode.transform.CharsetSwitchAppender;
import com.trident.barcode.transform.PaddingAppender;

import java.util.stream.Stream;

public class SimpleEncoderNew implements BarcodeEncoder {

    private final BarcodeDictionary barcodeDictionary;

    public SimpleEncoderNew(BarcodeDictionary barcodeDictionary) {
        this.barcodeDictionary = barcodeDictionary;
    }

    @Override
    public Code encode(String message) {
        return Stream.of(new BarcodeRawExtractor(barcodeDictionary).extract(message))
                .map(barcode -> new CharsetSwitchAppender().transform(barcode))
                .map(barcode -> new PaddingAppender(new NearestSquarePaddingStrategy()).transform(barcode))
                .flatMap(barcode -> barcode.signs().stream()
                        .map(BarcodeSign::codeRepresentation))
                .reduce((first, second) -> ImmutableCode.builder()
                        .addAllData(first.data())
                        .addAllData(second.data())
                        .build())
                .orElseThrow();
    }
}
