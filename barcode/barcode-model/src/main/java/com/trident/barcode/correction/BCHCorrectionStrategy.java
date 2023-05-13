package com.trident.barcode.correction;

import com.trident.barcode.model.Code;
import com.trident.barcode.model.ImmutableCode;
import com.trident.math.bch.BCHCode;
import com.trident.math.field.GFElement;
import com.trident.math.field.GFPMElement;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BCHCorrectionStrategy<Symbol extends GFElement<Symbol>, Locator extends GFPMElement<Locator>> implements CorrectionStrategy {
    private final BCHCode<Symbol, Locator> bchCode;

    public BCHCorrectionStrategy(BCHCode<Symbol, Locator> bchCode) {
        this.bchCode = bchCode;
    }

    @Override
    public Correction correct(Code code) {
        var correctionResult = bchCode.syndrome(toFieldMatrix(code)).getCorrectionResult();
        switch (correctionResult.getCorrectionStatus()) {
            case NO_ERROR:
                return ImmutableCorrection.of(CorrectionStatus.NO_ERROR, Optional.of(code));
            case CORRECTED_1:
            case CORRECTED_2:
                return ImmutableCorrection.of(CorrectionStatus.ERROR_CORRECTED,
                        correctionResult.correctedMessage().map(this::toCode));
            default:
                return ImmutableCorrection.of(CorrectionStatus.ERROR_DETECTED, Optional.empty());
        }
    }

    private FieldMatrix<Symbol> toFieldMatrix(Code code) {
        return FieldMatrixUtil.matrixRow(code.data().stream()
                .map(it -> bchCode.symbolField().getOfValue(it))
                .collect(Collectors.toList()));
    }

    private Code toCode(FieldMatrix<Symbol> code) {
        return ImmutableCode.of(Stream.of(code.getData()[0])
                .map(it -> (int) it.digitalRepresentation())
                .collect(Collectors.toList()));
    }
}
