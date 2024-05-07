package com.trident.math.reedsolomon;

import com.google.common.base.Preconditions;
import com.trident.math.PolyUtil;
import com.trident.math.field.GFP;
import com.trident.math.field.GFPElement;
import com.trident.math.reedsolomon.CorrectionResult.CorrectionStatus;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.*;

import static com.trident.math.field.GFPUtil.primitiveElement;
import static com.trident.math.matrix.FieldMatrixUtil.isZero;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRowOfValue;
import static com.trident.math.reedsolomon.ReedSolomonUtil.*;

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

    public FieldMatrix<GFPElement> decode(FieldMatrix<GFPElement> encoded) {
        var divisionResult = PolyUtil.dividePolynomialsWithRest(encoded, generatorPolynomial);
        Preconditions.checkArgument(isZero(divisionResult.getSecond()));
        return divisionResult.getFirst();
    }

    public CorrectionResult correct(FieldMatrix<GFPElement> message, List<Integer> erasurePositions) {
        var erasureLocators = findErasureLocators(generatorField, generatorFieldPrimitiveElement, erasurePositions);
        int erasureLocatorsCount = erasureLocators.size();
        if (erasureLocatorsCount > controlDigitsCount) {
            return ImmutableCorrectionResult.of(CorrectionStatus.TOO_MUCH_ERASURE, message, Optional.empty());
        }

        var erasureLocatorsPolynomial = calculateErasureLocatorsPolynomial(generatorField, erasureLocators);

        int errorsMaxCount = (controlDigitsCount - erasureLocatorsCount) / 2;

        var syndrome = calculateSyndrome(controlDigitsCount, generatorField, generatorFieldPrimitiveElement, message);

        boolean isZeroSyndrome = syndrome.stream()
                .allMatch(it -> it.equals(generatorField.getZero()));

        if (isZeroSyndrome) {
            return ImmutableCorrectionResult.of(CorrectionStatus.NO_ERROR, message, Optional.empty());
        }

        var syndromePolynomial = createSyndromePolynomial(generatorField, syndrome);

        var modifiedSyndromePolynomial = calculateModifiedSyndromePolynomial(controlDigitsCount, generatorField, erasureLocatorsPolynomial, syndromePolynomial);

        var errorSyndrome = calculateErrorSyndrome(modifiedSyndromePolynomial, errorsMaxCount);

        var errorLocatorsPolyOptional = calculateErrorLocatorsPolynomial(generatorField, errorSyndrome, errorsMaxCount);

        if (errorLocatorsPolyOptional.isEmpty()) {
            return ImmutableCorrectionResult.of(CorrectionStatus.MORE_THAN_XI_ERRORS, message, Optional.empty());
        }

        var errorLocatorsPoly = errorLocatorsPolyOptional.get();

        var errorLocators = calculateErrorLocators(errorLocatorsPoly);

        var mutationValuesPoly = calculateMutationValuesPoly(controlDigitsCount, generatorField, errorLocatorsPoly, modifiedSyndromePolynomial);

        var mutationLocators = mutationLocators(erasureLocators, errorLocators);

        var mutationValues = calculateMutationValues(generatorField, mutationValuesPoly, mutationLocators);

        var correctionValues = calculateCorrectionValues(mutationValues, mutationLocators);

        var correction = matrixRowOfValue(generatorField.getZero(), message.getColumnDimension());

        correctionValues.forEach(it -> correction.setEntry(0, it.getKey(), it.getValue()));

        return ImmutableCorrectionResult.of(CorrectionStatus.ERROR_CORRECTED, message, Optional.of(correction));
    }

    public GFP getGeneratorField() {
        return generatorField;
    }

    public int getControlDigitsCount() {
        return controlDigitsCount;
    }

}
