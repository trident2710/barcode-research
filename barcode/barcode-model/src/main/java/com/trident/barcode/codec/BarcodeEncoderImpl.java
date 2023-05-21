package com.trident.barcode.codec;

import com.trident.barcode.BarcodeRawExtractor;
import com.trident.barcode.model.*;

import java.util.stream.Stream;

public class BarcodeEncoderImpl implements BarcodeEncoder {

    private final BarcodeDictionary barcodeDictionary;
    private final BarcodeIntermediateCodeStrategy intermediateCodeStrategy;

    public BarcodeEncoderImpl(BarcodeDictionary barcodeDictionary,
                              BarcodeIntermediateCodeStrategy intermediateCodeStrategy) {
        this.barcodeDictionary = barcodeDictionary;
        this.intermediateCodeStrategy = intermediateCodeStrategy;
    }

    @Override
    public Code encode(String message) {
        return Stream.of(message)
                .map(this::extractBarcode)
                .map(intermediateCodeStrategy::buildIntermediateCode)
                .flatMap(BarcodeEncoderImpl::toCode)
                .reduce(BarcodeEncoderImpl::merge)
                .orElseThrow();
    }

    private Barcode extractBarcode(String message) {
        return new BarcodeRawExtractor(barcodeDictionary).extract(message);
    }

    private static Stream<Code> toCode(Barcode barcode) {
        return barcode.signs().stream()
                .map(BarcodeSign::codeRepresentation);
    }

    private static Code merge(Code first, Code second) {
        return ImmutableCode.builder()
                .addAllData(first.data())
                .addAllData(second.data())
                .build();
    }
}
