package com.trident.barcode.reedsolomon;

import com.trident.barcode.codec.BarcodeEncoder;
import com.trident.barcode.BarcodeRawExtractor;
import com.trident.barcode.model.BarcodeDictionary;
import com.trident.barcode.model.BarcodeSign;
import com.trident.barcode.model.Code;
import com.trident.barcode.model.ImmutableCode;
import com.trident.barcode.padding.NearestSquarePaddingStrategy;
import com.trident.barcode.padding.PaddingStrategy;
import com.trident.barcode.transform.CharsetSwitchAppender;
import com.trident.barcode.transform.PaddingAppender;
import com.trident.math.reedsolomon.ReedSolomonCode;

import java.util.stream.Stream;

public class ReedSolomonEncoder implements BarcodeEncoder {

    private final BarcodeDictionary barcodeDictionary;
    private final ReedSolomonCode reedSolomonCode;

    public ReedSolomonEncoder(BarcodeDictionary barcodeDictionary,
                              ReedSolomonCode reedSolomonCode) {
        this.barcodeDictionary = barcodeDictionary;
        this.reedSolomonCode = reedSolomonCode;
    }

    @Override
    public Code encode(String message) {
        PaddingStrategy paddingStrategy = length ->
                new NearestSquarePaddingStrategy().getPaddingCount(length + reedSolomonCode.getControlDigitsCount());
        return Stream.of(new BarcodeRawExtractor(barcodeDictionary).extract(message))
                .map(barcode -> new CharsetSwitchAppender().transform(barcode))
                .map(barcode -> new PaddingAppender(paddingStrategy).transform(barcode))
                .map(barcode -> new ReedSolomonErrorCorrectingCodeGenerator(reedSolomonCode).transform(barcode))
                .flatMap(barcode -> barcode.signs().stream()
                        .map(BarcodeSign::codeRepresentation))
                .reduce((first, second) -> ImmutableCode.builder().addAllData(first.data()).addAllData(second.data()).build())
                .orElseThrow();
    }
}
