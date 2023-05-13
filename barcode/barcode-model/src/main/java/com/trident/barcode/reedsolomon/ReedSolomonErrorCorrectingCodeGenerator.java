package com.trident.barcode.reedsolomon;

import com.trident.barcode.model.Barcode;
import com.trident.barcode.model.BarcodeDictionary;
import com.trident.barcode.model.BarcodeSign;
import com.trident.barcode.model.ImmutableBarcode;
import com.trident.barcode.transform.BarcodeTransformer;
import com.trident.math.field.GFPElement;
import com.trident.math.matrix.FieldMatrixUtil;
import com.trident.math.reedsolomon.ReedSolomonCode;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReedSolomonErrorCorrectingCodeGenerator implements BarcodeTransformer {

    private final ReedSolomonCode reedSolomonCode;

    public ReedSolomonErrorCorrectingCodeGenerator(ReedSolomonCode reedSolomonCode) {
        this.reedSolomonCode = reedSolomonCode;
    }

    @Override
    public Barcode transform(Barcode barcode) {
        return Optional.of(barcode)
                .map(Barcode::signs)
                .map(this::toDigitalRepresentation)
                .map(this::toFieldMatrix)
                .map(reedSolomonCode::encode)
                .map(encoded -> toBarcodeSigns(barcode.dictionary(), encoded))
                .map(signs -> ImmutableBarcode.copyOf(barcode).withSigns(signs))
                .orElseThrow();
    }

    private List<Integer> toDigitalRepresentation(List<BarcodeSign> signs) {
        return signs.stream()
                .map(BarcodeSign::digitalRepresentation)
                .collect(Collectors.toList());
    }

    private FieldMatrix<GFPElement> toFieldMatrix(List<Integer> digitalRepresentation) {
        return FieldMatrixUtil.matrixRow(digitalRepresentation.stream()
                .map(it -> reedSolomonCode.getGeneratorField().getOfValue(it))
                .collect(Collectors.toList()));
    }

    private List<BarcodeSign> toBarcodeSigns(BarcodeDictionary dictionary, FieldMatrix<GFPElement> fieldMatrix) {
        return Arrays.stream(fieldMatrix.getRow(0))
                .map(GFPElement::digitalRepresentation)
                .map(it -> dictionary.findSign(it.intValue()))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}
