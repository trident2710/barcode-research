package com.trident.math.reedsolomon;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.trident.math.PolyUtil;
import com.trident.math.field.GFP;
import com.trident.math.field.GFPElement;
import com.trident.math.reedsolomon.CorrectionResult.CorrectionStatus;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.ArrayList;
import java.util.List;

import static com.trident.math.field.GFPSimplePrimitiveElementCalculator.primitiveElement;

public class ReedSolomonCode {

    private final FieldMatrix<GFPElement> generatorPolynomial;

    private final GFP generatorField;
    private final GFPElement generatorFieldPrimitiveElement;
    private final int controlDigitsCount;

    public ReedSolomonCode(FieldMatrix<GFPElement> generatorPolynomial) {
        Preconditions.checkArgument(generatorPolynomial.getRowDimension() == 1);
        Preconditions.checkArgument(generatorPolynomial.getColumnDimension() > 1);
        this.generatorPolynomial = generatorPolynomial;
        this.generatorField = (GFP) generatorPolynomial.getField();
        this.generatorFieldPrimitiveElement = primitiveElement(generatorField);
        this.controlDigitsCount = generatorPolynomial.getColumnDimension() - 1;
    }

    public FieldMatrix<GFPElement> encode(FieldMatrix<GFPElement> message) {
        return PolyUtil.multiplyPolynomials(message, generatorPolynomial);
    }

    public CorrectionResult decode(FieldMatrix<GFPElement> encoded) {
        var erasureLocators = findErasureLocators(encoded);
        if (erasureLocators.size() > controlDigitsCount) {
            return ImmutableCorrectionResult.of(CorrectionStatus.TOO_MUCH_ERASURE);
        }
        return null;
    }

    @VisibleForTesting
    List<GFPElement> findErasureLocators(FieldMatrix<GFPElement> message) {
        var erasureLocators = new ArrayList<GFPElement>();
        for (int i = 0; i < message.getColumnDimension(); i++) {
            var elem = message.getEntry(0, i);
            if (elem.equals(generatorField.getZero())) {
                erasureLocators.add(generatorField.pow(generatorFieldPrimitiveElement, i));
            }
        }
        return erasureLocators;
    }
}
