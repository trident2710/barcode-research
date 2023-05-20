package com.trident.barcode.codec;

import com.trident.barcode.model.Code;
import com.trident.barcode.model.ImmutableCode;
import com.trident.barcode.transform.IntermediateCodeGenerator;

import java.util.stream.Collectors;

public class SignCorrectingBarcodeEncoder implements BarcodeEncoder {
    private final IntermediateCodeGenerator intermediateCodeGenerator;

    public SignCorrectingBarcodeEncoder(IntermediateCodeGenerator intermediateCodeGenerator) {
        this.intermediateCodeGenerator = intermediateCodeGenerator;
    }

    @Override
    public Code encode(String message) {
        return ImmutableCode.of(intermediateCodeGenerator.buildIntermediateCode(message).stream()
                .flatMap(barcodeSign -> barcodeSign.codeRepresentation().data().stream())
                .collect(Collectors.toList()));
    }
}
