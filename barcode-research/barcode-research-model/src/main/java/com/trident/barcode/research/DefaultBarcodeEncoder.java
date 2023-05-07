package com.trident.barcode.research;

import com.trident.barcode.research.model.Code;
import com.trident.barcode.research.model.ImmutableCode;

import java.util.stream.Collectors;

public class DefaultBarcodeEncoder implements BarcodeEncoder {
    private final IntermediateCodeGenerator intermediateCodeGenerator;

    public DefaultBarcodeEncoder(IntermediateCodeGenerator intermediateCodeGenerator) {
        this.intermediateCodeGenerator = intermediateCodeGenerator;
    }

    @Override
    public Code encode(String message) {
        return ImmutableCode.of(intermediateCodeGenerator.buildIntermediateCode(message).stream()
                .flatMap(barcodeSign -> barcodeSign.codeRepresentation().data().stream())
                .collect(Collectors.toList()));
    }
}
